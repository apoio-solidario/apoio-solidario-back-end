package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.OngSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngSocialRepository extends JpaRepository<OngSocial,Long> {
}
