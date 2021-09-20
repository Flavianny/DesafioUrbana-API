package com.projeto.desafiourbana.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.projeto.desafiourbana.domain.ObjetoIdentificado;
import com.projeto.desafiourbana.services.exceptions.IntegridadeDeDadosException;
import com.projeto.desafiourbana.services.exceptions.NaoTemPermissaoException;
import com.projeto.desafiourbana.services.exceptions.ObjetoNaoEncontradoException;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

public abstract class AbstractService<O extends ObjetoIdentificado, R extends JpaRepository<O, Long>>
		implements CRUDService<O> {

	@Autowired
	protected R repository;

	@Override
	public O salvar(O objeto) throws ServiceApplicationException {
		objeto.setCodigo(null);
		return repository.save(objeto);
	}

	public String getLoginUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@Override
	public List<O> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public O buscarPorCodigo(Long codigo) {
		Optional<O> objeto = repository.findById(codigo);

		return objeto.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + codigo + ", Tipo: " + ObjetoIdentificado.class.getName()));

	}

	@Override
	public O atualizar(Long codigo, O objetoModificado) throws ServiceApplicationException {
		O objetoAtualizado = buscarPorCodigo(codigo);
		BeanUtils.copyProperties(objetoModificado, objetoAtualizado, "codigo");
		return repository.save(objetoAtualizado);
	}

	@Override
	public void deletar(Long codigo) {
		buscarPorCodigo(codigo);
		try {
			repository.deleteById(codigo);

		} catch (DataIntegrityViolationException e) {
			lancarIntegridadeDeDadosException(e);
		}
	}

	public void lancarIntegridadeDeDadosException(DataIntegrityViolationException e) {
		throw new IntegridadeDeDadosException(
				"Integridade de dados violada, não é possível excluir um recurso que está relacionado à outro.");
	}

	public void lancarNaoTemPermissaoException() throws NaoTemPermissaoException {
		throw new NaoTemPermissaoException("Você não tem permissão para acessar/alterar esse recurso");
	}
}
