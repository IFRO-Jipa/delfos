package br.com.delfos.control.basic;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.MudarSenhaApp;
import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.dao.auditoria.UsuarioDAO;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.view.AlertAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

@Controller
public class MudarSenhaController implements Initializable {

	@FXML
	private Button btnSalvar;

	@FXML
	private Text txtNomeUsuario;

	@FXML
	private PasswordField txtNovaSenha;

	@FXML
	private PasswordField txtConfirmaSenha;

	@FXML
	private PasswordField txtSenhaAntiga;

	@Autowired
	private UsuarioDAO daoUsuario;

	private MudarSenhaApp application;

	private boolean senhaModificada = false;

	@FXML
	void handleButtonAplicar(ActionEvent event) {
		this.senhaModificada = false;
		if (preenchimentoValido()) {
			if (txtNovaSenha.getText().equals(txtConfirmaSenha.getText())) {
				Optional<Usuario> optional = daoUsuario.autentica(Autenticador.getUsuarioAutenticado().getLogin(),
						txtSenhaAntiga.getText());

				if (!optional.isPresent()) {
					this.senhaModificada = false;
					AlertAdapter.invalidParameters("A senha antiga informada é inválida.");
				}

				optional.ifPresent(usuario -> {
					usuario.setSenha(txtNovaSenha.getText());
					daoUsuario.save(usuario).ifPresent(value -> {
						this.senhaModificada = true;
						AlertAdapter.information("Senha modificada com sucesso.",
								"O usuário " + usuario.getLogin() + " teve a senha modificada com êxito.");
						this.application.getStage().close();
					});;
				});
			} else {
				this.senhaModificada = false;
				AlertAdapter.invalidParameters("As senhas informadas não coincidem.");
				txtNovaSenha.clear();
				txtConfirmaSenha.clear();
			}
		}
	}

	private boolean preenchimentoValido() {
		if (txtNovaSenha.getText().isEmpty()) {
			AlertAdapter.requiredDataNotInformed("Digite a sua nova senha.");
			txtNovaSenha.requestFocus();
			return false;
		}
		if (txtSenhaAntiga.getText().isEmpty()) {
			AlertAdapter.requiredDataNotInformed("Digite a sua senha antiga.");
			txtSenhaAntiga.requestFocus();
			return false;
		}

		if (txtConfirmaSenha.getText().isEmpty()) {
			AlertAdapter.requiredDataNotInformed("Confirme a sua senha");
			txtConfirmaSenha.requestFocus();
			return false;
		}

		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.txtNomeUsuario.setText(Autenticador.getUsuarioAutenticado().getLogin());
	}

	public boolean isSenhaModificada() {
		return senhaModificada;
	}

	public void setApplication(MudarSenhaApp app) {
		this.application = app;
	}

}
