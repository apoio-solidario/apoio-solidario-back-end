package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.OngImageRequest;
import com.github.apoioSolidario.domain.dto.response.OngImageResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.domain.model.OngImage;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngImageRepository;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OngImageService {
    private final OngImageRepository repository;
    private final OngRepository ongRepository;

    public OngImageService(OngImageRepository repository, OngRepository ongRepository) {
        this.repository = repository;
        this.ongRepository = ongRepository;
    }

    public Page<OngImageResponse> findAll(Pageable pageable) {
        return EntityMapper.toPage(repository.findAll(pageable),OngImageResponse.class);
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
        var ong = ongRepository.findById(ongImageRequest.getOngId()).orElseThrow(()->new EntityNotFoundException(ongImageRequest.getOngId(),"Ong"));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setOng(ong);
        entity.setType(ongImageRequest.getType());
        entity.setImageUrl(ongImageRequest.getImageUrl());
        OngImage response = repository.save(entity);
        return EntityMapper.toObject(response,OngImageResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Ong Images")
        );
        repository.delete(entity);
    }
}
