package com.iteratia.titanicquest.dto.pagination;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDto {
    private List<Boolean> pagination;
}
