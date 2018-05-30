package com.service;

import java.util.List;

import com.model.Grupo;

public interface IGrupoService extends IService{

	public abstract void add(Grupo Grupo);

	public abstract boolean isExiste(Grupo Grupo);

	public abstract Grupo recuperar(Integer id);

	public abstract void delete(Grupo Grupo);

	public abstract void update(Grupo Grupo);

	public abstract Grupo getById(Grupo Grupo);
	public abstract Grupo getByIdGrupo(Grupo Grupo);

	public abstract Grupo getByNome(Grupo Grupo);

	public abstract List<Grupo> getAll();

}