package br.com.delfos.control.pessoal;

import java.util.Set;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

@Controller
public class TemplateMeusQuestionariosController {

	@FXML
	private Text txtNomePesquisa;

	@FXML
	private Text txtResponsaveis;

	@FXML
	private Text txtVencimento;

	@FXML
	private TextField txtFiltro;

	@FXML
	private ListView<Questionario> listViewQuestionarios;

	public void set(Pesquisa pesquisa) {
		this.txtNomePesquisa.setText(pesquisa.getNome());
		this.txtResponsaveis.setText(criaStringComVirgulaEPonto(pesquisa.getPesquisadores()));
		this.txtVencimento.setText("04/94/2222");

		this.listViewQuestionarios.getItems().setAll(pesquisa.getQuestionarios());

	}

	private String criaStringComVirgulaEPonto(Set<Pessoa> pesquisadores) {
		StringBuilder resultado = new StringBuilder();
		boolean primeiro = true;
		for (Pessoa pessoa : pesquisadores) {

			if (primeiro)
				resultado.append(",");
			resultado.append(pessoa.getNome());

			primeiro = false;
		}

		return resultado.append(".").toString();
	}

}
