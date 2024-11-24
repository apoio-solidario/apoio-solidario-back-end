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
public class ImageResponse {
    @JsonProperty("image_id")
    private UUID imageId;
    @JsonProperty("image_name")
    private String imageName;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("entity_id")
    private UUID entityId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
