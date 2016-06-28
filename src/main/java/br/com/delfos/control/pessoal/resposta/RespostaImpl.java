package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import br.com.delfos.model.pesquisa.pergunta.Alternativa;

interface RespostaImpl<T extends Alternativa> {
	void clear();

	void set(Optional<T> t);

	Optional<T> get();
}
