package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.EventMapper;
import com.github.apoioSolidario.dto.request.EventRequest;
import com.github.apoioSolidario.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.dto.response.EventResponse;
import com.github.apoioSolidario.exception.AccessDeniedException;
import com.github.apoioSolidario.model.Event;
import com.github.apoioSolidario.model.Location;
import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.repository.EventRepository;
import com.github.apoioSolidario.repository.LocationRepository;
import com.github.apoioSolidario.repository.OngRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository repository;
    private final LocationRepository locationRepository;
    private final OngRepository ongRepository;
    private final EventMapper mapper;
    private final TokenService tokenService;

    public EventService(EventRepository repository, LocationRepository locationRepository, OngRepository ongRepository, EventMapper mapper, TokenService tokenService) {
        this.repository = repository;
        this.locationRepository = locationRepository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public Page<EventResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable), EventResponse.class);
    }

    public EventResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Event"));
        if(!checkAccessEvent(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toObject(entity, EventResponse.class);
    }

    @Transactional()
    public EventResponse findByHandler(String handler) {
        var entity = repository.findByHandler(handler).orElseThrow(() -> new EntityNotFoundException(String.format("Campaingn com handler %s não encontrada", handler)));
        if(!checkAccessEvent(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toObject(entity, EventResponse.class);
    }

    public EventResponse save(EventRequest request) {
        if(!checkAccessEvent(request.getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Ong"));
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Location"));
        Event entity = mapper.toObject(request, Event.class);
        entity.setLocation(location);
        entity.setOng(ong);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity), EventResponse.class);
    }

    public EventResponse update(UUID id, @Valid EventRequest request) {
        Event entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id, "event")
        );
        if(!checkAccessEvent(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Ong"));
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new EntityNotFoundException(request.getOngId(), "Location"));
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setContent(request.getContent());
        entity.setHandler(request.getHandler());
        entity.setLocation(location);
        entity.setOng(ong);
        entity.setStartData(request.getStartData());
        entity.setEndData(request.getEndData());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity), EventResponse.class);
    }

    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "event")
        );
        if(!checkAccessEvent(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        repository.delete(entity);
    }

    public EventResponse updateStatus(@Valid UUID id, @Valid UpdateStatusRequest request) {
        Event entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id, "event")
        );
        if(!checkAccessEvent(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity), EventResponse.class);
    }

    @Transactional
    public Page<EventResponse> finByOngId(UUID id, Pageable pageable) {
        if(!checkAccessEvent(id)){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toPage(repository.findByOng_OngId(id, pageable), EventResponse.class);
    }

    @Transactional
    public Page<EventResponse> finByLocationId(UUID id, Pageable pageable) {
        return mapper.toPage(repository.findByLocation_LocationId(id, pageable), EventResponse.class);
    }

    public boolean checkAccessEvent(UUID ongId) {
        var logado = tokenService.getPrincipal();
        Ong ongLogada = ongRepository.findByUser_UserId(logado.getUserId()).orElseThrow(()->new EntityNotFoundException(logado.getUserId(),"Ong"));
        return tokenService.isAdmin() || ongLogada.getOngId().equals(ongId);
    }
}
