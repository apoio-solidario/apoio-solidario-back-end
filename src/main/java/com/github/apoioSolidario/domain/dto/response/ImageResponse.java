package com.github.apoioSolidario.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private Long id;
    @JsonProperty("image_name")
    private String imageName;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("entity_id")
    private Long entityId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
