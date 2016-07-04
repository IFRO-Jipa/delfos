package br.com.delfos.control.pessoal.resposta;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import br.com.delfos.model.pesquisa.pergunta.MultiplaEscolha;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

@Controller
public class RespostaMultiplaEscolhaController implements RespostaControllerImpl<MultiplaEscolha, String> {

	@FXML
	private VBox boxItems;

	@FXML
	private AnchorPane rootElement;

	@FXML
	private Text txtNome;

	@FXML
	private Text txtDescricao;

	private ToggleGroup groupItems;

	private Optional<Pergunta<MultiplaEscolha>> option;

	@Override
	public void setOption(Optional<Pergunta<MultiplaEscolha>> optionalAlternativa) {
		optionalAlternativa.ifPresent(pergunta -> {
			txtNome.setText(pergunta.getNome());
			txtDescricao.setText(pergunta.getDescricao());

			MultiplaEscolha alternativa = pergunta.getAlternativa();
			this.configGroupItems(alternativa);
		});

		this.option = optionalAlternativa;
	}

	private void configGroupItems(MultiplaEscolha alternativa) {
		initGroupItem();
		alternativa.getEscolhas().forEach(item -> {
			RadioButton radio = new RadioButton(item);

			groupItems.getToggles().add(radio);

			VBox.setMargin(radio, new Insets(0, 5, 5, 5));

			boxItems.getChildren().add(radio);
		});
	}

	private void initGroupItem() {
		if (this.groupItems == null) {
			groupItems = new ToggleGroup();
		} else {
			groupItems.getToggles().clear();
		}
	}

	@Override
	public Optional<Pergunta<MultiplaEscolha>> getOption() {
		return option;
	}

	@Override
	public String getSelected() {
		RadioButton radio = (RadioButton) this.groupItems.getSelectedToggle();
		return radio.getText();
	}

	@Override
	public void clearSelected() {
		this.groupItems.selectToggle(null);
	}

	@Override
	public boolean isSelected() {
		return this.groupItems.getSelectedToggle() != null;
	}

}
