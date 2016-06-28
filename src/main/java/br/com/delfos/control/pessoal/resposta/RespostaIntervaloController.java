package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.pergunta.Intervalo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

@Controller
public class RespostaIntervaloController implements RespostaControllerImpl<Intervalo, Integer> {
	@FXML
	private Text txtNome;

	@FXML
	private Slider slideValor;

	@FXML
	private Text txtDescricao;

	private Optional<Pergunta<Intervalo>> option;

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

}
