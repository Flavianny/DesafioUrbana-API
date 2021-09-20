package com.projeto.desafiourbana.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "usuario")
public class Usuario extends ObjetoIdentificado {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String nome;

	@Email(message = "{email.nao.valido}")
	@NotBlank
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Size(min = 6)
	private String senha;
	
	@JsonIgnoreProperties("usuario")
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Cartao> cartoes = new ArrayList<Cartao>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Cartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<Cartao> cartoes) {
		this.cartoes = cartoes;
	}

}
