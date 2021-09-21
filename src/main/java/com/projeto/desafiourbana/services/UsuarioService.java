package com.projeto.desafiourbana.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.desafiourbana.domain.Cartao;
import com.projeto.desafiourbana.domain.Usuario;
import com.projeto.desafiourbana.repositories.UsuarioRepository;
import com.projeto.desafiourbana.repositories.filters.UsuarioFilter;
import com.projeto.desafiourbana.services.exceptions.ContaNaoPodeSerExcluidaException;
import com.projeto.desafiourbana.services.exceptions.EmailIndisponivelException;
import com.projeto.desafiourbana.services.exceptions.NaoTemPermissaoException;
import com.projeto.desafiourbana.services.exceptions.ServiceApplicationException;
import com.projeto.desafiourbana.utils.DesafioUrbanaProperty;

@Service
public class UsuarioService extends AbstractService<Usuario, UsuarioRepository> {

	@Autowired
	private DesafioUrbanaProperty property;

	@Override
	public Usuario salvar(Usuario usuario) throws ServiceApplicationException {
		emailJaExiste(usuario, null);

		usuario.setSenha(senhaToBcrypt(usuario.getSenha()));

		Usuario retorno = repository.save(usuario);

		return retorno;
	}

	@Override
	public Usuario atualizar(Long codigo, Usuario objetoModificado) throws ServiceApplicationException {

		Usuario objetoAtualizado = buscarPorCodigo(codigo);

		emailJaExiste(objetoModificado, codigo);

		if (objetoModificado.getSenha() == null)
			objetoModificado.setSenha(objetoAtualizado.getSenha());

		BeanUtils.copyProperties(objetoModificado, objetoAtualizado, "codigo", "senha");
		Usuario retorno = repository.save(objetoAtualizado);

		return retorno;

	}

	public boolean emailJaExiste(Usuario objeto, Long codigoDoObjetoAtualizado) throws EmailIndisponivelException {
		UsuarioFilter filter = new UsuarioFilter();
		filter.setEmail(objeto.getEmail());

		List<Usuario> listaDeObjetos = repository.filtrar(filter);

		if (!listaDeObjetos.isEmpty()) {
			Usuario usuarioExistente = listaDeObjetos.get(0);
			if (codigoDoObjetoAtualizado != null) {
				if (usuarioExistente.getCodigo().equals(codigoDoObjetoAtualizado)) {
					return false;
				}
			}

			throw new EmailIndisponivelException(Long.toString(usuarioExistente.getCodigo()));

		}

		return false;
	}

	public String senhaToBcrypt(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	public Long getCodigoUsuarioLogado() {
		Optional<Usuario> objeto = repository.findByEmail(getLoginUsuarioLogado());

		return objeto.get().getCodigo();
	}

	public Usuario getusuarioLogado() {
		Optional<Usuario> objeto = repository.findByEmail(getLoginUsuarioLogado());

		return objeto.get();
	}

	public String gerarNovoUrlToken() {
		SecureRandom secureRandom = new SecureRandom();
		Base64.Encoder base64Encoder = Base64.getUrlEncoder();

		byte[] randomBytes = new byte[35];
		secureRandom.nextBytes(randomBytes);
		return base64Encoder.encodeToString(randomBytes);
	}

	private String geradorDeSenha() {
		String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C",
				"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };

		String senha = "";

		for (int x = 0; x < 10; x++) {
			int j = (int) (Math.random() * carct.length);
			senha += carct[j];
		}

		return senha;
	}

	public List<Usuario> filtrar(UsuarioFilter filter) {
		return repository.filtrar(filter);
	}

	public Usuario buscarUsuarioPorCodigo(Long codigo) throws NaoTemPermissaoException {

		Usuario usuario = buscarPorCodigo(codigo);

		return usuario;

	}

	public void deletarConta(Long codigo) throws ServiceApplicationException {
		Usuario usuario = buscarPorCodigo(codigo);
		validarExclusao(usuario);
		deletar(codigo);
	}

	public void validarExclusao(Usuario usuario) throws ServiceApplicationException {
		for (Cartao cartao : usuario.getCartoes()) {
			if (cartao.getStatus() == true)
				throw new ContaNaoPodeSerExcluidaException(
						"A conta está associada a um cartão ativo então não pode ser excluída");

		}

	}

}
