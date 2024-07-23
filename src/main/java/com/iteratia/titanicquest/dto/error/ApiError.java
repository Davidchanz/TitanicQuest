package com.iteratia.titanicquest.dto.error;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ApiError {

    private String title;

    private int status;

    private String instance;

    private String created = Instant.now().toString();

    private List<Error> errors = new ArrayList<>();

    public ApiError(HttpStatus httpStatus, String instance, Error... errors){
        this.title = httpStatus.name();
        this.status = httpStatus.value();
        this.instance = instance;
        this.errors = List.of(errors);
    }
}
