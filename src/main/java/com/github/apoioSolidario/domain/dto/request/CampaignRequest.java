package com.github.apoioSolidario.domain.dto.request;

import com.github.apoioSolidario.domain.model.Feedback;
import com.github.apoioSolidario.domain.model.Ong;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private LocalDateTime startData;
    @NotNull
    private LocalDateTime endData;
    @NotNull
    private BigDecimal goalAmount;
    @NotNull
    private BigDecimal amountRaised;
    @NotBlank
    private String status;
    private Long ong;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
