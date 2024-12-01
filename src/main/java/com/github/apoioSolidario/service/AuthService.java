package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.GenericMapper;
import com.github.apoioSolidario.dto.request.UserRequest;
import com.github.apoioSolidario.dto.response.UserResponse;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.exception.TokenExpiredException;
import com.github.apoioSolidario.exception.UserAlreadyExistException;
import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.model.Token;
import com.github.apoioSolidario.model.User;
import com.github.apoioSolidario.repository.OngRepository;
import com.github.apoioSolidario.repository.TokenRepository;
import com.github.apoioSolidario.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericMapper genericMapper;
    private final MailService mailService;
    private final OngRepository ongRepository;
    private final TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, GenericMapper genericMapper, MailService mailService, OngRepository ongRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.genericMapper = genericMapper;
        this.mailService = mailService;
        this.ongRepository = ongRepository;
        this.tokenRepository = tokenRepository;
    }

    public UserResponse register(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null)
            throw new UserAlreadyExistException("Usuario já cadastrado");
        String encriptPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encriptPass);
        User newUser = genericMapper.toObject(request, User.class);
        return genericMapper.toObject(userRepository.save(newUser), UserResponse.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void gerarResetToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        System.out.println("userid:" + user.getUserId());
        var entity = ongRepository.findByUser_UserId(user.getUserId()).orElseThrow(() -> new EntityNotFoundException("Ong atrelada ao usuario não encontrada"));
        System.out.println(entity.getEmail());
        Token token = new Token();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpired(false);
        String resetLink = "http://localhost:8080/api/v1/auth/redefinir-senha/confirmar?token=" + token.getToken();
        tokenRepository.save(token);
        mailService.sendEmail(entity.getEmail(), "Redefinir Senha", "Clique no link para redefinir sua senha: " + resetLink);

    }

    public void resetPassword(String token, String novaSenha) {
        Token reponse = tokenRepository.findByToken(token).orElseThrow(() -> new TokenExpiredException("Token inválido ou expirado"));
        if (reponse.isExpired()) {
            throw new TokenExpiredException("Token inválido ou expirado");
        }
        reponse.setExpired(true);
        User user = userRepository.findById(reponse.getUser().getUserId()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        user.setPassword(passwordEncoder.encode(novaSenha));
        tokenRepository.save(reponse);
        userRepository.save(user);
    }
}
