package com.projeto.desafiourbana.resources.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projeto.desafiourbana.services.exceptions.ContaNaoPodeSerExcluidaException;
import com.projeto.desafiourbana.services.exceptions.DadoInvalidoException;
import com.projeto.desafiourbana.services.exceptions.EmailIndisponivelException;
import com.projeto.desafiourbana.services.exceptions.EmailIndisponivelRuntimeException;
import com.projeto.desafiourbana.services.exceptions.EmailInexistenteException;
import com.projeto.desafiourbana.services.exceptions.EmailJaCadastradoException;
import com.projeto.desafiourbana.services.exceptions.NaoTemPermissaoException;
import com.projeto.desafiourbana.services.exceptions.ObjetoNaoEncontradoException;
import com.projeto.desafiourbana.services.exceptions.RecursoDuplicadoRuntimeException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final HttpStatus CONFLICT = HttpStatus.CONFLICT;

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		status = HttpStatus.BAD_REQUEST;
		String mensagemUsuario = montarMensagemUsuario("mensagem.invalida");
		String mensagemDesenvolvedor = ex.getCause().toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return handleExceptionInternal(ex, responseBody, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		status = HttpStatus.BAD_REQUEST;
		List<ErroMensagem> responseBody = criarListaDeErros(ex.getBindingResult(), status);
		return handleExceptionInternal(ex, responseBody, headers, status, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessExeption(EmptyResultDataAccessException exception,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String mensagemUsuario = montarMensagemUsuario("recurso.nao-encontrado");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return handleExceptionInternal(exception, responseBody, new HttpHeaders(), status, request);
	}

	@ExceptionHandler({ ObjetoNaoEncontradoException.class })
	public ResponseEntity<Object> objetoNaoEncontrado(ObjetoNaoEncontradoException exception,
			HttpServletRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String mensagemUsuario = montarMensagemUsuario("recurso.nao-encontrado");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return ResponseEntity.status(status).body(responseBody);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception,
			WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String mensagemUsuario = montarMensagemUsuario("recurso.operacao-nao-permitida");
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(exception);
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmailIndisponivelException.class })
	public ResponseEntity<Object> handleEmailIndisponivelException(EmailIndisponivelException exception,
			WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String mensagemUsuario = montarMensagemUsuario("email.indisponivel");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return ResponseEntity.status(status).body(responseBody);
	}

	@ExceptionHandler({ DadoInvalidoException.class })
	public ResponseEntity<Object> dadoInvalido(DadoInvalidoException exception, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String mensagemUsuario = montarMensagemUsuario("recurso.dado-invalido");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return ResponseEntity.status(status).body(responseBody);
	}

	private List<ErroMensagem> montarResponseBody(HttpStatus status, String mensagemUsuario,
			String mensagemDesenvolvedor) {
		return Arrays.asList(
				new ErroMensagem(mensagemUsuario, mensagemDesenvolvedor, status.value(), System.currentTimeMillis()));
	}

	private String montarMensagemUsuario(String sourceMessage) {
		return messageSource.getMessage(sourceMessage, null, LocaleContextHolder.getLocale());
	}

	private List<ErroMensagemConflict> montarResponseBodyConflict(RecursoDuplicadoRuntimeException exception,
			String messageProperty) {
		String mensagemUsuario = montarMensagemUsuario(messageProperty);
		String mensagemDesenvolvedor = exception.toString();
		String resourceLocation = exception.getLinkRecurso();
		List<ErroMensagemConflict> responseBody = Arrays.asList(new ErroMensagemConflict(mensagemUsuario,
				mensagemDesenvolvedor, CONFLICT.value(), System.currentTimeMillis(), resourceLocation));
		return responseBody;
	}

	private List<ErroMensagem> criarListaDeErros(BindingResult bindingResult, HttpStatus status) {
		List<ErroMensagem> erros = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new ErroMensagem(mensagemUsuario, mensagemDesenvolvedor, status.value(),
					System.currentTimeMillis()));
		}
		return erros;
	}

	@ExceptionHandler(EmailIndisponivelRuntimeException.class)
	public ResponseEntity<Object> recursoEmailDuplicado(EmailIndisponivelRuntimeException exception, WebRequest request,
			HttpServletResponse response) {

		List<ErroMensagemConflict> responseBody = montarResponseBodyConflict(exception, "email.indisponivel");
		return ResponseEntity.status(CONFLICT).header("Location", exception.getLinkRecurso()).body(responseBody);
	}

	@ExceptionHandler({ NaoTemPermissaoException.class })
	public ResponseEntity<Object> naoTemPermissao(NaoTemPermissaoException exception, WebRequest request) {

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String mensagemUsuario = montarMensagemUsuario("nao-tem-permissao");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return ResponseEntity.status(status).body(responseBody);
	}

	@ExceptionHandler({ EmailJaCadastradoException.class })
	public ResponseEntity<Object> emailJaCadastrado(EmailJaCadastradoException exception, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String mensagemUsuario = montarMensagemUsuario("email.ja-cadastrado");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return ResponseEntity.status(status).body(responseBody);
	}

	@ExceptionHandler({ EmailInexistenteException.class })
	public ResponseEntity<Object> importancia(EmailInexistenteException exception, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String mensagemUsuario = montarMensagemUsuario("email-inexistente");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return ResponseEntity.status(status).body(responseBody);
	}
	
	@ExceptionHandler({ ContaNaoPodeSerExcluidaException.class })
	public ResponseEntity<Object> usuarioNaoPodeSerExcluido(ContaNaoPodeSerExcluidaException exception, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String mensagemUsuario = montarMensagemUsuario("usuario.nao-pode-ser-excluido");
		String mensagemDesenvolvedor = exception.toString();
		List<ErroMensagem> responseBody = montarResponseBody(status, mensagemUsuario, mensagemDesenvolvedor);

		return ResponseEntity.status(status).body(responseBody);
	}

}
