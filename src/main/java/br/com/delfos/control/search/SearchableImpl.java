package br.com.delfos.control.search;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

import javafx.collections.ObservableList;

interface SearchableImpl<T> {
	void setComparators(Map<String, BiPredicate<T, String>> comparators);

	Map<String, BiPredicate<T, String>> getComparators();

	Optional<T> getSelected();

	void setTarget(ObservableList<T> itens);

	void search();

	void setDescription(Function<T, String> description);

	void setText(Function<T, String> text);

	void addComparing(String key, BiPredicate<T, String> comparation);

}
