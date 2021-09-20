package com.projeto.desafiourbana.repositories.cartao;

import java.util.List;

import com.projeto.desafiourbana.domain.Cartao;
import com.projeto.desafiourbana.repositories.filters.CartaoFilter;


public interface CartaoRepositoryQuery {

	public List<Cartao> filtrar(CartaoFilter cenarioFilter);

}
