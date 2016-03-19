package br.com.delfos.control;

import br.com.delfos.util.ContextFactory;

public class AbstractController<T> {

	protected T dao;

	@SuppressWarnings("unchecked")
	public AbstractController() {
		this.dao = (T) ContextFactory.getBean(dao.getClass());
	}

	
	
}
