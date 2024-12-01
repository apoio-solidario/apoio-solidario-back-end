package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.GenericMapper;
import com.github.apoioSolidario.dto.request.UserRequest;
import com.github.apoioSolidario.dto.response.OngResponse;
import com.github.apoioSolidario.dto.response.UserResponse;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.exception.UserAlreadyExistException;
import com.github.apoioSolidario.model.User;
import com.github.apoioSolidario.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericMapper genericMapper;;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, GenericMapper genericMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.genericMapper = genericMapper;
    }

    public UserResponse save(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null){
            throw new UserAlreadyExistException("Usuario já cadastrado");
        }
        String encriptPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encriptPass);
        User newUser = genericMapper.toObject(request, User.class);
        return genericMapper.toObject(userRepository.save(newUser), UserResponse.class);
    }

    public Page<UserResponse> findAll(Pageable pageable) {
        return genericMapper.toPage(userRepository.findAll(pageable), UserResponse.class);
    }

    public UserResponse finById(@Valid UUID id) {
        var entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User com id %s não encontrado", id)));

        return genericMapper.toObject(entity, UserResponse.class);
    }

    public UserResponse update(@Valid UUID id, @Valid UserRequest userRequest) {
        var entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User"));
        if (userRepository.findByUsername(userRequest.getUsername()) != null){
            throw new UserAlreadyExistException("Usuario indisponivel");
        }
        entity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        entity.setUsername(userRequest.getUsername());
        return genericMapper.toObject(userRepository.save(entity), UserResponse.class);
    }

    public void deleteById(@Valid UUID id) {
        var entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User"));
        userRepository.delete(entity);
    }
}
