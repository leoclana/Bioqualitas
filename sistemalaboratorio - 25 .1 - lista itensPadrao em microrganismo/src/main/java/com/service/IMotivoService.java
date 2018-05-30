package com.service;

import java.util.List;

import com.model.MotivoAnalise;

public interface IMotivoService extends IService{

	public abstract void add(MotivoAnalise m);

	public abstract boolean isExiste(MotivoAnalise m);

	public abstract MotivoAnalise recuperar(Integer id);

	public abstract void delete(MotivoAnalise m);

	public abstract void update(MotivoAnalise m);

	public abstract MotivoAnalise getById(MotivoAnalise m);

	public abstract List<MotivoAnalise> getByNome(MotivoAnalise m);

	public abstract List<MotivoAnalise> getAll();

}