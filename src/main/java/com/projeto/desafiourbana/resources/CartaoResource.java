package com.projeto.desafiourbana.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.desafiourbana.domain.Cartao;
import com.projeto.desafiourbana.repositories.filters.CartaoFilter;
import com.projeto.desafiourbana.resources.api.CartaoAPI;
import com.projeto.desafiourbana.services.CartaoService;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

@RestController
public class CartaoResource extends AbstractResource<Cartao, CartaoService> implements CartaoAPI {

	@Override
	public ResponseEntity<Cartao> post(Cartao objeto, HttpServletResponse response)
			throws ServiceApplicationException {

		Cartao objetoSalvo = service.salvar(objeto);

		Long codigo = objetoSalvo.getCodigo();
		adicionarHeaderLocation(response, codigo, "/cartoes");
		adicionarLinkPermissao(objetoSalvo, codigo);
		return ResponseEntity.status(HttpStatus.CREATED).body(objetoSalvo);
	}

	@Override
	public ResponseEntity<Cartao> put(Long codigo, @Valid Cartao objetoModificado) throws ServiceApplicationException {
		Cartao objetoEditado = service.atualizar(codigo, objetoModificado);

		return ResponseEntity.ok(objetoEditado);
	}

	@Override
	public List<Cartao> getAll(CartaoFilter filter) {
		List<Cartao> objetos = service.buscarCartoes(filter);

		return adicionarLinks(objetos);
	}

	@Override
	public ResponseEntity<Cartao> getByCodigo(@PathVariable Long codigo) {
		Cartao objeto = service.buscarPorCodigo(codigo);
		adicionarLink(objeto, codigo);
		return objeto != null ? ResponseEntity.ok(objeto) : ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Void> delete(Long codigo) {
		service.deletar(codigo);
		return ResponseEntity.noContent().header("Entity", Long.toString(codigo)).build();
	}

	@Override
	public ResponseEntity<Void> putStatus(Long codigo, Boolean status) throws ServiceApplicationException {
		System.out.println(status);
		Cartao objetoEditado = service.atualizarStatus(codigo, status);
		return ResponseEntity.ok().build();
	}

}
