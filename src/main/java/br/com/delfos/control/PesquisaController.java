package br.com.delfos.control;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.dao.pesquisa.QuestionarioDAO;
import br.com.delfos.view.AlertBuilder;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Hyperlink;
import br.com.delfos.view.manipulador.ManipuladorDeComponentes;
import br.com.delfos.view.manipulador.ManipuladorDeTelas;

@Controller
public class PesquisaController {
	
	@FXML
	private AnchorPane rootPane;

	@FXML
	private DatePicker datePesquisa;

	@FXML
	private Button btnSalvar;

	@FXML
	private TextField txtLimite;

	@FXML
	private TextField txtNome;

	@FXML
	private Button pesquisaCodigoDaPesquisa;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private ListView<?> listQuestionario;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	@FXML
	private TableView<?> tbPesquisadores;

	@Autowired
	private PesquisaDAO dao;
	
	 @FXML
	 private Hyperlink linkAdicionaQuestionario;
	 
	 @FXML
	 private Hyperlink linkAdicionaEspecialista;
	 
	 @FXML
	 private ListView<?> listViewEspecialista;


	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {

	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(this))
			salva();
	}

	private void salva() {
		Pesquisa pesquisa = montaRegistro();

		Optional<Pesquisa> returned = dao.save(pesquisa);
		if (returned.isPresent()) {
			abreRegistro(returned.get());
			AlertBuilder.information("Salvo com sucesso");
		} else {
			AlertBuilder.warning("Não foi salvo... tente novamente");
		}

	}

	private void abreRegistro(Pesquisa pesquisa) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void handleButtonpesquisaCodigoDaPesquisa(ActionEvent event) {

	}

	@SuppressWarnings("unused")
	private Pesquisa montaRegistro() {
		Pesquisa p = new Pesquisa();
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();
		return p;
	}

	@FXML
	private void pesquisa() {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
		this.datePesquisa.disabledProperty();
	}
	
	@FXML
	private void handleLinkAdicionaQuestionario(ActionEvent event) {
		

	}

	
	@FXML
	private void handleLinkAdicionaEspecialista(ActionEvent event) {
		

	}
	
}
