package com.cadastro.usuarios.core.service;

import com.cadastro.usuarios.core.dto.UsuarioRequestDTO;
import com.cadastro.usuarios.core.dto.UsuarioResponseDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO create(UsuarioRequestDTO dto);

    List<UsuarioResponseDTO> findAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);
}