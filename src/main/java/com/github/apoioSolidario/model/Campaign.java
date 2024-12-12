package com.github.apoioSolidario.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "campaigns")
public class Campaign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID campaignId;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false, name = "start_data")
    private LocalDateTime startData;
    @Column(nullable = false, name = "end_data")
    private LocalDateTime endData;
    @Column(name = "goal_amount", precision = 10, scale = 2)
    private BigDecimal goalAmount;
    @Column(name = "amount_raised", precision = 10, scale = 2)
    private BigDecimal amountRaised;
    @Column(name = "image_profile")
    private String imageProfile;
    @Column(name = "image_banner")
    private String imageBanner;
    @Column(nullable = false, length = 50)
    private String status;
    @Column(name = "handler", length = 100, unique = true)
    private String handler;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ong_id")
    private Ong ong;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Campaign(String title, String status) {
        this.title = title;
        this.status = status;
    }
}
