package com.cadastro.usuarios.infra.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}
