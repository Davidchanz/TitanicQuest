package com.iteratia.titanicquest.service.impl;

import com.iteratia.titanicquest.service.PaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaginationServiceDB implements PaginationService {
    static Logger logger = LoggerFactory.getLogger(PaginationServiceDB.class);

    @Value("${pagination.max}")
    private int maxPages;

    /**
     * Get Pagination
     * @param itemsCount how many items needs to paged
     * @param page current active page
     * @param pageSize how many items must be shown on one page
     * */
    @Override
    public List<Boolean> getPagination(int itemsCount, Integer page, Integer pageSize) {
        logger.info("Start Method '{}' Input '{}'", "getPagination()",
                List.of(Map.of("itemsCount", itemsCount),
                        Map.of("page", page != null ? page : "")));

        if(pageSize == null || pageSize == 0)
            pageSize = 1; //prevent bad pageSize value

        int pageNumbers = (int) Math.ceil(itemsCount / (double) pageSize); // compute page count for items = all_items_count / items_count_on_one_page
        logger.info("Set Page Number '{}'", pageNumbers);

        List<Boolean> pagination = new ArrayList<>();

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

            pagination.add(i == page); // add page 'true' if current_page, otherwise add page 'false'
        }

        logger.info("End Method '{}' Return '{}'", "getPagination()", pagination);
        return pagination;
    }
}
