package com.service;

import java.util.List;

import com.model.Notificacao;
import com.model.Usuario;

public interface INotificacaoService extends IService{
	public abstract List<Notificacao> getAll(Usuario usuario);
	public abstract void salvar(Notificacao notificacao);
	public abstract void atualizar(Notificacao notificacao);
}
