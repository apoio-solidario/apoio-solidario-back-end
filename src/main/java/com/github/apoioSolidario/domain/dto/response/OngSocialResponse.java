package com.github.apoioSolidario.domain.dto.response;

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
    private String socialUrl;
    private String username;
    private OngResponse ong;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
