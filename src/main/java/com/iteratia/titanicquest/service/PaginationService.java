package com.iteratia.titanicquest.service;

import java.util.List;

public interface PaginationService {

    List<Boolean> getPagination(int count, Integer page, Integer pageSize);
}
