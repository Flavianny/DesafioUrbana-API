package com.projeto.desafiourbana.services.exceptions;

public class EmailJaCadastradoException extends ServiceApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailJaCadastradoException(String mensagem) {
		super(mensagem);
	}

	public EmailJaCadastradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
