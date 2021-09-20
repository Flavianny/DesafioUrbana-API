package com.projeto.desafiourbana.services.exceptions;

public class RecursoDuplicadoRuntimeException extends ServiceApplicationRuntimeException {

	private static final long serialVersionUID = 1L;
	private String linkRecurso;
	public RecursoDuplicadoRuntimeException(String mensagem) {
		super(mensagem);
	}
	
	public RecursoDuplicadoRuntimeException(String mensagem, String linkRecurso) {
		super(mensagem);
		this.linkRecurso = linkRecurso;
	}

	public RecursoDuplicadoRuntimeException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public String getLinkRecurso() {
		return linkRecurso;
	}

}

