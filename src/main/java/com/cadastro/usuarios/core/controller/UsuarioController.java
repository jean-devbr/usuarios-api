package com.cadastro.usuarios.core.controller;

import com.cadastro.usuarios.core.dto.LoginRequestDTO;
import com.cadastro.usuarios.core.dto.LoginResponseDTO;
import com.cadastro.usuarios.core.dto.UsuarioRequestDTO;
import com.cadastro.usuarios.core.dto.UsuarioResponseDTO;
import com.cadastro.usuarios.core.infra.exceptions.BusinessException;
import com.cadastro.usuarios.core.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService service;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {
        boolean autenticado = service.validarLogin(dto.nome(), dto.senha());

        if (!autenticado) {
            throw new BusinessException("Usuário ou senha inválidos");
        }

        UsuarioResponseDTO usuario = service.findByName(dto.nome());

        LoginResponseDTO response = new LoginResponseDTO(
                usuario.id(),
                usuario.nome(),
                "Login realizado com sucesso!"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody @Valid UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}