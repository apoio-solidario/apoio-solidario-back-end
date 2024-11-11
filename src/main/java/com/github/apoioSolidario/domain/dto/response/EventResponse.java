package com.github.apoioSolidario.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.apoioSolidario.domain.model.Feedback;
import com.github.apoioSolidario.domain.model.Location;
import com.github.apoioSolidario.domain.model.Ong;
import jakarta.persistence.*;
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
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startData;
    @JsonBackReference
    private LocationResponse location;
    @JsonBackReference
    private OngResponse ong;
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FeedbackResponse> feedbacks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
