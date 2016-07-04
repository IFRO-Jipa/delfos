package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

@Controller
public class RespostaTextoController implements RespostaControllerImpl<Texto, String> {

	@FXML
	private Text txtNome;

	@FXML
	private Text txtDescricao;

	@FXML
	private TextField txtMensagem;

	private Optional<Pergunta<Texto>> option;

	@Override
	public void setOption(Optional<Pergunta<Texto>> optionalAlternativa) {
		optionalAlternativa.ifPresent(pergunta -> {
			txtNome.setText(pergunta.getNome());
			txtDescricao.setText(pergunta.getDescricao());

		});

		this.option = optionalAlternativa;
	}

	@Override
	public Optional<Pergunta<Texto>> getOption() {
		return this.option;
	}

	@Override
	public String getSelected() {
		return txtMensagem.getText();
	}

	@Override
	public void clearSelected() {
		this.txtMensagem.setText("");
	}

	@Override
	public boolean isSelected() {
		return !this.txtMensagem.getText().isEmpty();
	}

}
