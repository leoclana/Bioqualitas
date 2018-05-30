package com.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Perfil;

public interface IPerfilService extends IService{

	public abstract void add(Perfil perfil);

	public abstract boolean isExiste(Perfil perfil);

	public abstract Perfil recuperar(Integer id);
	
	public abstract Perfil recuperarPorNome(String nome);

	public abstract void delete(Perfil perfil);

	public abstract void update(Perfil perfil);

	public abstract Perfil getById(Perfil perfil);

	public abstract List<Perfil> getAll();

	public abstract Perfil getByIdComLista(Perfil perfil);

}