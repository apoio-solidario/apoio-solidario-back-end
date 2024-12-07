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
@Table(name = "ong_socials")
public class OngSocial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ongSocialId;
    @Column(nullable = false,length = 50)
    private String platform;
    @Column(nullable = false,length = 255)
    private String socialUrl;
    @Column(nullable = false,length = 100)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ong_id")
    private Ong ong;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
