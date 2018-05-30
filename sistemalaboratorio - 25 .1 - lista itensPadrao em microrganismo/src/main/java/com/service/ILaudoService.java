package com.service;

import java.util.List;

import com.model.Laudo;
import com.util.enums.TipoAnalise;

public interface ILaudoService extends IService{

	public abstract void add(Laudo t);

	public abstract boolean isExiste(Laudo t);

	public abstract Laudo recuperar(Integer id);

	public abstract void delete(Laudo t);

	public abstract void update(Laudo t);

	public abstract Laudo getById(Laudo t);

	public abstract Laudo getById(Integer id);

	public abstract List<Laudo> getByNome(Laudo t);

	public abstract List<Laudo> getAll();
	
	public abstract List<Laudo> getByResultadoTipoAnalise(String r,TipoAnalise t);

	public List<Laudo> recuperarPorAtributo(Laudo laudo);

}