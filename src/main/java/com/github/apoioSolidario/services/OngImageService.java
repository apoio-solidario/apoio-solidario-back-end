package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.OngImageRequest;
import com.github.apoioSolidario.domain.dto.response.OngImageResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.domain.model.OngImage;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngImageRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OngImageService {
    private final OngImageRepository repository;

    public OngImageService(OngImageRepository ongImageRepository) {
        this.repository = ongImageRepository;
    }

    public List<OngImageResponse> findAll() {
        return EntityMapper.toList(repository.findAll(),OngImageResponse.class);
    }

    public OngImageResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngImage"));
        return EntityMapper.toObject(entity,OngImageResponse.class);
    }

    public OngImageResponse save( OngImageRequest ongImageRequest) {
        System.out.println(ongImageRequest);
        OngImage entity = EntityMapper.toObject(ongImageRequest, OngImage.class);
        System.out.println(entity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return EntityMapper.toObject(repository.save(entity),OngImageResponse.class);
    }

    public OngImageResponse update( Long id, @Valid OngImageRequest ongImageRequest) {
        OngImage entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"Ong")
        );
        entity.setUpdatedAt(LocalDateTime.now());
        EntityMapper.entityModelMapper.map(ongImageRequest, entity);
        OngImage response = repository.save(entity);
        return EntityMapper.toObject(response,OngImageResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Ong Images")
        );
        repository.delete(entity);
    }
}
