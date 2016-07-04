package br.com.delfos.model.generic;

import java.lang.reflect.Field;
import java.util.Collection;

public interface Upgrader<T> {

	/**
	 * Método responsável por atualizar um objeto antigo para novas informações
	 * vindas de um novo objeto.
	 * 
	 * A criação veio da necessidade de atualizar os dados de um objeto
	 * gerenciado pelo JPA, colocando nele novas informações (disponibilizadas
	 * pelo usuário)
	 * 
	 * @param oldValue
	 *            - Objeto que precisa sofrer atualização. Geralmente é managed
	 *            pelo JPA
	 * @param newValue
	 *            - Objeto que possui as informações novas. Geralmente é detach
	 *            pelo JPA
	 */
	default void update(T oldValue, T newValue) {
		try {
			Field[] oldFields = oldValue.getClass().getDeclaredFields();
			Class<? extends Object> newClass = newValue.getClass();

			for (Field oldField : oldFields) {

				Field newField = newClass.getDeclaredField(oldField.getName());

				// disponibiliza o acesso aos membros privados
				// tomar cuidado com o SecurityManager do java.
				oldField.setAccessible(true);
				newField.setAccessible(true);

				// pega os valores que estão atribuídos no objeto.
				Object oldVal = oldField.get(oldValue);
				Object newVal = newField.get(newValue);

				// verifica o estado dos objetos para atualizar aquele que está
				// managed pelo JPA
				// o managed é o oldVal e o que veio para atualizar o managed é
				// o newVal

				if (newVal != null) {
					// se o novo valor não for nulo, então verifica se o antigo
					// é nulo ou diferente do novo. Caso seja, atualiza o antigo
					// para o novo valor.
					// if (newVal instanceof Collection &&
					// !newVal.equals(oldVal)) {
					// oldVal = newVal;
					// continue;
					// }
					boolean iguais = oldVal == null ? false : oldVal.equals(newValue);
					if (!iguais) {
						oldVal = newVal;
					}
				} else {
					// caso o novo valor seja nulo, simplesmente define o antigo
					// como nulo também.
					oldVal = null;
				}
			}

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}
