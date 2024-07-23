package com.iteratia.titanicquest.service;

import com.iteratia.titanicquest.dto.pagination.Pagination;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaginationService {

    List<Pagination> getPagination(int count, Integer page, Integer pageSize);

    Pageable getPage(Integer page, String sort, String order, Integer pageSize);
}
