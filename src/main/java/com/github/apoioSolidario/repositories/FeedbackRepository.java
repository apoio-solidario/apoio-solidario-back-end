package com.github.apoioSolidario.repositories;

import com.github.apoioSolidario.domain.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
