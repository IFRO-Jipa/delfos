package br.com.delfos.dao.pesquisa;

import java.util.Optional;

import br.com.delfos.dao.generic.AbstractDAO;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.repository.pesquisa.AlternativaRepository;

public class AlternativaDAO extends AbstractDAO<Alternativa, Long, AlternativaRepository> {

	@Override
	@SuppressWarnings("unchecked")
	public <S extends Alternativa> Optional<S> save(S newValue) {
		if (newValue.getId() == null) {
			return null;
		} else {
			Alternativa other = this.findOne(newValue.getId());
			if (!other.equals(newValue)) {
				return (Optional<S>) Optional.ofNullable(newValue);

			} else
				return (Optional<S>) Optional.ofNullable(other);
		}
	}
}
