package com.cadastro.usuarios.core.service.impl;

import com.cadastro.usuarios.core.dto.UsuarioRequestDTO;
import com.cadastro.usuarios.core.dto.UsuarioResponseDTO;
import com.cadastro.usuarios.core.infra.exceptions.BusinessException;
import com.cadastro.usuarios.core.infra.exceptions.ResourceNotFoundException;
import com.cadastro.usuarios.core.mapper.UsuarioMapper;
import com.cadastro.usuarios.core.repository.UsuarioRepository;
import com.cadastro.usuarios.core.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        // 1. Valida se o nome já existe
        if (repository.existsByNome(dto.nome())) {
            throw new BusinessException("Já existe um usuário cadastrado com este nome.");
        }

        // 2. Valida se a senha já existe (se você quiser que senhas também sejam únicas)
        if (repository.existsBySenha(dto.senha())) {
            throw new BusinessException("Esta senha já está em uso por outro usuário.");
        }

        var entidade = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        var usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setSenha(dto.senha());

        return mapper.toResponse(repository.save(usuario));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validarLogin(String nome, String senha) {
        return repository.existsByNomeAndSenha(nome, senha);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findByName(String nome) {
        return repository.findByNome(nome)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}