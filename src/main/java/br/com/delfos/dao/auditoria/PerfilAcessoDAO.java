package br.com.delfos.dao.auditoria;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.auditoria.PerfilAcesso;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.repository.auditoria.PerfilAcessoRepository;

@Repository
public class PerfilAcessoDAO extends AbstractDAO<PerfilAcesso, Long, PerfilAcessoRepository> {

	public PerfilAcesso findByUsuario(Usuario usuario) {
		return repository.findByUsuario(usuario);
	}

	public List<PerfilAcesso> findByNome(String nome) {
		return repository.findByNome(nome);
	}

}
