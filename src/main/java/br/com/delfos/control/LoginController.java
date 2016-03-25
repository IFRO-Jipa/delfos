package br.com.delfos.control;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.LoginApp;
import br.com.delfos.app.PrincipalApp;
import br.com.delfos.dao.UsuarioDAO;
import br.com.delfos.model.Usuario;
import br.com.delfos.util.AlertFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Controller
public class LoginController implements Initializable {

	@Autowired
	private UsuarioDAO dao;

	@FXML
	private Button btnEntrar;

	@FXML
	private Button btnAjuda;

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private ImageView imgView;

	@FXML
	private void handleButtonLogar(ActionEvent event) throws IOException {

		if (logou()) {
			new PrincipalApp().start(new Stage());
			LoginApp.getStage().close();
		} else {
			AlertFactory.warning("Usuário e/ou senha incorretos.");
		}
	}

	private boolean logou() {
		System.out.println(dao == null ? "sim" : "não");
		String login = txtLogin.getText();
		String senha = txtSenha.getText();
		Optional<Usuario> usuario = Optional.ofNullable(dao.findByLoginAndSenha(login, senha));
		System.out.println(usuario.isPresent() ? usuario.get() : "nada foi encontrado.");
		return usuario.isPresent();
	}

	@FXML
	private void handleButtonAjuda(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image img = new Image(LoginController.class.getResourceAsStream("/imgs/logo-full.png"));
		imgView.setImage(img);
	}

}
