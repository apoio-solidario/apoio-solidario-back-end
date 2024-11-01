package com.github.apoioSolidario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ongs")
public class Ong implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String name;
    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = true,name = "website_url",length = 255)
    private String websiteUrl;
    @Column(nullable = false,length = 100)
    private String email;
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "ong",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OngImage> images;
    @OneToMany(mappedBy = "ong",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OngSocial> socials;
    @OneToMany(mappedBy = "ong",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Campaing> Campaings;
    @OneToMany(mappedBy = "ong",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Event> events;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
