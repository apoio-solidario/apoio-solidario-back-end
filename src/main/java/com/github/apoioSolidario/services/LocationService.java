package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.LocationRequest;
import com.github.apoioSolidario.domain.dto.response.LocationResponse;
import com.github.apoioSolidario.domain.model.Location;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.LocationRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public List<LocationResponse> findAll() {
        return EntityMapper.toList(repository.findAll(), LocationResponse.class);
    }

    public LocationResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Locations"));
        return EntityMapper.toObject(entity,LocationResponse.class);
    }

    public LocationResponse save( LocationRequest request) {
        Location entity = EntityMapper.toObject(request, Location.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return EntityMapper.toObject(repository.save(entity),LocationResponse.class);
    }

    public LocationResponse update( Long id, @Valid LocationRequest request) {
        Location entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"Locations")
        );
        entity.setUpdatedAt(LocalDateTime.now());
        EntityMapper.entityModelMapper.map(request, entity);
        Location response = repository.save(entity);
        return EntityMapper.toObject(response,LocationResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Locations")
        );
        repository.delete(entity);
    }
}
