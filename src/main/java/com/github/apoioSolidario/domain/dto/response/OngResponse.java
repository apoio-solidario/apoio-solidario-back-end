package com.github.apoioSolidario.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.apoioSolidario.domain.model.Campaing;
import com.github.apoioSolidario.domain.model.Event;
import com.github.apoioSolidario.domain.model.OngImage;
import com.github.apoioSolidario.domain.model.OngSocial;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngResponse {
    private Long id;
    private String name;
    private String description;
    private String websiteUrl;
    private String email;
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OngImage> images;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OngSocial> socials;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Campaing> Campaings;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Event> events;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
