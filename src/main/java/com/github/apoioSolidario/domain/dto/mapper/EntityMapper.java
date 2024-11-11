package com.github.apoioSolidario.domain.dto.mapper;

import com.github.apoioSolidario.domain.dto.request.EventRequest;
import com.github.apoioSolidario.domain.model.Event;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class EntityMapper {
    public static ModelMapper entityModelMapper = new ModelMapper();


    public static <T,S> T toObject(S source,Class<T> target) {
        return entityModelMapper.map(source,target);
    }

    public static <T,S> T objectToObject(S source, T target) {
        entityModelMapper.map(source, target);
        return target;
    }

    public static <T,S> List<T> toList(List<S> source, Class<T> target) {
        return  source.stream().map((it)->{
            return  entityModelMapper.map(it,target);
        }).toList();
    }

    public static <T,S> Page<T> toPage(Page<S> source, Class<T> target) {
        List<T> lista =  source.stream().map((it)->{
            return  entityModelMapper.map(it,target);
        }).toList();
        return new PageImpl<>(lista, PageRequest.of(source.getNumber(), source.getSize()), source.getTotalElements());
    }


}
