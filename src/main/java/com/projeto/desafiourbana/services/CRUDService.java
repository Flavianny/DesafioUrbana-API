package com.projeto.desafiourbana.services;

import java.util.List;

import com.projeto.desafiourbana.domain.ObjetoIdentificado;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationRuntimeException;

public interface CRUDService<O extends ObjetoIdentificado> {

	O salvar(O objeto) throws ServiceApplicationException;

	List<O> buscarTodos() throws ServiceApplicationRuntimeException;

	O buscarPorCodigo(Long codigo) throws ServiceApplicationRuntimeException;

	O atualizar(Long codigo, O objetoModificado) throws ServiceApplicationRuntimeException, ServiceApplicationException;

	void deletar(Long codigo) throws ServiceApplicationRuntimeException;
}