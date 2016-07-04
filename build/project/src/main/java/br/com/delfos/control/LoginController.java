package br.com.delfos.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController implements Initializable {

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
	private void handleButtonLogar(ActionEvent event) {

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
