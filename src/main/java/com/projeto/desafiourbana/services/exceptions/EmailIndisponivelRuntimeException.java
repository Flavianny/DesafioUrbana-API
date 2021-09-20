package com.projeto.desafiourbana.services.exceptions;

public class EmailIndisponivelRuntimeException extends RecursoDuplicadoRuntimeException{
	private static final long serialVersionUID = 1L;

	public EmailIndisponivelRuntimeException(String mensagem) {
		super(mensagem);
	}

	public EmailIndisponivelRuntimeException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public EmailIndisponivelRuntimeException(String mensagem, String linkRecurso) {
		super(mensagem, linkRecurso);
	}

}
