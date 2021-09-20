package com.projeto.desafiourbana.resources.exceptions;

public class ErroMensagemConflict extends ErroMensagem {

	private static final long serialVersionUID = 1L;

	public ErroMensagemConflict(String mensagemUsuario, String mensagemDesenvolvedor, Integer status, Long timeStamp,
			String resourceLocation) {
		super(mensagemUsuario, mensagemDesenvolvedor, status, timeStamp);
		this.resourceLocation = resourceLocation;
	}

	private String resourceLocation;

	public String getResourceLocation() {
		return resourceLocation;
	}

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}
}
