package com.github.apoioSolidario.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngRequest {
    @NotBlank(message = "O nome não pode estar vazio.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String name;

    @NotBlank(message = "A descrição não pode estar vazia.")
    @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres.")
    private String description;

    @Size(max = 255, message = "A URL do site deve ter no máximo 255 caracteres.")
    private String websiteUrl;

    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Email(message = "O e-mail deve ser válido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    private String email;

    @NotBlank(message = "O telefone não pode estar vazio.")
    private String phone;
}
