package com.github.apoioSolidario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class EventResponse {
    @JsonProperty("event_id")
    private UUID eventId;
    private String title;
    private String description;
    private String content;
    @JsonProperty("start_data")
    private LocalDateTime startData;
    @JsonProperty("image_profile")
    private String imageProfile;
    @JsonProperty("image_banner")
    private String imageBanner;
    private String status;
    private String handler;
    @JsonProperty("location_id")
    private UUID locationId;
    @JsonProperty("ong_id")
    private UUID ongId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
