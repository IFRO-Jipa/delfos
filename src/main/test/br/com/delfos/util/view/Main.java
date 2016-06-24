package br.com.delfos.util.view;

import java.util.Arrays;
import java.util.Optional;

public class Main {
	public static void main(String[] args) {
		Optional<String> value = Optional.empty();
		System.out.println(Arrays.asList(value.orElseThrow(() -> new IllegalArgumentException("Leonardo"))));

		Optional<String> vall = Optional.ofNullable("asdfasdf");
		System.out.println(Arrays.asList(vall.get()));
	}
}
