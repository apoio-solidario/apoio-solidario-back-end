package com.github.apoioSolidario.domain.dto.request;

import com.github.apoioSolidario.domain.model.Campaing;
import com.github.apoioSolidario.domain.model.Event;
import com.github.apoioSolidario.domain.model.OngImage;
import com.github.apoioSolidario.domain.model.OngSocial;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngRequest {
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    private String description;
    @Size(max = 255)
    private String websiteUrl;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phone;
}
