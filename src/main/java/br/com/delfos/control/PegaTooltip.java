package br.com.delfos.control;

import java.lang.reflect.Field;

import javax.validation.constraints.NotNull;

public class PegaTooltip {
	public static void main(String[] args) {
		pegaFields(AutenticadorDeUsuario.class);
		pegaFields(FuncionalidadeController.class);
		pegaFields(LoginController.class);
		pegaFields(PerfilAcessoController.class);
		pegaFields(PerguntaController.class);
		pegaFields(PesquisaController.class);
		pegaFields(PessoaController.class);
		pegaFields(PrincipalController.class);
		pegaFields(QuestionarioController.class);
		pegaFields(UsuarioController.class);
	}

	private static void pegaFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(NotNull.class)) {
				System.out.printf("%s.%s = \n", clazz.getName(), field.getName());
			}
		}
		System.out.println("\n");
	}
}
