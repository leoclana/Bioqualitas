package com.dao;

import com.model.Perfil;

public interface IPerfilDao extends IDao {
	public Perfil recuperarComLista(Integer idPerfil);
}
