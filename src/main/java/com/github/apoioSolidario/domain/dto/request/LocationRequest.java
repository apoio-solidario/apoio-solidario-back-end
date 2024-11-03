package com.github.apoioSolidario.domain.dto.request;

import com.github.apoioSolidario.domain.model.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    @NotBlank(message = "O nome da rua não pode estar vazio.")
    @Size(max = 100, message = "O nome da rua deve ter no máximo 100 caracteres.")
    private String streetName;
    @NotBlank(message = "O número não pode estar vazio.")
    @Size(max = 20, message = "O número deve ter no máximo 20 caracteres.")
    private String number;
    @NotBlank(message = "O complemento não pode estar vazio.")
    @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres.")
    private String complement;
    @NotBlank(message = "O bairro não pode estar vazio.")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres.")
    private String neighborhood;
    @NotBlank(message = "A cidade não pode estar vazia.")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres.")
    private String city;
    @NotBlank(message = "O estado não pode estar vazio.")
    @Size(max = 100, message = "O estado deve ter no máximo 100 caracteres.")
    private String state;
    @NotBlank(message = "O código postal não pode estar vazio.")
    @Size(max = 20, message = "O código postal deve ter no máximo 20 caracteres.")
    private String postalCode;
    @NotBlank(message = "O país não pode estar vazio.")
    @Size(max = 100, message = "O país deve ter no máximo 100 caracteres.")
    private String country;
    @NotNull(message = "A latitude não pode ser nula.")
    private BigDecimal latitude;
    @NotNull(message = "A longitude não pode ser nula.")
    private BigDecimal longitude;

}
