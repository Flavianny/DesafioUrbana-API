package com.projeto.usabilitybox.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.projeto.usabilitybox.domain.Avaliador;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Avaliador usuario;

	public UsuarioSistema(Avaliador usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	public Avaliador getUsuario() {
		return usuario;
	}

}
