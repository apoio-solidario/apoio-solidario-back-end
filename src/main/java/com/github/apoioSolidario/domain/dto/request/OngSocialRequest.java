package com.github.apoioSolidario.domain.dto.request;

import com.github.apoioSolidario.domain.model.Ong;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "A plataforma não pode estar vazia.")
    @Size(max = 50, message = "A plataforma deve ter no máximo 50 caracteres.")
    private String platform;
    @NotBlank(message = "A URL social não pode estar vazia.")
    @Size(max = 255, message = "A URL social deve ter no máximo 255 caracteres.")
    private String socialUrl;
    @NotBlank(message = "O nome de usuário não pode estar vazio.")
    @Size(max = 100, message = "O nome de usuário deve ter no máximo 100 caracteres.")
    private String username;
    private Long ongId;
}
