package br.com.delfos.control;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.delfos.view.animation.FadeInRightTransition;
import br.com.delfos.view.animation.FadeInTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SplashScreenController implements Initializable {
	@FXML
	private ImageView imgLogo;

	@FXML
	private Button btnClose;

	@FXML
	private Text lblWelcome;

	@FXML
	private Text lblRudy;

	@FXML
	private ImageView imgLoading;

	@FXML
	private VBox vboxBottom;

	@FXML
	private void handleBtnClose(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	private synchronized void longStart() {
		new FadeInRightTransition(imgLogo).play();
		new FadeInRightTransition(lblWelcome).play();
		new FadeInTransition(vboxBottom).play();
		new FadeInRightTransition(imgLoading).play();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image img = new Image(SplashScreenController.class.getResourceAsStream("/imgs/logo-full.png"));
		imgLogo.setImage(img);
		Image imgLoad = new Image(SplashScreenController.class.getResourceAsStream("/imgs/load.GIF"));
		imgLoading.setImage(imgLoad);
		longStart();

	}
}
