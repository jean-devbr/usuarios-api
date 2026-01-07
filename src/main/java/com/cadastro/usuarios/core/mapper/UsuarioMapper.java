package com.cadastro.usuarios.core.mapper;

import com.cadastro.usuarios.core.dto.UsuarioRequestDTO;
import com.cadastro.usuarios.core.dto.UsuarioResponseDTO;
import com.cadastro.usuarios.core.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequestDTO request);

    UsuarioResponseDTO toResponse(Usuario usuario);
}