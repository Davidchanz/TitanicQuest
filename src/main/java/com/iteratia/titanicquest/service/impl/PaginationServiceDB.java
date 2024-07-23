package com.iteratia.titanicquest.service.impl;

import com.iteratia.titanicquest.dto.pagination.Pagination;
import com.iteratia.titanicquest.service.PaginationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationServiceDB implements PaginationService {

    @Value("${iteratia.titanicquest.pagination-max}")
    private int maxPages;

    /**
     * Get Pagination
     * @param itemsCount how many items needs to paged
     * @param page current active page
     * @param pageSize how many items must be shown on one page
     * */
    @Override
    public List<Pagination> getPagination(int itemsCount, Integer page, Integer pageSize) {
        if(pageSize == null || pageSize == 0)
            pageSize = 1; //prevent bad pageSize value

        int pageNumbers = (int) Math.ceil(itemsCount / (double) pageSize); // compute page count for items = all_items_count / items_count_on_one_page

        List<Pagination> pagination = new ArrayList<>();

        if(page == null)
            page = 1; //prevent page bad value

        //      {1 - currentStartPage},{2},{3},{4},{5 - current_page},{6},{7},{8},{9 - currentLastPage}

        int currentStartPage = page - maxPages; // get first page for current_page
        int currentLastPage = page + maxPages; // get last page for current_page
        for(int i = currentStartPage; i <= currentLastPage; i++){
            if(i < 1)
                continue; // page 0 - is invalid, skip
            if(i > pageNumbers)
                break; // break if i more than all_pages_count

            pagination.add(new Pagination(i == page, i)); // add page 'true' if current_page, otherwise add page 'false'
        }

        return pagination;
    }

    /**
     * Get Pageable for current Page and Sort
     * @param page current page
     * @param sort sort by
     * @param order sort order
     * @param pageSize how many item can be shown on one page
     * */
    @Override
    public Pageable getPage(Integer page, String sort, String order, Integer pageSize) {
        if(page == 0)
            page = 1; //prevent page bad value

        if(pageSize == 0)
            pageSize = 1; //prevent pageSize bad value

        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC; // set sort order ASC or DESC

        return PageRequest.of(page - 1, pageSize, Sort.by(direction, sort)); // get pageable
    }
}
