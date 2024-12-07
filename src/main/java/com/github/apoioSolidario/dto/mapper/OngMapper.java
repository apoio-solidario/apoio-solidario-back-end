package com.github.apoioSolidario.dto.mapper;

import com.github.apoioSolidario.dto.request.OngRequest;
import com.github.apoioSolidario.dto.response.OngResponse;
import com.github.apoioSolidario.model.Ong;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class OngMapper extends AbstractMapper {
    public OngMapper(ModelMapper entityModelMapper) {
        super(entityModelMapper);
        entityModelMapper.addMappings(new PropertyMap<OngRequest, Ong>() {
            @Override
            protected void configure() {
                map(null,destination.getOngId());
                map(source.getUserId(),destination.getUser().getUserId());
            }
        });

    
    }
}
