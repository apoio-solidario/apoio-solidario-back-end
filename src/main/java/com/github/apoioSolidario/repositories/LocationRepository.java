package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Override
    Page<Location> findAll(Pageable pageable);

}
