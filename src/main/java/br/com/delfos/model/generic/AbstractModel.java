package br.com.delfos.model.generic;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.com.delfos.view.table.TableColumnConfig;

@MappedSuperclass
public abstract class AbstractModel<T> implements Identificator, Upgradable<T> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@TableColumnConfig(alias = "ID", name = "id")
	protected Long id;

	@Override
	public Long getId() {
		return this.id;
	}

	public AbstractModel(Long id) {
		this.id = id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AbstractModel() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractModel)) {
			return false;
		}
		AbstractModel<?> other = (AbstractModel<?>) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
