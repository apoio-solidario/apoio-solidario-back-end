package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Event;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Override
    Page<Event> findAll(Pageable pageable);

    Optional<Event> findByHandler(String handler);

    Page<Event> findByOng_OngId(@Valid UUID id, Pageable pageable);
//    Page<Event> findByLocation_LocationId(@Valid UUID id, Pageable pageable);

}
