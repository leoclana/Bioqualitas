package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.FormaPagamento;
import com.service.IFormaService;

@Transactional(readOnly = true)
public class FormaService extends Service implements Serializable, IFormaService {

    private static final long serialVersionUID = 1L;
    
    public FormaService () {
    }

    @Transactional(readOnly = false)
    public void add(FormaPagamento m) {
        getDao().<FormaPagamento>adicionar(m); 		// verifica se existe o ID cadastrado
    }

    public boolean isExiste(FormaPagamento m) {
        //Verifica se existe Motivo Motivo.getNome()
        return !getByNome(m).isEmpty();
    }

    public FormaPagamento recuperar(Integer id) {
        return getDao().recuperar(FormaPagamento.class, id);
    }

    @Transactional(readOnly = false)
    public void delete(FormaPagamento m) {
        getDao().deletar(m);
    }

    @Transactional(readOnly = false)
    public void update(FormaPagamento m) {
        getDao().<FormaPagamento>atualizar(m);
    }

    public FormaPagamento getById(FormaPagamento m) {
        return getDao().recuperar(m.getClass(), m.getIdForma());
    }

    public List<FormaPagamento> getByNome(FormaPagamento m) {
    	FormaPagamento f = new FormaPagamento();
    	f.setNome(m.getNome());
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

    public List<FormaPagamento> getAll() {
    	FormaPagamento f = new FormaPagamento();
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
