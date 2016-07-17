package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaParagrafo;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class RespostaParagrafoController implements RespostaControllerImpl<Paragrafo, String> {

	@FXML
	private Text txtNome;

	@FXML
	private Text txtDescricao;

	@FXML
	private TextArea txtMensagem;

	private Optional<Pergunta<Paragrafo>> option;

	@Override
	public void setOption(Optional<Pergunta<Paragrafo>> optionalAlternativa) {
		optionalAlternativa.ifPresent(pergunta -> {
			txtNome.setText(pergunta.getNome());
			txtDescricao.setText(pergunta.getDescricao());

		});
		this.option = optionalAlternativa;
	}

	@Override
	public Optional<Pergunta<Paragrafo>> getOption() {
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
		if (this.getOption().isPresent()) {
			RespostaParagrafo resposta = new RespostaParagrafo();
			resposta.setQuestionario(questionario);
			resposta.setPergunta(this.getOption().get());
			resposta.setEscolha(getSelected());
			return resposta;
		} else {
			throw new IllegalStateException("Não foi informado uma pergunta válida ou pertencente ao questionario.");
		}
	}

}
