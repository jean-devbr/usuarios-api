package com.cadastro.usuarios.core.infra.exceptions;

public record ValidationErrorDTO(String campo, String mensagem) {}