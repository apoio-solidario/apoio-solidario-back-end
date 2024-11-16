package com.github.apoioSolidario.domain.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {
    @NotBlank(message = "O nome da imagem não pode estar vazio.")
    @Size(max = 255, message = "O nome da imagem deve ter no máximo 255 caracteres.")
    @JsonProperty("image_name")
    private String imageName;
    @NotBlank(message = "A URL da imagem não pode estar vazia.")
    @Size(max = 255, message = "A URL da imagem deve ter no máximo 255 caracteres.")
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("entity_id")
    private Long entityId;

}
