package com.cadastro.usuarios.core.dto.login;

public record LoginResponseDTO(
        Long id,
        String nome,
        String mensagem,
        String token
) {}