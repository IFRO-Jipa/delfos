package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;

interface RespostaControllerImpl<T extends Alternativa, S> {

	void setOption(Optional<Pergunta<T>> optionalAlternativa);

	Optional<Pergunta<T>> getOption();

	S getSelected();

	void clearSelected();

	boolean isSelected();
}
