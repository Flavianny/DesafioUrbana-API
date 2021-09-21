package com.projeto.desafiourbana.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projeto.desafiourbana.domain.enums.TipoCartao;

@Entity
@Table(name = "cartao")
public class Cartao extends ObjetoIdentificado {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "numero_cartao")
	private Integer numeroCartao;

	@NotBlank
	private String nome;

	private boolean status;

	@ManyToOne
	@JsonIgnoreProperties("cartoes")
	@JoinColumn(name = "usuario")
	private Usuario usuario;
	
	@Column(name = "tipo_cartao")
	@Enumerated(EnumType.STRING)
	private TipoCartao tipoCartao;

	public Integer getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(Integer numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoCartao getTipoCartao() {
		return tipoCartao;
	}

	public void setTipoCartao(TipoCartao tipoCartao) {
		this.tipoCartao = tipoCartao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
