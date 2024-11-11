package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.Ong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong,Long> {
    @Override
    Page<Ong> findAll(Pageable pageable);
}