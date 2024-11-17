package com.github.apoioSolidario.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.apoioSolidario.domain.model.Ong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngSocialResponse {
    private Long id;
    private String platform;
    @JsonProperty("social_url")
    private String socialUrl;
    private String username;
    @JsonProperty("ong_id")
    private Long ongId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
