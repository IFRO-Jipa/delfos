package br.com.delfos.model;

public interface TipoPergunta {
	default TipoPergunta getTipo() { 
		return this;
	}
}
