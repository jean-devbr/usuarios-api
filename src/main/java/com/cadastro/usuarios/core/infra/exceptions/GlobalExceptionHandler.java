package com.cadastro.usuarios.core.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ValidationErrorDTO> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationErrorDTO(err.getField(), err.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessageDTO> handleBusinessError(BusinessException ex) {
        return ResponseEntity.status(400).body(new ErrorMessageDTO(ex.getMessage()));
    }
}