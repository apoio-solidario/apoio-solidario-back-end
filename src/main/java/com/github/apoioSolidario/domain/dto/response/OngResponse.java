package com.github.apoioSolidario.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
    @JsonProperty("website_url")
    private String websiteUrl;
    private String email;
    private String phone;
    @JsonProperty("image_profile")
    private String imageProfile;
    @JsonProperty("image_banner")
    private String imageBanner;
    private String status;
    private String category;
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
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
