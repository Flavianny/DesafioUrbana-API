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

import com.projeto.desafiourbana.domain.Cartao;
import com.projeto.desafiourbana.repositories.filters.CartaoFilter;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/cartoes")
public interface CartaoAPI {

	@ApiOperation(value = "Salva um cartão")
	@PostMapping(value = "/novo")
	public ResponseEntity<Cartao> post(@Valid @RequestBody Cartao objeto,
			HttpServletResponse response) throws ServiceApplicationException;

	@ApiOperation(value = "Busca todos os cartões")
	@GetMapping
	public List<Cartao> getAll(CartaoFilter cenarioFilter);

	@ApiOperation(value = "Busca um cartão pelo código")
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Cartao> getByCodigo(@PathVariable Long codigo);

	@ApiOperation(value = "Atualiza um determinado cartão")
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Cartao> put(@PathVariable Long codigo, @Valid @RequestBody Cartao objetoModificado)
			throws ServiceApplicationException;
	
	@ApiOperation(value = "Atualiza o status de um determinado cartão")
	@PutMapping(value = "/{codigo}/status")
	public ResponseEntity<Void> putStatus(@PathVariable Long codigo, @PathVariable Boolean status)
			throws ServiceApplicationException;


	@ApiOperation(value = "Deleta um determinado cartão")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Integridade de dados violada, não é possível excluir um recurso que está relacionado à outro."),
			@ApiResponse(code = 404, message = "Código inexistente.") })
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable Long codigo);

}
