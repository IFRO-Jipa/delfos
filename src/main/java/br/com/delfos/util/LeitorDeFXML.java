package br.com.delfos.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class LeitorDeFXML {
	/**
	 * Respons�vel por gerenciar as depend�ncias inseridas nos controllers para
	 * o JavaFX
	 * 
	 * @param url
	 *            - Local do arquivo FXML
	 * @return
	 * @throws IOException
	 */
	public synchronized static Object load(String url) throws IOException {
		return LeitorDeFXML.getLoader(url).load();
	}
	
	public synchronized static FXMLLoader getLoader(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LeitorDeFXML.class.getResource(url));
		loader.setControllerFactory(param -> ContextFactory.getBean(param));

		return loader;
	}
	
	public synchronized static Object getController(String url) throws IOException { 
		FXMLLoader loader = getLoader(url);
		loader.load();
		return loader.getController();
		
	}
}
