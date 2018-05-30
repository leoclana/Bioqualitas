package com.service;

import java.util.List;

import com.model.Cliente;
import com.model.Usuario;

public interface IUsuarioService extends IService {

	public abstract void adicionar(Usuario usuario);
	public abstract boolean isExisteUsuario(Usuario usuario);
	public abstract Usuario buscaPorLogin(String login,String senha);
	public abstract Usuario buscaPorClienteContatoPrincipal(Cliente cliente);
	public abstract List<Usuario> buscaPorCliente(Cliente cliente);
	public abstract Usuario buscaPorEmail(String email);
	public abstract boolean clientePossuiContatoPrincipal(Cliente cliente);
	public abstract Usuario recuperar(Integer id);
	public abstract void resetSenha(Usuario usuario);
	public abstract void delete(Usuario usuario);
	public abstract void atualizar(Usuario usuario);
	public abstract Usuario getById(Usuario usuario);
	public abstract List<Usuario> getAll();
	public abstract Usuario getByIdComLista(Usuario usuario);
	public abstract List<Usuario> getColetadores();
	public abstract List<Usuario> getTecnicos();
	public abstract List<Usuario> getAdministradores();
	public abstract void adicionarClientePf(Cliente cliente);
	public abstract void atualizarClientePf(Cliente cliente);
}