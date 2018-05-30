package com.service;

import java.util.List;

import com.model.FormaPagamento;

public interface IFormaService extends IService{

	public abstract void add(FormaPagamento m);

	public abstract boolean isExiste(FormaPagamento m);

	public abstract FormaPagamento recuperar(Integer id);

	public abstract void delete(FormaPagamento m);

	public abstract void update(FormaPagamento m);

	public abstract FormaPagamento getById(FormaPagamento m);

	public abstract List<FormaPagamento> getByNome(FormaPagamento m);

	public abstract List<FormaPagamento> getAll();

}