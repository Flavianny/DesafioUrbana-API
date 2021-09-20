package com.projeto.desafiourbana.domain;

import java.io.Serializable;

public interface Identificador<T> extends Serializable {
	T getCodigo();

	void setCodigo(T codigo);

	default boolean isSalvo() {
		return this.getCodigo() != null;
	}
}
