package br.com.delfos.view.table.property;

import br.com.delfos.model.pesquisa.Alternativa;
import br.com.delfos.model.pesquisa.Intervalo;
import br.com.delfos.model.pesquisa.MultiplaEscolha;
import br.com.delfos.model.pesquisa.Paragrafo;
import br.com.delfos.model.pesquisa.Pergunta;
import br.com.delfos.model.pesquisa.Texto;
import br.com.delfos.model.pesquisa.TipoPergunta;

public class PerguntaPropertyUtil {

	public static Pergunta<?> toValue(PerguntaProperty<?> property) {
		Pergunta<?> pergunta = create(property);

		pergunta.setNome(property.getNome());
		pergunta.setDescricao(property.getDescricao());
		pergunta.setId(property.getId());

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
			return new PerguntaProperty<Texto>(pergunta.getId(), pergunta.getNome(), pergunta.getDescricao(),
			        TipoPergunta.TEXTO);
		} else if (pergunta.getAlternativa() instanceof Paragrafo) {
			return new PerguntaProperty<Paragrafo>(pergunta.getId(), pergunta.getNome(), pergunta.getDescricao(),
			        TipoPergunta.PARAGRAFO);
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
		return new PerguntaProperty<MultiplaEscolha>(pergunta.getId(), pergunta.getNome(), pergunta.getDescricao(),
		        TipoPergunta.MULTIPLA_ESCOLHA);

	}

}
