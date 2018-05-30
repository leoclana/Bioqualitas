package com.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.INotificacaoDao;
import com.model.Notificacao;
import com.model.Usuario;
import com.service.INotificacaoService;

@Transactional(readOnly = true)
public class NotificacaoService extends Service implements Serializable, INotificacaoService {

	private static final long serialVersionUID = 1L;

	private INotificacaoDao notificacaoDAO;
	
	public NotificacaoService() {
	}
	
	@Override
	public List<Notificacao> getAll(Usuario usuario) { 
		
		List<Usuario> users = new ArrayList<Usuario>();
		users.add(usuario);
	/*	Notificacao notificacao = new Notificacao();
		notificacao.setUsuarios(users);
		notificacao.setUsuariosArquivados(users);
		return getNotificacaoDAO().recuperarPorAtributosPreenchidos(notificacao);*/
	
		return getNotificacaoDAO().listar(users);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void salvar(Notificacao notificacao) {
		getNotificacaoDAO().adicionar(notificacao);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void atualizar(Notificacao notificacao) {
		getNotificacaoDAO().atualizar(notificacao);		
	}

	private INotificacaoDao getNotificacaoDAO() {
		return notificacaoDAO;
	}

	public void setNotificacaoDAO(INotificacaoDao notificacaoDAO) {
		this.notificacaoDAO = notificacaoDAO;
	}

}
