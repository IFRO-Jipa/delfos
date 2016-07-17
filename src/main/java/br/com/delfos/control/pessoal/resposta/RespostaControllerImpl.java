package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;

public interface RespostaControllerImpl<T extends Alternativa, S> {

	void setOption(Optional<Pergunta<T>> optionalAlternativa);

	@SuppressWarnings("unchecked")
	default void set(Optional<Pergunta<?>> optional) {
		System.out.println("******************** resposta controller impl *************");
		optional.ifPresent(value -> {
			System.out.printf("Pergunta: %3d - %10s [Alternativa: %20s]\n", value.getId().intValue(), value.getNome(),
					value.getAlternativa());
			Pergunta<T> pergunta = (Pergunta<T>) value;
			this.setOption(Optional.ofNullable(pergunta));
		});
		System.out.println("******************** fim resposta controller impl *************");
	}

	Optional<Pergunta<T>> getOption();

	S getSelected();

	void clearSelected();

	boolean isSelected();

	Resposta<?> getResposta(Questionario questionario);
}
