package br.com.delfos.model.pesquisa;

import java.util.Arrays;
import java.util.List;

interface Personalizavel {
	boolean isPersonalizavel();
}

public enum TipoPergunta implements Personalizavel {
	TEXTO {
		@Override
		public boolean isPersonalizavel() {
			return false;
		}
	},
	PARAGRAFO {
		@Override
		public boolean isPersonalizavel() {
			return false;
		}
	},
	MULTIPLA_ESCOLHA {
		@Override
		public boolean isPersonalizavel() {
			return true;
		}
	},
	INTERVALO {
		@Override
		public boolean isPersonalizavel() {
			return true;
		}
	};

	public static List<TipoPergunta> getAll() {
		return Arrays.asList(TipoPergunta.values());
	}

}
