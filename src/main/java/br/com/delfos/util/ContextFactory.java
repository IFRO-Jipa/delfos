package br.com.delfos.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextFactory {

	private static final ApplicationContext CONTEXT;

	static {
		CONTEXT = new ClassPathXmlApplicationContext("/META-INF/applicationContext.xml");
	}

	public static <T> T getBean(Class<T> clazz) {
		return CONTEXT.getBean(clazz);
	}

}
