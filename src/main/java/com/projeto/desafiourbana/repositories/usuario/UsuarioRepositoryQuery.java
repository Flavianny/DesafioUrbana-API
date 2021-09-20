package com.projeto.desafiourbana.repositories.usuario;

import java.util.List;

import com.projeto.desafiourbana.domain.Usuario;
import com.projeto.desafiourbana.repositories.filters.UsuarioFilter;

public interface UsuarioRepositoryQuery {
	
	public List<Usuario> filtrar(UsuarioFilter filter);
}
