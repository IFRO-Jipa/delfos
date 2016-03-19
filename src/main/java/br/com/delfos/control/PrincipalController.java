package br.com.delfos.control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import br.com.delfos.util.ManipuladorDeMenus;
import br.com.delfos.util.SpringFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

@Controller
public class PrincipalController implements Initializable {

	@FXML
	private MenuBar menuBar;

	@FXML
	private Pane paneAtalhos;

	@FXML
	private TabPane tabPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			fechaJanelas();
			configuraECriaOsMenus();
			configuraAcoesParaMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configuraECriaOsMenus() throws Exception {
		List<Menu> menusParaOMenuBar = new ManipuladorDeMenus().create().getMenus();
		menuBar.getMenus().addAll(menusParaOMenuBar);
	}

	private void configuraAcoesParaMenu() {
		menuBar.getMenus().forEach(menu -> {
			menu.getItems().forEach(value -> {
				if (value.getId().contains(":")) {
					String[] props = value.getId().split(":");
					value.setOnAction(e -> {
						try {
							abreJanela(props[1], value.getText());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
				}
			});
		});
	}

	private void fechaJanelas() {
		tabPane.getTabs().clear();
	}

	private void abreJanela(String view, String title) throws IOException {
		AnchorPane load = (AnchorPane) SpringFXMLLoader.load(String.format("/fxml/%s", view));

		Tab tab = new Tab(title);
		tab.setContent(load);
		// tab.getTabPane().setMaxWidth(TabPane.USE_PREF_SIZE);
		// tab.getTabPane().setMaxHeight(TabPane.USE_PREF_SIZE);

		tabPane.getTabs().add(tab);

	}

}
