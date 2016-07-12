package br.com.delfos.control.auditoria;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.generic.AbstractController;
import br.com.delfos.dao.auditoria.PerfilAcessoDAO;
import br.com.delfos.dao.auditoria.UsuarioDAO;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.auditoria.PerfilAcesso;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.view.AlertAdapter;
import br.com.delfos.view.manipulador.ScreenUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

@Controller
public class UsuarioController extends AbstractController<Usuario, UsuarioDAO> {

	@FXML
	@NotNull
	private PasswordField txtSenha;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private Button btnPesquisaCodigo;

	@FXML
	@NotNull
	private TextField txtNomeResponsavel;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private Button btnSalvar;

	@FXML
	@NotNull
	private ComboBox<PerfilAcesso> comboPerfilAcesso;

	@FXML
	@NotNull
	private TextField txtLogin;

	@FXML
	private Button btnPesquisaResponsavel;

	@FXML
	@NotNull
	private PasswordField txtConfirmaSenha;

	@FXML
	@NotNull
	private CheckBox cbStatus;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	private Pessoa responsavel;

	@Autowired
	private PerfilAcessoDAO perfilDAO;

	@SuppressWarnings("unused")
	@Autowired
	private PessoaDAO pessoaDAO;

	@FXML
	private void handleButtonPesquisaCodigo(ActionEvent event) {
		pesquisaPorCodigo();
	}

	@Override
	protected void posiciona(Optional<Usuario> value) {
		value.ifPresent(usuario -> {
			txtCodigo.setText(usuario.getId() == null ? null : String.valueOf(usuario.getId()));
			txtDescricao.setText(usuario.getDescricao() == null ? null : usuario.getDescricao());
			txtLogin.setText(usuario.getLogin());
			txtNomeResponsavel.setText(usuario.getPessoa().getNome());
			comboPerfilAcesso.setValue(usuario.getPerfilAcesso());
			cbStatus.setSelected(usuario.isAtivo());
		});
	}

	@FXML
	private void handleButtonPesquisaResponsavel(ActionEvent event) {
		// TODO: Implementar a pesquisa personalizável.
	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ScreenUtils.limpaCampos(rootPane);
		txtLogin.requestFocus();
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		this.deleteIf(usuario -> usuario.getId() != null);
		ScreenUtils.limpaCampos(rootPane);
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {
		try {
			Optional<Usuario> optional = this.salvar(toValue(), this);

			optional.ifPresent(usuario -> {
				txtCodigo.setText(String.valueOf(usuario.getId()));

			});

		} catch (FXValidatorException e) {
			AlertAdapter.error(e);
		}
	}

	@Override
	protected Usuario toValue() {
		Usuario u = new Usuario();
		u.setId(txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText()));
		u.setPessoa(responsavel);
		u.setPerfilAcesso(comboPerfilAcesso.getValue());
		u.setLogin(txtLogin.getText());
		if (txtSenha.getText().equals(txtConfirmaSenha.getText())) {
			u.setSenha(txtSenha.getText());
		} else {
			txtSenha.clear();
			txtConfirmaSenha.clear();
			txtSenha.requestFocus();
			throw new IllegalArgumentException("As senhas informadas não coincidem.");
		}
		u.setStatus(cbStatus.isSelected());
		return u;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<PerfilAcesso> perfis = FXCollections.observableArrayList(perfilDAO.findAll());
		comboPerfilAcesso.setConverter(new StringConverter<PerfilAcesso>() {

			@Override
			public String toString(PerfilAcesso object) {
				return object.getNome();
			}

			@Override
			public PerfilAcesso fromString(String string) {
				return null;
			}
		});
		comboPerfilAcesso.setItems(perfis);
	}

}
