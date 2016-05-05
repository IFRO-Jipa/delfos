package br.com.delfos.model;

import javax.persistence.Entity;

@Entity
public class Pergunta extends AbstractModel<Pergunta>{

	private String nome;
	private String descricao;
	
	private TipoPergunta tipo;
	
	
	
}
