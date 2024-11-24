package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Override
    Page<Event> findAll(Pageable pageable);
}
