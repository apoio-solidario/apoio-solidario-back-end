package com.github.apoioSolidario.config;

import com.github.apoioSolidario.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Value("${server.servlet.context-path}")
    String contextPath;

    @Value("${server.servlet.session.cookie.name}")
    String sessionCookieName;

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
                        .requestMatchers(HttpMethod.POST, contextPath + "users").hasRole("ADMIN")

                        //ROTAS LIVRES
                        .requestMatchers(HttpMethod.GET, contextPath + "events", "/events/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, contextPath + "campaigns", "/campaigns/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, contextPath + "ongs", "/ongs/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, contextPath + "feedbacks/${id}").permitAll()
                        .requestMatchers(HttpMethod.GET, contextPath + "feedbacks/event/${id}", "/feedbacks/campaign/{id}").permitAll()

                        //AUTH
                        .requestMatchers(HttpMethod.GET, "/auth/me").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/auth/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset-password", "/auth/reset-password/confirm").permitAll()

                        //SWAGGER
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .deleteCookies(sessionCookieName)
                        .invalidateHttpSession(true)
                )
                .securityContext((context) ->
                        context.securityContextRepository(httpSessionSecurityContextRepository())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthService authService, PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
