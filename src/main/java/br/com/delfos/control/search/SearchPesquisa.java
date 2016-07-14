package br.com.delfos.control.search;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import br.com.delfos.model.pesquisa.Pesquisa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class SearchPesquisa {

	private AbstractSearchable<Pesquisa> searchable;

	private static Map<String, BiPredicate<Pesquisa, String>> comparadores = new HashMap<>();

	static {
		/**
		 * Criação dos comparadores para uma pesquisa.
		 */
		comparadores.put("Nome", (pesquisa, filtro) -> {
			return pesquisa.getNome().toLowerCase().contains(filtro.toLowerCase());
		});
		comparadores.put("Descricão", (pesquisa, filtro) -> {
			return pesquisa.getDescricao().toLowerCase().contains(filtro.toLowerCase());
		});
		comparadores.put("Limite de até", (pesquisa, filtro) -> {
			return pesquisa.getLimite() <= Integer.valueOf(filtro);
		});

		comparadores.put("Limite menor que", (pesquisa, filtro) -> {
			return pesquisa.getLimite() >= Integer.valueOf(filtro);
		});

		comparadores.put("Têm pesquisadores com o nome: ", (p, filtro) -> {
			return p.getPesquisadores().stream().filter(pessoa -> pessoa.getNome().toLowerCase().contains(filtro))
					.count() > 0;
		});

		comparadores.put("Têm especialistas com o nome: ", (p, filtro) -> {
			return p.getEspecialistas().stream().filter(pessoa -> pessoa.getNome().toLowerCase().contains(filtro))
					.count() > 0;
		});
	}

	public SearchPesquisa(List<Pesquisa> itens) {
		this(FXCollections.observableArrayList(itens));
	}

	public SearchPesquisa(ObservableList<Pesquisa> itens) {
		this.searchable = new AbstractSearchable<>(itens, comparadores);
		this.searchable.setText(Pesquisa::getNome);
		this.searchable.setDescription(this::formataNome);
		this.searchable.setOnCloseAfterDoubleClick(true);
	}

	private String formataNome(Pesquisa p) {
		return String.format(
				"%d-%s\nDescrição:%s\nPesquisadores: %s\nEspecialistas:%s\nData de Início: %s\nVencimento: %s",
				p.getId(), p.getNome(), p.getDescricao(),
				p.getPesquisadores().toString().replace("[", "").replace("]", ""),
				p.getEspecialistas().toString().replace("[", "").replace("]", ""),
				p.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				p.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

	public SearchPesquisa setConsumerPersonalizadoDoubleClick(Consumer<Pesquisa> consumerPersonalizadoDoubleClick) {
		searchable.setConsumerPersonalizadoDoubleClick(consumerPersonalizadoDoubleClick);
		return this;
	}

	public SearchPesquisa setOnCloseAfterDoubleClick(boolean status) {
		searchable.setOnCloseAfterDoubleClick(status);
		return this;
	}

	public Optional<Pesquisa> showAndWait() {
		return searchable.showAndWait();
	}

	public Optional<BorderPane> getPanel() {
		return searchable.getPanel();
	}

}
