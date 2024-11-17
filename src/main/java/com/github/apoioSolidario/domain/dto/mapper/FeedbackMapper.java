package com.github.apoioSolidario.domain.dto.mapper;

import com.github.apoioSolidario.domain.dto.request.EventRequest;
import com.github.apoioSolidario.domain.dto.request.FeedbackRequest;
import com.github.apoioSolidario.domain.dto.response.EventResponse;
import com.github.apoioSolidario.domain.dto.response.FeedbackResponse;
import com.github.apoioSolidario.domain.model.Event;
import com.github.apoioSolidario.domain.model.Feedback;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper extends AbstractMapper{
    public FeedbackMapper(ModelMapper mapper) {
        super(mapper);
        mapper.addMappings(new PropertyMap<FeedbackRequest, Feedback>() {
            @Override
            protected void configure() {
                map(null, destination.getId());
            }
        });

        mapper.addMappings(new PropertyMap<Feedback, FeedbackResponse>() {
            @Override
            protected void configure() {
                map(source.getCampaign().getId(), destination.getCampaignId());
                map(source.getEvent().getId(), destination.getEventId());
            }
        });
    }
}
