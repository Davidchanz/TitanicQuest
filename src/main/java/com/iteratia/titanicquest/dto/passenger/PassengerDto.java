package com.iteratia.titanicquest.dto.passenger;

import com.iteratia.titanicquest.model.PClass;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PassengerDto {
    private Long id;
    private Boolean survived;
    private PClass pClass;
    private String name;
    private String sex;
    private BigDecimal age;
    private Integer siblingsSpouses;
    private Integer parentsChildren;
    private BigDecimal fare;
}
