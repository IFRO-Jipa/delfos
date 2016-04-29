package br.com.delfos.model;

import java.lang.reflect.Field;

import javax.persistence.Id;

public interface Upgrader<T> {

	default void update(T oldValue, T newValue) {
		try {
			Field[] oldFields = oldValue.getClass().getDeclaredFields();
			Class<? extends Object> classOfNew = newValue.getClass();

			for (Field oldField : oldFields) {

				Field newField = classOfNew.getDeclaredField(oldField.getName());

				oldField.setAccessible(true);
				newField.setAccessible(true);

				Object oldVal = oldField.get(oldValue);
				Object newVal = newField.get(newValue);

				if (oldVal != null && newVal != null) {
					if (!oldVal.equals(newVal)
					        || (newField.isAnnotationPresent(Id.class) && newVal == null)) {
						newVal = oldVal;
					}
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
