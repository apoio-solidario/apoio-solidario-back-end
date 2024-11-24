package com.github.apoioSolidario.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenericMapper extends AbstractMapper {
    public GenericMapper(ModelMapper mapper) {
        super(mapper);
    }
}
