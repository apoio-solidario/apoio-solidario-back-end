package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.OngImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngImageRepository extends JpaRepository<OngImage,Long> {
}
