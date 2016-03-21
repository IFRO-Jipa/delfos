package delfos;

import java.util.List;

public class Produto {
	private String nome;
	private String id;
	private String descricaoTeste;

	private List<String> lista;

	public List<String> getLista() {
		return lista;
	}

	public void setLista(List<String> lista) {
		this.lista = lista;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricaoTeste() {
		return descricaoTeste;
	}

	public void setDescricaoTeste(String descricaoTeste) {
		this.descricaoTeste = descricaoTeste;
	}

}
