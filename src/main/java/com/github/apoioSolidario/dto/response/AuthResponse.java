package com.github.apoioSolidario.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthResponse{
    private String username;
    @JsonProperty("expired_at")
    private int expiredAt;
}
