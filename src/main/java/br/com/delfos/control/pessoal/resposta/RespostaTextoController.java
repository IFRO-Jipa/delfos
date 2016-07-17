package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaTexto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

	@Override
	public Resposta<?> getResposta(Questionario questionario) {
		RespostaTexto resposta = new RespostaTexto();
		if (this.getOption().isPresent()) {
			resposta.setQuestionario(questionario);
			resposta.setPergunta(this.getOption().get());
			resposta.setEscolha(getSelected());
		} else {
			throw new RuntimeException("Não foi informado uma pergunta válida ou pertencente ao questionario.");
		}
		return resposta;
	}

}
