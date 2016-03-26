package br.com.delfos.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ManipuladorDeMenus {
	private File file;

	public ManipuladorDeMenus(File file) {
		this.file = file;
	}

	public ManipuladorDeMenus() throws URISyntaxException {
		this.file = new File(ManipuladorDeMenus.class.getResource("/menu.xml").toURI());
	}

	public MenuBar create() throws JDOMException, IOException {
		Element rootElement = new SAXBuilder().build(file).getRootElement();

		MenuBar bar = new MenuBar();

		rootElement.getChildren().forEach(element -> {
			bar.getMenus().add(createMenu(element));
		});

		return bar;
	}

	private Menu createMenu(Element rootParentElement) {
		Menu menu = new Menu();
		menu.setText(rootParentElement.getChildText("name"));
		menu.setId(rootParentElement.getAttributeValue("id"));

		rootParentElement.getChildren().forEach(parentElement -> {
			if (parentElement.getName().equals("itens")) {
				parentElement.getChildren().forEach(element -> {
					if (element.getName().equals("menu")) {
						menu.getItems().add(createMenu(element));
					} else if (element.getName().equals("menuItem")) {
						menu.getItems().add(createMenuItem(element));
					}
				});
			}
		});

		menu.setVisible(false);
		return menu;
	}

	private MenuItem createMenuItem(Element element) {
		MenuItem item = new MenuItem(element.getChildText("name"));
		item.setId(String.format("%s:%s", element.getAttributeValue("id"), element.getChildText("view")));
		item.setVisible(false);
		return item;
	}
}
