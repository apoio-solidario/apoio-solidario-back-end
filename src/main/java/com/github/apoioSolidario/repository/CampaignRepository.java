package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    @Override
    Page<Campaign> findAll(Pageable pageable);
}
