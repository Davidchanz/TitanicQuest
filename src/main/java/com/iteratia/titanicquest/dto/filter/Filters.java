package com.iteratia.titanicquest.dto.filter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Filters {
    private List<Filter> filters = new ArrayList<>();
}
