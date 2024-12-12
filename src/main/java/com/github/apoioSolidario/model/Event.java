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
@Table(name = "events")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "start_data")
    private LocalDateTime startData;
    @Column(name = "end_data")
    private LocalDateTime endData;
    @Column(name = "image_profile", length = 255)
    private String imageProfile;
    @Column(name = "image_banner", length = 255)
    private String imageBanner;
    @Column(nullable = false, length = 50)
    private String status;
    @Column(nullable = false, name = "handler", length = 100, unique = true)
    private String handler;
    @ManyToOne
    @JoinColumn(name = "ong_id")
    private Ong ong;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Event(String title, String status) {
        this.title = title;
        this.status = status;
    }
}
