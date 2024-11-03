package com.github.apoioSolidario.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngImageResponse {
    private Long id;
    private String type;
    private String imageUrl;
    private Long ongId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
