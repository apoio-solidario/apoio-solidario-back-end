package com.github.apoioSolidario.dto.mapper;

import com.github.apoioSolidario.dto.request.CampaignRequest;
import com.github.apoioSolidario.dto.response.CampaignResponse;
import com.github.apoioSolidario.model.Campaign;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CampaignMapper extends AbstractMapper{
    public CampaignMapper(ModelMapper mapper) {
        super(mapper);
        mapper.addMappings(new PropertyMap<CampaignRequest, Campaign>() {
            @Override
            protected void configure() {
                map(null,destination.getCampaignId());
            }
        });
        mapper.addMappings(new PropertyMap<Campaign, CampaignResponse>() {
            @Override
            protected void configure() {
                map(source.getOng().getOngId(),destination.getOngId());
            }
        });

    }
}
