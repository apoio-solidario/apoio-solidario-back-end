package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.GenericMapper;
import com.github.apoioSolidario.dto.request.LocationRequest;
import com.github.apoioSolidario.dto.response.LocationResponse;
import com.github.apoioSolidario.model.Location;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.repository.LocationRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository repository;
    private final GenericMapper mapper;

    public LocationService(LocationRepository repository, GenericMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<LocationResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable), LocationResponse.class);
    }

    public LocationResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Locations"));
        return mapper.toObject(entity,LocationResponse.class);
    }

    public LocationResponse save( LocationRequest request) {
        Location entity = mapper.toObject(request, Location.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),LocationResponse.class);
    }

    public LocationResponse update( UUID id, @Valid LocationRequest request) {
        Location entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"Locations")
        );
        entity.setUpdatedAt(LocalDateTime.now());
        mapper.objectToObject(request, entity);
        Location response = repository.save(entity);
        return mapper.toObject(response,LocationResponse.class);
    }

    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Locations")
        );
        repository.delete(entity);
    }
}
