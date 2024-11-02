package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.CampaignRequest;
import com.github.apoioSolidario.domain.dto.response.CampaignResponse;
import com.github.apoioSolidario.domain.model.Campaign;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.CampaignRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository repository;

    public CampaignService(CampaignRepository repository) {
        this.repository = repository;
    }

    public CampaignResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"campaign"));
        return EntityMapper.toObject(entity,CampaignResponse.class);
    }

    public CampaignResponse save( CampaignRequest request) {
        System.out.println(request);
        Campaign entity = EntityMapper.toObject(request, Campaign.class);
        System.out.println(entity);
        return EntityMapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public CampaignResponse update( Long id, @Valid CampaignRequest request) {
        Campaign entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"campaign")
        );
        EntityMapper.entityModelMapper.map(request, entity);
        Campaign response = repository.save(entity);
        return EntityMapper.toObject(response,CampaignResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"campaign")
        );
        repository.delete(entity);
    }

    public List<CampaignResponse> findAll() {
        return  EntityMapper.toList(repository.findAll(), CampaignResponse.class);
    }
}
