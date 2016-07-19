package br.com.delfos.control.relatorio;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.RespostaIntervalo;
import br.com.delfos.view.graph.BarChartUtil;
import br.com.delfos.view.graph.PieChartUtil;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

@Controller
public class TemplateResumoKcController {

	@FXML
	private BarChart<String, Number> barChart;

	@FXML
	private PieChart pieChart;

	@Autowired
	private RespostaDAO daoResposta;

	private Optional<Pergunta<Intervalo>> pergunta;

	private Optional<Set<RespostaIntervalo>> respostasIntervalo;

	public Optional<Set<RespostaIntervalo>> set(Pergunta<Intervalo> value) {
		this.pergunta = Optional.ofNullable(value);

		this.pergunta.ifPresent(pergunta -> {
			respostasIntervalo = Optional.ofNullable(daoResposta.findByPerguntaIntervalo(pergunta));

			respostasIntervalo.ifPresent(respostas -> {
				respostas.forEach(resposta -> {
					Intervalo alternativa = resposta.getPergunta().getAlternativa();

					int inicio = alternativa.getValorInicial();
					int vFinal = alternativa.getValorFinal();
					int incremento = alternativa.getIncremento();

					Map<Integer, Long> escolhas = new HashMap<>();

					for (int i = 0; i < alternativa.getIntervalos() && inicio <= vFinal; i++, inicio += incremento) {
						Integer escolha = Integer.valueOf(i);
						long qtd = respostas.stream().filter(x -> x.getEscolha().equals(escolha))
								.mapToLong(RespostaIntervalo::getId).count();

						// addDataPieChart(escolha, qtd);
						// addDataBarChart(escolha, qtd);
						escolhas.put(escolha, qtd);
					}

					// escolhas.keySet().stream().sorted(Comparator.com)
					escolhas.keySet().stream().sorted(Comparator.comparingInt(t -> t)).forEach(item -> {
						addDataPieChart(item, escolhas.get(item));
						addDataBarChart(item, escolhas.get(item));
					});;

				});
			});

		});
		configPieChart();

		return respostasIntervalo;
	}

	private void configPieChart() {
		this.pieChart.getData().forEach(data -> PieChartUtil.setDisplayPieChart(data));
	}

	private void addDataBarChart(Integer escolha, long qtd) {
		if (!this.barChart.getData().stream().filter(data -> data.getName().equals(String.valueOf(escolha))).findFirst()
				.isPresent()) {
			Series<String, Number> series = new Series<>();
			series.setName(String.valueOf(escolha));
			javafx.scene.chart.XYChart.Data<String, Number> data = new XYChart.Data<String, Number>("", qtd);
			data.nodeProperty().addListener((obs, old, newv) -> BarChartUtil.setDisplayValueData(data));
			series.getData().add(data);
			barChart.getData().add(series);
		}
	}

	private void addDataPieChart(Integer escolha, long qtd) {
		if (qtd != 0) {
			Optional<Data> exists = pieChart.getData().stream()
					.filter(data -> data.getName().equals(String.valueOf(escolha))).findFirst();
			if (!exists.isPresent())
				pieChart.getData().add(new Data(String.valueOf(escolha), qtd));
		}
	}
}
