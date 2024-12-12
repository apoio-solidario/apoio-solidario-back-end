package com.github.apoioSolidario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.apoioSolidario.enums.UserRole;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserResponse {
    @JsonProperty("user_id")
    private UUID userId;
    private String username;
    private UserRole role;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
}
