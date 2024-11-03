package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.FeedbackRequest;
import com.github.apoioSolidario.domain.dto.response.FeedbackResponse;
import com.github.apoioSolidario.domain.model.Feedback;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.FeedbackRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public FeedbackResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Event"));
        return EntityMapper.toObject(entity,FeedbackResponse.class);
    }

    public FeedbackResponse save( FeedbackRequest request) {
        Feedback entity = EntityMapper.toObject(request, Feedback.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return EntityMapper.toObject(repository.save(entity),FeedbackResponse.class);
    }

    public FeedbackResponse update( Long id, @Valid FeedbackRequest request) {
        Feedback entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"feedback")
        );
        entity.setUpdatedAt(LocalDateTime.now());
        EntityMapper.entityModelMapper.map(request, entity);
        Feedback response = repository.save(entity);
        return EntityMapper.toObject(response,FeedbackResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"event")
        );
        repository.delete(entity);
    }

    public List<FeedbackResponse> findAll() {
       return  EntityMapper.toList(repository.findAll(),FeedbackResponse.class);
    }
}
