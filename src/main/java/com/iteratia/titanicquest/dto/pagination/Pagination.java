package com.iteratia.titanicquest.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {
    private boolean active;
    private int number;
}
