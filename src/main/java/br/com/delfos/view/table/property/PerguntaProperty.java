package br.com.delfos.view.table.property;

import br.com.delfos.model.pesquisa.Alternativa;
import br.com.delfos.model.pesquisa.MultiplaEscolha;
import br.com.delfos.model.pesquisa.TipoPergunta;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PerguntaProperty<T extends Alternativa> {

	private final SimpleLongProperty id;
	private final SimpleStringProperty nome;
	private final SimpleStringProperty descricao;
	private final SimpleObjectProperty<TipoPergunta> tipoPergunta;
	private final SimpleObjectProperty<Alternativa> alternativa;

	public PerguntaProperty(String nome, TipoPergunta tipoPergunta) {
		this.id = new SimpleLongProperty();
		this.nome = new SimpleStringProperty(nome);
		this.descricao = new SimpleStringProperty();
		this.tipoPergunta = new SimpleObjectProperty<TipoPergunta>(tipoPergunta);
		this.alternativa = new SimpleObjectProperty<Alternativa>(tipoPergunta.getType());
	}

	public PerguntaProperty(Long id, String nome, String descricao, TipoPergunta tipo) {
		this.id = new SimpleLongProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.descricao = new SimpleStringProperty(descricao);
		this.tipoPergunta = new SimpleObjectProperty<>(tipo);
		this.alternativa = new SimpleObjectProperty<>(tipo.getType());
	}

	public String getNome() {
		return nome.get();
	}

	public TipoPergunta getTipoPergunta() {
		return tipoPergunta.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}

	public Long getId() {
		return this.id.get();
	}

	public void setId(Long id) {
		this.id.set(id);
	}

	public SimpleLongProperty getIdProperty() {
		return id;
	}

	public SimpleStringProperty getDescricaoProperty() {
		return descricao;
	}

	public String getDescricao() {
		return this.descricao.get();
	}

	public void setDescricao(String descricao) {
		this.descricao.set(descricao);
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
