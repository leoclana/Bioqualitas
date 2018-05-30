package com.util;

import java.lang.reflect.Field;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;

public final class CriteriaUtil {

	/**
	 * Preenche a criteria apartir dos fields da classe
	 * 
	 * @param cl
	 *            class
	 * @param cr
	 *            criteria
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> void preencheCriteria(T filtro, Criteria cr)
			throws IllegalArgumentException, IllegalAccessException {
		if (filtro == null)
			return;

		for (Field f : filtro.getClass().getDeclaredFields()) {
			f.setAccessible(true);

			String n = f.getName();
			Object v = f.get(filtro);

			if ("serialVersionUID".equals(n))
				continue;

			if (v == null)
				continue;

			if ((v instanceof Integer) && ((int) v == 0))
				continue;

			//BREAKPOINT  NA LINHA ABAIXO - para ver como a consulta vai sendo montada
			if (v instanceof String) {
				cr.add(Property.forName(n).like(v+"%"));
			} else {
				cr.add(Property.forName(n).eq(v));
			}
		}
	}

	/**
	 * Adiciona datas no criteria verificando as datas passadas no parametro
	 * para a contrucao.
	 * 
	 * if (dataDe != null) { if (dataAte != null) { //Se ambas datas forem
	 * preenchidas, cria um 'BETWEEN' } else { // Se apenas "Data de" for
	 * preenchida, cria um '>=' } } else if (dataAte != null) { // Se apenas
	 * "Data até" for preenchida, cria um '<=' }
	 * 
	 * @param dataDe
	 *            data 'de' no periodo de data
	 * @param dataAte
	 *            data 'ate' no periodo de data
	 * @param coluna
	 *            coluna a ser comparada
	 * @param criteria
	 *            criteria que a expressao sera adicionada
	 * @return true - caso alguma data tenha entrado no criteria / false - se
	 *         ambas as datas forem igual a null
	 */
	public static boolean adicionaDatasDeAte(Date dataDe, Date dataAte,
			String coluna, Criteria criteria) {
		if (dataDe != null) {
			if (dataAte != null) {
				// Se ambas datas forem preenchidas, cria um 'BETWEEN'
				criteria.add(Property.forName(coluna).between(dataDe, dataAte));
				return true;
			} else {
				// Se apenas "Data de" for preenchida, cria um '>='
				criteria.add(Property.forName(coluna).ge(dataDe));
				return true;
			}
		} else if (dataAte != null) {
			// Se apenas "Data até" for preenchida, cria um '<='
			criteria.add(Property.forName(coluna).le(dataAte));
			return true;
		}
		return false;
	}
}
