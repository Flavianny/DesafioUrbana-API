package com.projeto.desafiourbana.services.exceptions;

public class EmailInexistenteException extends ServiceApplicationException {
	
	private static final long serialVersionUID = 1L;

	public EmailInexistenteException(String mensagem) {
		super(mensagem);
	}

	public EmailInexistenteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
