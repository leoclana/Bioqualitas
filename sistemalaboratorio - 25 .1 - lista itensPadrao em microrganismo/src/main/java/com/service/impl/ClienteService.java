package com.service.impl;

import java.io.Serializable;
import java.util.List;

import com.model.Usuario;
import org.springframework.transaction.annotation.Transactional;

import com.aop.log.Log;
import com.model.Cliente;
import com.service.IClienteService;
import com.service.IUsuarioService;
import com.util.enums.AcoesLog;

@Transactional(readOnly = true)
public class ClienteService extends Service implements Serializable, IClienteService {

	private static final long serialVersionUID = 1L;
	
	private IUsuarioService usuarioService;

	public ClienteService() {
	}

	@Transactional(readOnly = false)
	@Log
	public void adicionar(Cliente cliente) {
		if ("1".equals(cliente.getTipopessoa()))
			getUsuarioService().adicionarClientePf(cliente);
		getDao().<Cliente>adicionar(cliente); // verifica se existe o ID
		logar(AcoesLog.adicionou, cliente);	// cadastrado
	}

	@Override
	public boolean isExisteCliente(Cliente cliente) {
		Cliente f = new Cliente();
		f.setCpfcnpj(cliente.getCpfcnpj());
		f.setAtivo(true);
		f.setTipopessoa(null);
		f.setIdCliente(null);

		List<Cliente> resultado = null;

		try {
			resultado = getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (resultado != null && resultado.size() > 0);
	}

	@Override
	public Cliente recuperar(Integer id) {
		return getDao().recuperar(Cliente.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	@Log
	public void delete(Cliente cliente) throws Exception {
		Cliente clientePersistido = recuperar(cliente.getIdCliente());
		if(clientePersistido.getSolicitacoes().isEmpty()) {
			//** Desativar os Usuarios do Cliente ha ser excluido(desativado)
			for(Usuario usuariocliente : getUsuarioService().buscaPorCliente(clientePersistido)){
				usuariocliente.setAtivo(false);
				getUsuarioService().atualizar(usuariocliente);
			}

			clientePersistido.setAtivo(false);
			atualizar(clientePersistido);
		}else{
			throw new Exception("Cliente possui Solicitação aberta.");
		}
	}

	@Transactional(readOnly = false)
	@Log
	public void atualizar(Cliente cliente) {
	/*	if ("1".equals(cliente.getTipopessoa()))
			getUsuarioService().atualizarClientePf(cliente);*/
		getDao().<Cliente>atualizar(cliente);
		logar(AcoesLog.atualizou, cliente);
	}

	@Override
	public Cliente getById(Cliente cliente) {
		return getDao().recuperar(cliente.getClass(), cliente.getIdCliente());
	}

	@Override
	public Cliente getByCpfCnpj(Cliente cliente) {

		Cliente f = new Cliente();
		f.setTipopessoa(null);
		f.setIdCliente(null);
		f.setCpfcnpj(cliente.getCpfcnpj());

		List<Cliente> resultado = null;

		try {
			resultado = getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ((resultado != null) && (!resultado.isEmpty()))
			return resultado.get(0);
		else
			return null;
	}

	@Override
	public List<Cliente> getAll() {
		Cliente f = new Cliente();
		f.setTipopessoa(null);
		f.setIdCliente(null);
		f.setTipoinscricao(null);
		f.setAtivo(true);

		try {
			return getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
}
