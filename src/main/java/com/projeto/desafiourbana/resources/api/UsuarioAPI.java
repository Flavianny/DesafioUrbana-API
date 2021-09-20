package com.projeto.desafiourbana.resources.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projeto.desafiourbana.domain.Usuario;
import com.projeto.desafiourbana.dtos.UsuarioEditaDTO;
import com.projeto.desafiourbana.repositories.filters.UsuarioFilter;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/usuarios")
public interface UsuarioAPI {

	@ApiOperation(value = "Salva o usuário")
	@PostMapping(value = "/novo")
	public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario objeto, HttpServletResponse response)
			throws ServiceApplicationException;

	@ApiOperation(value = "Busca todos os usuarios")
	@GetMapping
	public List<Usuario> getAll(UsuarioFilter usuarioFilter);

	@ApiOperation(value = "Busca um Avaliador pelo código")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Sem permissão") })
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Usuario> getByCodigoPermissao(@PathVariable Long codigo) throws ServiceApplicationException;

	@ApiOperation(value = "Atualiza um determinado usuario")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Sem permissão") })
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Usuario> put(@PathVariable Long codigo, @Valid @RequestBody UsuarioEditaDTO dto)
			throws ServiceApplicationException;

	@ApiOperation(value = "Deleta um determinado usuario")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Integridade de dados violada, não é possível excluir um recurso que está relacionado à outro."),
			@ApiResponse(code = 404, message = "Código inexistente.") })
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable Long codigo) throws ServiceApplicationException;

}
