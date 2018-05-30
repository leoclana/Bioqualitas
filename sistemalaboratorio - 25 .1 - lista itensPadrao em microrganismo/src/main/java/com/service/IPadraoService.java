package com.service;

import java.util.List;

import com.model.Padrao;

public interface IPadraoService extends IService{

	public abstract void add(Padrao Padrao);

	public abstract boolean isExiste(Padrao Padrao);

	public abstract Padrao recuperar(Integer id);

	public abstract void delete(Padrao Padrao);

	public abstract void update(Padrao Padrao);

	public abstract Padrao getById(Padrao Padrao);

	public abstract Padrao getByNome(Padrao Padrao);

	public abstract List<Padrao> getAll();

}