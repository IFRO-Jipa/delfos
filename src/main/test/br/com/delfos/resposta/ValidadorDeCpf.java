package br.com.delfos.resposta;

/**
 * Classe responsável por realizar a validação do CPF a partir de seus dígitos
 * verificadores.
 * 
 * O cálculo é feito a partir da verificação dos seus últimos dígitos
 * comparando-os com seus valores antecessores.
 * 
 * @author Alexandre Lotério
 *
 */
public class ValidadorDeCpf {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	// Expressão regular que verifica se os valores não são números repetidos,
	// como 22222222222,33333333333,44444444444, etc.
	private static final String REGEX_CPF = "^(0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|9{11}|9{11})$";

	/**
	 * método responsável por fazer o cálculo do digito verificador a partir dos
	 * seus outros valores.
	 * 
	 * @param str
	 *            para validação
	 * @return
	 */
	private static int calcularDigito(String str) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * pesoCPF[pesoCPF.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	/**
	 * Método responsável por validar o CPF informado.
	 * 
	 * @param cpf
	 *            com ou sem máscara. Ex: 000.000.000-00 ou 00000000000
	 * @return cpf é ou não é valido.
	 */
	public static boolean valida(String cpf) {
		cpf = clearMask(cpf);

		if (cpf == null || cpf.length() != 11 || cpf.matches(REGEX_CPF))
			return false;
		// Faz o cálculo do primeiro digito

		/**
		 * cada um dos nove primeiros números do CPF é multiplicado por um peso
		 * que começa de 10 e que vai sendo diminuido de 1 a cada passo,
		 * representado pela constante pesoCPF.
		 */

		Integer digito1 = calcularDigito(cpf.substring(0, 9));
		// faz o calculo do segundo dígito
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1);

		// verifica se os digitos vão bater com o que foi informado no cpf.

		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	public static void main(String[] args) {
		// INFORMAR O CPF SEM OS PONTOS E TRAÇOS, FAZER ISSO NA APLICAÇÃO COM O
		// JFORMATTERTEXTFIELD
		System.out.printf("CPF Valido:%s \n", valida("42806954401")); // true

		System.out.printf("CPF Válido:%s \n", valida("428.069.544-01"));
	}

	private static String clearMask(String cpf) {
		// retira a máscara do cpf (os pontos [.] e traços [-])
		return (cpf.replace("-", "").replace(".", ""));
	}

	public static String setMask(String CPF) {
		// máscara do CPF: 999.999.999-99
		return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-"
				+ CPF.substring(9, 11));
	}
}