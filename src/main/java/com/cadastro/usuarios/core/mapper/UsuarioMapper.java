package com.cadastro.usuarios.core.mapper;

import com.cadastro.usuarios.core.dto.cadastro.UsuarioRequestDTO;
import com.cadastro.usuarios.core.dto.cadastro.UsuarioResponseDTO;
import com.cadastro.usuarios.core.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    UsuarioEntity toEntity(UsuarioRequestDTO request);

    UsuarioResponseDTO toResponse(UsuarioEntity usuarioEntity);
}