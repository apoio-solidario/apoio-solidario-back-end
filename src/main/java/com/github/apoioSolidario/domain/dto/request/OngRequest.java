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
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(min = 5)
    private String description;
    @Size(max = 255)
    private String websiteUrl;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phone;
}
