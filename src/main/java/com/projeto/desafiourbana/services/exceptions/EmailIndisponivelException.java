package com.projeto.desafiourbana.services.exceptions;

public class EmailIndisponivelException extends ServiceApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailIndisponivelException(String mensagem) {
		super(mensagem);
	}

	public EmailIndisponivelException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
