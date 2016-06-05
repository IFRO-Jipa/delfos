package br.com.delfos.view.table.property;

import br.com.delfos.model.pesquisa.Alternativa;
import br.com.delfos.model.pesquisa.MultiplaEscolha;
import br.com.delfos.model.pesquisa.TipoPergunta;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PerguntaProperty<T extends Alternativa> {

	private final SimpleStringProperty nome;
	private final SimpleObjectProperty<TipoPergunta> tipoPergunta;
	private final SimpleObjectProperty<Alternativa> alternativa;

	public PerguntaProperty(String nome, TipoPergunta tipoPergunta) {
		this.nome = new SimpleStringProperty(nome);
		this.tipoPergunta = new SimpleObjectProperty<TipoPergunta>(tipoPergunta);
		this.alternativa = new SimpleObjectProperty<Alternativa>(tipoPergunta.getType());
	}

	public String getNome() {
		return nome.get();
	}

	public TipoPergunta getTipoPergunta() {
		return tipoPergunta.get();
	}

	public void setTipoPergunta(TipoPergunta tipo) {
		this.tipoPergunta.set(tipo);
	}

	public ObjectProperty<TipoPergunta> getTipoPerguntaProperty() {
		return tipoPergunta;
	}

	public SimpleStringProperty getNomeProperty() {
		return nome;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa.set(alternativa);
	}

	@SuppressWarnings("unchecked")
	public T getAlternativa() {
		return (T) this.alternativa.get();
	}

	@Override
	public String toString() {
		return "PerguntaProperty [nome=" + nome.get() + ", tipoPergunta=" + tipoPergunta.get() + "]";
	}

	public static void main(String[] args) {
		PerguntaProperty<MultiplaEscolha> property = new PerguntaProperty<MultiplaEscolha>("asdfasdfa",
		        TipoPergunta.MULTIPLA_ESCOLHA);
		property.setAlternativa(new MultiplaEscolha());
		property.getAlternativa().getEscolhas().keySet().forEach(System.out::println);
		property.getAlternativa().add("Alto", 33);
	}

}
