package com.cadastro.usuarios.infra.exceptions;

public record ValidationErrorDTO(String campo, String mensagem) {}