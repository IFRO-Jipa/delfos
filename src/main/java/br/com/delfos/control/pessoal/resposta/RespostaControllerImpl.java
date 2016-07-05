package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;

interface RespostaControllerImpl<T extends Alternativa, S> {

	void setOption(Optional<Pergunta<T>> optionalAlternativa);

	@SuppressWarnings("unchecked")
	default void set(Optional<Pergunta<?>> optional) {
		optional.ifPresent(value -> {
			Pergunta<T> pergunta = (Pergunta<T>) value;
			this.setOption(Optional.ofNullable(pergunta));
		});
	}

	Optional<Pergunta<T>> getOption();

	S getSelected();

	void clearSelected();

	boolean isSelected();

	Resposta<?> getResposta();
}
