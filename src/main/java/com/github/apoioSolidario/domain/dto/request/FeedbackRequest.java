package com.github.apoioSolidario.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
    @NotBlank(message = "O nome de usuário não pode estar vazio.")
    @Size(max = 100, message = "O nome de usuário deve ter no máximo 100 caracteres.")
    private String username;
    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Email(message = "O e-mail deve ser válido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    private String email;
    @NotBlank(message = "O conteúdo não pode estar vazio.")
    private String content;
    @NotNull(message = "A avaliação não pode ser nula.")
    @Min(value = 1, message = "A avaliação deve ser no mínimo 1.")
    @Max(value = 5, message = "A avaliação deve ser no máximo 5.")
    private int rating;
    private Long event;
    private Long campaign;

}
