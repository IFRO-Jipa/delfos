package br.com.delfos.fxml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.search.AbstractSearchable;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.view.AlertAdapter;
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
		 * *
		 * Parâmetros de comparação(nome, cpf e rg) *
		 * *
		 ***************************************************/
		Map<String, BiPredicate<Pessoa, String>> comparators = new HashMap<>();

		comparators.put("Nome", (p, filtro) -> {
			return p.getNome().toLowerCase().contains(filtro.toLowerCase());
		});
		comparators.put("CPF", (p, filtro) -> {
			return p.getCpf().toLowerCase().contains(filtro.toLowerCase());
		});
		comparators.put("RG", (p, filtro) -> {
			return p.getRg().toLowerCase().contains(filtro.toLowerCase());
		});

		comparators.put("Nascido antes de", (p, filtro) -> {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
						filtro.length() == 8 ? "dd/MM/yy" : (filtro.length() == 10 ? "dd/MM/yyyy" : "invalid format"));

				LocalDate informedDate = LocalDate.parse(filtro, formatter);
				return p.getDataNascimento().isBefore(informedDate);
			} catch (DateTimeParseException e) {
				AlertAdapter.error(String.format(
						"A data %s não está no padrão. Informe uma data que se adeque ao padrão 00/00/00 ou 00/00/0000",
						filtro));
				return false;
			}
		});

		comparators.put("Nascido depois de", (p, filtro) -> {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
						filtro.length() == 8 ? "dd/MM/yy" : (filtro.length() == 10 ? "dd/MM/yyyy" : "invalid format"));

				LocalDate informedDate = LocalDate.parse(filtro, formatter);
				return p.getDataNascimento().isAfter(informedDate);
			} catch (DateTimeParseException e) {
				AlertAdapter.error(String.format(
						"A data %s não está no padrão. Informe uma data que se adeque ao padrão 00/00/00 ou 00/00/0000",
						filtro));
				return false;
			}
		});

		/***************************************************
		 * *
		 * Inicialização do componente (Searchable) *
		 * *
		 ***************************************************/

		AbstractSearchable<Pessoa> seletor = new AbstractSearchable<Pessoa>(FXCollections.observableArrayList(pessoas), comparators);
		Optional<Pessoa> selected = seletor.setText(Pessoa::getNome).setDescription(this::configNome).showAndWait();
		// caso tenha selecionado alguma coisa
		selected.ifPresent(pessoa -> {
			System.out.println("Pessoa selecionada");
			System.out.println(this.configNome(pessoa));
		});
	}

	private String configNome(Pessoa p) {
		return String.format("Código: [%4$d], Nome: [%-50s], CPF: [%14s], RG: [%s]", p.getNome(), p.getCpf(), p.getRg(),
				p.getId());
	}
}
