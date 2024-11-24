package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.FeedbackMapper;
import com.github.apoioSolidario.dto.request.FeedbackRequest;
import com.github.apoioSolidario.dto.response.FeedbackResponse;
import com.github.apoioSolidario.model.Campaign;
import com.github.apoioSolidario.model.Event;
import com.github.apoioSolidario.model.Feedback;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.repository.CampaignRepository;
import com.github.apoioSolidario.repository.EventRepository;
import com.github.apoioSolidario.repository.FeedbackRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;
    private final CampaignRepository campaignRepository;
    private final EventRepository eventRepository;

    public FeedbackService(FeedbackRepository repository, FeedbackMapper mapper, CampaignRepository campaignRepository, EventRepository eventRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.campaignRepository = campaignRepository;
        this.eventRepository = eventRepository;
    }

    public FeedbackResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Event"));
        return mapper.toObject(entity,FeedbackResponse.class);
    }

    public FeedbackResponse save( FeedbackRequest request) {
        Feedback entity = mapper.toObject(request, Feedback.class);
        Campaign campaign = campaignRepository.findById(request.getCampaignId()).orElseThrow(()-> new EntityNotFoundException(request.getCampaignId(), "Campaign"));
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(()-> new EntityNotFoundException(request.getEventId(), "Event"));
        entity.setCampaign(campaign);
        entity.setEvent(event);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),FeedbackResponse.class);
    }

    public FeedbackResponse update( UUID id, @Valid FeedbackRequest request) {
        Feedback entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"feedback")
        );
        Campaign campaign = campaignRepository.findById(request.getCampaignId()).orElseThrow(()-> new EntityNotFoundException(request.getCampaignId(), "Campaign"));
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(()-> new EntityNotFoundException(request.getEventId(), "Event"));
        entity.setEmail(request.getEmail());
        entity.setContent(request.getContent());
        entity.setRating(request.getRating());
        entity.setUsername(request.getUsername());
        entity.setEvent(event);
        entity.setCampaign(campaign);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),FeedbackResponse.class);
    }

    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"event")
        );
        repository.delete(entity);
    }

    public Page<FeedbackResponse> findAll(Pageable pageable) {
       return  mapper.toPage(repository.findAll(pageable),FeedbackResponse.class);
    }
}
