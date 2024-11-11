package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    @Override
    Page<Campaign> findAll(Pageable pageable);
}
