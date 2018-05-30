package com.dao;

import java.util.List;

import com.model.Notificacao;
import com.model.Usuario;

public interface INotificacaoDao extends IDao {
	public List<Notificacao> listar(List<Usuario> userList);
}
