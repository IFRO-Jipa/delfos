package br.com.delfos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.delfos.model.PerfilAcesso;
import br.com.delfos.model.Usuario;

public interface PerfilAcessoRepository extends JpaRepository<PerfilAcesso, Long> {
	@Query("select u.perfilAcesso from Usuario u where u = ?1")
	PerfilAcesso findByUsuario(Usuario usuario);

	List<PerfilAcesso> findByNome(String nome);
}
