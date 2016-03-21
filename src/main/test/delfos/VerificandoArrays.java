package delfos;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class VerificandoArrays {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException,
	        NoSuchMethodException, SecurityException, InvocationTargetException {
		Produto produto = new Produto();
		produto.setNome("Produto do Leonardo");
		produto.setId("10");
		produto.setDescricaoTeste("Verificando produto por reflection");
		produto.setLista(Arrays.asList("Value 1", "Value 2", "value3"));

		Class<?> clazz = produto.getClass();

		for (Field f : clazz.getDeclaredFields()) {
			String nomeDoMetodoGet = "get" + f.getName().toUpperCase().charAt(0) + f.getName().substring(1);
			Method method = clazz.getDeclaredMethod(nomeDoMetodoGet);
			Object value = method.invoke(produto);
			if (value instanceof Iterable<?>) {
				for (Object val : ((Iterable<?>) value)) {
					System.out.println(String.format("%s: %s", val.getClass().getSimpleName(), val));
				}
			} else
			System.out.println(f.getName() + ":" + value);

		}
	}
}
