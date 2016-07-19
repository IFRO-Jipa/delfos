package br.com.delfos.control.relatorio;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.graph.LineChartUtil;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

@Controller
public class RelatorioPesquisaController implements Initializable {

	@FXML
	private LineChart<String, Integer> lineChartPesquisa;

	@FXML
	private ScrollPane scrollTabTexto;

	@FXML
	private Accordion accordionTexto;

	@FXML
	private ScrollPane scrollPaneKa;

	@FXML
	private ScrollPane scrollPaneKc;

	@FXML
	private Accordion accordionKa;

	@FXML
	private Accordion accordionKc;

	@FXML
	private SplitPane splitPane;

	@FXML
	private Tab tabKa;

	@FXML
	private Tab tabKc;

	@FXML
	private Tab tabTexto;

	@Autowired
	private RespostaDAO daoResposta;

	private Optional<Map<Questionario, Map<Month, Number>>> frequenciaMensal;

	// Será usada para realizar a impressão
	@SuppressWarnings("unused")
	private Optional<Pesquisa> pesquisa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configWidthAccordionInheritedScrollPane(scrollPaneKa, accordionKa);
		configWidthAccordionInheritedScrollPane(scrollPaneKc, accordionKc);
		configWidthAccordionInheritedScrollPane(scrollTabTexto, accordionTexto);
	}

	private void configWidthAccordionInheritedScrollPane(ScrollPane pane, Accordion accordion) {
		pane.viewportBoundsProperty().addListener((ChangeListener<Bounds>) (observable, oldValue, newValue) -> {
			accordion.setPrefWidth(newValue.getWidth());
		});
	}

	public void set(Optional<Pesquisa> pesquisa) {
		this.pesquisa = pesquisa;

		pesquisa.ifPresent(value -> {
			populaLineChart(value);
			openContextKa(value);
			openContextKc(value);
			openContextTexto(value);
		});
	}

	private void openContextTexto(Pesquisa value) {
		this.accordionTexto.getPanes().clear();

		value.getQuestionarios().forEach(questionario -> {
			TabPane tabPerguntas = new TabPane();

			questionario.getPerguntas().ifPresent(perguntas -> {
				perguntas.stream()
						.filter(p -> p.getAlternativa() instanceof Texto || p.getAlternativa() instanceof Paragrafo)
						.collect(Collectors.toList()).forEach(pergunta -> {
							// perguntas de multipla escolha aqui
							Tab tab = new Tab(pergunta.getNome());
							tab.setClosable(false);
							try {
								FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/texto/ResumoTexto.fxml");
								AnchorPane pane = loader.load();
								ResumoTextoController controller = loader.getController();
								if (pergunta.getAlternativa() instanceof Texto) {
									controller.setTexto((Pergunta<Texto>) pergunta);
								} else {
									controller.setParagrafo((Pergunta<Paragrafo>) pergunta);
								}
								// controller.set((Pergunta<Intervalo>) pergunta);
								tab.setContent(pane);
							} catch (IOException e) {
								e.printStackTrace();
							}
							tabPerguntas.getTabs().add(tab);
						});;
			});

			if (tabPerguntas.getTabs().size() > 0) {
				TitledPane panel = new TitledPane();
				panel.setText("Nome do questionário: " + questionario.getNome());
				panel.setContent(tabPerguntas);
				panel.setAlignment(Pos.TOP_LEFT);
				this.accordionTexto.getPanes().add(panel);
			}

		});

		if (!this.accordionTexto.getPanes().isEmpty())
			this.accordionTexto.setExpandedPane(this.accordionTexto.getPanes().get(0));
	}

	@SuppressWarnings("unchecked")
	private void openContextKc(Pesquisa value) {
		this.accordionKc.getPanes().clear();

		value.getQuestionarios().forEach(questionario -> {
			TabPane tabPerguntas = new TabPane();

			questionario.getPerguntas().ifPresent(perguntas -> {
				perguntas.stream().filter(p -> p.getAlternativa() instanceof Intervalo).collect(Collectors.toList())
						.forEach(pergunta -> {
							// perguntas de multipla escolha aqui
							Tab tab = new Tab(pergunta.getNome());
							tab.setClosable(false);
							try {
								FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/kc/TemplateResumoKc.fxml");
								AnchorPane pane = loader.load();
								TemplateResumoKcController controller = loader.getController();
								controller.set((Pergunta<Intervalo>) pergunta);
								tab.setContent(pane);
							} catch (IOException e) {
								e.printStackTrace();
							}
							tabPerguntas.getTabs().add(tab);
						});;
			});

			if (tabPerguntas.getTabs().size() > 0) {
				TitledPane panel = new TitledPane();
				panel.setText("Nome do questionário: " + questionario.getNome());
				panel.setContent(tabPerguntas);
				panel.setAlignment(Pos.TOP_LEFT);
				this.accordionKc.getPanes().add(panel);
			}

		});

		if (!this.accordionKc.getPanes().isEmpty())
			this.accordionKc.setExpandedPane(this.accordionKc.getPanes().get(0));

	}

	@SuppressWarnings("unchecked")
	private void openContextKa(Pesquisa value) {
		this.accordionKa.getPanes().clear();

		value.getQuestionarios().forEach(questionario -> {
			TabPane tabPerguntas = new TabPane();

			questionario.getPerguntas().ifPresent(perguntas -> {
				perguntas.stream().filter(p -> p.getAlternativa() instanceof MultiplaEscolha)
						.collect(Collectors.toList()).forEach(pergunta -> {
							// perguntas de multipla escolha aqui
							Tab tab = new Tab(pergunta.getNome());
							tab.setClosable(false);
							try {
								FXMLLoader loader = LeitorDeFXML.getLoader("fxml/relatorio/ka/TemplateResumoKa.fxml");
								AnchorPane pane = loader.load();
								TemplateResumoKaController controller = loader.getController();
								controller.set((Pergunta<MultiplaEscolha>) pergunta);
								tab.setContent(pane);
							} catch (IOException e) {
								e.printStackTrace();
							}
							tabPerguntas.getTabs().add(tab);
						});;
			});

			TitledPane panel = new TitledPane();
			panel.setText("Nome do questionário: " + questionario.getNome());
			panel.setContent(tabPerguntas);
			panel.setAlignment(Pos.TOP_LEFT);
			this.accordionKa.getPanes().add(panel);

		});

		if (!this.accordionKa.getPanes().isEmpty())
			this.accordionKa.setExpandedPane(this.accordionKa.getPanes().get(0));
	}

	private void populaLineChart(Pesquisa value) {
		this.frequenciaMensal = Optional.ofNullable(daoResposta.getRespostasAgrupadosPelaData(value));
		this.lineChartPesquisa.setTitle(value.getNome() + " - Frequência de respostas");

		this.lineChartPesquisa.getData().addAll(getSeries());

		LineChartUtil.addTooltip(this.lineChartPesquisa,
				data -> String.format("Número de respostas: %d", data.getYValue().intValue()));
	}

	private List<Series<String, Integer>> getSeries() {
		List<Series<String, Integer>> result = new ArrayList<>();

		this.frequenciaMensal.ifPresent(value -> {
			value.forEach((questionario, frequencias) -> {
				Series<String, Integer> seriesQuest = new Series<>();
				seriesQuest.setName(questionario.getNome());

				for (Month month : Month.values()) {

					Integer qtdRespostas = frequencias.get(month).intValue();

					seriesQuest.getData()
							.add(new Data<>(configNomeMes(month), qtdRespostas != null ? qtdRespostas : 0));
				}

				result.add(seriesQuest);
			});
		});

		return result;
	}

	private String configNomeMes(Month month) {
		return month.getDisplayName(TextStyle.SHORT, Locale.getDefault());
	}

}
