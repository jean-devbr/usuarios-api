package com.cadastro.usuarios.core.dto;

public record LoginResponseDTO(
        Long id,
        String nome,
        String mensagem
) {}