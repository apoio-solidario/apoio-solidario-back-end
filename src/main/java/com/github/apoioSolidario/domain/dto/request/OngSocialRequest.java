package com.github.apoioSolidario.domain.dto.request;

import com.github.apoioSolidario.domain.model.Ong;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngSocialRequest {
    @NotBlank
    private String platform;
    @NotBlank
    private String socialUrl;
    @NotBlank
    private String username;
    private Long ong;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
