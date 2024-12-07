package com.github.apoioSolidario.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ImageUpdateRequest {
    @NotBlank(message = "O nome da imagem não pode estar vazio.")
    @Size(max = 255, message = "O nome da imagem deve ter no máximo 255 caracteres.")
    @JsonProperty("image_name")
    private String imageName;
    @JsonProperty("entity_id")
    private UUID entityId;
    private MultipartFile file;

}
