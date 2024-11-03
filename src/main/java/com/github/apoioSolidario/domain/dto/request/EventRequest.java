package com.github.apoioSolidario.domain.dto.request;

import com.github.apoioSolidario.domain.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @NotBlank(message = "O título não pode estar vazio.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String title;
    @NotBlank(message = "A descrição não pode estar vazia.")
    @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres.")
    private String description;
    @NotNull(message = "A data de início não pode ser nula.")
    private LocalDateTime startData;
    @NotNull(message = "A data de término não pode ser nula.")
    private LocalDateTime endData;
    @NotNull(message = "O ID da localização não pode ser nulo.")
    private Long location;
    @NotNull(message = "O ID da ONG não pode ser nulo.")
    private Long ong;
}
