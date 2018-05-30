package com.service;

import java.util.List;

import com.model.CondicaoAmostra;

public interface ICondicaoAmostraService extends IService{

	public abstract void add(CondicaoAmostra c);

	public abstract boolean isExiste(CondicaoAmostra c);

	public abstract CondicaoAmostra recuperar(Integer id);

	public abstract void delete(CondicaoAmostra c);

	public abstract void update(CondicaoAmostra c);

	public abstract CondicaoAmostra getById(CondicaoAmostra c);

	public abstract List<CondicaoAmostra> getByNome(CondicaoAmostra c);

	public abstract List<CondicaoAmostra> getAll();

}