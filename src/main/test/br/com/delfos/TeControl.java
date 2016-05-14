package br.com.delfos;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TeControl implements Initializable {
	@FXML
	private DatePicker date;

	@FXML
	private TableColumn<?, ?> columnNome;

	@FXML
	private Label lbl;

	@FXML
	private TableView<?> tbPergunta;

	@FXML
	private TableColumn<?, ?> columnTipo;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbPergunta.setEditable(true);
	}

}
