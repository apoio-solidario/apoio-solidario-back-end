package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Ong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OngRepository extends JpaRepository<Ong, UUID> {
    @Override
    Page<Ong> findAll(Pageable pageable);
}