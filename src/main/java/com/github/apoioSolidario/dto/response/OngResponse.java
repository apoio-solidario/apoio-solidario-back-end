package com.github.apoioSolidario.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OngResponse {
    @JsonProperty("ong_id")
    private UUID ongId;
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