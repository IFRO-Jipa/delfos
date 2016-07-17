package br.com.delfos.control.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import br.com.delfos.model.auditoria.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchUsuario {

	private AbstractSearchable<Usuario> searchable;

	private static Map<String, BiPredicate<Usuario, String>> comparadores = new HashMap<>();

	static {

		comparadores.put("Login", (usuario, filtro) -> {
			return usuario.getLogin().toLowerCase().contains(filtro.toLowerCase());
		});

		comparadores.put("Perfil de acesso", (usuario, filtro) -> {
			return usuario.getPerfilAcesso().getNome().toLowerCase().contains(filtro);
		});

		comparadores.put("Responsável pela conta", (usuario, filtro) -> {
			return usuario.getPessoa().getNome().toLowerCase().contains(filtro)
					|| usuario.getPessoa().getApelido().contains(filtro);
		});

	}

	public SearchUsuario(List<Usuario> itens) {
		this(FXCollections.observableArrayList(itens));
	}

	public SearchUsuario(ObservableList<Usuario> itens) {
		this.searchable = new AbstractSearchable<>(itens, comparadores);
		this.searchable.setText(Usuario::getLogin);
		this.searchable.setDescription(this::formataNome);
		this.setOnCloseAfterDoubleClick(true);
	}

	private String formataNome(Usuario usuario) {
		return String.format("%d-%s\nPerfil de acesso: %s\nResponsável pela conta: %s", usuario.getId(),
				usuario.getLogin(), usuario.getPerfilAcesso().getNome(), usuario.getPessoa().getNome());
	}

	public SearchUsuario setConsumerPersonalizadoDoubleClick(Consumer<Usuario> consumerPersonalizadoDoubleClick) {
		searchable.setConsumerPersonalizadoDoubleClick(consumerPersonalizadoDoubleClick);
		return this;
	}

	public SearchUsuario setOnCloseAfterDoubleClick(boolean status) {
		searchable.setOnCloseAfterDoubleClick(status);
		return this;
	}

	public Optional<Usuario> showAndWait() {
		return searchable.showAndWait();
	}

}
