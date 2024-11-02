package com.github.apoioSolidario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street_name", nullable = true,length = 100)
    private String streetName;
    @Column( nullable = true,length = 20)
    private String number;
    @Column( nullable = true,length = 100)
    private String complement;
    @Column( nullable = true,length = 100)
    private String neighborhood;
    @Column( nullable = false,length = 100)
    private String city;
    @Column( nullable = false,length = 100)
    private String state;
    @Column( name = "postal_code", nullable = false,length = 20)
    private String postalCode;
    @Column( nullable = false,length = 100)
    private String country;
    @Column( precision = 9,scale = 6)
    private BigDecimal latitude ;
    @Column( precision = 9,scale = 6)
    private BigDecimal longitude ;
    @OneToMany(mappedBy = "location",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Event> events;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
