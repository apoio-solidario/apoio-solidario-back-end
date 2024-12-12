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
@Table(name = "ongs")
public class Ong implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ongId;
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(name = "website_url")
    private String websiteUrl;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false, name = "image_profile")
    private String imageProfile;
    @Column(nullable = false, name = "image_banner")
    private String imageBanner;
    @Column(nullable = false, length = 50)
    private String status;
    @Column(nullable = false, name = "category", length = 100)
    private String category;
    @Column(nullable = false, name = "handler", length = 100, unique = true)
    private String handler;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Ong(String name, String category, String status) {
        this.name = name;
        this.category = category;
        this.status = status;
    }
}
