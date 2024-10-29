package com.github.apoioSolidario.domain.dto.mapper;

import org.modelmapper.ModelMapper;

import java.util.List;

public class EntityMapper {
    public static ModelMapper entityModelMapper = new ModelMapper();

    public static <T,S> T toObject(S source,Class<T> target) {
        return entityModelMapper.map(source,target);
    }

    public static <T,S> List<T> toList(List<S> source, Class<T> target) {
        return  source.stream().map((it)->{
            return  entityModelMapper.map(it,target);
        }).toList();
    }
}
