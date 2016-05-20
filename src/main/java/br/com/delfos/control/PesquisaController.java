package br.com.delfos.control;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.TableCellFactory;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.scene.control.RadioButton;

@Controller
public class PesquisaController {

	@FXML
	private ListView<Questionario> listViewQuestionario;

	@FXML
	private RadioButton statusAtivo;

	@FXML
	private ListView<Pessoa> listViewPesquisador;

	@FXML
	private Hyperlink linkAdicionaQuestionario;

	@FXML
	@NotNull
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
	
	private List<Pessoa> pesquisadores;
	
	private List<Questionario> questionarios;

	// Link Especialista

	@FXML
	private void handleLinkAdicionaEspecialista(ActionEvent event) {

		try {
			ListSelection<Pessoa> seletor = new ListSelection<>("Selecione os Especialistas",
					filtraEspecialistaInexistentes());

			seletor.setCellFactory(new TableCellFactory<Pessoa>(null).getCellFactory(pessoa -> pessoa.getNome()));

			Optional<List<Pessoa>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewEspecialista.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * private ListCell<Pessoa> configuraTextoNaCelula() { ListCell<Pessoa> cell
	 * = new ListCell<Pessoa>() {
	 * 
	 * @Override protected void updateItem(final Pessoa p, final boolean bln) {
	 * super.updateItem(p, bln); if (p != null) { setText(p.getNome()); } else {
	 * setText(null); } }
	 * 
	 * }; return cell; }
	 */

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

	// Link Pesquisador

	@FXML
	private void handleLinkAdicionaPesquisador(ActionEvent event) {
		
		try {
			ListSelection<Pessoa> seletor = new ListSelection<>("Selecione os Pesquisadores",
					filtraPesquisadorInexistente());

			seletor.setCellFactory(new TableCellFactory<Pessoa>(null).getCellFactory(pessoa -> pessoa.getNome()));

			Optional<List<Pessoa>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewPesquisador.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private List<Pessoa> filtraPesquisadorInexistente() {
		List<Pessoa> result = new ArrayList<>();

		if (listViewPesquisador.getItems().isEmpty()) {
			System.out.println("Pesquisadores estão vazios? " + pesquisadores.isEmpty());
			result.addAll(pesquisadores);
		} else {
			pesquisadores.forEach(pessoa -> {
				if (!listViewPesquisador.getItems().contains(pessoa)) {
					result.add(pessoa);
				}

			});
		}

		return result;
	}

	// Link Questionario

	@FXML
	private void handleLinkAdicionaQuestionario(ActionEvent event) {
		try {
			ListSelection<Questionario> seletor = new ListSelection<>("Selecione os Questionários",
					filtraQuestionarioInexistente());

			seletor.setCellFactory(new TableCellFactory<Questionario>(null).getCellFactory(questionario -> questionario.getNome()));

			Optional<List<Questionario>> target = seletor.showAndWait();
			target.ifPresent(result -> {
						listViewQuestionario.getItems().addAll(result);
			});
				} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private List<Questionario> filtraQuestionarioInexistente() {
		List<Questionario> result = new ArrayList<>();

		if (listViewQuestionario.getItems().isEmpty()) {
			System.out.println("Questionarios estão vazios? " + questionarios.isEmpty());
			result.addAll(questionarios);
		} else {
			pesquisadores.forEach(questionario -> {
				if (!listViewQuestionario.getItems().contains(questionario)) {
					//	result.add(questionario);
				}

			});
		}

		return result;
	}


	@FXML
	private void handleButtonSalvar(ActionEvent event) {

		this.salvar(montaRegistro());
	}

	// Implementação do botão Salvar

	private void salvar(Pesquisa value) {
		salva(montaRegistro());
	}

	private Pesquisa montaRegistro() {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();
		LocalDate data = datePesquisa.getValue();
		int limite = txtLimite.getText().isEmpty() ? 0 : Integer.parseInt(txtLimite.getText());

		// Continuar inicialização de variáveis

		p.setId(id);
		p.setDescricao(descricao);
		p.setNome(nome);
		p.setLimite(limite);
		p.setDate(data);

		return p;
	}

	// Implementação do botão Novo

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	private void salva(Pesquisa value) {
		try {
			if (ManipuladorDeComponentes.validaCampos(rootPane)) {
				Optional<Pesquisa> save = daoPesquisa.save(value);
				save.ifPresent(pesquisa -> {
					txtCodigo.setText(String.valueOf(pesquisa.getId()));
					AlertBuilder.information("Salvo com sucesso");
				});

				if (!save.isPresent())
					AlertBuilder.information("Não foi salvo, algo de estranho aconteceu.\nTente novamente mais tarde");
			}
		} catch (IllegalArgumentException ex) {
			AlertBuilder.warning("Preencha os campos corretamente.");
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

	// Implementação do botão Excluir

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
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

	// Implementação dos links

	/*
	 * private Callback<ListView<Pessoa>, ListCell<Pessoa>> cellFactory() {
	 * 
	 * return p -> { ListCell<Pessoa> cell = configuraTextoNaCelula();
	 * 
	 * ContextMenu menu = getContextMenuToListView(cell);
	 * 
	 * cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> { //
	 * cell.setContextMenu(null); } else { cell.setContextMenu(menu); } });
	 * 
	 * return cell; }; }
	 * 
	 * private <T> ContextMenu getContextMenuToListView(ListCell<T> cell) {
	 * MenuItem menuRemoveOnly = new MenuItem();
	 * menuRemoveOnly.setText("Remover"); menuRemoveOnly.setOnAction(action -> {
	 * listViewEspecialista.getItems().remove(cell.getItem());
	 * listViewPesquisador.getItems().remove(cell.getItem());
	 * listViewQuestionario.getItems().remove(cell.getItem()); });
	 * 
	 * MenuItem menuRemoveAll = new MenuItem(); menuRemoveAll.setText(
	 * "Remover todos"); menuRemoveAll.setOnAction(action -> { if
	 * (AlertBuilder.confirmation(
	 * "Remover todas as funcionalidades selecionadas?")) {
	 * listViewPesquisador.getItems().clear();
	 * listViewEspecialista.getItems().clear();
	 * listViewQuestionario.getItems().clear(); } });
	 * 
	 * ContextMenu menu = new ContextMenu(menuRemoveOnly, menuRemoveAll); return
	 * menu; }
	 */

	@FXML
	private void pesquisa() {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
		this.datePesquisa.setValue(LocalDate.now());
		this.especialistas = new ArrayList<>(daoPessoa.findAll());
		System.out.println(especialistas);
		listViewEspecialista.setCellFactory(
				new TableCellFactory<Pessoa>(listViewEspecialista).getCellFactory(pessoa -> pessoa.getNome()));

		listViewPesquisador.setCellFactory(
				new TableCellFactory<Pessoa>(listViewPesquisador).getCellFactory(pessoa -> pessoa.getNome()));

		listViewQuestionario.setCellFactory(new TableCellFactory<Questionario>(listViewQuestionario)
				.getCellFactory(questionario -> String.valueOf(questionario.getId() + "-" + questionario.getNome())));
	}

	// Pesquisa por código

	@FXML
	private void pesquisar() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Consulta por código");
		dialog.setHeaderText("Consulta de Registros");
		dialog.setContentText("Informe código da Pesquisa");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Optional<Pesquisa> optional = Optional.ofNullable(this.daoPesquisa.findOne(Long.parseLong(result.get())));
			if (optional.isPresent()) {
				this.abreRegistro(optional.get());
			} else {
				ManipuladorDeTelas.limpaCampos(this.rootPane);
				AlertBuilder.warning("Nenhum registro foi encontrado.");
			}
		} else {
			ManipuladorDeTelas.limpaCampos(this.rootPane);
			AlertBuilder.warning("Nenhum registro foi encontrado.");
		}
	}

}
