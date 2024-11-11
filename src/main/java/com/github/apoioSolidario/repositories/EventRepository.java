package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    @Override
    Page<Event> findAll(Pageable pageable);
}
