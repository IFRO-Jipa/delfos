package br.com.delfos.except.pesquisa.resposta;

public class RespostaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RespostaInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	public RespostaInvalidaException(String message) {
		super(message);
	}

}
