package com.service;

import java.util.List;

import com.model.Microrganismo;

public interface IMicrorganismoService extends IService{

	public abstract void add(Microrganismo Microrganismo);

	public abstract boolean isExiste(Microrganismo Microrganismo);

	public abstract Microrganismo recuperar(Integer id);

	public abstract void delete(Microrganismo Microrganismo);

	public abstract void update(Microrganismo Microrganismo);

	public abstract Microrganismo getById(Microrganismo Microrganismo);

	public abstract List<Microrganismo> getByNome(Microrganismo Microrganismo);

	public abstract List<Microrganismo> getAll();

}