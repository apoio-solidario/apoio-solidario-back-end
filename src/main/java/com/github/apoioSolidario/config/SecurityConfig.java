package com.github.apoioSolidario.config;

import com.github.apoioSolidario.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Value("${server.servlet.context-path}")
    String contextPath;
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        //ONGS
                        .requestMatchers(HttpMethod.GET, contextPath + "ongs/user/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, contextPath + "ongs/handler/${handler}").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.PUT, contextPath + "ongs/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, contextPath + "ongs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "ongs").hasRole("ADMIN")
                        //ONGSOCIAL
                        .requestMatchers(HttpMethod.GET, contextPath + "ongSocials").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, contextPath + "ongSocials/ong/{id}").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.GET, contextPath + "ongSocials/${id}").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.PUT, contextPath + "ongSocials/*").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.POST, contextPath + "ongSocials").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "ongSocials").hasAnyRole("ADMIN", "ONG")
                        //IMAGES
                        .requestMatchers(HttpMethod.GET, contextPath + "images").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, contextPath + "images/${id}").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.PUT, contextPath + "images/*").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.POST, contextPath + "images/ongs/${id}").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "images").hasAnyRole("ADMIN", "ONG")
                        //LOCATIONS
                        .requestMatchers(HttpMethod.GET, contextPath + "locations/${id}", contextPath + "locations").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, contextPath + "locations/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, contextPath + "locations").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "locations").hasRole("ADMIN")
                        //CAMPANHA
                        .requestMatchers(HttpMethod.GET, contextPath + "campaigns/ong/${id}", contextPath + "campaigns/handler/${handler}").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.PUT, contextPath + "campaigns/*").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.POST, contextPath + "campaigns").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "campaigns").hasAnyRole("ADMIN", "ONG")
                        //EVENTS
                        .requestMatchers(HttpMethod.GET, contextPath + "events/handler/${handler}", contextPath + "/events/ong/{id}").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.GET, contextPath + "events/location/${id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, contextPath + "events/*").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.POST, contextPath + "events").hasAnyRole("ADMIN", "ONG")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "events").hasAnyRole("ADMIN", "ONG")
                        //FEEDBACKS
                        .requestMatchers(HttpMethod.GET, contextPath + "feedbacks").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, contextPath + "feedbacks/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, contextPath + "feedbacks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "feedbacks").hasRole("ADMIN")
                        //USER
                        .requestMatchers(HttpMethod.GET, contextPath + "users", contextPath + "users/${id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, contextPath + "users/${id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, contextPath + "users/${id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        //ROTAS LIVRES
                        .requestMatchers(HttpMethod.GET, "/events", "/events/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/campaigns", "/campaigns/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ongs", "/ongs/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/feedbacks/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/feedbacks/event/${id}", "/feedbacks/campaign/{id}").permitAll()

                        //AUTH
                        .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()
                        .requestMatchers(HttpMethod.POST, "/auth/logout").authenticated()
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset-password", "/auth/reset-password/confirm").permitAll()

                        //SWAGGER
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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
