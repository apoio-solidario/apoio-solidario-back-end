package com.github.apoioSolidario.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException(UUID id, String entity) {
        super(String.format("A %s com o id %s não foi encontrado", entity, id));
    }

}
