package br.com.delfos.control;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.QuestionarioApp;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.except.pesquisa.LimiteDeEspecialistasAtingidoException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.TableCellFactory;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ValidadorDeCampos;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

@Controller
public class PesquisaController implements Initializable {

	@FXML
	private ListView<Questionario> listViewQuestionario;

	@FXML
	private Text textAtivo;

	@FXML
	private ListView<Pessoa> listViewPesquisador;

	@FXML
	private Hyperlink linkAdicionaQuestionario;

	@FXML
	@NotNull
	private TextField txtNome;

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

	@SuppressWarnings("unused")
	private List<Questionario> questionarios;

	// Link Especialista

	@FXML
	private void handleLinkAdicionaEspecialista(ActionEvent event) {

		try {
			ListSelection<Pessoa> seletor = new ListSelection<>("Selecione os Especialistas",
			        filtraPessoasParaSelecao(TipoPessoa.ESPECIALISTA));

			seletor.textFormat(pessoa -> pessoa.getNome());

			Optional<List<Pessoa>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewEspecialista.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<Pessoa> filtraPessoasParaSelecao(TipoPessoa tipo) {
		List<Pessoa> filtro = new ArrayList<>();

		// cria refer�ncia na mem�ria para objetos j� criados
		List<Pessoa> cache = tipo.equals(TipoPessoa.ESPECIALISTA) ? especialistas : pesquisadores;
		ListView<Pessoa> list = tipo.equals(TipoPessoa.ESPECIALISTA) ? listViewEspecialista : listViewPesquisador;

		boolean listaVazia = cache.isEmpty();

		if (listaVazia) {
			filtro.addAll(cache);
		} else {
			cache.forEach(pessoa -> {
				if (!list.getItems().contains(pessoa)) {
					filtro.add(pessoa);
				}
			});
		}

		return filtro;

	}

	@FXML
	private void handleLinkAdicionaPesquisador(ActionEvent event) {
		try {
			ListSelection<Pessoa> seletor = new ListSelection<>("Selecione os Pesquisadores",
			        filtraPessoasParaSelecao(TipoPessoa.PESQUISADOR));

			seletor.textFormat(pessoa -> pessoa.getNome());

			Optional<List<Pessoa>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewPesquisador.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleLinkAdicionaQuestionario(ActionEvent event) {
		// ABRIR A TELA DE QUESTIONÁRIO E ESPERAR POR UM REGISTRO NOVO
		try {
			QuestionarioApp frame = new QuestionarioApp();
			Optional<Questionario> result = frame.showAndWait();

			if (result.isPresent()) {
				System.out.println("Tem registro ai dentro.");
				System.out.println(result.get());
			} else {
				System.out.println("não tem");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		try {
			this.salvar(montaRegistro());
		} catch (LimiteDeEspecialistasAtingidoException ex) {
			AlertBuilder.error(ex);
		}
	}

	private void salvar(Pesquisa value) throws LimiteDeEspecialistasAtingidoException {
		try {
			if (ValidadorDeCampos.validateAll(rootPane)) {
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

	Pesquisa montaRegistro() throws LimiteDeEspecialistasAtingidoException {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();
		LocalDate data = datePesquisa.getValue();
		int limite = txtLimite.getText().isEmpty() ? 0 : Integer.parseInt(txtLimite.getText());

		List<Pessoa> pesquisadores = listViewPesquisador.getItems().isEmpty() ? null : listViewPesquisador.getItems();
		List<Pessoa> especialistas = listViewEspecialista.getItems().isEmpty() ? null : listViewEspecialista.getItems();
		// List<Questionario> questionarios =
		// listViewQuestionario.getItems().isEmpty() ? null
		// : listViewQuestionario.getItems();

		p.setId(id);
		p.setDescricao(descricao);
		p.setNome(nome);
		p.setLimite(limite);
		p.setDate(data);
		// p.addQuestionarios(questionarios);
		p.addEspecialistas(especialistas);
		p.addPesquisadores(pesquisadores);
		if (getStatus()) {
			p.setAtivo();
		} else {
			p.finaliza();
		}
		return p;
	}

	private boolean getStatus() {
		// TODO Auto-generated method stub
		return textAtivo.getText().equals("Em andamento");
	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		excluiRegistro();

	}

	private void excluiRegistro() {
		if (!txtCodigo.getText().isEmpty()) {
			if (AlertBuilder.confirmation("Deseja realmente excluir o registro?")) {
				daoPesquisa.delete(Long.parseLong(txtCodigo.getText()));
				ManipuladorDeTelas.limpaCampos(rootPane);
				AlertBuilder.information("Exclu�do com sucesso");
			}
		} else
			return;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		configFields();
		configListViews();
		configCache();
		configViews();
	}

	private void configViews() {
		setStatus(true);
	}

	private void setStatus(boolean status) {
		if (status) {
			textAtivo.setText("Em andamento");
			textAtivo.setStyle("-fx-text-fill: #007FFF");
		} else {
			textAtivo.setText("Finalizada");
			textAtivo.setStyle("-fx-text-fill: #32CD32");
		}
	}

	private void configCache() {
		this.especialistas = new ArrayList<>(daoPessoa.findByTipo(TipoPessoa.ESPECIALISTA));
		this.pesquisadores = new ArrayList<>(daoPessoa.findByTipo(TipoPessoa.PESQUISADOR));
	}

	private void configFields() {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
		this.datePesquisa.setValue(LocalDate.now());
	}

	private void configListViews() {
		listViewEspecialista.setCellFactory(
		        new TableCellFactory<Pessoa>(listViewEspecialista).getCellFactory(pessoa -> pessoa.getNome()));

		listViewPesquisador.setCellFactory(
		        new TableCellFactory<Pessoa>(listViewPesquisador).getCellFactory(pessoa -> pessoa.getNome()));

		listViewQuestionario.setCellFactory(new TableCellFactory<Questionario>(listViewQuestionario)
		        .getCellFactory(questionario -> String.valueOf(questionario.getId() + "-" + questionario.getNome())));
	}

	// Pesquisa por c�digo
	@FXML
	private void pesquisar() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Consulta por c�digo");
		dialog.setHeaderText("Consulta de Registros");
		dialog.setContentText("Informe c�digo da Pesquisa");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Optional<Pesquisa> optional = Optional.ofNullable(this.daoPesquisa.findOne(Long.parseLong(result.get())));
			if (optional.isPresent()) {
				this.abreRegistro(optional);
			} else {
				ManipuladorDeTelas.limpaCampos(this.rootPane);
				AlertBuilder.warning("Nenhum registro foi encontrado.");
			}
		} else {
			ManipuladorDeTelas.limpaCampos(this.rootPane);
			AlertBuilder.warning("Nenhum registro foi encontrado.");
		}
	}

	void abreRegistro(Optional<Pesquisa> optional) {
		optional.ifPresent(pesquisa -> {
			txtCodigo.setText(String.valueOf(pesquisa.getId()));
			txtNome.setText(pesquisa.getNome());
			txtDescricao.setText(pesquisa.getDescricao());
			txtLimite.setText(String.valueOf(pesquisa.getLimite()));
			setStatus(pesquisa.isAtivo());

			listViewEspecialista.getItems().clear();
			listViewEspecialista.getItems().addAll(pesquisa.getEspecialistas());

			listViewPesquisador.getItems().clear();
			listViewPesquisador.getItems().addAll(pesquisa.getPesquisadores());
		});
	}

}
