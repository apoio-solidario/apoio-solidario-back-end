package com.github.apoioSolidario.domain.dto.response;

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
    private LocationResponse location;
    private OngResponse ong;
    private List<FeedbackResponse> feedbacks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
