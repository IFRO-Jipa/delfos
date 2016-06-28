package br.com.delfos.model.generic;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.com.delfos.view.table.TableColumnConfig;

@MappedSuperclass
public abstract class AbstractModel<T> implements Identificator, Upgrader<T> {

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

}
