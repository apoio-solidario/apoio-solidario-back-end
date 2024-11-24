package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OngImageRepository extends JpaRepository<Image, UUID> {
    @Override
    Page<Image> findAll(Pageable pageable);
}
