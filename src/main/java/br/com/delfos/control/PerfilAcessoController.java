package br.com.delfos.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.controlsfx.control.ListSelectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.FuncionalidadeDAO;
import br.com.delfos.dao.PerfilAcessoDAO;
import br.com.delfos.model.Funcionalidade;
import br.com.delfos.model.PerfilAcesso;
import br.com.delfos.util.AlertBuilder;
import br.com.delfos.util.ManipuladorDeComponentes;
import br.com.delfos.util.ManipuladorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Controller
public class PerfilAcessoController implements Initializable {

	@FXML
	private Button btnSalvar;

	@FXML
	private AnchorPane rootPane;

	@FXML
	@NotNull
	private TextField txtNome;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextArea txtDescricao;

	@FXML
	private Button btnExcluir;

	@FXML
	private Hyperlink linkAdicionaFuncionalidade;

	@FXML
	private Button btnPesquisar;

	@FXML
	@NotNull
	private ListView<Funcionalidade> listViewPermissoes;

	@FXML
	private Button btnNovo;

	@Autowired
	private PerfilAcessoDAO perfilDao;

	@Autowired
	private FuncionalidadeDAO daoFuncionalidade;

	private List<Funcionalidade> funcionalidades;

	@FXML
	void handleBtnPesquisar(ActionEvent event) {
		// TODO: Próxima implementação para resolver.......
	}

	@FXML
	void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
	}

	@FXML
	void handleButtonExcluir(ActionEvent event) {
		if (!txtCodigo.getText().isEmpty()) {
			perfilDao.delete(Long.parseLong(txtCodigo.getText()));
			AlertBuilder.information("Excluído com sucesso");
			handleButtonNovo(event);
		}
	}

	@FXML
	void handleButtonSalvar(ActionEvent event) {
		if (ManipuladorDeComponentes.validaCampos(rootPane)) {
			PerfilAcesso perfil = new PerfilAcesso(txtNome.getText());
			perfil.setDescricao((txtDescricao.getText() != null ? txtDescricao.getText() : null));
			perfil.setId((txtCodigo.getText() != null ? Long.parseLong(txtCodigo.getText()) : null));
			perfil.adicionaPermissoes(pegaPermissoes());

			Optional<PerfilAcesso> response = Optional.ofNullable(perfilDao.save(perfil));

			if (response.isPresent()) {
				AlertBuilder.information("Salvo com sucesso!");
				txtCodigo.setText(String.valueOf(response.get().getId()));
			} else {
				AlertBuilder.warning("Oops! Não saiu como esperado.\nPor favor, tente novamente.");
			}
		}
	}

	private List<Funcionalidade> pegaPermissoes() {
		List<Funcionalidade> permissoes = new ArrayList<>();

		listViewPermissoes.getItems().forEach(funcionalidade -> permissoes.add(funcionalidade));

		return permissoes;
	}

	@FXML
	void handleLinkAdicionaFuncionalidade(ActionEvent event) {
		// TODO: configurar corretamente

		ListSelectionView<Funcionalidade> selector = new ListSelectionView<>();
		selector.getSourceItems().addAll(funcionalidades);

		if (!listViewPermissoes.getItems().isEmpty()) {
			selector.getTargetItems().addAll(listViewPermissoes.getItems());
		}

		listViewPermissoes.getItems().addAll(selector.getTargetItems());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		funcionalidades = daoFuncionalidade.findAll();
	}

}
