package com.github.apoioSolidario.domain.dto.response;

import com.github.apoioSolidario.domain.model.Feedback;
import com.github.apoioSolidario.domain.model.Ong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startData;
    private LocalDateTime endData;
    private BigDecimal goalAmount ;
    private BigDecimal amountRaised ;
    private String status;
    private Ong ong;
    private List<Feedback> feedbacks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
