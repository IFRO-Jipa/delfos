package br.com.delfos.control.basic;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Controller;

import br.com.delfos.app.PrincipalApp;
import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertAdapter;
import br.com.delfos.view.manipulador.ManipuladorDeMenus;
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

	private Map<String, MenuItem> menus;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			menus = new HashMap<>();
			fechaJanelas();
			configuraECriaOsMenus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configuraECriaOsMenus() throws Exception {
		Set<Funcionalidade> permissoes = Autenticador.getPermissoesDeAcesso();

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
		if (AlertAdapter.confirmation("Deseja realmente deslogar?")) {
			PrincipalApp.getStage().hide();
			AlertAdapter.information("teste");
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
			menus.put(props[1], value);
			value.setOnAction(e -> {
				try {

					abreJanela(props[1], value.getText());
				} catch (IOException e1) {
					AlertAdapter.error(e1);
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
		AnchorPane load = (AnchorPane) LeitorDeFXML.load(String.format("fxml/%s", view));

		configTabPane(title, load);
	}

	private void abreJanela(AnchorPane pane, String title) {
		configTabPane(title, pane);
	}

	private void configTabPane(String title, AnchorPane load) {
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

	/**
	 * Open pane in TabPane, located in frame Principal
	 * 
	 * @param view
	 *            - The FXML Document referenced by View (exclude .fxml from
	 *            String)
	 * @throws IOException
	 */
	public void openWindow(String view) throws IOException {
		configPane(view);
	}

	private void configPane(String view) throws IOException {
		view = view.concat(".fxml");
		MenuItem item = this.menus.get(view);
		String[] properties = item.getId().split(":");
		this.abreJanela(properties[1], item.getText());
	}

	public void openWindow(AnchorPane pane, String title) {
		this.abreJanela(pane, title);
	}

}
