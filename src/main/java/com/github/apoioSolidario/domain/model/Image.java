package com.github.apoioSolidario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_name", nullable = false,length = 255)
    private String imageName;
    @Column(name = "image_url", nullable = false,length = 255)
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_Id")
    private Ong ong;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
