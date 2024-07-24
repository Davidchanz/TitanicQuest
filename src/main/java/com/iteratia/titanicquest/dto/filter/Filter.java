package com.iteratia.titanicquest.dto.filter;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Filter {
    private String field;
    private String condition;
    private String value;
    private String logicalRelation;

    /**
     * Get Filter Value Type <br>
     * @apiNote Only for Primitive types
     * @return {@link Integer} OR {@link Float} OR {@link Boolean} OR {@link String}
     * */
    public Class<?> getValueType() {
        try{
            Integer.parseInt(value);
            return Integer.class;
        }catch (NumberFormatException ignore){

        }
        try{
            Float.parseFloat(value);
            return BigDecimal.class;
        }catch (NumberFormatException ignore){

        }
        if("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value))
            return Boolean.class;
        else if(value.startsWith("'") && value.endsWith("'"))
            return String.class;
        else
            return Exception.class;
    }

    /**
     * Check if Filter Condition Valid <br>
     * for {@link Integer} AND {@link BigDecimal} valid condition is <strong>></strong> <strong><</strong> <strong>=</strong> <strong><></strong> <br>
     * for {@link Boolean} valid condition is <strong>true</strong> <strong>false</strong> <br>
     * for {@link String} valid condition is <strong>=</strong> <strong><></strong> <br>
     * */
    public boolean isConditionValid() {
        Class<?> valueType = getValueType();
        if (valueType.equals(Integer.class) || valueType.equals(BigDecimal.class)) {
            return (condition.equalsIgnoreCase("=")
                    || condition.equalsIgnoreCase(">")
                    || condition.equalsIgnoreCase("<")
                    || condition.equalsIgnoreCase("<>"));
        } else if(valueType.equals(Boolean.class)){
            return (condition.equalsIgnoreCase("="));
        } else
            return (condition.equalsIgnoreCase("=")
                    || condition.equalsIgnoreCase("<>"));
    }

    /**
     * Check if Filter LogicalRelation Valid <br>
     * @return <strong>true</strong> if LogicalRelation is <strong>AND</strong> or <strong>OR</strong>
     * */
    public boolean isLogicalRelationValid(){
        return logicalRelation.equalsIgnoreCase("AND")
                || logicalRelation.equalsIgnoreCase("OR");
    }
}
