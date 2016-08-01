package br.com.delfos.control.pessoal.resposta;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import br.com.delfos.model.pesquisa.pergunta.Texto;
import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaTexto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RespostaTextoController implements RespostaControllerImpl<Texto, String>, Initializable {

	@FXML
	private Text txtNome;

	@FXML
	private Text txtDescricao;

	@FXML
	private TextField txtMensagem;

	@FXML
	private CheckBox cbNaoResponder;

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
			if (!cbNaoResponder.isSelected())
				resposta.setEscolha(getSelected());
		} else {
			throw new RuntimeException("Não foi informado uma pergunta válida ou pertencente ao questionario.");
		}
		return resposta;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.cbNaoResponder.selectedProperty().addListener((obs, oldValue, newValue) -> {
			this.txtMensagem.setDisable(newValue);
		});
	}
	
	@Override
	public boolean isIgnored() {
		return cbNaoResponder.isSelected();
		
	}

}
