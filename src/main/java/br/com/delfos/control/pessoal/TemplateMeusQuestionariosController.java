package br.com.delfos.control.pessoal;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.delfos.app.RespostaApp;
import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaQuestionario;
import br.com.delfos.util.ContextFactory;
import br.com.delfos.util.TableCellFactory;
import br.com.delfos.view.AlertAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class TemplateMeusQuestionariosController implements Initializable {

	@FXML
	private Text txtNomePesquisa;

	@FXML
	private Text txtResponsaveis;

	@FXML
	private Text txtVencimento;

	@FXML
	private TextField txtFiltro;

	@FXML
	private RadioButton rbOrdenacaoNome;

	@FXML
	private RadioButton rbOrdenacaoSituacao;

	@FXML
	private Text txtSituacaoQuestionario;

	private ToggleGroup groupRadio;

	private ObservableList<Questionario> questionarios = FXCollections.observableArrayList();

	private Set<RespostaQuestionario> respostaQuestionarios;

	@FXML
	private ListView<Questionario> listViewQuestionarios;

	@Autowired
	private RespostaDAO daoResposta;

	private SortedList<Questionario> ordenador;

	private Pesquisa pesquisa;

	public synchronized void set(final Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
		this.txtNomePesquisa.setText(pesquisa.getNome());
		this.txtResponsaveis.setText(criaStringComVirgulaEPonto(pesquisa.getPesquisadores()));
		this.txtVencimento.setText(pesquisa.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		updateCache(pesquisa);
	}

	private void updateCache(final Pesquisa pesquisa) {
		this.questionarios = getObservableList(pesquisa.getQuestionarios());

		this.listViewQuestionarios.setItems(getSortedItems());

		this.respostaQuestionarios = getRespostasParaPesquisa(pesquisa);
	}

	private Set<RespostaQuestionario> getRespostasParaPesquisa(Pesquisa pesquisa) {
		if (this.respostaQuestionarios == null) {
			this.respostaQuestionarios = new HashSet<>();
		}

		// gambiarra :(
		if (this.daoResposta == null) {
			this.daoResposta = ContextFactory.getBean(RespostaDAO.class);
		}

		this.respostaQuestionarios.clear();
		return daoResposta.getRespostaDosQuestionarios(pesquisa, Autenticador.getDonoDaConta());
	}

	private ObservableList<Questionario> getSortedItems() {
		FilteredList<Questionario> filtroDeRegistros = new FilteredList<>(questionarios);
		txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
			filtroDeRegistros.setPredicate(questionario -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCase = newValue.toLowerCase();

				if (configNomeItem(questionario).toLowerCase().contains(lowerCase)) {
					return true;
				}

				return false;
			});
		});

		this.ordenador = new SortedList<>(filtroDeRegistros);
		ordenador.setComparator(Comparator.comparing(Questionario::getNome));

		return ordenador;
	}

	private ObservableList<Questionario> getObservableList(Set<Questionario> set) {
		return FXCollections.observableArrayList(set);
	}

	private String criaStringComVirgulaEPonto(Set<Pessoa> pesquisadores) {
		List<String> nomes = new ArrayList<>();
		pesquisadores.forEach(pessoa -> nomes.add(pessoa.getNome()));
		return nomes.toString().replace("[", "").replace("]", "");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configListView();

		configToggleGroup();
	}

	private void configListView() {
		setCellFactoryToListView();
		setMouseClickToListView();
		setOnChangeToListView();
	}

	private void showPopup() {
		AlertAdapter.information("Você já respondeu esse questionário e não é possível editá-lo!");
	}

	private boolean questionarioRespondido(Questionario q) {
		return this.respostaQuestionarios.stream().filter(resposta -> resposta.getQuestionario().equals(q)).findFirst()
				.isPresent();
	}

	private void setCellFactoryToListView() {
		this.listViewQuestionarios.setCellFactory(
				new TableCellFactory<Questionario>(listViewQuestionarios).getCellFactory(q -> configNomeItem(q)));
	}

	private String configNomeItem(Questionario q) {

		return String.format("[%s] %s", this.questionarioRespondido(q) ? "RESPONDIDO" : "DISPONÍVEL", q.getNome());
	}

	private void setOnChangeToListView() {
		listViewQuestionarios.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			Optional<Resposta<?>> possivelResposta = this.respostaQuestionarios.stream()
					.filter(resposta -> resposta.getQuestionario().equals(newValue))
					.collect(Collectors.minBy(Comparator.comparing(Resposta::getHoraResposta)));

			String situacao = possivelResposta.isPresent() ? getDataResposta(possivelResposta.get().getHoraResposta())
					: "Disponível para responder";
			txtSituacaoQuestionario.setText(String.format("Situação: %s", situacao));

		});
	}

	private String getDataResposta(LocalDateTime localDateTime) {
		StringBuilder builder = new StringBuilder();

		builder.append("Respondido em ");
		builder.append(localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		builder.append(" às ");
		builder.append(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

		return builder.toString();
	}

	private void setMouseClickToListView() {
		this.listViewQuestionarios.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				abreTelaResposta(listViewQuestionarios.getSelectionModel().getSelectedItem());
			}
		});
	}

	private void configToggleGroup() {
		if (this.groupRadio == null) {
			groupRadio = new ToggleGroup();
			groupRadio.getToggles().addAll(rbOrdenacaoNome, rbOrdenacaoSituacao);
		}

		this.groupRadio.selectToggle(rbOrdenacaoNome);

		configOrdenadores();
	}

	private void configOrdenadores() {
		rbOrdenacaoNome.setOnAction(e -> ordenador.setComparator(Comparator.comparing(Questionario::getNome)));
		rbOrdenacaoSituacao
				.setOnAction(e -> ordenador.setComparator(Comparator.comparing(this::questionarioRespondido)));
	}

	private void abreTelaResposta(Questionario selectedItem) {
		if (!questionarioRespondido(selectedItem)) {
			try {
				RespostaApp app = new RespostaApp();
				app.setQuestionario(Optional.ofNullable(selectedItem));
				try {
					app.showAndWait().stream().filter(resposta -> resposta instanceof RespostaQuestionario)
							.map(resposta -> (RespostaQuestionario) resposta).collect(Collectors.toList());

					this.updateCache(pesquisa);
				} catch (NullPointerException e) {
					// nada a fazer, só será lançado caso não tenha retornado
					// nenhuma resposta.
				}

			} catch (IOException e) {
				AlertAdapter.error(e);
			}
		} else {
			showPopup();
		}
	}

}
