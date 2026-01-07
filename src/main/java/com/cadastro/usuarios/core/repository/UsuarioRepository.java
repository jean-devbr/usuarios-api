package com.cadastro.usuarios.core.repository;

import com.cadastro.usuarios.core.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Verifica se o nome já existe
    boolean existsByNome(String nome);

    // Verifica se a senha já existe (opcional, dependendo da sua regra)
    boolean existsBySenha(String senha);
}