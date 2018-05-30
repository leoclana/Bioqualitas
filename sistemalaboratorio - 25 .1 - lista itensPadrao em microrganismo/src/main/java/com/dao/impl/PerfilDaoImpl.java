package com.dao.impl;

import java.io.Serializable;

import com.dao.IPerfilDao;
import com.model.Perfil;

public class PerfilDaoImpl extends Dao implements
		IPerfilDao, Serializable {

	private static final long serialVersionUID = 1L;

	protected Class<Perfil> getEntityClass() {
		return Perfil.class;
	}

	public Perfil recuperarComLista(Integer idPerfil) {
		
		return (Perfil) getSession().createQuery(
				"select p " +
				"from Perfil p join fetch p.funcoes " +
				"where p.idPerfil = "+idPerfil.toString())
				.uniqueResult();
	}

}
