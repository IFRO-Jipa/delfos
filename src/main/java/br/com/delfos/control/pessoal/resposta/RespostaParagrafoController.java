package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.pergunta.Paragrafo;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaParagrafo;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

@Controller
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
	public Resposta<?> getResposta() {
		RespostaParagrafo resposta = new RespostaParagrafo();
		resposta.setEscolha(getSelected());
		return resposta;
	}

}
