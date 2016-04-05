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
import br.com.delfos.view.TableViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
	private TableView<Funcionalidade> tbRegistros;

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

	@Autowired
	private FuncionalidadeDAO dao;

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
				// tbRegistros.getItems().add(save);
			} else {
				AlertBuilder.information("Registro não foi salvo... cuidado");
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbRegistros.getColumns().clear();
		tbRegistros.getItems().clear();
		this.populaTabela(dao.findAll());

		tbRegistros.getSelectionModel().selectedItemProperty()
		        .addListener((observable, oldValue, newValue) -> abreRegistro(newValue));

	}

	private void abreRegistro(Funcionalidade item) {
		if (item != null) {
			txtCodigo.setText(String.valueOf(item.getId()));
			txtNome.setText(item.getNome());
			txtChave.setText(item.getChave());
			txtDescricao.setText(item.getDescricao());
		}
	}

	private void populaTabela(List<Funcionalidade> funcionalidades) {
		TableView<Funcionalidade> temp = new TableViewFactory<Funcionalidade>()
		        .criaTableView(funcionalidades);

		tbRegistros.getColumns().addAll(temp.getColumns());
		tbRegistros.setItems(temp.getItems());
	}

}
