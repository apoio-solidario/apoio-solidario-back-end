package com.github.apoioSolidario.dto.mapper;

import com.github.apoioSolidario.dto.request.FeedbackRequest;
import com.github.apoioSolidario.dto.response.FeedbackResponse;
import com.github.apoioSolidario.model.Feedback;
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
                map(null, destination.getFeedbackId());
            }
        });

        mapper.addMappings(new PropertyMap<Feedback, FeedbackResponse>() {
            @Override
            protected void configure() {
                map(source.getCampaign().getCampaignId(), destination.getCampaignId());
                map(source.getEvent().getEventId(), destination.getEventId());
            }
        });
    }
}
