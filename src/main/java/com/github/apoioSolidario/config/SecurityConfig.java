package com.github.apoioSolidario.config;

import com.github.apoioSolidario.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                /*.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(HttpMethod.GET,"/api/v1/ongs/${id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/ongs/user/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1//ongs/handler/{handler}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/ongs/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/ongs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/ongs").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/ongSocials/${id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/ongSocials/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/ongSocials").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/ongSocials").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/feedbacks/${id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/feedbacks/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/feedbacks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/feedbacks").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/images/${id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/images/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/images/ongs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/images").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/locations/${id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/locations/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/locations").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/locations").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/campaigns/${id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/campaigns/handler/{handler}").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,"/api/v1/campaigns/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/campaigns").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/campaigns").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/v1/events/${id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1//events/handler/{handler}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/events/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/Logout").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/users/${id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/${id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/${id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")




                        .requestMatchers(HttpMethod.GET,"/events").permitAll()
                        .requestMatchers(HttpMethod.GET,"/campaigns").permitAll()
                        .requestMatchers(HttpMethod.GET,"/ongs").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset-password/confirm").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                       // .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")

                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)*/
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
