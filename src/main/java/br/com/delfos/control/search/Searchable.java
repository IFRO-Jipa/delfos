package br.com.delfos.control.search;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javafx.collections.ObservableList;

public interface Searchable<T> {
	void setComparators(Map<String, Function<T, String>> comparators);

	Map<String, Function<T, String>> getComparators();

	Optional<T> getSelected();

	void setTarget(ObservableList<T> itens);

	void search();

	void setDescription(Function<T, String> description);

	void setText(Function<T, String> text);

	void addComparing(String key, Function<T, String> comparation);

}
