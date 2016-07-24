package br.com.delfos.control.auditoria;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.LoginApp;
import br.com.delfos.app.PrincipalApp;
import br.com.delfos.except.auditoria.UserNotAuthenticatedException;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.util.view.FXValidator;
import br.com.delfos.view.AlertAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Controller
public class LoginController implements Initializable {

	@FXML
	private Button btnEntrar;

	@FXML
	private Button btnAjuda;

	@FXML
	@NotNull
	private TextField txtLogin;

	@FXML
	@NotNull
	private PasswordField txtSenha;

	@FXML
	private ImageView imgView;

	@Autowired
	private Autenticador autenticador;

	@FXML
	private void handleButtonLogar(ActionEvent event) {
		try {
			autenticaUsuario();
		} catch (FXValidatorException e) {
			AlertAdapter.requiredDataNotInformed(e);
		} catch (UserNotAuthenticatedException e) {
			AlertAdapter.warning("Falha de autenticação", "O usuário e/ou senha informados não estão corretos.");
		}
	}

	private void autenticaUsuario() throws UserNotAuthenticatedException, FXValidatorException {
		if (FXValidator.validate(this)) {
			boolean autentica = autenticador.autentica(txtLogin.getText(), txtSenha.getText());

			if (autentica) {
				try {
					new PrincipalApp().start(new Stage());
				} catch (IOException e) {
					AlertAdapter.erroLoadFXML(e);
				}
				LoginApp.getStage().close();
			} else {
				throw new UserNotAuthenticatedException();

			}
		}
	}

	@FXML
	private void handleButtonAjuda(ActionEvent event) {
		// TODO: Implementar uma telinha de ajuda para o usuário.
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Autenticador.logout();
		configuraComponentes();
	}

	private void configuraComponentes() {
		txtLogin.setOnAction(event -> txtSenha.requestFocus());
		txtSenha.setOnAction(event -> handleButtonLogar(event));
		txtLogin.setText("root");
		txtSenha.setText("root");
	}

}
