package com.iteratia.titanicquest.dto.stats;

import com.iteratia.titanicquest.dto.filter.Filter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Statistics {
    private String operation;
    private String field;
    private List<Filter> filters;

    /**
     * Check if operation Valid
     * @param fieldType type of entity field
     * @return <strong>true</strong> if field is <strong>*</strong> and operation is <strong>COUNT</strong>
     * OR if fieldType is Number and operation is <strong>SUM</strong>
     * */
    public boolean isOperationValid(Class<?> fieldType) {
        if(field.equals("*")){
            return operation.equalsIgnoreCase("count");

        } else if (fieldType.equals(Integer.class)
                || fieldType.equals(Float.class)
                || fieldType.equals(BigDecimal.class)) {

            return (operation.equalsIgnoreCase("sum")); // valid operation for numbers is SUM

        }

        return false; // false if unknown operation
    }
}
