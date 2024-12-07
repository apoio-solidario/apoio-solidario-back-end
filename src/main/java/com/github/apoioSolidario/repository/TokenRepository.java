package com.github.apoioSolidario.repository;

import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByToken(String token);
    Optional<Ong> findByUser_UserId(UUID userId);

}
