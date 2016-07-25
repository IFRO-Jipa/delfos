package br.com.delfos.control.auditoria;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.delfos.control.generic.AbstractController;
import br.com.delfos.dao.auditoria.FuncionalidadeDAO;
import br.com.delfos.dao.auditoria.PerfilAcessoDAO;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.auditoria.Funcionalidade;
import br.com.delfos.model.auditoria.PerfilAcesso;
import br.com.delfos.util.TableCellFactory;
import br.com.delfos.view.AlertAdapter;
import br.com.delfos.view.ListSelection;
import br.com.delfos.view.manipulador.ScreenUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Controller
public class PerfilAcessoController extends AbstractController<PerfilAcesso, PerfilAcessoDAO> {

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
	private FuncionalidadeDAO daoFuncionalidade;

	private List<Funcionalidade> funcionalidades;

	@FXML
	private void handleBtnPesquisar(ActionEvent event) {
		pesquisaPorCodigo();
	}

	@Override
	protected void posiciona(Optional<PerfilAcesso> value) {
		value.ifPresent(perfil -> {
			txtCodigo.setText(perfil.getId() != null ? String.valueOf(perfil.getId()) : null);
			txtNome.setText(perfil.getNome());
			txtDescricao.setText(perfil.getDescricao());
			listViewPermissoes.getItems().clear();
			listViewPermissoes.getItems().addAll(perfil.getPermissoes());
		});
	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ScreenUtils.limpaCampos(rootPane);
		txtNome.requestFocus();
	}

	@FXML
	void handleButtonExcluir(ActionEvent event) {
		if (this.deleteIf(perfil -> perfil.getId() != null))
			ScreenUtils.limpaCampos(rootPane);
	}

	@FXML
	public void handleButtonSalvar(ActionEvent event) {
		getValue().ifPresent(montado -> {
			try {
				Optional<PerfilAcesso> resultado = this.salvar(montado, this);
				resultado.ifPresent(valor -> {
					txtCodigo.setText(String.valueOf(valor.getId()));
				});
			} catch (FXValidatorException e) {
				AlertAdapter.requiredDataNotInformed(e);
			}
		});
	}

	@Override
	public PerfilAcesso toValue() {
		PerfilAcesso perfil = new PerfilAcesso(txtNome.getText());
		perfil.setDescricao((txtDescricao.getText() != null ? txtDescricao.getText() : null));
		perfil.setId((txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText())));
		perfil.addPermissoes(pegaPermissoes());

		return perfil;
	}

	private List<Funcionalidade> pegaPermissoes() {
		return listViewPermissoes.getItems().isEmpty() ? null : listViewPermissoes.getItems();
	}

	@FXML
	private void handleLinkAdicionaFuncionalidade(ActionEvent event) {
		ListSelection<Funcionalidade> seletor = new ListSelection<>("Selecione as funcionalidades",
				filtraFuncionalidadesInexistentes());

		seletor.textFormat(p -> p.getNome());

		Optional<List<Funcionalidade>> target = seletor.showAndWait();
		target.ifPresent(result -> {
			try {
				listViewPermissoes.getItems().addAll(result);
				this.salvar(toValue(), this).ifPresent(persisted -> {
					System.out.println("Atualizou as informações com sucesso.");
				});
			} catch (FXValidatorException e) {
				AlertAdapter.requiredDataNotInformed(e);
			}
		});

	}

	private List<Funcionalidade> filtraFuncionalidadesInexistentes() {

		if (listViewPermissoes.getItems().isEmpty()) {
			return funcionalidades;
		} else {
			// funcionalidades.forEach(funcionalidade -> {
			// if (!listViewPermissoes.getItems().contains(funcionalidade)) {
			// result.add(funcionalidade);
			// }
			//
			// });
			return funcionalidades.stream().filter(f -> !listViewPermissoes.getItems().contains(f))
					.collect(Collectors.toList());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		funcionalidades = daoFuncionalidade.findAll();
		listViewPermissoes.setCellFactory(
				new TableCellFactory<Funcionalidade>(listViewPermissoes).getCellFactory(p -> p.getNome()));
	}

}
