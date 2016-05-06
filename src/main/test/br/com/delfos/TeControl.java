package br.com.delfos;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@Controller
public class TeControl {

	@FXML
	private Label lbl;

	@FXML
	private Button btn;

	@FXML
	void hue(ActionEvent event) {
		this.btn.setOnAction(action -> {
			if (this.lbl.getStyle().equals("-fx-text-fill: #ff5c33")) {
				this.lbl.setStyle("-fx-text-fill: #228d22");
				this.lbl.setText("Ativo");
			} else {
				this.lbl.setStyle("-fx-text-fill: #ff5c33");
				this.lbl.setText("Desativo");
			}
		});
	}

}
