package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Feedback;
import com.github.apoioSolidario.model.Image;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    @Override
    Page<Feedback> findAll(Pageable pageable);
    Page<Feedback> findByCampaign_CampaignId(@Valid UUID id, Pageable pageable);
    Page<Feedback> findByEvent_EventId(@Valid UUID id, Pageable pageable);

}
