package br.com.delfos.control;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

@Controller
public class PesquisaController {

	@FXML
	private ListView<Questionario> listViewQuestionario;

	@FXML
	private ListView<Pessoa> listViewPesquisador;

	@FXML
	private Hyperlink linkAdicionaQuestionario;

	@FXML
	private TextField txtNome;

	@FXML
	private ListView<Pessoa> listViewPesquisadores;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private DatePicker datePesquisa;

	@FXML
	private Button btnSalvar;

	@FXML
	private ListView<Pessoa> listViewEspecialista;

	@FXML
	private TextField txtLimite;

	@FXML
	private Hyperlink linkAdicionaPesquisador;

	@FXML
	private Button pesquisaCodigoDaPesquisa;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	@FXML
	private Hyperlink linkAdicionaEspecialista;

	@FXML
	private AnchorPane rootPane;

	@Autowired
	private PesquisaDAO daoPesquisa;

	@Autowired
	private PessoaDAO daoPessoa;

	private List<Pessoa> especialistas;

	@FXML
	private void handleLinkAdicionaEspecialista(ActionEvent event) {

		try {
			ListSelection<Pessoa> seletor = new ListSelection<>("Selecione os Especialistas",
					filtraEspecialistaInexistentes());

			seletor.setCellFactory(cellFactory());

			Optional<List<Pessoa>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewEspecialista.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ListCell<Pessoa> configuraTextoNaCelula() {
		ListCell<Pessoa> cell = new ListCell<Pessoa>() {
			@Override
			protected void updateItem(final Pessoa p, final boolean bln) {
				super.updateItem(p, bln);
				if (p != null) {
					setText(p.getNome());
				} else {
					setText(null);
				}
			}

		};
		return cell;
	}

	private List<Pessoa> filtraEspecialistaInexistentes() {
		List<Pessoa> result = new ArrayList<>();

		if (listViewEspecialista.getItems().isEmpty()) {
			System.out.println("Especialistas estão vazios? " + especialistas.isEmpty());
			result.addAll(especialistas);
		} else {
			especialistas.forEach(pessoa -> {
				if (!listViewEspecialista.getItems().contains(pessoa)) {
					result.add(pessoa);
				}

			});
		}

		return result;
	}

	@FXML
	private void handleLinkAdicionaPesquisador(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		this.salvar(montaRegistro());
	}

	@FXML
	private void handleLinkAdicionaQuestionario(ActionEvent event) {

	}

	private void salvar(Pesquisa value) {
		if (ManipuladorDeComponentes.validaCampos(rootPane)) {
			Optional<Pesquisa> save = daoPesquisa.save(value);
			save.ifPresent(pesquisa -> {
				txtCodigo.setText(String.valueOf(pesquisa.getId()));
				AlertBuilder.information("Salvo com sucesso");
			});

			if (!save.isPresent())
				AlertBuilder.information("Não foi salvo, algo de estranho aconteceu.\nTente novamente mais tarde");
		}
	}

	@SuppressWarnings("unused")
	private Pesquisa montaRegistro() {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();

		// Continuar inicialização de variáveis

		return p;
	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {

		ManipuladorDeTelas.limpaCampos(rootPane);
		if (ManipuladorDeComponentes.validaCampos(this))
			salva();
	}

	private void salva() {
		Pesquisa pesquisa = montaRegistro();

		Optional<Pesquisa> returned = daoPesquisa.save(pesquisa);

		if (returned.isPresent()) {
			abreRegistro(returned.get());
			AlertBuilder.information("Salvo com sucesso");
		} else {
			AlertBuilder.warning("Não foi salvo... tente novamente");
		}

	}

	private void abreRegistro(Pesquisa pesquisa) {
		if (pesquisa != null) {
			atualizaCampos(pesquisa);
		}
	}

	private void atualizaCampos(Pesquisa pesquisa) {
		txtCodigo.setText(String.valueOf(pesquisa.getId()));
		txtNome.setText(pesquisa.getNome());
		txtDescricao.setText(pesquisa.getDescricao());
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		// TODO excluir
		excluiRegistro();

	}

	private void excluiRegistro() {
		if (!txtCodigo.getText().isEmpty()) {
			if (AlertBuilder.confirmation("Deseja realmente excluir o registro?")) {
				daoPesquisa.delete(Long.parseLong(txtCodigo.getText()));
				ManipuladorDeTelas.limpaCampos(rootPane);
				AlertBuilder.information("Excluído com sucesso");
			}
		} else
			return;
	}

	@SuppressWarnings("unused")
	// TODO: Averiguar a devida utilização para esse método em pesquisa. É
	// necessário?
	private Callback<DatePicker, DateCell> factoryDeVencimento = param -> new DateCell() {
		@Override
		public void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);

			if (item.isBefore(PesquisaController.this.datePesquisa.getValue().plusDays(1))) {
				this.setDisable(true);
				this.setStyle("-fx-background-color: #ffc0cb;");
			}

			long p = ChronoUnit.DAYS.between(PesquisaController.this.datePesquisa.getValue(), item);
			this.setTooltip(new Tooltip(String.format("Sua pesquisa durará %d dia(s).", p)));
		};
	};

	@FXML
	private void pesquisa() {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
	}

	private Callback<ListView<Pessoa>, ListCell<Pessoa>> cellFactory() {

		return p -> {
			ListCell<Pessoa> cell = configuraTextoNaCelula();

			ContextMenu menu = getContextMenuToListView(cell);

			cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
				// TODO: criar implementação correta.
				if (isNowEmpty) {
					cell.setContextMenu(null);
				} else {
					cell.setContextMenu(menu);
				}
			});

			return cell;
		};
	}

	private <T> ContextMenu getContextMenuToListView(ListCell<T> cell) {
		MenuItem menuRemoveOnly = new MenuItem();
		menuRemoveOnly.setText("Remover");
		menuRemoveOnly.setOnAction(action -> {
			listViewEspecialista.getItems().remove(cell.getItem());
			listViewPesquisador.getItems().remove(cell.getItem());
			listViewQuestionario.getItems().remove(cell.getItem());
		});

		MenuItem menuRemoveAll = new MenuItem();
		menuRemoveAll.setText("Remover todos");
		menuRemoveAll.setOnAction(action -> {
			if (AlertBuilder.confirmation("Remover todas as funcionalidades selecionadas?")) {
				listViewPesquisador.getItems().clear();
				listViewEspecialista.getItems().clear();
				listViewQuestionario.getItems().clear();
			}
		});

		ContextMenu menu = new ContextMenu(menuRemoveOnly, menuRemoveAll);
		return menu;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
		this.datePesquisa.setValue(LocalDate.now());
		this.especialistas = new ArrayList<>(daoPessoa.findAll());
		System.out.println(especialistas);
		listViewEspecialista.setCellFactory(cellFactory());
	}

}
