package com.iteratia.titanicquest.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaginationService {

    List<Boolean> getPagination(Long count, Integer page, Integer pageSize);

    Pageable getPage(Integer page, String sort, String order, Integer pageSize);
}
