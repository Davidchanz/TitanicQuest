package com.iteratia.titanicquest.repository.impl;

import com.iteratia.titanicquest.dto.filter.Filter;
import com.iteratia.titanicquest.dto.filter.Filters;
import com.iteratia.titanicquest.dto.search.SearchGuessItem;
import com.iteratia.titanicquest.exception.notFound.EntityNotFoundException;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.repository.SearchRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchRepositoryDB implements SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${search.guess.max}")
    int maxSearchGuess;

    // this map contains properties for search by different entities
    private static final Map<String, EntityProperty> urlEntityPropertyMap = new HashMap<>();

    static {
        urlEntityPropertyMap.put("passengers", new EntityProperty(Passenger.class, "", List.of("name")));
    }

    @Getter
    @ToString
    private static class EntityProperty{
        Class<?> entity; // entity for search
        String entityGraphName; // entity graph name, if empty no one will be used
        List<String> columns; // entity columns for search must have at least one column
        List<String> allColumns; // all entity columns
        Field[] fields;

        public EntityProperty(Class<?> entity, String entityGraphName, List<String> columns){
            this.entity = entity;
            this.entityGraphName = entityGraphName;
            this.columns = columns;
            this.fields = entity.getDeclaredFields();
            this.allColumns = Arrays.stream(this.fields)
                    .map(Field::getName)
                    .toList();
        }
    }

    /**
     * Get Entity Property for Url
     * @param url url for entity property
     * */
    private EntityProperty getEntity(String url){

        EntityProperty entityProperty = urlEntityPropertyMap.get(url);
        if(entityProperty == null){
            throw new EntityNotFoundException(url);
        }
        return entityProperty;
    }

    /**
     * Get Search Guess
     * @param url url for urlEntityPropertyMap to get EntityProperty for search request
     * @param searchInput search request
     * */
    @Override
    public List<SearchGuessItem> getSearchGuess(String url, String searchInput) {
        EntityProperty entityProperty = this.getEntity(url); // get Entity Property
        StringBuilder query = this.getQueryForSearchGuess(searchInput, entityProperty); // get query for search

        // exec query and return result
        return this.entityManager.createQuery(
                        query.toString(),
                        SearchGuessItem.class)
                .setMaxResults(this.maxSearchGuess)
                .getResultList();
    }

    /**
     * Get Search Paged
     * @param url url for urlEntityPropertyMap to get EntityProperty for search request
     * @param searchRequest search request
     * */
    @Override
    public Long getSearchedPagesCount(String searchRequest, String url) {
        EntityProperty entityProperty = this.getEntity(url); // get Entity Property
        StringBuilder query = this.getQueryForSearchPaged(searchRequest, entityProperty); // get query for search

        Long count;
        String entityGraphName = entityProperty.getEntityGraphName(); // get entity graph name
        if(!entityGraphName.isEmpty()) { // if entity graph exist use it otherwise not
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(entityGraphName);

            count = entityManager.createQuery(
                            query.toString(),
                            Long.class)
                    .setHint("jakarta.persistence.fetchgraph", entityGraph)
                    .getSingleResult();
        }else {
            count = entityManager.createQuery(
                            query.toString(),
                            Long.class)
                    .getSingleResult();
        }

        return count;
    }

    /**
     * Get Search Paged
     * @param url url for urlEntityPropertyMap to get EntityProperty for search request
     * @param searchRequest search request
     * @param entity search result class
     * @param pageable page and sorting for search
     * */
    @Override
    public <T> List<T> search(String searchRequest, String url, Class<T> entity, Pageable pageable, Filters filters) {
        EntityProperty entityProperty = this.getEntity(url); // get Entity Property

        StringBuilder query = this.getQuery(searchRequest, entityProperty); // get query for search

        this.addFilters(query, entityProperty, filters);

        //parse pageable to get sort by and order
        String sort = pageable.getSort().toString();
        String by = sort.split(": ")[0]; // sort by
        String dir = sort.split(": ")[1]; // sort order
        if(this.isValidSort(by, dir, entityProperty)) { // check sorting valid
            query.append(" ORDER BY s.").append(by).append(" ").append(dir); // if sorting is valid add sorting in query
        } // otherwise ignore sorting

        // get page params
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        List<T> resultList;
        String entityGraphName = entityProperty.getEntityGraphName();  // get entity graph name
        if(!entityGraphName.isEmpty()) { // if entity graph exist use it otherwise not
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(entityGraphName);

            resultList = entityManager.createQuery(
                            query.toString(),
                            entity)
                    .setHint("jakarta.persistence.fetchgraph", entityGraph)
                    .setFirstResult((pageNumber) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }else {
            resultList = entityManager.createQuery(
                            query.toString(),
                            entity)
                    .setFirstResult((pageNumber) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }

        return resultList;
    }

    /**
     * Add Filtering to Query <br>
     * if all filters is valid - add filtering to query otherwise
     * if at least one filter is not valid <strong>ignore</strong> filtering
     * @param query query for filtering adding
     * @param entityProperty entity for filtering
     * @param filters for adding
     * */
    private void addFilters(StringBuilder query, EntityProperty entityProperty, Filters filters) {
        StringBuilder queryFilter =  new StringBuilder();
        if(!query.toString().contains("WHERE")){
            queryFilter.append(" WHERE "); // if query not contains condition WHERE add it
        } else {
            queryFilter.append(" AND "); //otherwise if query contains condition WHERE add AND
        }
        for (Filter filter : filters.getFilters()) { // for all filters
            if(this.isValidFilter(filter, entityProperty)){ //check if filter is valid
                queryFilter.append(filter.getField()) //add filter
                        .append(filter.getCondition())
                        .append(filter.getValue())
                        .append(" AND ");
            } else
                return; //if at least one filter is invalid ignore all filters
        }
        int i = queryFilter.lastIndexOf(" AND ");
        queryFilter.delete(i, i+5); // remove last AND

        query.append(queryFilter); // add filters in query
    }

    /**
     * Check Filter Valid <br>
     * @return <strong>true</strong> if filter has valid condition,
     * and has same type and field name with one of entityProperty fields
     * otherwise return <strong>false</strong>
     * @param filter filter for check
     * @param entityProperty entity for filtering
     * */
    private boolean isValidFilter(Filter filter, EntityProperty entityProperty) {
        return Arrays.stream(entityProperty.getFields())
                .anyMatch(field ->
                        (field.getName().equals(filter.getField()))
                                && (filter.getValueType().equals(field.getType()))
                                && (filter.isConditionValid())
        );
    }

    /**
     * Check if Sort is valid <br>
     * Sort is valid if sort By exist in entityProperty columns or equal id and sort Order is ASC or DESC
     * @param by sort by
     * @param dir sort order
     * @param entityProperty entity property for sorting
     * */
    private boolean isValidSort(String by, String dir, EntityProperty entityProperty){
        return (entityProperty.getAllColumns().contains(by)) &&
                (dir.equalsIgnoreCase("ASC") || dir.equalsIgnoreCase("DESC"));
    }

    /**
     * Query Builder for Search <br>
     * SELECT * FROM Entity
     * @param searchInput  search request
     * @param entityProperty entity property for search request
     * */
    private StringBuilder getQuery(String searchInput, EntityProperty entityProperty){
        List<String> columns = entityProperty.getColumns(); // get columns for search
        StringBuilder query = new StringBuilder();
        query.append("SELECT s FROM ")
                .append(entityProperty.getEntity().getSimpleName()) // select from entity
                .append(" s");
        this.addConditions(query, columns, searchInput); // add conditions to query

        return query;
    }

    /**
     * Query Builder for Search Paged <br>
     * SELECT COUNT(*) FROM Entity
     * @param searchInput  search request
     * @param entityProperty entity property for search request
     * */
    private StringBuilder getQueryForSearchPaged(String searchInput, EntityProperty entityProperty) {
        List<String> columns = entityProperty.getColumns(); // get columns for search

        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*)") // select rows count
                .append(" FROM ")
                .append(entityProperty.getEntity().getSimpleName()) // from entity
                .append(" s");
        this.addConditions(query, columns, searchInput); // add conditions to query

        return query;
    }

    /**
     * Query Builder for Search Guess <br>
     * SELECT SearchGuessItem FROM Entity
     * @param searchInput  search request
     * @param entityProperty entity property for search request
     * */
    private StringBuilder getQueryForSearchGuess(String searchInput, EntityProperty entityProperty) {
        List<String> columns = entityProperty.getColumns(); // get columns for search

        StringBuilder query = new StringBuilder();
        query.append("SELECT new com.iteratia.titanicquest.dto.search.SearchGuessItem(s.id, s.") // select SearchGuessItem
                .append(columns.get(0)) // select first column for result in SearchGuessItem
                .append(") FROM ")
                .append(entityProperty.getEntity().getSimpleName()) // from entity
                .append(" s");
        this.addConditions(query, columns, searchInput); // add conditions to query

        return query;
    }

    /**
     * Conditions Builder for Search Query <br>
     * For all columns add condition {column} LIKE %{searchInput}%
     * @param query query for conditions
     * @param columns columns for conditions
     * @param searchInput search request = conditions value
     * */
    private void addConditions(StringBuilder query, List<String> columns, String searchInput){
        if(searchInput.isEmpty())
            return;// not adding conditions if searchInput is empty

        query.append(" WHERE");
        for (String column : columns) { // for all columns
            query.append(" ").append(column).append(" LIKE '%").append(searchInput).append("%'"); // add condition {column} LIKE {searchInput}
            query.append(" OR");
        }
        int i = query.lastIndexOf(" OR");
        query.delete(i, i+3); // remove last OR
    }
}
