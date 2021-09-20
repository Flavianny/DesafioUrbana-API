package com.projeto.desafiourbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.desafiourbana.domain.Cartao;
import com.projeto.desafiourbana.repositories.CartaoRepository;
import com.projeto.desafiourbana.repositories.filters.CartaoFilter;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;

@Service
public class CartaoService extends AbstractService<Cartao, CartaoRepository> {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public Cartao salvar(Cartao objeto) throws ServiceApplicationException {

		Cartao ensaio = repository.save(objeto);

		return ensaio;
	}


	@Override
	public Cartao atualizar(Long codigo, Cartao objetoModificado) throws ServiceApplicationException {
//		Cartao objetoAtualizado = buscarCartaoPorCodigo(codigo);

		return repository.save(objetoModificado);
	}

	public List<Cartao> buscarCartoes(CartaoFilter filter) {
		System.out.println(filter.getBusca());
		return repository.filtrar(filter);
	}
	
	public Cartao atualizarStatus(Long codigo, Boolean status) throws ServiceApplicationException {
		Cartao objetoModificado = buscarPorCodigo(codigo);
		objetoModificado.setStatus(status);
		return repository.save(objetoModificado);
	}

}
