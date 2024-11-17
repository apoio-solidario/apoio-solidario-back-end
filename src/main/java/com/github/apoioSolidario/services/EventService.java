package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EventMapper;
import com.github.apoioSolidario.domain.dto.request.EventRequest;
import com.github.apoioSolidario.domain.dto.response.EventResponse;
import com.github.apoioSolidario.domain.model.Event;
import com.github.apoioSolidario.domain.model.Location;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.EventRepository;
import com.github.apoioSolidario.repositories.LocationRepository;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    private final EventRepository repository;
    private final LocationRepository locationRepository;
    private final OngRepository ongRepository;
    private final EventMapper mapper;

    public EventService(EventRepository repository, LocationRepository locationRepository, OngRepository ongRepository, EventMapper mapper) {
        this.repository = repository;
        this.locationRepository = locationRepository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
    }

    public Page<EventResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable), EventResponse.class);
    }

    public EventResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Event"));
        return mapper.toObject(entity, EventResponse.class);
    }

    public EventResponse save(EventRequest request) {
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Ong"));
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Location"));
        Event entity = mapper.toObject(request, Event.class);
        entity.setLocation(location);
        entity.setOng(ong);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity), EventResponse.class);
    }

    public EventResponse update(Long id, @Valid EventRequest request) {
        Event entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id, "event")
        );
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Ong"));
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Location"));
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setLocation(location);
        entity.setOng(ong);
        entity.setStartData(request.getStartData());
        entity.setEndData(request.getEndData());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity), EventResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "event")
        );
        repository.delete(entity);
    }

}
