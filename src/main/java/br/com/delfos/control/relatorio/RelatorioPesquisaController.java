package br.com.delfos.control.relatorio;

import java.net.URL;
import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.delfos.dao.pesquisa.RespostaDAO;
import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

public class RelatorioPesquisaController implements Initializable {

	@FXML
	private LineChart<?, ?> lineChartPesquisa;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private SplitPane splitPane;

	@FXML
	private Tab tabKa;

	@FXML
	private Tab tabKc;

	@Autowired
	private RespostaDAO daoResposta;

	private Map<Questionario, Map<Month, Number>> frequenciaMensal;

	private Optional<Pesquisa> pesquisa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void set(Optional<Pesquisa> pesquisa) {
		this.pesquisa = pesquisa;

		pesquisa.ifPresent(value -> {
			this.frequenciaMensal = daoResposta.getRespostasAgrupadosPelaData(value);
			this.lineChartPesquisa.setTitle(value.getNome() + "\nFrequência de respostas");
			populaLineChart();
		});
	}

	private void populaLineChart() {
		// TODO Implementar
	}

}
