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

	private static Pergunta<?> create(PerguntaProperty<?> property) {
		Pergunta<?> pergunta = null;
		// if (property.)
		// TODO: Implementar a criação
		return pergunta;

	}

	public static PerguntaProperty<?> fromPergunta(Pergunta<?> pergunta) {
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
			return new PerguntaProperty<Texto>(pergunta.getNome(), TipoPergunta.TEXTO);
		} else if (pergunta.getAlternativa() instanceof Paragrafo) {
			return new PerguntaProperty<Paragrafo>(pergunta.getNome(), TipoPergunta.PARAGRAFO);
		} else
			return null;
	}

	private static PerguntaProperty<Intervalo> createByIntervalo(Pergunta<?> pergunta) {
		PerguntaProperty<Intervalo> property = new PerguntaProperty<Intervalo>(pergunta.getNome(),
		        TipoPergunta.INTERVALO);

		property.setAlternativa(pergunta.getAlternativa());

		// TODO implementar

		return property;
	}

	private static PerguntaProperty<MultiplaEscolha> createByMultiplaEscolha(Pergunta<?> pergunta) {
		PerguntaProperty<MultiplaEscolha> property = new PerguntaProperty<MultiplaEscolha>(pergunta.getNome(),
		        TipoPergunta.MULTIPLA_ESCOLHA);

		// TODO implementar
		return property;

	}

}
