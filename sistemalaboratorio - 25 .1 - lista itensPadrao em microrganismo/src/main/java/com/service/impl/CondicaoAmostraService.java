package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.CondicaoAmostra;
import com.service.ICondicaoAmostraService;

@Transactional(readOnly = true)
public class CondicaoAmostraService extends Service implements Serializable, ICondicaoAmostraService {

    private static final long serialVersionUID = 1L;
    
    public CondicaoAmostraService () {
    }

    @Transactional(readOnly = false)
    public void add(CondicaoAmostra c) {
        getDao().<CondicaoAmostra>adicionar(c); 
    }

    public boolean isExiste(CondicaoAmostra c) {
        return !getByNome(c).isEmpty();
    }

    public CondicaoAmostra recuperar(Integer id) {
        return getDao().recuperar(CondicaoAmostra.class, id);
    }

    @Transactional(readOnly = false)
    public void delete(CondicaoAmostra c) {
        getDao().deletar(c);
    }

    @Transactional(readOnly = false)
    public void update(CondicaoAmostra c) {
        getDao().<CondicaoAmostra>atualizar(c);
    }

    public CondicaoAmostra getById(CondicaoAmostra c) {
        return getDao().recuperar(c.getClass(), c.getIdCondicaoAmostra());
    }

    public List<CondicaoAmostra> getByNome(CondicaoAmostra c) {
    	CondicaoAmostra f = new CondicaoAmostra();
    	f.setNome(c.getNome());
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

    public List<CondicaoAmostra> getAll() {
    	CondicaoAmostra f = new CondicaoAmostra();
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

}
