package br.com.delfos.control.relatorio;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.RespostaMultiplaEscolha;
import br.com.delfos.view.graph.BarChartUtil;
import br.com.delfos.view.graph.PieChartUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

@Controller
public class TemplateResumoKaController implements Initializable {

	@FXML
	private BarChart<String, Number> barChart;

	@FXML
	private PieChart pieChart;

	@Autowired
	private RespostaDAO daoResposta;

	private Optional<Pergunta<MultiplaEscolha>> pergunta;

	private Optional<Set<RespostaMultiplaEscolha>> respostasMultiplaEscolha;

	public Optional<Set<RespostaMultiplaEscolha>> set(Pergunta<MultiplaEscolha> value) {
		this.pergunta = Optional.ofNullable(value);

		this.pergunta.ifPresent(pergunta -> {
			respostasMultiplaEscolha = Optional.ofNullable(daoResposta.findByPerguntaMultiplaEscolha(pergunta));

			this.respostasMultiplaEscolha.ifPresent(respostas -> {

				pergunta.getAlternativa().getEscolhas().forEach(item -> {
					long quantidade = respostas.stream().filter(resposta -> resposta.getEscolha().equals(item))
							.mapToLong(RespostaMultiplaEscolha::getId).count();

					addDataPieChart(item, quantidade);
					addDataBarChart(item, quantidade);
				});
			});

		});

		configPieChart();

		return respostasMultiplaEscolha;
	}

	private void configPieChart() {
		this.pieChart.getData().forEach(data -> {
			PieChartUtil.setDisplayPieChart(data);
		});
	}

	private void addDataPieChart(String item, long quantidade) {
		if (quantidade != 0)
			pieChart.getData().add(new Data(item, quantidade));

	}

	private void addDataBarChart(String item, long quantidade) {
		Series<String, Number> series = new Series<>();
		series.setName(item);
		javafx.scene.chart.XYChart.Data<String, Number> data = new XYChart.Data<String, Number>("", quantidade);
		data.nodeProperty().addListener((obs, old, newv) -> BarChartUtil.setDisplayValueData(data));
		series.getData().add(data);
		barChart.getData().add(series);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configBarChart();
	}

	private void configBarChart() {
		// TODO implementar
	}

}
