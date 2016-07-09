package br.com.delfos.fxml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.delfos.util.TableCellFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SeletorDeRadioButton extends Application {

	public class Item {
		private String nome;
		private boolean situacao = false;
		private int codigo;

		public Item(String nome, boolean situacao, int codigo) {
			super();
			this.nome = nome;
			this.situacao = situacao;
			this.codigo = codigo;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public int getCodigo() {
			return codigo;
		}

		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}

		public boolean isSituacao() {
			return situacao;
		}

		public void setSituacao(boolean situacao) {
			this.situacao = situacao;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Item [nome=");
			builder.append(nome);
			builder.append(", situacao=");
			builder.append(situacao);
			builder.append(", codigo=");
			builder.append(codigo);
			builder.append("]");
			return builder.toString();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	private ListView<Item> listItens;
	private SortedList<Item> ordenador;

	@Override
	public void start(Stage primaryStage) throws Exception {
		listItens = new ListView<>();
		listItens.setItems(getItens());
		listItens.setCellFactory(new TableCellFactory<Item>(listItens).getCellFactory(item -> getName(item)));

		BorderPane pane = new BorderPane(listItens);
		pane.setBottom(getComparators());
		primaryStage.setScene(new Scene(pane, 250, 400));
		primaryStage.setTitle("Teste de ordenadores");
		primaryStage.show();
	}

	private HBox getComparators() {
		HBox box = new HBox();

		ToggleGroup grupo = new ToggleGroup();

		RadioButton rbNome = new RadioButton("Nome");
		RadioButton rbSituacao = new RadioButton("Situação");
		RadioButton rbCodigo = new RadioButton("Código");

		rbNome.setOnAction(event -> ordenaPeloNome());
		rbSituacao.setOnAction(event -> ordenaPelaSituacao());
		rbCodigo.setOnAction(event -> ordenaPeloCodigo());

		grupo.getToggles().addAll(rbNome, rbSituacao, rbCodigo);

		box.getChildren().addAll(rbNome, rbSituacao, rbCodigo);
		return box;
	}

	private void ordenaPeloCodigo() {
		ordenador.setComparator(Comparator.comparing(Item::getCodigo));
	}

	private void ordenaPelaSituacao() {
		ordenador.setComparator(Comparator.comparing(Item::isSituacao));
	}

	private void ordenaPeloNome() {
		ordenador.setComparator(Comparator.comparing(Item::getNome));
	}

	private String getName(Item item) {
		return String.format("[ %b ] - %d-%s", item.isSituacao(), item.getCodigo(), item.getNome());
	}

	private SortedList<Item> getItens() {
		List<Item> itens = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			itens.add(new Item("Item " + (i + 1), i % 2 == 0, (i + 1)));
		}

		ordenador = new SortedList<>(FXCollections.observableArrayList(itens));
		ordenador.setComparator(Comparator.comparing(item -> item.getNome()));

		return ordenador;
	}
}
