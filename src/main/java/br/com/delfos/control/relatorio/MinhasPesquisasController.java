package br.com.delfos.control.relatorio;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.control.search.SearchPesquisa;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
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
		this.configPanel();
	}

	private void configPanel() {
		SearchPesquisa searchPesquisa = new SearchPesquisa(
				daoPesquisa.findByPesquisador(Autenticador.getDonoDaConta()));

		searchPesquisa.setOnCloseAfterDoubleClick(false).setConsumerPersonalizadoDoubleClick(pesquisa -> {
			// Abrir a tela do relatório aqui
			System.out.println("Pesquisa selecionada: " + pesquisa.getNome());
		}).getPanel().ifPresent(panel -> {
			AnchorPane.setLeftAnchor(panel, 0.0);
			AnchorPane.setRightAnchor(panel, 0.0);
			AnchorPane.setBottomAnchor(panel, 0.0);
			AnchorPane.setTopAnchor(panel, 0.0);
			this.rootPane.setCenter(new AnchorPane(panel));
		});
	}
}
