package br.com.delfos.dao.auditoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.delfos.model.auditoria.PerfilAcesso;
import br.com.delfos.model.auditoria.Usuario;
import br.com.delfos.repository.auditoria.PerfilAcessoRepository;

@Repository
public class PerfilAcessoDAO {

	@Autowired
	private PerfilAcessoRepository repository;

	public PerfilAcesso findByUsuario(Usuario usuario) {
		return repository.findByUsuario(usuario);
	}

	public List<PerfilAcesso> findByNome(String nome) {
		return repository.findByNome(nome);
	}

	public long count() {
		return repository.count();
	}

	public void delete(Long arg0) {
		repository.delete(arg0);
	}

	public boolean exists(Long arg0) {
		return repository.exists(arg0);
	}

	public List<PerfilAcesso> findAll() {
		return repository.findAll();
	}

	public List<PerfilAcesso> findAll(Iterable<Long> arg0) {
		return repository.findAll(arg0);
	}

	public Page<PerfilAcesso> findAll(Pageable arg0) {
		return repository.findAll(arg0);
	}

	public PerfilAcesso findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public <S extends PerfilAcesso> S save(S arg0) {
		return repository.save(arg0);
	}

}
