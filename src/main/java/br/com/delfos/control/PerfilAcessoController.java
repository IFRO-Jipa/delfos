package br.com.delfos.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.dao.FuncionalidadeDAO;
import br.com.delfos.dao.PerfilAcessoDAO;
import br.com.delfos.model.Funcionalidade;
import br.com.delfos.model.PerfilAcesso;
import br.com.delfos.util.AlertBuilder;
import br.com.delfos.util.ManipuladorDeComponentes;
import br.com.delfos.util.ManipuladorDeTelas;
import br.com.delfos.view.ListSelection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

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

		// TODO: Retirar esse código feio.... isso não vai ser aqui, e sim numa tela de
		// consulta.
		TextInputDialog dialog = new TextInputDialog("ex: 1");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("PRÉVIA - Consulta de Registros");
		dialog.setContentText("informe o código do perfil");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Optional<PerfilAcesso> optional = Optional
			        .ofNullable(perfilDao.findOne(Long.parseLong(result.get())));
			if (optional.isPresent()) {
				posicionaRegistro(optional.get());
			} else {
				ManipuladorDeTelas.limpaCampos(rootPane);
				AlertBuilder.warning("Nenhum registro foi encontrado.");
			}
		} else {
			ManipuladorDeTelas.limpaCampos(rootPane);
			AlertBuilder.warning("Nenhum registro foi encontrado.");
		}
	}

	private void posicionaRegistro(PerfilAcesso perfil) {
		txtCodigo.setText(perfil.getId() != null ? String.valueOf(perfil.getId()) : null);
		txtNome.setText(perfil.getNome());
		txtDescricao.setText(perfil.getDescricao());
		listViewPermissoes.getItems().clear();
		listViewPermissoes.getItems().addAll(perfil.getPermissoes());
	}

	@FXML
	void handleButtonNovo(ActionEvent event) {
		ManipuladorDeTelas.limpaCampos(rootPane);
		txtNome.requestFocus();
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
			perfil.setId((txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText())));
			perfil.addPermissoes(pegaPermissoes());

			Optional<PerfilAcesso> response = Optional.ofNullable(perfilDao.save(perfil));

			if (response.isPresent()) {
				AlertBuilder.information("Salvo com sucesso!");
				txtCodigo.setText(String.valueOf(response.get().getId()));
			} else {
				AlertBuilder.warning("Oops! Não saiu como esperado.\nPor favor, tente novamente.");
			}
		} else {
			AlertBuilder.warning("Campos não foram validados.");
		}
	}

	private List<Funcionalidade> pegaPermissoes() {
		return listViewPermissoes.getItems().isEmpty() ? null : listViewPermissoes.getItems();
	}

	@FXML
	private void handleLinkAdicionaFuncionalidade(ActionEvent event) {
		try {
			ListSelection<Funcionalidade> seletor = new ListSelection<>("Selecione as funcionalidades",
			        filtraFuncionalidadesInexistentes());

			seletor.setCellFactory(p -> configuraTextoNaCelula());

			Optional<List<Funcionalidade>> target = seletor.showAndWait();
			target.ifPresent(result -> {
				listViewPermissoes.getItems().addAll(result);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Callback<ListView<Funcionalidade>, ListCell<Funcionalidade>> cellFactory() {

		return p -> {
			ListCell<Funcionalidade> cell = configuraTextoNaCelula();

			ContextMenu menu = getContextMenuToListView(cell);

			cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
				// TODO: criar implementação correta.
				if (isNowEmpty) {
					cell.setContextMenu(null);
				} else {
					cell.setContextMenu(menu);
				}
			});

			return cell;
		};
	}

	private ContextMenu getContextMenuToListView(ListCell<Funcionalidade> cell) {
		MenuItem menuRemoveOnly = new MenuItem();
		menuRemoveOnly.setText("Remover");
		menuRemoveOnly.setOnAction(action -> {
			listViewPermissoes.getItems().remove(cell.getItem());
		});

		MenuItem menuRemoveAll = new MenuItem();
		menuRemoveAll.setText("Remover todos");
		menuRemoveAll.setOnAction(action -> {
			if (AlertBuilder.confirmation("Remover todas as funcionalidades selecionadas?")) {

			}
		});

		ContextMenu menu = new ContextMenu(menuRemoveOnly, menuRemoveAll);
		return menu;
	}

	private ListCell<Funcionalidade> configuraTextoNaCelula() {
		ListCell<Funcionalidade> cell = new ListCell<Funcionalidade>() {

			@Override
			protected void updateItem(final Funcionalidade t, final boolean bln) {
				super.updateItem(t, bln);
				if (t != null) {
					setText(t.getNome());
				}
			}

		};
		return cell;
	}

	private List<Funcionalidade> filtraFuncionalidadesInexistentes() {
		List<Funcionalidade> result = new ArrayList<>();

		if (listViewPermissoes.getItems().isEmpty()) {
			result.addAll(funcionalidades);
		} else {
			funcionalidades.forEach(funcionalidade -> {
				if (!listViewPermissoes.getItems().contains(funcionalidade)) {
					result.add(funcionalidade);
				}

			});
		}

		return result;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		funcionalidades = daoFuncionalidade.findAll();
		listViewPermissoes.setCellFactory(cellFactory());
	}

}
