package br.com.delfos.repository.auditoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.auditoria.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLoginAndSenha(String login, String senha);

	Optional<Usuario> findByLogin(String login);

}
