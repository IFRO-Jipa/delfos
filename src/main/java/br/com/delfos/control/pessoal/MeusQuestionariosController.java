package br.com.delfos.control.pessoal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.view.AlertBuilder;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

@Controller
public class MeusQuestionariosController implements Initializable {

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Accordion accordionPesquisas;

	@FXML
	private AnchorPane rootPane;

	@Autowired
	private PesquisaDAO pesquisaDAO;

	private ObservableList<Pesquisa> pesquisas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configCache();
		configScroll();
		mostraPesquisas();
	}

	private void configScroll() {
		scrollPane.viewportBoundsProperty().addListener((ChangeListener<Bounds>) (observable, oldValue, newValue) -> {
			accordionPesquisas.setPrefWidth(newValue.getWidth());
		});
	}

	private void mostraPesquisas() {
		accordionPesquisas.getPanes().setAll(getTitledPanes());
	}

	private List<TitledPane> getTitledPanes() {
		List<TitledPane> panes = new ArrayList<>();

		try {
			for (Pesquisa pesquisa : pesquisas) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MeusQuestionariosController.class.getClassLoader()
						.getResource("fxml/conta/TemplateMeusQuestionarios.fxml"));
				AnchorPane borderPane = loader.load();
				AnchorPane.setTopAnchor(borderPane, 0.0);
				AnchorPane.setBottomAnchor(borderPane, 0.0);
				AnchorPane.setLeftAnchor(borderPane, 0.0);
				AnchorPane.setRightAnchor(borderPane, 0.0);

				final TemplateMeusQuestionariosController controller = loader.getController();
				controller.set(pesquisa);

				TitledPane panel = new TitledPane();
				panel.setText(pesquisa.getNome());
				panel.setContent(borderPane);
				panel.setPadding(new Insets(0, 0, 4, 0));
				panel.setAlignment(Pos.TOP_LEFT);
				panes.add(panel);
			}
		} catch (IOException e) {
			AlertBuilder.error(e);
		}

		return panes;
	}

	private void configCache() {
		pesquisas = FXCollections.observableArrayList(pesquisaDAO.findByEspecialista(Autenticador.getDonoDaConta()));
	}

}
