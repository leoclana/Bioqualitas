package com.service;

import java.util.List;

import com.model.Grupo;
import com.model.ItemGrupo;

public interface IItemGrupoService extends IService{

	public abstract void add(ItemGrupo ItemGrupo);

	public abstract boolean isExiste(ItemGrupo ItemGrupo);

	public abstract ItemGrupo recuperar(Integer id);

	public abstract void delete(ItemGrupo ItemGrupo);

	public abstract void update(ItemGrupo ItemGrupo);

	public abstract ItemGrupo getById(ItemGrupo ItemGrupo);

	public abstract ItemGrupo getByNome(ItemGrupo ItemGrupo);
	
	public abstract ItemGrupo getByNomeIdGrupo(ItemGrupo ItemGrupo);

	public abstract List<ItemGrupo> getAll();
	
	public abstract List<ItemGrupo> getAllByGrupo(Grupo grupo);

}