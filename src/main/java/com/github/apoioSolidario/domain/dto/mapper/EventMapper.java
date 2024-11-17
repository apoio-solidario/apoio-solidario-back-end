package com.github.apoioSolidario.domain.dto.mapper;

import com.github.apoioSolidario.domain.dto.request.EventRequest;
import com.github.apoioSolidario.domain.dto.response.EventResponse;
import com.github.apoioSolidario.domain.model.Event;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class EventMapper extends AbstractMapper {
    public EventMapper(ModelMapper mapper) {
        super(mapper);
        mapper.addMappings(new PropertyMap<EventRequest, Event>() {
            @Override
            protected void configure() {
                map(null, destination.getId());
            }
        });

        mapper.addMappings(new PropertyMap<Event, EventResponse>() {
            @Override
            protected void configure() {
                map(source.getOng().getId(), destination.getOngId());
                map(source.getLocation().getId(), destination.getLocationId());
            }
        });

    }
}
