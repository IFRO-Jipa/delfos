package br.com.delfos.control.auditoria;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.generic.AbstractController;
import br.com.delfos.dao.auditoria.FuncionalidadeDAO;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.util.TableCellFactory;
import br.com.delfos.view.AlertAdapter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

@Controller
public class FuncionalidadeController extends AbstractController<Funcionalidade, FuncionalidadeDAO> {
	@FXML
	private Button btnSalvar;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private ListView<Funcionalidade> tbRegistros;

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
	@NotNull
	private ComboBox<Funcionalidade> cbPreRequisito;

	@FXML
	private void handleBtnPesquisar(ActionEvent event) {
		this.pesquisaPorCodigo();
	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		limpa();
	}

	private void limpa() {
		this.txtCodigo.setText("");
		this.txtNome.setText("");
		this.txtDescricao.setText("");
		this.txtChave.setText("");
		this.cbPreRequisito.setValue(null);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		if (this.deleteIf(func -> func.getId() != null)) {

			this.tbRegistros.getItems().removeIf(f -> f.getId().equals(Long.parseLong(txtCodigo.getText())));

			limpa();
		}
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		try {
			Optional<Funcionalidade> retorno = this.salvar(toValue(), this);

			retorno.ifPresent(funcionalidadeNova -> {
				txtCodigo.setText(String.valueOf(funcionalidadeNova.getId()));
				tbRegistros.getItems().add(funcionalidadeNova);
				tbRegistros.getItems().sort(Comparator.comparing(Funcionalidade::getNome));
			});

		} catch (FXValidatorException e) {
			AlertAdapter.requiredDataNotInformed(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbRegistros.getItems().clear();
		this.populaTabela(dao.findAll());

		tbRegistros.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> atualizaCampos(newValue));

		cbPreRequisito.setItems(tbRegistros.getItems());

		this.tbRegistros.setCellFactory(new TableCellFactory<Funcionalidade>(this.tbRegistros).getCellFactory(f -> {
			return String.format("%s (%s)", f.getNome(), f.getChave());
		}));

		cbPreRequisito.setConverter(new StringConverter<Funcionalidade>() {

			@Override
			public Funcionalidade fromString(String string) {
				return null;
			}

			@Override
			public String toString(Funcionalidade object) {
				return object.getNome();
			}
		});

		this.tbRegistros.getItems().sort(Comparator.comparing(Funcionalidade::getNome));

	}

	@Override
	protected Funcionalidade toValue() {
		Long id = txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText());
		String nome = txtNome.getText();
		String chave = txtChave.getText();
		String descricao = txtDescricao.getText();
		Funcionalidade preRequisito = (cbPreRequisito.getValue() == null ? null : cbPreRequisito.getValue());
		return new Funcionalidade(id, nome, chave, descricao, preRequisito);
	}

	@Override
	protected void posiciona(Optional<Funcionalidade> value) {
		value.ifPresent(funcionalidade -> {
			atualizaCampos(funcionalidade);
			atualizaRegistroNaTabela(funcionalidade);
		});
	}

	private void atualizaCampos(Funcionalidade funcionalidade) {
		txtCodigo.setText(String.valueOf(funcionalidade.getId()));
		txtNome.setText(funcionalidade.getNome());
		txtChave.setText(funcionalidade.getChave());
		txtDescricao.setText(funcionalidade.getDescricao());

		cbPreRequisito.setValue(funcionalidade.getPreRequisito());
	}

	private void atualizaRegistroNaTabela(Funcionalidade funcionalidade) {
		Optional<Funcionalidade> optional = tbRegistros.getItems().stream()
				.filter(f -> f.getId().equals(funcionalidade.getId())).findFirst();

		optional.ifPresent(value -> {
			int index = tbRegistros.getItems().indexOf(value);
			tbRegistros.getItems().set(index, funcionalidade);
		});
	}

	private void populaTabela(List<Funcionalidade> funcionalidades) {

		tbRegistros.setItems(FXCollections.observableArrayList(funcionalidades));

	}

}
