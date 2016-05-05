package br.com.delfos.control;


import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Controller
public class QuestionarioController {

    @FXML
    private Button btnSalvar;
    
    @FXML
    private TableView<?> tbPerguntas;

    @FXML
    private AnchorPane anchorPaneEndereco;

    @FXML
    private Tab tbEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCod;

    @FXML
    private TextArea txtDesc;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnNovo;

    @FXML
    void handleButtonNovo(ActionEvent event) {
    	txtCod.setText("");
    	txtDesc.setText("");
    	txtNome.setText("");
    	tbPerguntas.getItems().clear();
    }

    @FXML
    void handleButtonExcluir(ActionEvent event) {

    }

    @FXML
    void handleButtonSalvar(ActionEvent event) {

    }

}
