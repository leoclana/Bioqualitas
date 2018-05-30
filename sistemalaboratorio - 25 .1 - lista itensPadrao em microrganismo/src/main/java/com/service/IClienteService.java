package com.service;

import java.util.List;

import com.model.Cliente;

public interface IClienteService extends IService{

	public abstract void adicionar(Cliente cliente);

	public abstract boolean isExisteCliente(Cliente cliente);

	public abstract Cliente recuperar(Integer id);

	public abstract void delete(Cliente cliente) throws Exception;

	public abstract void atualizar(Cliente cliente);

	public abstract Cliente getById(Cliente cliente);

	public abstract Cliente getByCpfCnpj(Cliente cliente);

	public abstract List<Cliente> getAll();

}