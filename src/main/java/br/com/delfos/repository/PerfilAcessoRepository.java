package br.com.delfos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delfos.model.PerfilAcesso;
import br.com.delfos.model.Usuario;

public interface PerfilAcessoRepository extends JpaRepository<PerfilAcesso, Long> {
	PerfilAcesso findByUsuario(Usuario usuario);

	List<PerfilAcesso> findByNome(String nome);
}
