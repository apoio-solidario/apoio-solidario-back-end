package com.github.apoioSolidario.domain.dto.response;

import com.github.apoioSolidario.domain.model.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Long id;
    private String streetName;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private BigDecimal latitude ;
    private BigDecimal longitude ;
    private List<Event> events;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
