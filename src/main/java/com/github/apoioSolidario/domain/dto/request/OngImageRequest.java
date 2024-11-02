package com.github.apoioSolidario.domain.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngImageRequest {
    @NotBlank
    private String type;
    @NotBlank
    private String imageUrl;
    private Long ong;

}
