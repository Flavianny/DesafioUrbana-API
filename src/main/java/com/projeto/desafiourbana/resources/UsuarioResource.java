package com.projeto.desafiourbana.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.desafiourbana.domain.Usuario;
import com.projeto.desafiourbana.dtos.UsuarioEditaDTO;
import com.projeto.desafiourbana.repositories.filters.UsuarioFilter;
import com.projeto.desafiourbana.resources.api.UsuarioAPI;
import com.projeto.desafiourbana.services.UsuarioService;
import com.projeto.desafiourbana.services.exceptions.EmailIndisponivelException;
import com.projeto.desafiourbana.services.exceptions.EmailIndisponivelRuntimeException;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

@CrossOrigin(maxAge = 10, origins = "*")
@RestController
public class UsuarioResource extends AbstractResource<Usuario, UsuarioService> implements UsuarioAPI {

	@Override
	public ResponseEntity<Usuario> post(@Valid Usuario objeto, HttpServletResponse response)
			throws ServiceApplicationException {

		Usuario objetoSalvo = null;

		try {
			objetoSalvo = salvar(objeto);
		} catch (EmailIndisponivelException e) {
			lancarExceptionComLocation(e);
		}

		Long codigo = objetoSalvo.getCodigo();
		adicionarHeaderLocation(response, codigo, "/usuarios");
		adicionarLinkPermissao(objetoSalvo, codigo);
		return ResponseEntity.status(HttpStatus.CREATED).body(objetoSalvo);
	}

	private Usuario salvar(Usuario objeto) throws ServiceApplicationException {
		return service.salvar(objeto);
	}

	@Override
	public ResponseEntity<Usuario> getByCodigoPermissao(@PathVariable Long codigo) throws ServiceApplicationException {
		Usuario objeto = service.buscarUsuarioPorCodigo(codigo);
		adicionarLinkPermissao(objeto, codigo);
		return objeto != null ? ResponseEntity.ok(objeto) : ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Usuario> put(@PathVariable Long codigo, @Valid @RequestBody UsuarioEditaDTO dto)
			throws ServiceApplicationException {
		Usuario objetoModificado = service.buscarUsuarioPorCodigo(codigo);
		objetoModificado.setNome(dto.getNome());
		objetoModificado.setEmail(dto.getEmail());
		System.out.println(objetoModificado.getEmail());
		Usuario objetoEditado = service.atualizar(codigo, objetoModificado);

		return ResponseEntity.ok(objetoEditado);
	}

	protected void lancarExceptionComLocation(ServiceApplicationException e) {
		Usuario usuarioExistente = service.buscarPorCodigo(Long.parseLong(e.getMessage()));

		adicionarLink(usuarioExistente, usuarioExistente.getCodigo());

		if (e instanceof EmailIndisponivelException) {
			throw new EmailIndisponivelRuntimeException(
					"Já existe um Usuario com esse login " + usuarioExistente.getEmail(),
					usuarioExistente.getId().getHref());

		}
	}

	@Override
	public ResponseEntity<Void> delete(Long codigo) throws ServiceApplicationException {
		service.deletarConta(codigo);
		return ResponseEntity.noContent().header("Entity", Long.toString(codigo)).build();
	}

	@Override
	public List<Usuario> getAll(UsuarioFilter usuarioFilter) {
		return service.filtrar(usuarioFilter);

	}

}
