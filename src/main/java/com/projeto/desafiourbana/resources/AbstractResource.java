package com.projeto.desafiourbana.resources;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;

import com.projeto.desafiourbana.domain.ObjetoIdentificado;
import com.projeto.desafiourbana.event.RecursoCriadoEvent;
import com.projeto.desafiourbana.resources.HATEOAS.HATEOAS;
import com.projeto.desafiourbana.services.AbstractService;
import com.projeto.desafiourbana.services.exceptions.RecursoNomeDuplicadoRuntimeException;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

public abstract class AbstractResource<O extends ObjetoIdentificado, S extends AbstractService<O, ?>> {

	@Autowired
	protected S service;

	@Autowired
	private HATEOAS<AbstractResource<O, ?>, O> hateoas;

	@Autowired
	private ApplicationEventPublisher publisher;

	public ResponseEntity<O> getByCodigo(Long codigo) {
		return null;
	}

	public ResponseEntity<O> getByCodigoPermissao(Long codigo) throws ServiceApplicationException {
		return null;
	}

	protected void adicionarLink(O objeto, Long codigo) {
		hateoas.buildBasicLinks(this, objeto, codigo);
	}

	protected List<O> adicionarLinks(List<O> objetos) {

		ArrayList<O> objetosComReferencia = new ArrayList<O>();

		objetos.forEach((o) -> {
			adicionarLink(o, o.getCodigo());
			objetosComReferencia.add(o);
		});

		return objetosComReferencia;
	}

	protected void adicionarLinkPermissao(O objeto, Long codigo) throws ServiceApplicationException {
		hateoas.buildBasicLinksPermissao(this, objeto, codigo);
	}

	protected List<O> adicionarLinksPermissao(List<O> objetos) {

		ArrayList<O> objetosComReferencia = new ArrayList<O>();

		objetos.forEach((o) -> {
			try {
				adicionarLinkPermissao(o, o.getCodigo());
			} catch (ServiceApplicationException e) {
				e.printStackTrace();
			}
			objetosComReferencia.add(o);
		});

		return objetosComReferencia;
	}

	protected void adicionarHeaderLocation(HttpServletResponse response, Long codigo, String path) {
		publisher.publishEvent(new RecursoCriadoEvent(this, response, codigo, path));
	}

	protected void lancarExceptionComLocation(ServiceApplicationException e) {
		O objetoExistente = service.buscarPorCodigo(Long.parseLong(e.getMessage()));
		adicionarLink(objetoExistente, objetoExistente.getCodigo());
		throw new RecursoNomeDuplicadoRuntimeException("Já existe um item com esse nome!",
				objetoExistente.getId().getHref());
	}
}