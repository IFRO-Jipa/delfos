package br.com.delfos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Teste {
	public static void main(String[] args) {
		List<String> lista = new ArrayList<>();
		lista.add("a");
		lista.add("a");
		lista.add("a");
		lista.add("a");
		lista.add("a");
		lista.add("a");
		lista.add("a");
		lista.add("a");
		lista.add("a");

		System.out.println(lista);
		System.out.println(lista.size());

		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("az");
		set.add("a");
		set.add("a");
		set.add("a");
		set.add("a");
		set.add("a");
		set.add("a");
		set.add("a");

		System.out.println("SET : " + set);
		System.out.println(set.size());
	}
}
