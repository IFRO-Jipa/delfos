package br.com.delfos.except;

public class PessoaInvalidaException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public PessoaInvalidaException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PessoaInvalidaException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PessoaInvalidaException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PessoaInvalidaException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
