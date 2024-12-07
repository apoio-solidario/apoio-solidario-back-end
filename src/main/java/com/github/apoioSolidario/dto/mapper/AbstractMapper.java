package com.github.apoioSolidario.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public abstract class AbstractMapper {


    protected final ModelMapper entityModelMapper;

    public AbstractMapper(ModelMapper entityModelMapper) {
        this.entityModelMapper = entityModelMapper;
    }

    public  <T,S> T toObject(S source, Class<T> target) {
        return entityModelMapper.map(source,target);
    }

    public  <T,S> T objectToObject(S source, T target) {
        entityModelMapper.map(source, target);
        return target;
    }

    public  <T,S> List<T> toList(List<S> source, Class<T> target) {
        return  source.stream().map((it)->{
            return  entityModelMapper.map(it,target);
        }).toList();
    }

    public  <T,S> Page<T> toPage(Page<S> source, Class<T> target) {
        List<T> lista =  source.stream().map((it)->{
            return  entityModelMapper.map(it,target);
        }).toList();
        return new PageImpl<>(lista, PageRequest.of(source.getNumber(), source.getSize()), source.getTotalElements());
    }


}
