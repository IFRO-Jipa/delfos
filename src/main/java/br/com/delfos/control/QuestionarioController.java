package br.com.delfos.control;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

@Controller
public class QuestionarioController {

    @FXML
    private Button btnSalvar;

    @FXML
    private AnchorPane anchorPaneEndereco;

    @FXML
    private Tab tbEndereco;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnNovo;

    @FXML
    void handleButtonNovo(ActionEvent event) {

    }

    @FXML
    void handleButtonExcluir(ActionEvent event) {

    }

    @FXML
    void handleButtonSalvar(ActionEvent event) {

    }

}
