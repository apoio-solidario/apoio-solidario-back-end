package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngImageRepository extends JpaRepository<Image,Long> {
    @Override
    Page<Image> findAll(Pageable pageable);
}
