package com.service.impl;

import java.util.Calendar;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IDao;
import com.managed.bean.SessaoDoUsuario;
import com.model.ILoggable;
import com.model.Log;
import com.service.IService;
import com.util.JsfUtil;
import com.util.enums.AcoesLog;

@Transactional(readOnly = true)
public class Service implements IService {

    private IDao dao;
    
    public Service () {
    }

    public IDao getDao() {
		return dao;
	}
	
    public void setDao(IDao dao) {
		this.dao = dao;
	}
	
	public <T> void logar(AcoesLog a, T t) {
				SessaoDoUsuario sdo = (SessaoDoUsuario) JsfUtil
				.getSessionValue("SessaoDoUsuario");
		if (sdo != null && sdo.getUsuario() != null) {
			Log l = new Log();
			l.setData(Calendar.getInstance().getTime());
			// l.setUsuario(sdo.getUsuario());
			StringBuilder sb = new StringBuilder();

			sb.append("O usuario [");
			sb.append(sdo.getUsuario().getNome());
			sb.append("] ");
			sb.append(a.name());
			sb.append(" o(a) ");
			sb.append(t.getClass().getSimpleName());

			if (t instanceof ILoggable) {
				ILoggable logavel = (ILoggable)t;
				
				sb.append(" [");
				sb.append(logavel.getIdentifier());
				sb.append("]");
			}
			
			l.setDescricao(sb.toString());

			getDao().adicionar(l);
		}
	}
}
