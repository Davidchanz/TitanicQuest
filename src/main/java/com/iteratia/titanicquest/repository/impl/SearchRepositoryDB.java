package com.iteratia.titanicquest.repository.impl;

import com.iteratia.titanicquest.dto.search.SearchGuessItem;
import com.iteratia.titanicquest.exception.notFound.EntityNotFoundException;
import com.iteratia.titanicquest.model.Passenger;
import com.iteratia.titanicquest.repository.SearchRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @AllArgsConstructor
    @ToString
    private static class EntityProperty{
        Class<?> entity; // entity for search
        String entityGraphName; // entity graph name, if empty no one will be used
        List<String> columns; // entity columns for search must have at least one column
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

    @Override
    public int getSearchedPagesCount(String searchRequest, String url) {
        EntityProperty entityProperty = this.getEntity(url); // get Entity Property
        StringBuilder query = this.getQueryForSearch(searchRequest, entityProperty); // get query for search

        int count;
        String entityGraphName = entityProperty.getEntityGraphName(); // get entity graph name
        if(!entityGraphName.isEmpty()) { // if entity graph exist use it otherwise not
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(entityGraphName);

            count = entityManager.createQuery(
                            query.toString(),
                            Integer.class)
                    .setHint("jakarta.persistence.fetchgraph", entityGraph)
                    .getSingleResult();
        }else {
            count = entityManager.createQuery(
                            query.toString(),
                            Integer.class)
                    .getSingleResult();
        }

        return count;
    }

    /**
     * Query Builder for Search
     * @param searchInput  search request
     * @param entityProperty entity property for search request
     * SELECT COUNT(*) FROM Entity
     * */
    private StringBuilder getQueryForSearch(String searchInput, EntityProperty entityProperty) {
        List<String> columns = entityProperty.getColumns(); // get columns for search

        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*)") // select rows count
                .append(" FROM ")
                .append(entityProperty.getEntity().getSimpleName()) // from entity
                .append(" s WHERE");
        this.addConditions(query, columns, searchInput); // add conditions to query

        return query;
    }

    /**
     * Query Builder for Search Guess
     * @param searchInput  search request
     * @param entityProperty entity property for search request
     * SELECT SearchGuessItem FROM Entity
     * */
    private StringBuilder getQueryForSearchGuess(String searchInput, EntityProperty entityProperty) {
        List<String> columns = entityProperty.getColumns(); // get columns for search

        StringBuilder query = new StringBuilder();
        query.append("SELECT new com.iteratia.titanicquest.dto.search.SearchGuessItem(s.id, s.") // select SearchGuessItem
                .append(columns.get(0)) // select first column for result in SearchGuessItem
                .append(") FROM ")
                .append(entityProperty.getEntity().getSimpleName()) // from entity
                .append(" s WHERE");
        this.addConditions(query, columns, searchInput); // add conditions to query

        return query;
    }

    /**
     * Conditions Builder for Search Query
     * @param query query for conditions
     * @param columns columns for conditions
     * @param searchInput search request = conditions value
     * For all columns add condition {column} LIKE %{searchInput}%
     * */
    private void addConditions(StringBuilder query, List<String> columns, String searchInput){
        for (String column : columns) { // for all columns
            query.append(" ").append(column).append(" LIKE '%").append(searchInput).append("%'"); // add condition {column} LIKE {searchInput}
            query.append(" OR");
        }
        int i = query.lastIndexOf(" OR");
        query.delete(i, i+3); // remove last OR
    }
}
