package com.projeto.desafiourbana.services.exceptions;

public class ObjetoNaoEncontradoException extends ServiceApplicationRuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjetoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ObjetoNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
