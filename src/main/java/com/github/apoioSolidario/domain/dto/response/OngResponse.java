package com.github.apoioSolidario.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ImageResponse> images;
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OngSocialResponse> socials;
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CampaignResponse> campaigns;
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EventResponse> events;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
