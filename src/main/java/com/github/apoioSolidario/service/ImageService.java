package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.ImageMapper;
import com.github.apoioSolidario.dto.request.ImageRequest;
import com.github.apoioSolidario.dto.request.ImageUpdateRequest;
import com.github.apoioSolidario.dto.response.EventResponse;
import com.github.apoioSolidario.dto.response.ImageResponse;
import com.github.apoioSolidario.exception.AccessDeniedException;
import com.github.apoioSolidario.exception.UploadImageException;
import com.github.apoioSolidario.model.Image;
import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.repository.OngImageRepository;
import com.github.apoioSolidario.repository.OngRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {
    private final OngImageRepository repository;
    private final OngRepository ongRepository;
    private final ImageMapper mapper;
    private final TokenService tokenService;
    private final CloudinaryService cloudinaryService;

    public ImageService(OngImageRepository repository, OngRepository ongRepository, ImageMapper mapper, TokenService tokenService, CloudinaryService cloudinaryService) {
        this.repository = repository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.cloudinaryService = cloudinaryService;
    }

    public Page<ImageResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable), ImageResponse.class);
    }

    public ImageResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "OngImage"));
        if (!checkAccessOngSocial(entity.getOng().getOngId())) {
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toObject(entity, ImageResponse.class);
    }

    @Transactional
    public ImageResponse save(ImageRequest imageRequest) {
        Image entity = new Image();

        if (!checkAccessOngSocial(imageRequest.getEntityId())) {
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setImageName(imageRequest.getImageName());
        entity.setImageUrl(cloudinaryService.uploadFile(imageRequest.getFile(), "folder_1"));
        if (entity.getImageUrl() == null) {
            throw new UploadImageException("Erro no upload da imagem");
        }
        Ong ong = ongRepository.findById(imageRequest.getEntityId()).orElseThrow(() -> new EntityNotFoundException(imageRequest.getEntityId(), "Ong"));
        entity.setOng(ong);
        return mapper.toObject(repository.save(entity), ImageResponse.class);
    }
@Transactional
    public ImageResponse update(UUID id, @Valid ImageUpdateRequest imageUpdateRequest) {
        Image entity = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id, "Ong")
        );
        if (!checkAccessOngSocial(entity.getOng().getOngId())) {
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        Ong ong = ongRepository.findById(imageUpdateRequest.getEntityId()).orElseThrow(() -> new EntityNotFoundException(imageUpdateRequest.getEntityId(), "Ong"));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setOng(ong);
        if (imageUpdateRequest.getFile()!=null) {
            String publicId = cloudinaryService.extractPublicIdFromUrl(entity.getImageUrl());
            cloudinaryService.deleteImage(publicId);
            entity.setImageUrl(cloudinaryService.uploadFile(imageUpdateRequest.getFile(), "folder_1"));
        }
        entity.setImageName(imageUpdateRequest.getImageName());
        Image response = repository.save(entity);
        return mapper.toObject(response, ImageResponse.class);
    }
    @Transactional
    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Ong Images"));
        if (!checkAccessOngSocial(entity.getOng().getOngId())) {
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        String publicId = cloudinaryService.extractPublicIdFromUrl(entity.getImageUrl());
        System.out.println(publicId);
        cloudinaryService.deleteImage(publicId);
        repository.delete(entity);
    }

    @Transactional
    public Page<ImageResponse> finByEntityId(UUID id, Pageable pageable) {
        if (!checkAccessOngSocial(id)) {
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toPage(repository.findByOng_OngId(id, pageable), ImageResponse.class);
    }

    public boolean checkAccessOngSocial(UUID ongId) {
        var logado = tokenService.getPrincipal();
        Ong ongLogada = ongRepository.findByUser_UserId(logado.getUserId()).orElseThrow(() -> new EntityNotFoundException(logado.getUserId(), "Ong"));
        return tokenService.isAdmin() || ongLogada.getOngId().equals(ongId);
    }
}
