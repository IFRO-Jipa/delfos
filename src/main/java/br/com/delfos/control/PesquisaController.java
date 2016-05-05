package br.com.delfos.control;

import java.awt.event.ActionEvent;

import br.com.delfos.model.Questionario;
import br.com.delfos.util.ManipuladorDeComponentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PesquisaController {

    @FXML
    private Button removeEspecialistas;

    @FXML
    private TextField txtNome;

    @FXML
    private Button addPesquisador;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private TableView<?> tbEspecialistas;

    @FXML
    private ListView<?> listQuestionario;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button removePesquisador;

    @FXML
    private Button removeQuestionario;

    @FXML
    private DatePicker datePesquisa;

    @FXML
    private TextField txtLimite;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button addEspecialistas;

    @FXML
    private Button addQuestionario;

    @FXML
    private Button btnNovo;

    @FXML
    private TableView<?> tbPesquisadores;

    @FXML
    void handleButtonNovo(ActionEvent event) {
    	txtNome.setText("");
    	txtCodigo.setText("");
    	txtLimite.setText("");
    	txtDescricao.setText("");
		tbPesquisadores.getItems().clear();
		tbEspecialistas.getItems().clear();
		listQuestionario.getItems().clear();
		
		//Falta adicionar a data da pesquisa nesta lista
    }

    @FXML
    void handleButtonExcluir(ActionEvent event) {
    	
    }

    @FXML
    void handleButtonSalvar(ActionEvent event) {

    }
    
}
