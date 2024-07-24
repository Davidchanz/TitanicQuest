package com.iteratia.titanicquest.dto.error;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Error {

    @NotNull
    private String error;

    @NotNull
    private String description;
}
