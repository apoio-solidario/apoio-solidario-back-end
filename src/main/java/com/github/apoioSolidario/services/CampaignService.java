package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.CampaignRequest;
import com.github.apoioSolidario.domain.dto.response.CampaignResponse;
import com.github.apoioSolidario.domain.model.Campaign;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.CampaignRepository;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository repository;
    private final OngRepository ongRepository;

    public CampaignService(CampaignRepository repository, OngRepository ongRepository) {
        this.repository = repository;
        this.ongRepository = ongRepository;
    }

    public CampaignResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"campaign"));
        return EntityMapper.toObject(entity,CampaignResponse.class);
    }

    public CampaignResponse save( CampaignRequest request) {
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        Campaign entity = new Campaign();
        entity.setOng(ong);
        entity.setDescription(request.getDescription());
        entity.setFeedbacks(new ArrayList<>());
        entity.setAmountRaised(request.getAmountRaised());
        entity.setStatus(request.getStatus());
        entity.setTitle(request.getTitle());
        entity.setStartData(request.getStartData());
        entity.setEndData(request.getEndData());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return EntityMapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public CampaignResponse update( Long id, @Valid CampaignRequest request) {
        Campaign entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"campaign")
        );
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        entity.setOng(ong);
        entity.setDescription(request.getDescription());
        entity.setGoalAmount(request.getGoalAmount());
        entity.setFeedbacks(new ArrayList<>());
        entity.setAmountRaised(request.getAmountRaised());
        entity.setStatus(request.getStatus());
        entity.setTitle(request.getTitle());
        entity.setStartData(request.getStartData());
        entity.setEndData(request.getEndData());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return EntityMapper.toObject(repository.save(entity),CampaignResponse.class);
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
