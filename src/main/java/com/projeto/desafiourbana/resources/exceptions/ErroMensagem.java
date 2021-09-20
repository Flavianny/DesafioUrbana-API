package com.projeto.desafiourbana.resources.exceptions;

import java.io.Serializable;

public class ErroMensagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mensagemUsuario;
	private String mensagemDesenvolvedor;
	private Integer status;
	private Long timeStamp;

	public ErroMensagem(String mensagemUsuario, String mensagemDesenvolvedor, Integer status, Long timeStamp) {
		super();
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		this.status = status;
		this.timeStamp = timeStamp;
	}

	public String getMensagemUsuario() {
		return mensagemUsuario;
	}

	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}

	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}

	public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
}