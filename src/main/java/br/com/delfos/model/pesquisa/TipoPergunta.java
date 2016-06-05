package br.com.delfos.model.pesquisa;

import java.util.Arrays;
import java.util.List;

interface Conversor {
	Alternativa getType();

	String getLocation();
}

public enum TipoPergunta implements Conversor {
	TEXTO {

		@Override
		public Alternativa getType() {
			return new Texto();
		}

		@Override
		public String getLocation() {
			return "/fxml/pergunta/ConfigTextoParagrafoView.fxml";
		}

	},
	PARAGRAFO {
		@Override
		public Alternativa getType() {
			return new Paragrafo();
		}

		@Override
		public String getLocation() {
			return "/fxml/pergunta/ConfigTextoParagrafoView.fxml";
		}

	},
	MULTIPLA_ESCOLHA {

		@Override
		public Alternativa getType() {
			return new MultiplaEscolha();
		}

		@Override
		public String getLocation() {
			return "/fxml/pergunta/ConfigMultiplaEscolhaView.fxml";
		}

	},
	INTERVALO {

		@Override
		public Alternativa getType() {
			return new Intervalo();
		}

		@Override
		public String getLocation() {
			return "/fxml/pergunta/ConfigIntervaloView.fxml";
		}

	};

	public static List<TipoPergunta> getAll() {
		return Arrays.asList(TipoPergunta.values());
	}

}
