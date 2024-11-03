package com.github.apoioSolidario.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private OngResponse ong;
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FeedbackResponse> feedbacks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
