package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.CampaignMapper;
import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.CampaignRequest;
import com.github.apoioSolidario.domain.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.domain.dto.response.CampaignResponse;
import com.github.apoioSolidario.domain.model.Campaign;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.CampaignRepository;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository repository;
    private final OngRepository ongRepository;
    private final CampaignMapper mapper;

    public CampaignService(CampaignRepository repository, OngRepository ongRepository, CampaignMapper mapper) {
        this.repository = repository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
    }

    public CampaignResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"campaign"));
        return mapper.toObject(entity,CampaignResponse.class);
    }

    public CampaignResponse save( CampaignRequest request) {
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        Campaign entity = mapper.toObject(request,Campaign.class);
        entity.setOng(ong);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public CampaignResponse update( Long id, @Valid CampaignRequest request) {
        Campaign entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"campaign")
        );
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        entity.setOng(ong);
        entity.setDescription(request.getDescription());
        entity.setGoalAmount(request.getGoalAmount());
        entity.setAmountRaised(request.getAmountRaised());
        entity.setStatus(request.getStatus());
        entity.setTitle(request.getTitle());
        entity.setStartData(request.getStartData());
        entity.setEndData(request.getEndData());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }
    public CampaignResponse updateStatus(@Valid Long id, @Valid UpdateStatusRequest request) {
        Campaign entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"campaign")
        );
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"campaign")
        );
        repository.delete(entity);
    }

    public Page<CampaignResponse> findAll(Pageable pageable) {
        return  mapper.toPage(repository.findAll(pageable), CampaignResponse.class);
    }


}
