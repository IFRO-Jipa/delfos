package br.com.delfos.control.relatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.resposta.RespostaParagrafo;
import br.com.delfos.model.pesquisa.resposta.RespostaTexto;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

@Controller
public class TemplateResumoTextoController {

	@FXML
	private TextArea txtResposta;

	@FXML
	private Text txtFormaEntrada;

	@FXML
	private Text txtDataResposta;

	public void set(RespostaTexto texto) {
		this.txtResposta.setText(texto.getEscolha());
		this.txtFormaEntrada.setText("Campo de texto simples");
		this.txtDataResposta.setText(getHoraResposta(texto.getHoraResposta()));
	}

	public void set(RespostaParagrafo paragrafo) {
		this.txtResposta.setText(paragrafo.getEscolha());
		this.txtFormaEntrada.setText("Texto longo ou parágrafo");
		this.txtDataResposta.setText(getHoraResposta(paragrafo.getHoraResposta()));
	}

	private String getHoraResposta(LocalDateTime localDateTime) {
		StringBuilder builder = new StringBuilder();

		builder.append("Respondido em ");
		builder.append(localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		builder.append(" às ");
		builder.append(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

		return builder.toString();
	}

}
