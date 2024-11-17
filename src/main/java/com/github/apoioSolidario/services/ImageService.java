package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.ImageMapper;
import com.github.apoioSolidario.domain.dto.request.ImageRequest;
import com.github.apoioSolidario.domain.dto.response.ImageResponse;
import com.github.apoioSolidario.domain.model.Image;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngImageRepository;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ImageService {
    private final OngImageRepository repository;
    private final OngRepository ongRepository;
    private final ImageMapper mapper;

    public ImageService(OngImageRepository repository, OngRepository ongRepository, ImageMapper mapper) {
        this.repository = repository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
    }

    public Page<ImageResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable), ImageResponse.class);
    }

    public ImageResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "OngImage"));
        return mapper.toObject(entity, ImageResponse.class);
    }

    public ImageResponse save(ImageRequest imageRequest) {
        Image entity = mapper.toObject(imageRequest, Image.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        Ong ong = ongRepository.findById(imageRequest.getEntityId()).orElseThrow(() -> new EntityNotFoundException(imageRequest.getEntityId(), "Ong"));
        entity.setOng(ong);
        return mapper.toObject(repository.save(entity), ImageResponse.class);
    }

    public ImageResponse update(Long id, @Valid ImageRequest imageRequest) {
        Image entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id, "Ong")
        );
        Ong ong = ongRepository.findById(imageRequest.getEntityId()).orElseThrow(() -> new EntityNotFoundException(imageRequest.getEntityId(), "Ong"));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setOng(ong);
        entity.setImageUrl(imageRequest.getImageUrl());
        entity.setImageName(imageRequest.getImageName());
        Image response = repository.save(entity);
        return mapper.toObject(response, ImageResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Ong Images")
        );
        repository.delete(entity);
    }
}
