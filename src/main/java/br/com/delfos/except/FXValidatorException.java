package br.com.delfos.except;

public class FXValidatorException extends Exception {

	private static final long serialVersionUID = 1L;

	public FXValidatorException() {
		super();
	}

	public FXValidatorException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FXValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public FXValidatorException(String message) {
		super(message);
	}

	public FXValidatorException(Throwable cause) {
		super(cause);
	}

}
