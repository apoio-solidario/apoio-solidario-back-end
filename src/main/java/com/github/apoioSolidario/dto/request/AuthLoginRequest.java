package com.github.apoioSolidario.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}