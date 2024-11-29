package com.github.apoioSolidario.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CampaignResponse {
    @JsonProperty("campaign_id")
    private UUID campaignId;
    private String title;
    private String description;
    @JsonProperty("start_data")
    private LocalDateTime startData;
    @JsonProperty("end_data")
    private LocalDateTime endData;
    @JsonProperty("goal_amount")
    private BigDecimal goalAmount;
    @JsonProperty("amount_raised")
    private BigDecimal amountRaised;
    @JsonProperty("image_profile")
    private String imageProfile;
    @JsonProperty("image_banner")
    private String imageBanner;
    private String handler;
    private String status;
    @JsonProperty("ong_id")
    private UUID ongId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
