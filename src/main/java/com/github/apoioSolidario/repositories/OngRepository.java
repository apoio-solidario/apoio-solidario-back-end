package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong,Long> {
}
