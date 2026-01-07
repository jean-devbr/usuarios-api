package com.cadastro.usuarios.core.repository;

import com.cadastro.usuarios.core.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Verifica se o nome já existe
    boolean existsByNome(String nome);

    // Verifica se a senha já existe (opcional, dependendo da sua regra)
    boolean existsBySenha(String senha);

    // Verifica se existe usuário com nome e senha para login
    boolean existsByNomeAndSenha(String nome, String senha);

    // Busca usuário pelo nome
    Optional<Usuario> findByNome(String nome);
}