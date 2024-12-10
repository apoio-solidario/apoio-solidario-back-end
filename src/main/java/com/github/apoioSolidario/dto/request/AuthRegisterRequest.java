package com.github.apoioSolidario.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.apoioSolidario.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthRegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private UserRole role;
    @NotBlank
    private String password;
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;
    @NotBlank
    private String email;
}