package com.github.apoioSolidario.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "feedbacks")
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID feedbackId;
    @Column(nullable = false,length = 100)
    private String username;
    @Column(nullable = false,length = 100)
    private String email;
    @Lob
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private int rating;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    @CreatedDate
    @Column(name = "Submitted_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
