package com.github.apoioSolidario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OngRequest {
    @NotBlank(message = "O nome não pode estar vazio.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String name;
    @NotBlank(message = "A descrição não pode estar vazia.")
    @Size(min = 5, message = "O descrição deve ter no mínimo 5 caracteres.")
    private String description;
    @NotBlank(message = "O content não pode estar vazia.")
    @Size(min = 5, message = "O content deve ter no mínimo 5 caracteres.")
    private String content;

    @Size(max = 255, message = "A URL do site deve ter no máximo 255 caracteres.")
    @JsonProperty("website_url")
    private String websiteUrl;
    @NotBlank(message = "A imagem de perfil não pode estar vazia.")
    @JsonProperty("image_profile")
    private String imageProfile;
    @NotBlank(message = "A imagem do banner não pode estar vazia.")
    @JsonProperty("image_banner")
    private String imageBanner;
    @NotBlank(message = "O status não pode estar vazio.")
    @Size(max = 50, message = "O status deve ter no máximo 50 caracteres.")
    private String status;
    @NotBlank(message = "A Categoria não pode estar vazia.")
    private String category;
    @NotBlank(message = "A handler não pode estar vazia.")
    private String handler;
    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Email(message = "O e-mail deve ser válido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    private String email;
    @NotNull(message = "O ID do usuario não pode ser nulo.")
    @JsonProperty("user_id")
    private UUID userId;
    @NotBlank(message = "O telefone não pode estar vazio.")
    private String phone;
}
