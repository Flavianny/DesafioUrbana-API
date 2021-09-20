package com.projeto.desafiourbana.services.exceptions;

public class ContaNaoPodeSerExcluidaException extends ServiceApplicationException {

	public ContaNaoPodeSerExcluidaException(String mensagem) {
		super(mensagem);
	}

	public ContaNaoPodeSerExcluidaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
