package br.com.delfos.control.search;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import br.com.delfos.model.basic.Pessoa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchPessoa {

	private AbstractSearchable<Pessoa> searchable;

	private static Map<String, BiPredicate<Pessoa, String>> comparadores = new HashMap<>();

	static {
		comparadores.put("Nome", (pessoa, filtro) -> {
			return pessoa.getNome().toLowerCase().contains(filtro);
		});
		comparadores.put("Apelido", (pessoa, filtro) -> {
			return pessoa.getApelido().toLowerCase().contains(filtro);
		});

		comparadores.put("CPF", (pessoa, filtro) -> {
			return pessoa.getCpf().toLowerCase().replaceAll(".", "").replaceAll("-", "").contains(filtro);
		});

		comparadores.put("RG", (pessoa, filtro) -> {
			return pessoa.getRg().toLowerCase().contains(filtro);
		});

		comparadores.put("E-mail", (pessoa, filtro) -> {
			return pessoa.getEmail().toLowerCase().contains(filtro);
		});

		comparadores.put("Usuário de acesso", (pessoa, filtro) -> {
			return pessoa.getUsuario().getLogin().toLowerCase().contains(filtro);
		});

	}

	public SearchPessoa(List<Pessoa> itens) {
		this(FXCollections.observableArrayList(itens));
	}

	public SearchPessoa(ObservableList<Pessoa> itens) {
		this.searchable = new AbstractSearchable<>(itens, comparadores);
		this.searchable.setText(Pessoa::getNome);
		this.searchable.setDescription(this::formataNome);
		this.setOnCloseAfterDoubleClick(true);
	}

	private String formataNome(Pessoa pessoa) {
		StringBuilder builder = new StringBuilder();
		builder.append(pessoa.getId());
		builder.append("-");
		builder.append(pessoa.getNome());
		builder.append("\nE-mail         :  " + pessoa.getEmail());
		builder.append("\nCPF            : " + pessoa.getCpf());
		builder.append("\nRg             :" + pessoa.getRg());
		builder.append(
				"\nData Nascimento: " + pessoa.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		builder.append("\nTipo de pessoa :" + configuraNomeDosTipos(pessoa));
		return builder.toString();
	}

	private String configuraNomeDosTipos(Pessoa pessoa) {

		if (pessoa.isPesquisador() && pessoa.isEspecialista()) {
			return "Pesquisador(a) e Especialista";
		} else {
			if (pessoa.isPesquisador()) {
				return "Pesquisador(a)";
			}
			if (pessoa.isEspecialista()) {
				return "Especialista";
			}

			return "Não informado";
		}
	}

	public SearchPessoa setConsumerPersonalizadoDoubleClick(Consumer<Pessoa> consumerPersonalizadoDoubleClick) {
		searchable.setConsumerPersonalizadoDoubleClick(consumerPersonalizadoDoubleClick);
		return this;
	}

	public SearchPessoa setOnCloseAfterDoubleClick(boolean status) {
		searchable.setOnCloseAfterDoubleClick(status);
		return this;
	}

	public Optional<Pessoa> showAndWait() {
		return searchable.showAndWait();
	}

}
