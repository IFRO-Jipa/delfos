package br.com.delfos.control;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.PerfilAcessoDAO;
import br.com.delfos.dao.PessoaDAO;
import br.com.delfos.dao.UsuarioDAO;
import br.com.delfos.model.PerfilAcesso;
import br.com.delfos.model.Pessoa;
import br.com.delfos.model.Usuario;
import br.com.delfos.util.AlertBuilder;
import br.com.delfos.util.ManipuladorDeComponentes;
import br.com.delfos.util.ManipuladorDeTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

@Controller
public class UsuarioController implements Initializable {

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
	private CheckBox cbStatus;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	private Pessoa responsavel;

	@Autowired
	private PerfilAcessoDAO perfilDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@SuppressWarnings("unused")
	@Autowired
	private PessoaDAO pessoaDAO;

	@FXML
	void handleButtonPesquisaCodigo(ActionEvent event) {
		pesquisaPorCodigo();
	}

	private void pesquisaPorCodigo() {
		// TODO: Retirar esse código feio.... isso não vai ser aqui, e sim numa tela de
		// consulta.
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("PRÉVIA - Consulta de Registros");
		dialog.setContentText("informe o código da pessoa");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Optional<Usuario> optional = Optional
			        .ofNullable(usuarioDAO.findOne(Long.parseLong(result.get())));
			if (optional.isPresent()) {
				posicionaRegistro(optional.get());
			} else {
				ManipuladorDeTelas.limpaCampos(rootPane);
				AlertBuilder.warning("Nenhum registro foi encontrado.");
			}
		} else {
			ManipuladorDeTelas.limpaCampos(rootPane);
			AlertBuilder.warning("Nenhum registro foi encontrado.");
		}
	}

	private void posicionaRegistro(Usuario usuario) {
		txtCodigo.setText(usuario.getId() == null ? null : String.valueOf(usuario.getId()));
		txtDescricao.setText(usuario.getDescricao() == null ? null : usuario.getDescricao());
		txtLogin.setText(usuario.getLogin());
		txtNomeResponsavel.setText(usuario.getPessoa().getNome());
		comboPerfilAcesso.setValue(usuario.getPerfilAcesso());
		cbStatus.setSelected(usuario.isAtivo());
	}

	@FXML
	void handleButtonPesquisaResponsavel(ActionEvent event) {

	}

	@FXML
	void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
		txtLogin.requestFocus();
	}

	@FXML
	void handleButtonExcluir(ActionEvent event) {
		excluir(txtCodigo.getText());
	}

	private void excluir(String codigo) {
		if (codigo.isEmpty()) {
			usuarioDAO.delete(Long.parseLong(txtCodigo.getText()));
			AlertBuilder.information("Excluído com sucesso");
			ManipuladorDeTelas.limpaCampos(rootPane);
			txtLogin.requestFocus();
		}
	}

	@FXML
	void handleButtonSalvar(ActionEvent event) {
		this.salvar(montaRegistro());
	}

	private void salvar(Usuario value) {
		if (ManipuladorDeComponentes.validaCampos(rootPane)) {
			Optional<Usuario> save = usuarioDAO.save(value);
			save.ifPresent(bean -> {
				txtCodigo.setText(String.valueOf(bean.getId()));
				AlertBuilder.information("Salvo com sucesso");
			});

			if (!save.isPresent())
				AlertBuilder.information(
				        "Não foi salvo, algo de estranho aconteceu.\nTente novamente mais tarde");
		}
	}

	private Usuario montaRegistro() {
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
