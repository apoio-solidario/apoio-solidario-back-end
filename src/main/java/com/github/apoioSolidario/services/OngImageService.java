package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.OngImageRequest;
import com.github.apoioSolidario.domain.dto.response.OngImageResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.domain.model.OngImage;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngImageRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OngImageService {
    private final OngImageRepository repository;

    public OngImageService(OngImageRepository ongImageRepository) {
        this.repository = ongImageRepository;
    }

    public List<OngImageResponse> findAll() {
        return EntityMapper.toList(repository.findAll(),OngImageResponse.class);
    }

    public OngImageResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("OngImage com id %s não encontrada",id)));
        return EntityMapper.toObject(entity,OngImageResponse.class);
    }

    public OngImageResponse save( OngImageRequest ongImageRequest) {
        System.out.printf(ongImageRequest.toString());
        OngImage entity = EntityMapper.toObject(ongImageRequest, OngImage.class);
        System.out.printf(entity.toString());
        return EntityMapper.toObject(repository.save(entity),OngImageResponse.class);
    }

    public OngImageResponse update( Long id, @Valid OngImageRequest ongImageRequest) {
        OngImage entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Ong"));
        EntityMapper.entityModelMapper.map(ongImageRequest, entity);
        OngImage response = repository.save(entity);
        return EntityMapper.toObject(response,OngImageResponse.class);
    }
}
