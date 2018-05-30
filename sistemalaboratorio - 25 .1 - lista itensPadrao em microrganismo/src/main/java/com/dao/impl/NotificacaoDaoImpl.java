package com.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import com.dao.INotificacaoDao;
import com.model.Notificacao;
import com.model.Usuario;

public class NotificacaoDaoImpl extends Dao implements INotificacaoDao, Serializable {

	private static final long serialVersionUID = 1L;

	protected Class<Notificacao> getEntityClass() {
		return Notificacao.class;
	}
	
	@Override
    public List<Notificacao> listar( List<Usuario> userList) {
        String hql = "select distinct  n from Notificacao n join n.usuarios p where p in (:usuarios)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("usuarios", userList);
       List<Notificacao> notificacaoList  = query.list();
       return  notificacaoList;
    }

}
