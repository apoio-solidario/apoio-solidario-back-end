package com.github.apoioSolidario.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationResponse {
    @JsonProperty("location_id")
    private UUID locationId;
    @JsonProperty("street_name")
    private String streetName;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    private String country;
    private BigDecimal latitude ;
    private BigDecimal longitude ;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
