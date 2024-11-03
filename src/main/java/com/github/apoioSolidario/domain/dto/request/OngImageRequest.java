package com.github.apoioSolidario.domain.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngImageRequest {
    @NotBlank(message = "O tipo da imagem não pode estar vazio.")
    @Size(max = 50, message = "O tipo da imagem deve ter no máximo 50 caracteres.")
    private String type;
    @NotBlank(message = "A URL da imagem não pode estar vazia.")
    @Size(max = 255, message = "A URL da imagem deve ter no máximo 255 caracteres.")
    private String imageUrl;
    private Long ongId; // Referência ao ID da ONG, sem validação aqui.

}
