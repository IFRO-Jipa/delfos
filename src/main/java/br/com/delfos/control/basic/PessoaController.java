package br.com.delfos.control.basic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;

import br.com.delfos.control.auditoria.UsuarioController;
import br.com.delfos.control.generic.AbstractController;
import br.com.delfos.control.search.SearchPessoa;
import br.com.delfos.dao.basic.CidadeDAO;
import br.com.delfos.dao.basic.PessoaDAO;
import br.com.delfos.dao.basic.TipoLogradouroDAO;
import br.com.delfos.except.view.FXValidatorException;
import br.com.delfos.model.basic.Cidade;
import br.com.delfos.model.basic.Endereco;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.basic.TipoLogradouro;
import br.com.delfos.model.basic.TipoPessoa;
import br.com.delfos.util.ContextFactory;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertAdapter;
import br.com.delfos.view.manipulador.ScreenUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

@Controller
public class PessoaController extends AbstractController<Pessoa, PessoaDAO> {

	@FXML
	private Button btnPesquisaCodigo;

	@FXML
	private ComboBox<TipoLogradouro> comboBoxTipoLogradouro;

	@FXML
	@NotNull
	private TextField txtNome;

	@FXML
	private TextField txtCodigo;

	@FXML
	@NotNull
	private TextField txtCpf;

	@FXML
	private TextArea txtDescricao;

	@FXML
	@NotNull
	private TextField txtNumero;

	@FXML
	private Button btnSalvar;

	@FXML
	private DatePicker dtDataNascimento;

	@FXML
	private CheckBox cbEspecialista;

	@FXML
	private Tab tbEndereco;

	@FXML
	private TextField txtApelido;

	@FXML
	private CheckBox cbPesquisador;

	@FXML
	@NotNull
	private ComboBox<Cidade> comboBoxCidade;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnPesquisaCpf;

	@FXML
	@NotNull
	private TextField txtLogradouro;

	@FXML
	private Button btnNovo;

	@FXML
	@NotNull
	private TextField txtRg;

	@FXML
	private Pane pnlClassificacao;

	@FXML
	private TextArea txtDescricaoEndereco;

	@FXML
	@NotNull
	private TextField txtBairro;

	@FXML
	private AnchorPane anchorPaneEndereco;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private CheckBox cbAcessoSistema;

	@FXML
	@NotNull
	private TextField txtEmail;

	@FXML
	private TextField txtCep;

	@FXML
	private Tab tbUsuario;

	private UsuarioController usuarioController;

	@FXML
	private void handleButtonPesquisaCodigo(ActionEvent event) {
		// pesquisaPorCodigo();
		SearchPessoa search = new SearchPessoa(this.getDao().findAll());
		this.posiciona(search.showAndWait());
	}

	@Override
	protected void posiciona(Optional<Pessoa> value) {
		value.ifPresent(pessoa -> {
			txtCodigo.setText(String.valueOf(pessoa.getId()));
			txtNome.setText(pessoa.getNome());
			txtApelido.setText(pessoa.getApelido());
			txtCpf.setText(pessoa.getCpf());
			txtRg.setText(pessoa.getRg());
			txtEmail.setText(pessoa.getEmail());
			dtDataNascimento.setValue(pessoa.getDataNascimento());
			txtDescricao.setText(pessoa.getDescricao());
			posicionaTipoDePessoa(pessoa);

			comboBoxTipoLogradouro.getSelectionModel().select(pessoa.getEndereco().getTipoLogradouro());
			comboBoxCidade.getSelectionModel().select(pessoa.getEndereco().getCidade());
			txtLogradouro.setText(pessoa.getEndereco().getLogradouro());
			txtNumero.setText(pessoa.getEndereco().getNumero());
			txtBairro.setText(pessoa.getEndereco().getBairro());
			txtDescricaoEndereco.setText(pessoa.getEndereco().getDescricao());
			txtCep.setText(pessoa.getEndereco().getCep());

			configPermissaoCriaUsuario(pessoa);
		});
	}

	private void configPermissaoCriaUsuario(Pessoa pessoa) {
		// this.cbAcessoSistema.setSelected(pessoa != null && pessoa.getUsuario() == null);
		this.cbAcessoSistema.setDisable(pessoa == null || pessoa.getUsuario() != null);
	}

	private void posicionaTipoDePessoa(Pessoa pessoa) {
		pessoa.getTipo().forEach(tipo -> {
			if (tipo.equals(TipoPessoa.ESPECIALISTA))
				cbEspecialista.setSelected(true);
			if (tipo.equals(TipoPessoa.PESQUISADOR))
				cbPesquisador.setSelected(true);
		});;
	}

	@FXML
	private void handleButtonPesquisaCpf(ActionEvent event) {
		SearchPessoa search = new SearchPessoa(this.getDao().findAll());
		this.posiciona(search.showAndWait());
	}

	@FXML
	private void handleButtonNovo(ActionEvent event) {
		ScreenUtils.limpaCampos(anchorPane);
		// configPermissaoCriaUsuario(null);
		this.cbAcessoSistema.setDisable(false);
	}

	@FXML
	private void handleButtonExcluir(ActionEvent event) {
		this.deleteIf(pessoa -> pessoa.getId() != null);

		ScreenUtils.limpaCampos(anchorPane);

		configPermissaoCriaUsuario(null);
	}

	@FXML
	private void handleButtonSalvar(ActionEvent event) {

		try {
			Optional<Pessoa> retorno = this.salvar(toValue(), this);

			retorno.ifPresent(pessoa -> {
				txtCodigo.setText(String.valueOf(pessoa.getId()));
				if (cbAcessoSistema.isSelected()) {
					this.usuarioController.setResponsavel(pessoa);
					this.usuarioController.salvar().ifPresent(usuario -> {
						configPermissaoCriaUsuario(null);
						AlertAdapter.information("Pessoa e usuário criados com sucesso.");
					});

				} else
					AlertAdapter.information("Salvo com sucesso.");
			});

		} catch (FXValidatorException e) {
			AlertAdapter.error(e);
		}

	}

	@Override
	protected Pessoa toValue() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(txtNome.getText());
		pessoa.setApelido(txtApelido.getText());
		pessoa.setDescricao(txtDescricao.getText());
		pessoa.setEmail(txtEmail.getText());
		pessoa.setCpf(txtCpf.getText());
		pessoa.setRg(txtRg.getText());
		pessoa.setDataNascimento(dtDataNascimento.getValue());
		pessoa.setEndereco(montaEndereco(pessoa));
		pessoa.setTipo(pegaTipoPessoa());
		pessoa.setId((txtCodigo.getText().isEmpty() ? null : Long.parseLong(txtCodigo.getText())));
		return pessoa;
	}

	private List<TipoPessoa> pegaTipoPessoa() {
		List<TipoPessoa> tipos = new ArrayList<>();
		if (cbPesquisador.isSelected())
			tipos.add(TipoPessoa.PESQUISADOR);
		if (cbEspecialista.isSelected())
			tipos.add(TipoPessoa.ESPECIALISTA);
		return tipos;
	}

	private Endereco montaEndereco(Pessoa pessoa) {
		Endereco endereco = new Endereco();
		endereco.setBairro(txtBairro.getText());
		endereco.setLogradouro(txtLogradouro.getText());
		endereco.setCidade(comboBoxCidade.getValue());
		endereco.setNumero(txtNumero.getText());

		TipoLogradouro tipoLogradouro = comboBoxTipoLogradouro.getValue();
		System.out.println(tipoLogradouro);

		endereco.setTipoLogradouro(comboBoxTipoLogradouro.getValue());
		endereco.setDescricao(txtDescricaoEndereco.getText());
		endereco.setCep(txtCep.getText());
		return endereco;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtCodigo.setEditable(false);
		preencheComboBox();
		this.cbAcessoSistema.setOnAction(event -> {
			this.tbUsuario.setDisable(!this.cbAcessoSistema.isSelected());
		});
		configTabUsuario();

		this.cbAcessoSistema.setTooltip(
				new Tooltip("Para essa pessoa acessar o sistema, basta informar o CPF para o usuário e senha."));
	}

	private void configTabUsuario() {
		try {
			FXMLLoader loader = LeitorDeFXML.getLoader("fxml/auditoria/UsuarioView.fxml");
			this.tbUsuario.setContent(loader.load());
			this.usuarioController = loader.getController();
			this.usuarioController.setGenerateCredentials(true);
			this.usuarioController.setVisibleButtons(false);
			this.usuarioController.setVisibleTxtDescricao(false);
		} catch (IOException e) {
			AlertAdapter.error(e);
		}
	}

	private void preencheComboBox() {
		comboBoxTipoLogradouro.getItems().addAll(ContextFactory.getBean(TipoLogradouroDAO.class).findAll());
		comboBoxCidade.getItems().addAll(ContextFactory.getBean(CidadeDAO.class).findAll());
	}

}
