package br.com.delfos.view.table.property;

import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import br.com.delfos.model.pesquisa.pergunta.TipoPergunta;

public class PerguntaPropertyUtil {

	public static Pergunta<?> toValue(PerguntaProperty<?> property) {
		Pergunta<?> pergunta = create(property);

		pergunta.setNome(property.getNome());
		pergunta.setDescricao(property.getDescricao());
		pergunta.setId(property.getId() == 0 ? null : property.getId());

		return pergunta;
	}

	private static Pergunta<?> create(PerguntaProperty<?> tipoPergunta) {
		return tipoPergunta.getTipoPergunta().create(tipoPergunta.getAlternativa());
	}

	public static PerguntaProperty<?> fromPergunta(Pergunta<?> pergunta) {
		PerguntaProperty<?> myself = create(pergunta);

		return myself;
	}

	private static PerguntaProperty<?> create(Pergunta<?> pergunta) {
		PerguntaProperty<?> myself = null;

		Alternativa myInstance = pergunta.getAlternativa();

		if (myInstance instanceof MultiplaEscolha) {
			myself = createByMultiplaEscolha(pergunta);
		} else if (myInstance instanceof Intervalo) {
			myself = createByIntervalo(pergunta);
		} else if (myInstance instanceof Texto || myInstance instanceof Paragrafo) {
			myself = createByTextoParagrafo(pergunta);
		}
		return myself;
	}

	private static PerguntaProperty<?> createByTextoParagrafo(Pergunta<?> pergunta) {
		if (pergunta.getAlternativa() instanceof Texto) {
			PerguntaProperty<Texto> property = new PerguntaProperty<Texto>(pergunta.getId(), pergunta.getNome(), pergunta.getDescricao(),
					TipoPergunta.TEXTO);
			property.setAlternativa(pergunta.getAlternativa());
			return property;
		} else if (pergunta.getAlternativa() instanceof Paragrafo) {
			PerguntaProperty<Paragrafo> property = new PerguntaProperty<Paragrafo>(pergunta.getId(), pergunta.getNome(), pergunta.getDescricao(),
					TipoPergunta.PARAGRAFO);
			property.setAlternativa(pergunta.getAlternativa());
			return property;
		} else
			return null;
	}

	private static PerguntaProperty<Intervalo> createByIntervalo(Pergunta<?> pergunta) {
		PerguntaProperty<Intervalo> property = new PerguntaProperty<Intervalo>(pergunta.getId(), pergunta.getNome(),
				pergunta.getDescricao(), TipoPergunta.INTERVALO);

		property.setAlternativa(pergunta.getAlternativa());

		return property;
	}

	private static PerguntaProperty<MultiplaEscolha> createByMultiplaEscolha(Pergunta<?> pergunta) {
		PerguntaProperty<MultiplaEscolha> multiplaEscolha = new PerguntaProperty<MultiplaEscolha>(pergunta.getId(), pergunta.getNome(), pergunta.getDescricao(),
				TipoPergunta.MULTIPLA_ESCOLHA);
		multiplaEscolha.setAlternativa(pergunta.getAlternativa());
		return multiplaEscolha;

	}

}
