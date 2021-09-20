package com.projeto.desafiourbana.services.exceptions;

public class NaoTemPermissaoException extends ServiceApplicationException {
	
	private static final long serialVersionUID = 1L;

	public NaoTemPermissaoException(String mensagem) {
		super(mensagem);
	}

	public NaoTemPermissaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
