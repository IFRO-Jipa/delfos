package br.com.delfos.control.pessoal.resposta;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaIntervalo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class RespostaIntervaloController implements RespostaControllerImpl<Intervalo, Integer>, Initializable {
	@FXML
	private Text txtNome;

	@FXML
	private Slider slideValor;

	@FXML
	private Text txtDescricao;

	private Optional<Pergunta<Intervalo>> option;

	@FXML
	private CheckBox cbNaoResponder;

	@Override
	public void setOption(Optional<Pergunta<Intervalo>> optionalAlternativa) {
		optionalAlternativa.ifPresent(pergunta -> {
			txtNome.setText(pergunta.getNome());
			txtDescricao.setText(pergunta.getDescricao());

			Intervalo intervalo = pergunta.getAlternativa();
			slideValor.setMin(intervalo.getValorInicial());
			slideValor.setMax(intervalo.getValorFinal());
			slideValor.setValue(intervalo.getValorInicial());
			slideValor.setBlockIncrement(intervalo.getIncremento());
		});

		this.option = optionalAlternativa;
	}

	@Override
	public Optional<Pergunta<Intervalo>> getOption() {
		return option;
	}

	@Override
	public Integer getSelected() {
		return Double.valueOf(slideValor.getValue()).intValue();
	}

	@Override
	public void clearSelected() {
		this.slideValor.setValue(this.slideValor.getMin());
	}

	@Override
	public boolean isSelected() {
		return slideValor.getValue() != -1.0;
	}

	@Override
	public Resposta<?> getResposta(Questionario questionario) {
		if (this.getOption().isPresent()) {
			RespostaIntervalo resposta = new RespostaIntervalo();
			resposta.setQuestionario(questionario);
			resposta.setPergunta(this.getOption().get());
			if (!this.cbNaoResponder.isSelected())
				resposta.setEscolha(getSelected());
			return resposta;
		} else {
			throw new RuntimeException("Não foi informado uma pergunta válida ou pertencente ao questionario.");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.cbNaoResponder.selectedProperty().addListener((obs, oldValue, newValue) -> {
			this.slideValor.setDisable(newValue);
		});
	}

}
