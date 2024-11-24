package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Override
    Page<Location> findAll(Pageable pageable);

}
