package com.github.apoioSolidario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CampaignRequest {
    @NotBlank(message = "O título não pode estar vazio.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String title;
    @NotBlank(message = "A descrição não pode estar vazia.")
    @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres.")
    private String description;
    @NotNull(message = "A data de início não pode ser nula.")
    @JsonProperty("start_data")
    private LocalDateTime startData;
    @NotNull(message = "A data de término não pode ser nula.")
    @JsonProperty("end_data")
    private LocalDateTime endData;
    @NotNull(message = "O valor da meta não pode ser nulo.")
    @JsonProperty("goal_amount")
    private BigDecimal goalAmount;
    @NotNull(message = "O valor arrecadado não pode ser nulo.")
    @JsonProperty("amount_raised")
    private BigDecimal amountRaised;
    @NotBlank(message = "A imagem de perfil não pode estar vazia.")
    @JsonProperty("image_profile")
    private String imageProfile;
    @NotBlank(message = "A imagem do banner não pode estar vazia.")
    @JsonProperty("image_banner")
    private String imageBanner;
    @NotBlank(message = "O status não pode estar vazio.")
    @Size(max = 50, message = "O status deve ter no máximo 50 caracteres.")
    private String status;
    @NotNull(message = "O ID da ONG não pode ser nulo.")
    @JsonProperty("ong_id")
    private UUID ongId;

}
