package com.github.apoioSolidario.domain.dto.response;

import com.github.apoioSolidario.domain.model.Campaign;
import com.github.apoioSolidario.domain.model.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Long id;
    private String username;
    private String email;
    private String content;
    private int rating;
    private Event event;
    private Campaign campaign;
}
