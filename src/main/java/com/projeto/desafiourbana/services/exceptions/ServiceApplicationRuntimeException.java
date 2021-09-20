package com.projeto.desafiourbana.services.exceptions;

public class ServiceApplicationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceApplicationRuntimeException(String mensagem) {
		super(mensagem);
	}

	public ServiceApplicationRuntimeException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}