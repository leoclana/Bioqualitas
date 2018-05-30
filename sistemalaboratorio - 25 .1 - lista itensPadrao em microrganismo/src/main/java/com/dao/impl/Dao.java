package com.dao.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dao.IDao;
import com.managed.bean.SessaoDoUsuario;
import com.model.ILoggable;
import com.model.Log;
import com.util.CriteriaUtil;
import com.util.JsfUtil;

public class Dao implements IDao {

	public Dao(){
		
	}

    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @SuppressWarnings("unchecked")
	public <T> T recuperar(Class<T> type, Serializable id) {
        return (T) getSession().load(type, id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T> T recuperarUnico(Class<T> type) {
		Criteria criteria = getSession().createCriteria(type);
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> recuperarPorAtributoPreenchido(T filtro) throws IllegalArgumentException, IllegalAccessException {
		Criteria criteria = getSession().createCriteria(filtro.getClass());
		
		CriteriaUtil.preencheCriteria(filtro, criteria);
		
		return (List<T>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T adicionar(T t) {
        return (T) getSession().save(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T atualizar(T t) {
        return (T) getSession().merge(t);
	}

	@Override
	public void deletar(Object t) {
        getSession().delete(t);
	}
}
