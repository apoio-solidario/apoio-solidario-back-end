package com.github.apoioSolidario.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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
    @JsonProperty("start_data")
    private LocalDateTime startData;
    @JsonProperty("image_profile")
    private String imageProfile;
    @JsonProperty("image_banner")
    private String imageBanner;
    private String status;
    @JsonProperty("location_id")
    private UUID locationId;
    @JsonProperty("ong_id")
    private UUID ongId;
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FeedbackResponse> feedbacks;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
