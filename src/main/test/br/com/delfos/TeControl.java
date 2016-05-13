package br.com.delfos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;

public class TeControl {

	@FXML
	private DatePicker date;

	@FXML
	private Label lbl;

	@FXML
	private Button btn;

	@FXML
	void hue(ActionEvent event) {
		System.out.println("hue");
	}

	@FXML
	void action(ActionEvent event) {
		System.out.println("action");
	}

	@FXML
	void dragdetected(ActionEvent event) {
		System.out.println("dragdetected");
	}

	@FXML
	void dragdropped(ActionEvent event) {
		System.out.println("dragdropped");

	}

	@FXML
	void dragexit(ActionEvent event) {
		System.out.println("dragexit");
	}

}
