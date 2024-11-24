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
public class OngSocialResponse {
    @JsonProperty("ong_social_id")
    private UUID ongSocialId;
    private String platform;
    @JsonProperty("social_url")
    private String socialUrl;
    private String username;
    @JsonProperty("ong_id")
    private UUID ongId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
