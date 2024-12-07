package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Campaign;
import com.github.apoioSolidario.model.Event;
import com.github.apoioSolidario.model.Ong;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    @Override
    Page<Campaign> findAll(Pageable pageable);
    Optional<Campaign> findByHandler(String handler);
    Page<Campaign> findByOng_OngId(@Valid UUID id, Pageable pageable);

}
