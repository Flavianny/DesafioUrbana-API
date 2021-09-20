package com.projeto.desafiourbana.services.exceptions;

public class ServiceApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceApplicationException(String mensagem) {
		super(mensagem);
	}

	public ServiceApplicationException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
