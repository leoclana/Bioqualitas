package com.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Util {
	
	public static String[] getNomes(Object[] o) {

		String[] s = new String[o.length];

		for (int i = 0; i < o.length; i++) {
			s[i] = o[i].toString();
		}
		return s;
	}

	private static final String CARACTERES_INVALIDOS = "[\\(\\)\\.\\-\\' ']";

	public static boolean isNaoNuloOuVazio(Object... objects) {

		boolean isNaoNuloOuVazio = true;
		for (Object object : objects) {
			if (Util.isNuloOuVazio(object)) {
				isNaoNuloOuVazio = false;
				break;
			}
		}
		return isNaoNuloOuVazio;
	}

	public static boolean isNuloOuVazio(Object object) {

		boolean resultado = object == null;

		if (!resultado) {
			if (object instanceof String) {
				String temp = (String) object;
				resultado = isEmpty(temp);
			}
			if (object instanceof Collection) {
				Collection<?> temp = (Collection<?>) object;
				resultado = temp.isEmpty();
			}
		}
		return resultado;
	}

	private static boolean isEmpty(String str) {

		return str == null || str.length() == 0;
	}

	public static boolean isCpfValido(String cpfNum) {

		boolean isValido = false;
		int resultP = 0;
		int resultS = 0;

		cpfNum = convertCaracteresInvalid(cpfNum);

		if (!cpfNum.equals("00000000000")
				|| !cpfNum.equals("11111111111")
				|| !cpfNum.equals("22222222222")
				|| !cpfNum.equals("33333333333")
				|| !cpfNum.equals("44444444444")
				|| !cpfNum.equals("55555555555")
				|| !cpfNum.equals("66666666666")
				|| !cpfNum.equals("77777777777")
				|| !cpfNum.equals("88888888888")
				|| !cpfNum.equals("99999999999")) {

			int[] cpf = new int[cpfNum.length()];
			for (int i = 0; i < cpf.length; i++) {
				cpf[i] = Integer.parseInt(cpfNum.substring(i, i + 1));
			}

			for (int i = 0; i < 9; i++) {
				resultP += cpf[i] * (i + 1);
			}
			int divP = resultP % 11;

			if (divP == cpf[9]) {
				for (int i = 0; i < 10; i++) {
					resultS += cpf[i] * (i);
				}
				int divS = resultS % 11;
				if (divS == cpf[10]) {
					isValido = true;
				}
			}
		}
		return isValido;
	}

	public static boolean isEmailValido(String email) {

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static String convertCaracteresInvalid(String str) {
		if(str != null && !str.equals("")) {
			str = str.replaceAll(CARACTERES_INVALIDOS, "");
		}
		return str;
	}


}
