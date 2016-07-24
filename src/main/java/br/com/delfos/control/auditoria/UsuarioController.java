package br.com.delfos.control.auditoria;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.generic.AbstractController;
import br.com.delfos.control.search.SearchPessoa;
import br.com.delfos.control.search.SearchUsuario;
import br.com.delfos.dao.auditoria.PerfilAcessoDAO;
import br.com.delfos.dao.auditoria.UsuarioDAO;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.auditoria.PerfilAcesso;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.view.AlertAdapter;
import br.com.delfos.view.manipulador.ScreenUtils;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
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
	private TextField txtLogin;

	@FXML
	private Button btnPesquisaResponsavel;

	@FXML
	private PasswordField txtConfirmaSenha;

	@FXML
	@NotNull
	private CheckBox cbStatus;

	@FXML
	private Button btnExcluir;

	@FXML
	private CheckBox cbGeraCredenciais;

	@FXML
	private Button btnNovo;

	private Optional<Pessoa> responsavel;

	@Autowired
	private PerfilAcessoDAO daoPerfilAcesso;

	@Autowired
	private PessoaDAO daoPessoa;

	@FXML
	private void handleButtonPesquisaCodigo(ActionEvent event) {
		SearchUsuario search = new SearchUsuario(this.getDao().findAll());
		this.posiciona(search.showAndWait());
	}

	@Override
	protected void posiciona(Optional<Usuario> value) {
		value.ifPresent(usuario -> {
			txtCodigo.setText(usuario.getId() == null ? null : String.valueOf(usuario.getId()));
			txtDescricao.setText(usuario.getDescricao() == null ? null : usuario.getDescricao());
			txtLogin.setText(usuario.getLogin());
			comboPerfilAcesso.setValue(usuario.getPerfilAcesso());
			cbStatus.setSelected(usuario.isAtivo());

			this.setResponsavel(usuario.getPessoa());
		});
	}

	public void setResponsavel(Pessoa pessoa) {
		this.responsavel = Optional.ofNullable(pessoa);

		this.responsavel.ifPresent(value -> txtNomeResponsavel.setText(value.getNome()));
	}

	@FXML
	private void handleButtonPesquisaResponsavel(ActionEvent event) {
		SearchPessoa search = new SearchPessoa(this.daoPessoa.findByUsuarioIsNull());
		search.showAndWait().ifPresent(pessoa -> {
			this.setResponsavel(pessoa);
		});
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
				AlertAdapter.information("Salvo com sucesso",
						"O usuário foi criado para o/a " + usuario.getPerfilAcesso().getNome() + " com o login "
								+ usuario.getLogin() + " e ingressado ao perfil "
								+ usuario.getPerfilAcesso().getNome());
			});

		} catch (FXValidatorException e) {
			AlertAdapter.error("Dados obrigatórios não preenchidos" , e);
		}
	}

	public Optional<Usuario> salvar() {
		try {
			return this.salvar(toValue(), this);
		} catch (FXValidatorException e) {
			AlertAdapter.error("Dados obrigatórios não preenchidos" , e);
			return Optional.empty();
		}
	}

	@Override
	protected Usuario toValue() {
		try {
			Usuario u = new Usuario();
			u.setId(txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText()));
			u.setPessoa(this.getResponsavel());
			u.setPerfilAcesso(comboPerfilAcesso.getValue());
			if (cbGeraCredenciais.isSelected()) {
				String cpf = u.getPessoa().getCpf().replace(".", "").replace("-", "");
				u.setLogin(cpf);
				u.setSenha(cpf);
			} else {
				if (!txtLogin.getText().isEmpty() || !txtConfirmaSenha.getText().isEmpty()
						|| !txtSenha.getText().isEmpty()) {
					throw new IllegalStateException("É necessário informar o login e senha do usuário.");
				}
				u.setLogin(txtLogin.getText());
				if (txtSenha.getText().equals(txtConfirmaSenha.getText())) {
					u.setSenha(txtSenha.getText());
				} else {
					txtSenha.clear();
					txtConfirmaSenha.clear();
					txtSenha.requestFocus();
					throw new IllegalArgumentException("As senhas informadas não coincidem.");
				}
			}
			u.setStatus(cbStatus.isSelected());
			return u;
		} catch (RuntimeException runtime) {
			AlertAdapter.error("Problema desconhecido." , runtime);
			return null;
		}

	}

	private Pessoa getResponsavel() {
		return this.responsavel
				.orElseThrow(() -> new IllegalStateException("Não foi informado o responsável por essa conta."));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<PerfilAcesso> perfis = FXCollections.observableArrayList(daoPerfilAcesso.findAll());
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

		cbGeraCredenciais.selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
			disableCredenciais(newValue);
		});

		this.txtCodigo.setDisable(true);
	}

	public void setGenerateCredentials(boolean flag) {
		this.cbGeraCredenciais.setSelected(flag);
	}

	public void setVisibleButtons(boolean flag) {
		this.btnExcluir.setVisible(flag);
		this.btnNovo.setVisible(flag);
		this.btnSalvar.setVisible(flag);
		this.btnPesquisaResponsavel.disableProperty().bind(new BooleanBinding() {

			@Override
			protected boolean computeValue() {
				return !flag;
			}
		});

	}

	private void disableCredenciais(boolean value) {
		txtLogin.setDisable(value);
		txtSenha.setDisable(value);
		txtConfirmaSenha.setDisable(value);

	}

	public void setVisibleTxtDescricao(boolean flag) {
		this.txtDescricao.setVisible(flag);
	}

}
