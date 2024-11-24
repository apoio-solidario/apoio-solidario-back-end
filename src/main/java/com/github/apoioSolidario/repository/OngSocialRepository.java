package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.OngSocial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OngSocialRepository extends JpaRepository<OngSocial, UUID> {
    @Override
    Page<OngSocial> findAll(Pageable pageable);
}
