package com.dao;

import java.util.List;

import com.model.Perfil;
import com.model.Usuario;

public interface IUsuarioDao extends IDao {
	public Usuario recuperarComLista(Integer idUsuario);
	//public List<Usuario> recuperarPorPerfil(Perfil perfil);
	public List<Usuario> recuperarPorPerfil(List<Perfil> perfil);
}
