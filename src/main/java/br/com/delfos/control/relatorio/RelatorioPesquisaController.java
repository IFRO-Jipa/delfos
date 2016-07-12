package br.com.delfos.control.relatorio;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

public class RelatorioPesquisaController implements Initializable {

	@FXML
	private BarChart<?, ?> barChartPesquisa;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private SplitPane splitPane;

	@FXML
	private Tab tabKa;

	@FXML
	private Tab tabKc;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
