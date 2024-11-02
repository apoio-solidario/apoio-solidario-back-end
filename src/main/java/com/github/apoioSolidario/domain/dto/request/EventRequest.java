package com.github.apoioSolidario.domain.dto.request;

import com.github.apoioSolidario.domain.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private LocalDateTime startData;
    @NotNull
    private LocalDateTime endData;
    @NotNull
    private Long location;
    @NotNull
    private Long ong;
}
