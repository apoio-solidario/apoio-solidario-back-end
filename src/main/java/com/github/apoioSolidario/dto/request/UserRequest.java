package com.github.apoioSolidario.dto.request;
import com.github.apoioSolidario.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private UserRole role;
}
