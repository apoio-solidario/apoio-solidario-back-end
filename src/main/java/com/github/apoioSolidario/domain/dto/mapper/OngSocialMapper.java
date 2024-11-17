package com.github.apoioSolidario.domain.dto.mapper;

import com.github.apoioSolidario.domain.dto.request.OngSocialRequest;
import com.github.apoioSolidario.domain.dto.response.OngSocialResponse;
import com.github.apoioSolidario.domain.model.OngSocial;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class OngSocialMapper extends AbstractMapper {
    public OngSocialMapper(ModelMapper mapper) {
        super(mapper);
        mapper.addMappings(new PropertyMap<OngSocialRequest, OngSocial>() {
            @Override
            protected void configure() {
                map(null, destination.getId());
            }
        });
        mapper.addMappings(new PropertyMap<OngSocial, OngSocialResponse>() {
            @Override
            protected void configure() {
                map(source.getOng().getId(), destination.getOngId());
            }
        });
    }
}
