package com.projeto.desafiourbana.domain.enums;

public enum TipoCartao {
	COMUM("COMUM"), ESTUDANTE("ESTUDANTE"), TRABALHADOR("TRABALHADOR");

	private final String tipo;

	TipoCartao(String tipoCartao) {
		this.tipo = tipoCartao;
	}

	public String getTipo() {
		return tipo;
	}

	public static TipoCartao converter(String texto) {

		if (texto.equals(COMUM.tipo)) {
			return COMUM;
		} else if (texto.equals(ESTUDANTE.tipo)) {
			return ESTUDANTE;
		} else if (texto.equals(TRABALHADOR.tipo)) {
			return TRABALHADOR;
		}
		return null;
	}

}
