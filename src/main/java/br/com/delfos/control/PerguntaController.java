package br.com.delfos.control;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.TipoPergunta;
import br.com.delfos.view.table.factory.ComboBoxCellFactory;
import br.com.delfos.view.table.factory.TextFieldCellFactory;
import br.com.delfos.view.table.property.PerguntaProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.util.StringConverter;

@Controller
public class PerguntaController implements Initializable {

	@FXML
	private TableView<PerguntaProperty> tbPerguntas;

	@FXML
	private TableColumn<PerguntaProperty, String> columnNome;

	@FXML
	private TableColumn<PerguntaProperty, TipoPergunta> columnTipo;

	@FXML
	private TableColumn<PerguntaProperty, PerguntaProperty> columnAcao;

	private final ObservableList<TipoPergunta> tiposDePergunta = FXCollections
	        .observableArrayList(TipoPergunta.getAll());

	private final ObservableList<PerguntaProperty> dadosTabela = FXCollections.observableArrayList(
	        new PerguntaProperty("Qual é o seu nome?", TipoPergunta.INTERVALO),
	        new PerguntaProperty("Qual é o seu grau de conhecimento em Java?", TipoPergunta.MULTIPLA_ESCOLHA),
	        new PerguntaProperty("Deixe seu comentário sobre a pesquisa?", TipoPergunta.PARAGRAFO));

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTablePergunta();
	}

	private void initTablePergunta() {

		initColumnNome();
		initColumnTipoPergunta();
		initColumnAcao();

	}

	private void initColumnAcao() {
		columnAcao.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		columnAcao.setResizable(false);
		columnAcao.setCellFactory(getButtonFactory());
	}

	private Callback<TableColumn<PerguntaProperty, PerguntaProperty>, TableCell<PerguntaProperty, PerguntaProperty>>
	        getButtonFactory() {
		// TODO Auto-generated method stub
		return param -> new TableCell<PerguntaProperty, PerguntaProperty>() {
			
		};
	}

	private void initColumnNome() {
		this.columnNome.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
		this.columnNome.setCellFactory(getTextFieldFactory());
	}

	private void initColumnTipoPergunta() {
		this.columnTipo.setCellValueFactory(cellData -> cellData.getValue().getTipoPerguntaProperty());
		this.columnTipo.setCellFactory(getComboBoxFactory());
	}

	private Callback<TableColumn<PerguntaProperty, TipoPergunta>, TableCell<PerguntaProperty, TipoPergunta>>
	        getComboBoxFactory() {

		
		
		return param -> new ComboBoxCellFactory<PerguntaProperty, TipoPergunta>(tiposDePergunta,
		        getComboBoxConverter());
	}

	private StringConverter<TipoPergunta> getComboBoxConverter() {
		return new StringConverter<TipoPergunta>() {

			@Override
			public TipoPergunta fromString(String string) {
				return null;
			}

			@Override
			public String toString(TipoPergunta object) {
				return object.name();
			}
		};
	}

	private Callback<TableColumn<PerguntaProperty, String>, TableCell<PerguntaProperty, String>> getTextFieldFactory() {
		return param -> new TextFieldCellFactory<PerguntaProperty>();
	}

}
