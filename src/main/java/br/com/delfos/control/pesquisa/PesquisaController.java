package br.com.delfos.control.pesquisa;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.app.QuestionarioApp;
import br.com.delfos.control.generic.AbstractController;
import br.com.delfos.control.search.SearchPesquisa;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.pesquisa.PesquisaDAO;
import br.com.delfos.except.pesquisa.LimiteDeEspecialistasAtingidoException;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.util.TableCellFactory;
import br.com.delfos.view.AlertAdapter;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ScreenUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

@Controller
public class PesquisaController extends AbstractController<Pesquisa, PesquisaDAO> {

	@FXML
	private ListView<Questionario> listViewQuestionario;

	@FXML
	private Text textAtivo;

	@FXML
	@NotNull
	private ListView<Pessoa> listViewPesquisador;

	@FXML
	private Hyperlink linkAdicionaQuestionario;

	@FXML
	@NotNull
	private TextField txtNome;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	@NotNull
	private DatePicker datePesquisa;

	@FXML
	@NotNull
	private DatePicker dateVencimento;

	@FXML
	private Button btnSalvar;

	@FXML
	@NotNull
	private ListView<Pessoa> listViewEspecialista;

	@FXML
	@NotNull
	private TextField txtLimite;

	@FXML
	private Hyperlink linkAdicionaPesquisador;

	@FXML
	private Button pesquisaCodigoDaPesquisa;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnNovo;

	@FXML
	private Button btFinalizar;

	@FXML
	private Hyperlink linkAdicionaEspecialista;

	@FXML
	private AnchorPane rootPane;

	@Autowired
	private PessoaDAO daoPessoa;

	private List<Pessoa> especialistas;

	private List<Pessoa> pesquisadores;

	private QuestionarioApp questionarioApp;

	private Callback<DatePicker, DateCell> factoryDeVencimento = param -> new DateCell() {
		@Override
		public void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);

			if (item.isBefore(PesquisaController.this.datePesquisa.getValue().plusDays(1))) {
				this.setDisable(true);
				this.setStyle("-fx-background-color: #ffc0cb;");
			}

			long p = PesquisaController.this.getTotalDeDias(item);
			this.setTooltip(new Tooltip(String.format("Sua pesquisa durará %s dia(s).", p)));
		};
	};

	private long getTotalDeDias(LocalDate item) {
		return ChronoUnit.DAYS.between(PesquisaController.this.datePesquisa.getValue(), item);
	}

	@FXML
	private void handleLinkAdicionaEspecialista(ActionEvent event) {

		try {
			ListSelection<Pessoa> seletor = new ListSelection<>("Selecione os Especialistas",
					filtraPessoasParaSelecao(TipoPessoa.ESPECIALISTA));

			seletor.textFormat(pessoa -> pessoa.getNome());

			Optional<List<Pessoa>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewEspecialista.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<Pessoa> filtraPessoasParaSelecao(TipoPessoa tipo) {
		List<Pessoa> filtro = new ArrayList<>();

		// cria referência na memória para os objetos criados
		List<Pessoa> cache = tipo.equals(TipoPessoa.ESPECIALISTA) ? especialistas : pesquisadores;
		ListView<Pessoa> list = tipo.equals(TipoPessoa.ESPECIALISTA) ? listViewEspecialista : listViewPesquisador;

		boolean listaVazia = cache.isEmpty();

		if (listaVazia) {
			filtro.addAll(cache);
		} else {
			cache.forEach(pessoa -> {
				if (!list.getItems().contains(pessoa)) {
					filtro.add(pessoa);
				}
			});
		}

		return filtro;

	}

	// Link para adicionar pesquisadores
	@FXML
	private void handleLinkAdicionaPesquisador(ActionEvent event) {
		try {
			ListSelection<Pessoa> seletor = new ListSelection<>("Selecione os Pesquisadores",
					filtraPessoasParaSelecao(TipoPessoa.PESQUISADOR));

			seletor.textFormat(pessoa -> pessoa.getNome());

			Optional<List<Pessoa>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewPesquisador.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Link para adicionar questionários
	@FXML
	private void handleLinkAdicionaQuestionario(ActionEvent event) {
		// ABRIR A TELA DE QUESTIONÁRIO E ESPERAR POR UM REGISTRO NOVO
		try {
			questionarioApp = new QuestionarioApp();
			Optional<Questionario> result = questionarioApp.showAndWait();

			result.ifPresent(questionario -> {
				this.listViewQuestionario.getItems().add(questionario);
			});
		} catch (IOException e) {
			AlertAdapter.error(e);
		}
	}

	// Botão Salvar

	@FXML
	private void handleButtonSalvar(ActionEvent event) {

		try {
			Optional<Pesquisa> result = this.salvar(toValue(), this);

			result.ifPresent(pesquisa -> {
				txtCodigo.setText(String.valueOf(pesquisa.getId()));
				AlertAdapter.information("Salvo com sucesso.");
				setStatus(pesquisa);
			});

		} catch (FXValidatorException e) {
			AlertAdapter.error(e);
		}
	}

	@Override
	protected Pesquisa toValue() {
		try {
			Pesquisa p = new Pesquisa();

			p.setId(txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText()));

			p.setNome(txtNome.getText());
			p.setDescricao(txtDescricao.getText());
			p.setLimite(Integer.parseInt(txtLimite.getText()));

			p.setDataInicio(datePesquisa.getValue());
			p.setDataVencimento(dateVencimento.getValue());
			p.addEspecialistas(
					listViewEspecialista.getItems().isEmpty() ? null : new HashSet<>(listViewEspecialista.getItems()));
			p.addPesquisadores(
					listViewPesquisador.getItems().isEmpty() ? null : new HashSet<>(listViewPesquisador.getItems()));
			p.addQuestionarios(
					listViewQuestionario.getItems().isEmpty() ? null : new HashSet<>(listViewQuestionario.getItems()));

			return p;
		} catch (LimiteDeEspecialistasAtingidoException e) {
			AlertAdapter.error(e);
			return null;
		}
	}

	// Botão Novo
	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ScreenUtils.limpaCampos(rootPane);
		setStatus(null);
		verificaSituacao(null);
	}

	// Botão Excluir
	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		deleteIf(pesquisa -> pesquisa.getId() != null);
		setStatus(null);
		verificaSituacao(null);
		ScreenUtils.limpaCampos(rootPane);
	}

	// Botão Finalizar Pesquisa
	@FXML
	private void handleButtonFinalizar(ActionEvent event) {
		String mensagem = "Se você finalizar essa pesquisa, ela não poderá ser utilizada novamente e \nnão "
				+ "estará disponível para interação entre os especialistas, \nsendo necessária a criação de uma nova. "
				+ "\n\nDeseja realmente finalizar?";
		if (AlertAdapter.confirmation(mensagem)) {
			try {
				Pesquisa pesquisa = toValue();
				pesquisa.finaliza();
				this.salvar(pesquisa, this).ifPresent(optional -> {
					verificaSituacao(pesquisa);
					AlertAdapter.information("Finalizada com sucesso");
				});
			} catch (FXValidatorException e) {
				AlertAdapter.error(e);
			}
		}
	}

	// Muda Status
	private void setStatus(Pesquisa pesquisa) {
		if (pesquisa != null) {
			if (pesquisa.isValida()) {
				textAtivo.setText("Em andamento");
				btFinalizar.setDisable(false);
				btnSalvar.setDisable(false);
			} else {
				if (pesquisa.isVencida()) {
					long dias = ChronoUnit.DAYS.between(LocalDate.now(), pesquisa.getDataVencimento());
					textAtivo.setText(String.format("Vencido há %d dia(s)", dias));
				} else if (pesquisa.isFinalizada()) {
					long dias = ChronoUnit.DAYS.between(LocalDate.now(), pesquisa.getDataFinalizada());
					textAtivo.setText(String.format("Finalizado em %s [%d dia(s)]",
							pesquisa.getDataFinalizada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dias));
				}
			}
		} else {
			textAtivo.setText("Não informada.");
		}
	}

	// Inicializando
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		configFields();
		configListViews();
		configCache();
		setStatus(null);
	}

	private void configCache() {
		this.especialistas = new ArrayList<>(daoPessoa.findByTipo(TipoPessoa.ESPECIALISTA));
		this.pesquisadores = new ArrayList<>(daoPessoa.findByTipo(TipoPessoa.PESQUISADOR));
	}

	private void configFields() {
		this.datePesquisa.setEditable(false);
		this.datePesquisa.disarm();
		this.datePesquisa.setValue(LocalDate.now());
		this.dateVencimento.setDayCellFactory(factoryDeVencimento);
	}

	private void configListViews() {
		listViewEspecialista.setCellFactory(
				new TableCellFactory<Pessoa>(listViewEspecialista).getCellFactory(pessoa -> pessoa.getNome()));

		listViewPesquisador.setCellFactory(
				new TableCellFactory<Pessoa>(listViewPesquisador).getCellFactory(pessoa -> pessoa.getNome()));

		listViewQuestionario.setCellFactory(new TableCellFactory<Questionario>(listViewQuestionario)
				.getCellFactory(questionario -> String.valueOf(questionario.getNome())));

		listViewQuestionario.setOnMouseClicked(evt -> doubleClickListViewQuestionario(evt));
	}

	private void doubleClickListViewQuestionario(MouseEvent evt) {
		try {
			if (evt.getClickCount() == 2) {
				int position = listViewQuestionario.getSelectionModel().getSelectedIndex();
				if (position >= 0) {
					Optional<Questionario> selected = Optional
							.ofNullable(listViewQuestionario.getSelectionModel().getSelectedItem());

					QuestionarioApp frame = new QuestionarioApp();
					frame.init(selected);

					Optional<Questionario> result = frame.showAndWait();

					result.ifPresent(value -> {
						this.listViewQuestionario.getItems().set(position, value);
					});
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Pesquisa por códigos
	@FXML
	private void pesquisar() {
		SearchPesquisa search = new SearchPesquisa(this.getDao().findAll());
		this.posiciona(search.showAndWait());
	}

	@Override
	protected void posiciona(Optional<Pesquisa> optional) {
		optional.ifPresent(pesquisa -> {
			txtCodigo.setText(String.valueOf(pesquisa.getId()));
			txtNome.setText(pesquisa.getNome());
			txtDescricao.setText(pesquisa.getDescricao());
			txtLimite.setText(String.valueOf(pesquisa.getLimite()));
			datePesquisa.setValue(pesquisa.getDataInicio());
			dateVencimento.setValue(pesquisa.getDataVencimento());

			listViewPesquisador.getItems().clear();
			listViewEspecialista.getItems().clear();
			listViewQuestionario.getItems().clear();

			if (pesquisa.getPesquisadores() != null) {
				listViewPesquisador.getItems().setAll(pesquisa.getPesquisadores());
			}

			if (pesquisa.getEspecialistas() != null) {
				listViewEspecialista.getItems().setAll(pesquisa.getEspecialistas());
			}

			if (pesquisa.getQuestionarios() != null) {
				listViewQuestionario.getItems().setAll(pesquisa.getQuestionarios());
			}

			verificaSituacao(pesquisa);
			setStatus(pesquisa);
		});
	}

	private void verificaSituacao(Pesquisa pesquisa) {
		if (pesquisa != null) {
			if (pesquisa.isVencida() || pesquisa.isFinalizada()) {
				btnSalvar.setDisable(true);
			}

			if (pesquisa.isVencida() && !pesquisa.isFinalizada()) {

				btnSalvar.setDisable(true);
				try {
					this.salvar(toValue(), this)
							.ifPresent(optional -> AlertAdapter.warning(
									"Por conta da pesquisa estar vencida, ela será finalizada automaticamente. "
											+ "As alterações realizadas nela não serão refletidas."));
				} catch (FXValidatorException e) {
					AlertAdapter.error(e);
				}
			}

			if (pesquisa.isFinalizada()) {
				btnSalvar.setDisable(true);
				btFinalizar.setDisable(true);
			}

		} else {
			btnSalvar.setDisable(false);
		}
	}

}
