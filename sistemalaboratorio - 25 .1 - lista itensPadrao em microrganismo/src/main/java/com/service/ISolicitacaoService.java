package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.model.Cliente;
import com.model.Solicitacao;


public interface ISolicitacaoService extends IService{

	public abstract void salvar(Solicitacao solicitacao);
	public abstract void atualizar(Solicitacao solicitacao);
	public abstract void deletar(Solicitacao solicitacao);
	public abstract List<Solicitacao> getAll();
	public abstract List<Solicitacao> getAllByCliente(Cliente cliente);
	public abstract void enviarParaAnalise(Solicitacao solicitacao);
	public abstract void enviarParaAprovacao(Solicitacao solicitacao);
	//public abstract void liberarParaCliente(Solicitacao solicitacao);
	public abstract void liberarParaCliente(Solicitacao solicitacao, HttpServletRequest r);
	
}
