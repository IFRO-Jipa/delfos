package br.com.delfos.app;

import java.io.IOException;
import java.util.Optional;

import org.controlsfx.control.MaskerPane;

import br.com.delfos.control.relatorio.RelatorioPesquisaController;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.util.LeitorDeFXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class RelatorioPesquisaApp {

	private Optional<Pesquisa> pesquisa;

	private static MaskerPane paneLoad = new MaskerPane();

	public void show() throws IOException {
		Pesquisa p = this.pesquisa
				.orElseThrow(() -> new IllegalStateException("Nenhuma pesquisa foi informada para análise."));
		FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/RelatorioPesquisaView.fxml");
		AnchorPane load = loader.load();

		PrincipalApp.openWindow(load, "Análise de Pesquisa: " + getNomePesquisa(p) + "...", "analise-pesquisa.png");
		RelatorioPesquisaController controller = loader.getController();
		controller.set(pesquisa);

		/*
		 * Task<Void> task = new Task<Void>() {
		 * 
		 * @Override protected void running() {
		 * paneLoad.setText("Analisando..."); paneLoad.setVisible(true);
		 * super.running(); }
		 * 
		 * @Override protected Void call() throws Exception {
		 * 
		 * Platform.runLater(() -> { });
		 * 
		 * return null; }
		 * 
		 * protected void succeeded() { paneLoad.setVisible(false); }; }; new
		 * Thread(task).start();
		 */

	}

	private String getNomePesquisa(Pesquisa p) {
		return p.getNome().substring(0, p.getNome().length() <= 25 ? p.getNome().length() - 1 : 25);
	}

	public RelatorioPesquisaApp setValue(Pesquisa p) {
		this.pesquisa = Optional.ofNullable(p);
		return this;
	}

	public static void closeLoading() {
		paneLoad.setVisible(false);
	}

	public static void showLoading() {
		paneLoad.setVisible(true);
	}

}
