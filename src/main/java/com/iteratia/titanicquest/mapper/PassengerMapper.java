package com.iteratia.titanicquest.mapper;

import com.iteratia.titanicquest.dto.PassengerDto;
import com.iteratia.titanicquest.model.Passenger;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PassengerDto modelToDto(Passenger model);
}
