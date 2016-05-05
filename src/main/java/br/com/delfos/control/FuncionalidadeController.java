package br.com.delfos.control;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

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
	private ComboBox<Funcionalidade> cbPreRequisito;

	@FXML
	private void handleBtnPesquisar(ActionEvent event) {

	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		try {
			if (!txtCodigo.getText().isEmpty()) {
				dao.delete(Long.parseLong(txtCodigo.getText()));
				tbRegistros.getItems()
				        .removeIf(valor -> valor.getId() == Long.parseLong(txtCodigo.getText()));
				AlertBuilder.information("Excluído com sucesso");
			} else {
				AlertBuilder.information("Selecione um registro para poder excluir");
			}
		} catch (DataIntegrityViolationException e) {
			AlertBuilder.error("Não é possível excluir esse registro\nEle é utilizado em outras informações.",
			        e, false);
		}
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(this))
			salva();
	}

	private void salva() {
		Funcionalidade funcionalidade = montaRegistro();

		Optional<Funcionalidade> returned = Optional.ofNullable(dao.save(funcionalidade));
		if (returned.isPresent()) {
			abreRegistro(returned.get());
			AlertBuilder.information("Salvo com sucesso");
		} else {
			AlertBuilder.warning("Não foi salvo... tente novamente");
		}
	}

	private Funcionalidade montaRegistro() {
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String chave = txtChave.getText();
		String descricao = txtDescricao.getText();
		Funcionalidade preRequisito = (cbPreRequisito.getValue() == null ? null : cbPreRequisito.getValue());
		return new Funcionalidade(id, nome, chave, descricao, preRequisito);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbRegistros.getColumns().clear();
		tbRegistros.getItems().clear();
		this.populaTabela(dao.findAll());

		tbRegistros.getSelectionModel().selectedItemProperty()
		        .addListener((observable, oldValue, newValue) -> abreRegistro(newValue));

		cbPreRequisito.setItems(tbRegistros.getItems());
		cbPreRequisito.setConverter(new StringConverter<Funcionalidade>() {

			@Override
			public String toString(Funcionalidade object) {
				return object.getNome();
			}

			@Override
			public Funcionalidade fromString(String string) {
				return null;
			}

		});
	}

	private void abreRegistro(Funcionalidade funcionalidade) {
		if (funcionalidade != null) {
			atualizaCampos(funcionalidade);
			atualizaTabela(funcionalidade);
		}
	}

	private void atualizaCampos(Funcionalidade funcionalidade) {
		txtCodigo.setText(String.valueOf(funcionalidade.getId()));
		txtNome.setText(funcionalidade.getNome());
		txtChave.setText(funcionalidade.getChave());
		txtDescricao.setText(funcionalidade.getDescricao());

		cbPreRequisito.setValue(funcionalidade.getPreRequisito());
	}

	private void atualizaTabela(Funcionalidade funcionalidade) {
		// tbRegistros.getItems().removeIf(registro -> registro.getId() ==
		// registro.getId());
		// tbRegistros.getItems().add(funcionalidade);
		// tbRegistros.getSelectionModel().select(funcionalidade);
		// tbRegistros.sort();
	}

	private void populaTabela(List<Funcionalidade> funcionalidades) {
		TableView<Funcionalidade> temp = new TableViewFactory<Funcionalidade>()
		        .criaTableView(funcionalidades);

		tbRegistros.getColumns().addAll(temp.getColumns());
		tbRegistros.setItems(temp.getItems());
		
	}

}
