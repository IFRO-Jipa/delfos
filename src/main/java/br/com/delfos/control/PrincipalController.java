package br.com.delfos.control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Controller;

import br.com.delfos.app.PrincipalApp;
import br.com.delfos.model.Funcionalidade;
import br.com.delfos.util.AlertBuilder;
import br.com.delfos.util.ManipuladorDeMenus;
import br.com.delfos.util.SpringFXMLLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

@Controller
public class PrincipalController implements Initializable {

	@FXML
	private MenuBar menuBar;

	@FXML
	private Pane paneAtalhos;

	@FXML
	private TabPane tabPane;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private BorderPane subRootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			fechaJanelas();
			configuraECriaOsMenus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configuraECriaOsMenus() throws Exception {
		Set<Funcionalidade> permissoes = AutenticadorDeUsuario.getPermissoesDeAcesso();

		List<Menu> menusParaOMenuBar = new ManipuladorDeMenus(permissoes).create().getMenus();

		menusParaOMenuBar.add(menuLogout());

		menuBar.getMenus().addAll(menusParaOMenuBar);
		configuraMenuBar(menuBar.getMenus());
	}

	private Menu menuLogout() {
		Menu menu = new Menu("Logout");
		menu.setOnAction(action -> acaoParaLogout(action));
		return menu;
	}

	private void acaoParaLogout(ActionEvent event) {
		if (AlertBuilder.confirmation("Deseja realmente deslogar?")) {
			PrincipalApp.getStage().hide();
			AlertBuilder.information("teste");
			PrincipalApp.getStage().show();
		}
	}

	private void configuraMenuBar(ObservableList<Menu> menus) {
		menus.forEach(menu -> {
			configuraAcoesParaMenu(menu);
		});
	}

	private void configuraAcoesParaMenu(Menu menu) {
		menu.getItems().forEach(value -> {
			if (value instanceof Menu)
				this.configuraAcoesParaMenu((Menu) value);
			else
				setOnActionMenu(value);
		});
	}

	private void setOnActionMenu(MenuItem value) {
		if (value.getId().contains(":")) {
			String[] props = value.getId().split(":");
			value.setOnAction(e -> {
				try {
					abreJanela(props[1], value.getText());
				} catch (IOException e1) {
					AlertBuilder.error(null, e1, true);
				}
			});
		}
		if (value.getText().equals("Logout")) {
			System.out.println("vai configurar a ação para o menu de logout");
			value.setOnAction(action -> acaoParaLogout(action));
		}
	}

	private void fechaJanelas() {
		tabPane.getTabs().clear();
	}

	private void abreJanela(String view, String title) throws IOException {
		AnchorPane load = (AnchorPane) SpringFXMLLoader.load(String.format("/fxml/%s", view));

		Tab tab = new Tab(title);
		tab.setContent(load);

		tabPane.getTabs().add(tab);

		tabPane.getSelectionModel().select(tab);

		configuraMinSizeDaTela(tab);

	}

	private void configuraMinSizeDaTela(Tab tab) {
		// TODO: realizar calculo a partir do Stage presente no PrincipalApp

		if (tab.getTabPane().getWidth() > rootPane.getMinWidth()) {
			rootPane.setMinWidth(tab.getTabPane().getWidth());
		}

		if (tab.getTabPane().getMinHeight() > rootPane.getMinWidth()) {
			rootPane.setMinHeight(tab.getTabPane().getHeight());
		}
	}

}
