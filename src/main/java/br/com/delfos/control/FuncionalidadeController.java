package br.com.delfos.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.FuncionalidadeDAO;
import br.com.delfos.model.Funcionalidade;
import br.com.delfos.util.AlertBuilder;
import br.com.delfos.util.ManipuladorDeComponentes;
import br.com.delfos.util.ManipuladorDeTelas;
import br.com.delfos.view.FuncionalidadeProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Controller
public class FuncionalidadeController implements Initializable {
	@FXML
	private Button btnSalvar;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<?> tbRegistros;

	@FXML
	@NotNull
	private TextField txtNome;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	@NotNull
	private TextField txtChave;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnPesquisar;

	@FXML
	private Button btnNovo;

	@FXML
	private TableColumn<FuncionalidadeProperty, String> tcFuncionalidade;

	@FXML
	private TableColumn<FuncionalidadeProperty, Long> tcId;

	@FXML
	private TableColumn<FuncionalidadeProperty, String> tcChave;

	@Autowired
	private FuncionalidadeDAO dao;

	private ObservableList<Funcionalidade> funcionalidades = FXCollections.observableArrayList();

	@FXML
	private void handleBtnPesquisar(ActionEvent event) {

	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		if (!txtCodigo.getText().isEmpty()) {

		} else {
			AlertBuilder.information("Selecione um registro para poder excluir");
		}
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(this)) {
			Funcionalidade f = new Funcionalidade(txtNome.getText(), txtChave.getText(),
			        txtDescricao.getText(), null);
			Funcionalidade save = dao.save(f);

			if (save != null) {
				AlertBuilder.information("Salvo com sucesso");
//				tbRegistros.getItems().add(save);
			} else {
				AlertBuilder.information("Registro não foi salvo... cuidado");
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		funcionalidades.clear();
		funcionalidades.addAll(dao.findAll());
//		tbRegistros.setItems(funcionalidades);
	}

	private void populaTabela(List<Funcionalidade> funcionalidades) {

	}

}
