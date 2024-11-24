package com.github.apoioSolidario.model;

import com.github.apoioSolidario.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "users")
@Entity(name = "users")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    private String username;
    private String password;
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}
