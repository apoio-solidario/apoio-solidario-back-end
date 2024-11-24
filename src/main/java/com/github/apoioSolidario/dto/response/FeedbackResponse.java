package com.github.apoioSolidario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FeedbackResponse {
    @JsonProperty("feedback_id")
    private UUID feedbackId;
    private String username;
    private String email;
    private String content;
    private int rating;
    @JsonProperty("event_id")
    private UUID eventId;
    @JsonProperty("campaign_id")
    private UUID campaignId;
}
