package br.com.delfos.model.pesquisa;

import java.util.Arrays;
import java.util.List;

interface Conversor {
	Alternativa getType();
}

public enum TipoPergunta implements Conversor {
	TEXTO {

		@Override
		public Alternativa getType() {
			return new Texto();
		}

	},
	PARAGRAFO {

		@Override
		public Alternativa getType() {
			return new Paragrafo();
		}

	},
	MULTIPLA_ESCOLHA {

		@Override
		public Alternativa getType() {
			return new MultiplaEscolha();
		}

	},
	INTERVALO {

		@Override
		public Alternativa getType() {
			return new Intervalo();
		}

	};

	public static List<TipoPergunta> getAll() {
		return Arrays.asList(TipoPergunta.values());
	}

}
