package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.EventRequest;
import com.github.apoioSolidario.domain.dto.response.EventResponse;
import com.github.apoioSolidario.domain.model.Event;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.EventRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository eventRepository) {
        this.repository = eventRepository;
    }
    public List<EventResponse> findAll() {
        return EntityMapper.toList(repository.findAll(), EventResponse.class);
    }

    public EventResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Event"));
        return EntityMapper.toObject(entity,EventResponse.class);
    }

    public EventResponse save( EventRequest request) {
        Event entity = EntityMapper.toObject(request, Event.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return EntityMapper.toObject(repository.save(entity),EventResponse.class);
    }

    public EventResponse update( Long id, @Valid EventRequest request) {
        Event entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"event")
        );
        entity.setUpdatedAt(LocalDateTime.now());
        EntityMapper.entityModelMapper.map(request, entity);
        Event response = repository.save(entity);
        return EntityMapper.toObject(response,EventResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"event")
        );
        repository.delete(entity);
    }

}
