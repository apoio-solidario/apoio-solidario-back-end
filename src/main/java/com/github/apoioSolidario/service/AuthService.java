package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.GenericMapper;
import com.github.apoioSolidario.dto.request.UserRequest;
import com.github.apoioSolidario.dto.response.UserResponse;
import com.github.apoioSolidario.exception.UserAlreadyExistException;
import com.github.apoioSolidario.model.User;
import com.github.apoioSolidario.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericMapper genericMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, GenericMapper genericMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.genericMapper = genericMapper;
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
}
