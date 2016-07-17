package br.com.delfos.control.relatorio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.RelatorioPesquisaApp;
import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.control.search.SearchPesquisa;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.view.AlertAdapter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@Controller
public class MinhasPesquisasController implements Initializable {

	@FXML
	private BorderPane rootPane;

	@Autowired
	private PesquisaDAO daoPesquisa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.configPanel();
		} catch (RuntimeException e) {
			AlertAdapter.error(e);
		}
	}

	private void configPanel() {
		if (Autenticador.getDonoDaConta().isPesquisador()) {
			SearchPesquisa searchPesquisa = new SearchPesquisa(
					daoPesquisa.findByPesquisador(Autenticador.getDonoDaConta()));

			searchPesquisa.setOnCloseAfterDoubleClick(false).setConsumerPersonalizadoDoubleClick(pesquisa -> {
				// Abrir a tela do relatório aqui
				try {
					System.out.println("Pesquisa selecionada: " + pesquisa.getNome());
					new RelatorioPesquisaApp().setValue(pesquisa).show();
				} catch (IOException e) {
					AlertAdapter.error(e);
				}
			}).getPanel().ifPresent(panel -> {
				AnchorPane.setLeftAnchor(panel, 0.0);
				AnchorPane.setRightAnchor(panel, 0.0);
				AnchorPane.setBottomAnchor(panel, 0.0);
				AnchorPane.setTopAnchor(panel, 0.0);
				this.rootPane.setCenter(new AnchorPane(panel));
			});
		} else {
			throw new IllegalStateException("Desculpe, parece que você não é um pesquisador para ver suas pesquisas.");
		}
	}
}
