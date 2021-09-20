package com.projeto.usabilitybox.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto.usabilitybox.domain.Avaliador;
import com.projeto.usabilitybox.repositories.AvaliadorRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
	
	@Autowired
	private AvaliadorRepository avaliadorRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Avaliador> usuarioOptional = avaliadorRepository.findByLogin(username);
		Avaliador usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}
	
	private Collection<? extends GrantedAuthority> getPermissoes(Avaliador usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("AVALIADOR"));
		return authorities;
	}


}
