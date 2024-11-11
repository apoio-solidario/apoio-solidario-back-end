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
@Table(name = "campaigns")
public class Campaign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String title;
    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, name = "start_data")
    private LocalDateTime startData;
    @LastModifiedDate
    @Column(nullable = false, name = "end_data")
    private LocalDateTime endData;
    @Column( name = "goal_amount", precision = 10,scale = 2)
    private BigDecimal goalAmount ;
    @Column( name = "amount_raised", precision = 10,scale = 2)
    private BigDecimal amountRaised ;
    @Column(nullable = false,length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ong_id")
    private Ong ong;
    @OneToMany(mappedBy = "campaign",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
