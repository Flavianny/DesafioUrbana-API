package com.projeto.desafiourbana.services.exceptions;

public class RecursoNomeDuplicadoRuntimeException extends RecursoDuplicadoRuntimeException {

	private static final long serialVersionUID = 1L;

	public RecursoNomeDuplicadoRuntimeException(String mensagem) {
		super(mensagem);
	}

	public RecursoNomeDuplicadoRuntimeException(String mensagem, String linkRecurso) {
		super(mensagem, linkRecurso);
	}

	public RecursoNomeDuplicadoRuntimeException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
