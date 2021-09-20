package com.projeto.desafiourbana.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.desafiourbana.domain.Usuario;
import com.projeto.desafiourbana.repositories.usuario.UsuarioRepositoryQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {

	public Optional<Usuario> findByEmail(String email);

}
