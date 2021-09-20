package com.projeto.desafiourbana.repositories.cartao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.projeto.desafiourbana.domain.Cartao;
import com.projeto.desafiourbana.domain.enums.TipoCartao;
import com.projeto.desafiourbana.repositories.filters.CartaoFilter;

public class CartaoRepositoryImpl implements CartaoRepositoryQuery {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Cartao> filtrar(CartaoFilter cartaoFilter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cartao> criteria = builder.createQuery(Cartao.class);
		Root<Cartao> root = criteria.from(Cartao.class);

		Predicate[] predicates = criarRestricoes(cartaoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Cartao> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(CartaoFilter cartaoFilter, CriteriaBuilder builder, Root<Cartao> root) {

		List<Predicate> predicates = new ArrayList<>();

		String busca = cartaoFilter.getBusca();

		if (busca != null && !busca.equals("undefined")) {

			Predicate nome = builder.like(builder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%");

			Predicate predicadoFormatado = builder.or(nome);

			predicates.add(predicadoFormatado);

		}
		
		if(cartaoFilter.getTipoCartao() != null && !cartaoFilter.getTipoCartao().equals("undefined") && !cartaoFilter.getTipoCartao().equals("null")) {				
			TipoCartao tipoCartao = TipoCartao.converter(cartaoFilter.getTipoCartao());
			
			Predicate nivelFalhaPredicate = builder.equal (root.get("tipoCartao"), tipoCartao);
			predicates.add(nivelFalhaPredicate);
		}

		if (predicates.size() == 1) {
			predicates.add(predicates.get(0));
		} else if (predicates.size() == 2) {
			predicates.add(builder.and(predicates.get(0), predicates.get(1)));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
