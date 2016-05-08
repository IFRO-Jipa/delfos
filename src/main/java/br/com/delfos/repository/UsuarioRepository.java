package br.com.delfos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// List<Usuario> findByPerfilAcesso(PerfilAcesso perfilAcesso);

	Usuario findByLoginAndSenha(String login, String senha);

	Optional<Usuario> findByLogin(String login);
	
	
}
