package br.com.delfos.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

public class LeitorDeFXML {
	/**
	 * Responsável por gerenciar as dependências inseridas nos controllers para o JavaFX
	 * 
	 * @param url
	 *            - Local do arquivo FXML
	 * @return
	 * @throws IOException
	 */
	public synchronized static Object carrega(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		System.out.println(url);
		loader.setLocation(LeitorDeFXML.class.getResource(url));
		loader.setControllerFactory(new Callback<Class<?>, Object>() {

			@Override
			public Object call(Class<?> param) {
				return ContextFactory.getBean(param);
			}

		});

		return loader.load();
	}
}
