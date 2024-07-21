package com.iteratia.titanicquest.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "PASSENGERS")
@Getter
@Setter
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Boolean survived;

    @Enumerated(EnumType.STRING)
    private PClass pClass;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private Float age;

    @Column(nullable = false)
    private Integer siblingsSpouses;

    @Column(nullable = false)
    private Integer parentsChildren;

    @Column(nullable = false)
    private BigDecimal fare;
}
