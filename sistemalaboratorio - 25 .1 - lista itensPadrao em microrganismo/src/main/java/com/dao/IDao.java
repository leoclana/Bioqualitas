package com.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao {
	<T> T recuperar(Class<T> type, Serializable id);

	<T> T recuperarUnico(Class<T> type);
	
	public <T> List<T> recuperarPorAtributoPreenchido(T filtro)
			throws IllegalArgumentException, IllegalAccessException;

	<T> T adicionar(T t);

	<T> T atualizar(T t);

	void deletar(Object t);
}
