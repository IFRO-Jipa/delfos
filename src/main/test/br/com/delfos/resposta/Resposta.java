package br.com.delfos.resposta;

import java.util.Optional;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;

public abstract class Resposta<S extends Alternativa, T>  {
	private Pessoa expert;
	private Pergunta<S> pergunta;

	public void setExpert(Optional<Pessoa> optionalExpert) {
		optionalExpert.ifPresent(expert -> {
			if (expert.isEspecialista()) {
				this.expert = expert;
			} else
				throw new IllegalArgumentException("Essa pessoa não é um especialista válido.");
		});
	}

	public void setPergunta(Pergunta<S> pergunta) {
		this.pergunta = pergunta;
	}

	public Pergunta<S> getPergunta() {
		return pergunta;
	}

	public Pessoa getExpert() {
		return expert;
	}

	public abstract void setEscolha(T t);

	public abstract T getEscolha();
}
