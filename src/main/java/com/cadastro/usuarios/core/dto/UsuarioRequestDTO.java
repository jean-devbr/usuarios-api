package com.cadastro.usuarios.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres")
        String nome,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        String senha
) {}