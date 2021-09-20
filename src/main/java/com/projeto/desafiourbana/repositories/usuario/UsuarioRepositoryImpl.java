package com.projeto.desafiourbana.repositories.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.projeto.desafiourbana.domain.Usuario;
import com.projeto.desafiourbana.repositories.filters.UsuarioFilter;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Usuario> filtrar(UsuarioFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<Usuario> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(UsuarioFilter filter, CriteriaBuilder builder, Root<Usuario> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (filter.getEmail() != null) {
			predicates.add(builder.equal(root.get("email"), filter.getEmail()));
		}

		String busca = filter.getBusca();

		System.out.println(busca);
		
		if (busca != null && !busca.equals("undefined")) {

			Predicate nome = builder.like(builder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%");

			Predicate email = builder.like(builder.lower(root.get("email")), "%" + busca.toLowerCase() + "%");

			Predicate predicadoFormatado = builder.or(nome, email);

			predicates.add(predicadoFormatado);
		}

		if (predicates.size() == 1) {
			predicates.add(predicates.get(0));
		} else if (predicates.size() == 2) {
			predicates.add(builder.and(predicates.get(0), predicates.get(1)));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
