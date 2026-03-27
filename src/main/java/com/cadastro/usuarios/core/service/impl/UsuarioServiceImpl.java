package com.cadastro.usuarios.core.service.impl;

import com.cadastro.usuarios.core.dto.login.LoginRequestDTO;
import com.cadastro.usuarios.core.dto.login.LoginResponseDTO;
import com.cadastro.usuarios.core.dto.cadastro.UsuarioRequestDTO;
import com.cadastro.usuarios.core.dto.cadastro.UsuarioResponseDTO;
import com.cadastro.usuarios.infra.exceptions.BusinessException;
import com.cadastro.usuarios.infra.exceptions.ResourceNotFoundException;
import com.cadastro.usuarios.core.mapper.UsuarioMapper;
import com.cadastro.usuarios.core.repository.UsuarioRepository;
import com.cadastro.usuarios.core.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        validarNomeUnicos(dto.nome(), null);

        var entidade = mapper.toEntity(dto);
        entidade.setSenha(passwordEncoder.encode(dto.senha()));
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

        validarNomeUnicos(dto.nome(), id);

        usuario.setNome(dto.nome());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

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
        var usuario = repository.findByNome(nome).orElse(null);
        if (usuario == null) {
            return false;
        }
        return passwordEncoder.matches(senha, usuario.getSenha());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findByName(String nome) {
        return repository.findByNome(nome)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO dto) {
        var usuario = repository.findByNome(dto.nome()).orElseThrow(()
                -> new BusinessException("Usuário ou senha não encontrado"));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new BusinessException("Usuário ou Senha inválido.");
        }

        var now = Instant.now();
        var expirenIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(usuario.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirenIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                "Login realizado com sucesso!",
                jwtValue
        );
    }


    private void validarNomeUnicos(String nome, Long idAtual) {
        boolean existe = (idAtual == null)
                ? repository.existsByNome(nome)
                : repository.existsByNomeAndIdNot(nome, idAtual);

        if (existe) {
            throw new BusinessException("Nome já esta em uso.");
        }
    }

}