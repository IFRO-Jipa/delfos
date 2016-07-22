package br.com.delfos.app;

import java.io.IOException;
import java.util.Optional;

import br.com.delfos.control.relatorio.RelatorioPesquisaController;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.util.LeitorDeFXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class RelatorioPesquisaApp {

	private Optional<Pesquisa> pesquisa;

	public void show() throws IOException {
		Pesquisa p = this.pesquisa
				.orElseThrow(() -> new IllegalStateException("Nenhuma pesquisa foi informada para análise."));
		FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/RelatorioPesquisaView.fxml");
		AnchorPane load = loader.load();
		RelatorioPesquisaController controller = loader.getController();
		controller.set(pesquisa);

		PrincipalApp.openWindow(load, "Análise de Pesquisa: " + getNomePesquisa(p) + "...", "analise-pesquisa.png");
	}

	private String getNomePesquisa(Pesquisa p) {
		return p.getNome().substring(0, p.getNome().length() <= 25 ? p.getNome().length() - 1 : 25);
	}

	public RelatorioPesquisaApp setValue(Pesquisa p) {
		this.pesquisa = Optional.ofNullable(p);
		return this;
	}

}
