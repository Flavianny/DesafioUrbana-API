package com.projeto.desafiourbana.resources.HATEOAS;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

import com.projeto.desafiourbana.domain.ObjetoIdentificado;
import com.projeto.desafiourbana.resources.AbstractResource;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

@Component
public class HATEOAS<C extends AbstractResource<O, ?>, O extends ObjetoIdentificado> {

	public void buildBasicLinks(C resourceController, O objeto, Long codigo) {
		objeto.add(linkTo(methodOn(resourceController.getClass()).getByCodigo(codigo)).withSelfRel()
				.withType("GET/DELETE/PUT").withDeprecation("false").withHreflang("pt-br").withMedia("JSON")
				.withTitle("Permite operações de busca, exclusão e atualização."));
	}

	public void buildBasicLinksPermissao(C resourceController, O objeto, Long codigo)
			throws ServiceApplicationException {
		objeto.add(linkTo(methodOn(resourceController.getClass()).getByCodigo(codigo)).withSelfRel()
				.withType("GET/DELETE/PUT").withDeprecation("false").withHreflang("pt-br").withMedia("JSON")
				.withTitle("Permite operações de busca, exclusão e atualização."));
	}
}
