package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.GenericMapper;
import com.github.apoioSolidario.dto.request.OngRequest;
import com.github.apoioSolidario.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.dto.response.OngResponse;
import com.github.apoioSolidario.exception.AccessDeniedException;
import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.exception.UniqueDataException;
import com.github.apoioSolidario.repository.OngRepository;
import com.github.apoioSolidario.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OngService {

    private final OngRepository repository;
    private final UserRepository userRepository;
    private final GenericMapper mapper;
    private final TokenService tokenService;

    public OngService(OngRepository repository, UserRepository userRepository, GenericMapper mapper, TokenService tokenService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public Page<OngResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable), OngResponse.class);
    }

    public OngResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Ong com id %s não encontrada", id)));
        return mapper.toObject(entity, OngResponse.class);
    }
    @Transactional()
    public OngResponse findByUserId(UUID id,String category,String status) {
        var entity = repository.findByUser_UserId(id).orElseThrow(()->new EntityNotFoundException("Ong atrelada ao usuario não encontrada"));
        return  mapper.toObject(entity, OngResponse.class);
    }
    @Transactional()
    public OngResponse findByHandler(String handler) {
        var entity = repository.findByHandler(handler).orElseThrow(() -> new EntityNotFoundException(String.format("Ong com handler %s não encontrada", handler)));
        if(!checkAccessOng(entity.getUser().getUserId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return  mapper.toObject(entity, OngResponse.class);
    }

    public OngResponse save(OngRequest ongRequest) {
        try {
            this.userRepository.findById(ongRequest.getUserId()).orElseThrow(()->new EntityNotFoundException(ongRequest.getUserId(),"user"));
            Ong entity = mapper.toObject(ongRequest, Ong.class);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            return mapper.toObject(repository.save(entity), OngResponse.class);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueDataException("O nome da ong devem ser unicos");
        }
    }

    public OngResponse update(UUID id, @Valid OngRequest ongRequest) {
        try {
            var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Ong"));
            this.userRepository.findById(ongRequest.getUserId()).orElseThrow(()->new EntityNotFoundException(ongRequest.getUserId(),"user"));
            entity.setUpdatedAt(LocalDateTime.now());
            mapper.objectToObject(ongRequest, entity);
            Ong response = repository.save(entity);
            return mapper.toObject(response, OngResponse.class);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueDataException("O nome da ong devem ser unicos");
        }
    }

    public OngResponse updateStatus(@Valid UUID id, @Valid UpdateStatusRequest ongRequest) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Ong"));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setStatus(ongRequest.getStatus());
        return mapper.toObject(repository.save(entity), OngResponse.class);

    }

    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Ong"));
        repository.delete(entity);
    }


    public boolean checkAccessOng(UUID userId) {
        var logado = tokenService.getPrincipal();
        return tokenService.isAdmin() || logado.getUserId().equals(userId);
    }




}