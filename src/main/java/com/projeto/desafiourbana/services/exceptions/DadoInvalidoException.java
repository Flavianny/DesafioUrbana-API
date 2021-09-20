package com.projeto.desafiourbana.services.exceptions;

public class DadoInvalidoException extends ServiceApplicationException {

	private static final long serialVersionUID = 1L;

	public DadoInvalidoException(String mensagem) {
		super(mensagem);
	}

	public DadoInvalidoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
