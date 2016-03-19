package br.com.delfos.util;

import java.net.URL;

import javafx.scene.control.Menu;

public class ManipuladorDeMenus {

	// arquivo em xml com o pattern criado pelo projeto.
	private URL arquivo;

	public ManipuladorDeMenus(URL arquivo) {
		super();
		this.arquivo = arquivo;
	}

	public Menu getMenu() {
		return null;
	}

	public URL getArquivo() {
		return arquivo;
	}
}
