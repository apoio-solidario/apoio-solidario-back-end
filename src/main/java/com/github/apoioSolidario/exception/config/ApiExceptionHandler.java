package com.github.apoioSolidario.exception.config;

import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.exception.GenerationTokenException;
import com.github.apoioSolidario.exception.UniqueDataException;
import com.github.apoioSolidario.exception.UserAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler{


    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request,BindingResult result) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY,"Dados invalidos", ex.getBindingResult()));
    }
    @ExceptionHandler(UniqueDataException.class)
    public ResponseEntity<ErrorMessage> uniqueDataException(UniqueDataException ex,HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT,ex.getMessage()));
    }

    @ExceptionHandler(GenerationTokenException.class)
    public ResponseEntity<ErrorMessage> generationTokenException(GenerationTokenException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.BAD_REQUEST,ex.getMessage()));
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistException(UserAlreadyExistException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT,ex.getMessage()));
    }




}
