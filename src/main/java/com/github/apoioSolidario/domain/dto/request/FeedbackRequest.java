package com.github.apoioSolidario.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String content;
    @NotNull
    private int rating;
    private Long event;
    private Long campaign;
}
