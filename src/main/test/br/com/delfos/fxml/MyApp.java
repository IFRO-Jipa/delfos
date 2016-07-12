package br.com.delfos.fxml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.Searchable;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.model.basic.Pessoa;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class MyApp {

	@Autowired
	private PessoaDAO daoPessoa;

	@FXML
	private Button btnClick;

	@FXML
	private void handleClick(ActionEvent event) {
		// lista de pessoas vindas do banco de dados
		List<Pessoa> pessoas = daoPessoa.findAll();

		/***************************************************
		 *                                                 *
		 *     Parâmetros de comparação(nome, cpf e rg)    *
		 *                                                 *
		 ***************************************************/
		Map<String, Function<Pessoa, String>> comparators = new HashMap<>();

		comparators.put("Nome", Pessoa::getNome);
		comparators.put("CPF", Pessoa::getCpf);
		comparators.put("RG", Pessoa::getRg);
		
		/***************************************************
		 *                                                 *
		 *        Inicialização do componente (Searchable) *
		 *                                                 *
		 ***************************************************/

		Searchable<Pessoa> seletor = new Searchable<Pessoa>(FXCollections.observableArrayList(pessoas), comparators);
		Optional<Pessoa> selected = seletor.setText(Pessoa::getNome)
										   .setDescription(this::configNome)
										   .showAndWait();	
		// caso tenha selecionado alguma coisa
		selected.ifPresent(pessoa -> {
			System.out.println("Pessoa selecionada");
			System.out.println(this.configNome(pessoa));
		});
	}

	private String configNome(Pessoa p) {
		return String.format("Código: [%4$d], Nome: [%-50s], CPF: [%14s], RG: [%s]", p.getNome(), p.getCpf(), p.getRg(), p.getId());
	}
}
