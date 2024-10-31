package com.github.apoioSolidario.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException(Long id, String entity) {
        super(String.format("A %s com o id %d não foi encontrado", entity, id));
    }

}
