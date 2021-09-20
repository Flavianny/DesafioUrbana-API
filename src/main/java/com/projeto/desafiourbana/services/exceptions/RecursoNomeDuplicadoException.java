package com.projeto.desafiourbana.services.exceptions;

public class RecursoNomeDuplicadoException extends ServiceApplicationException {

	private static final long serialVersionUID = 1L;

	public RecursoNomeDuplicadoException(String mensagem) {
		super(mensagem);
	}

	public RecursoNomeDuplicadoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}