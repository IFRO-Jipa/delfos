package br.com.delfos.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class LeitorDeFXML {
	/**
	 * Responsável por gerenciar as dependências inseridas nos controllers para
	 * o JavaFX
	 * 
	 * @param url
	 *            - Local do arquivo FXML
	 * @return
	 * @throws IOException
	 */
	public synchronized static Object carrega(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LeitorDeFXML.class.getResource(url));
		loader.setControllerFactory(param -> ContextFactory.getBean(param));

		return loader.load();
	}
}
