package com.cadastro.usuarios.core.repository;

import com.cadastro.usuarios.core.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByNome(String nome);

    boolean existsBySenha(String senha);

    boolean existsByNomeAndSenha(String nome, String senha);

    boolean existsByNomeOrSenha(String nome, String senha);

    boolean existsByNomeOrSenhaAndIdNot(String nome, String senha, Long id);

    Optional<Usuario> findByNome(String nome);

    // Busca usuário pela senha
    Optional<Usuario> findBySenha(String senha);
}