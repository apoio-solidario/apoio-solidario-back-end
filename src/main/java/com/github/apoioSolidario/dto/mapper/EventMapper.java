package com.github.apoioSolidario.dto.mapper;

import com.github.apoioSolidario.dto.request.EventRequest;
import com.github.apoioSolidario.dto.response.EventResponse;
import com.github.apoioSolidario.model.Event;
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
                map(null, destination.getEventId());
            }
        });

        mapper.addMappings(new PropertyMap<Event, EventResponse>() {
            @Override
            protected void configure() {
                map(source.getOng().getOngId(), destination.getOngId());
                map(source.getLocation().getLocationId(), destination.getLocationId());
            }
        });

    }
}
