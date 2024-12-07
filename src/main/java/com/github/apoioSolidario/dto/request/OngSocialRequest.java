package com.github.apoioSolidario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OngSocialRequest {
    @NotBlank(message = "A plataforma não pode estar vazia.")
    @Size(max = 50, message = "A plataforma deve ter no máximo 50 caracteres.")
    private String platform;
    @NotBlank(message = "A URL social não pode estar vazia.")
    @Size(max = 255, message = "A URL social deve ter no máximo 255 caracteres.")
    @JsonProperty("social_url")
    private String socialUrl;
    @NotBlank(message = "O nome de usuário não pode estar vazio.")
    @Size(max = 100, message = "O nome de usuário deve ter no máximo 100 caracteres.")
    private String username;
    @JsonProperty("ong_id")
    private UUID ongId;
}
