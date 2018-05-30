package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IDao;
import com.model.MotivoAnalise;
import com.service.IMotivoService;

@Transactional(readOnly = true)
public class MotivoService extends Service implements Serializable, IMotivoService {


    private static final long serialVersionUID = 1L;

    public IDao dao;
    
    public MotivoService () {
    }

    @Transactional(readOnly = false)
    public void add(MotivoAnalise m) {
        getDao().<MotivoAnalise>adicionar(m); 		// verifica se existe o ID cadastrado
    }

    public boolean isExiste(MotivoAnalise m) {
        //Verifica se existe Motivo Motivo.getNome()
        return !getByNome(m).isEmpty();
    }

    public MotivoAnalise recuperar(Integer id) {
        return getDao().recuperar(MotivoAnalise.class, id);
    }

    @Transactional(readOnly = false)
    public void delete(MotivoAnalise m) {
        getDao().deletar(m);
    }

    @Transactional(readOnly = false)
    public void update(MotivoAnalise m) {
        getDao().<MotivoAnalise>atualizar(m);
    }

    public MotivoAnalise getById(MotivoAnalise m) {
        return getDao().recuperar(m.getClass(), m.getIdMotivo());
    }
    
    @Transactional(readOnly = false)
    public List<MotivoAnalise> getByNome(MotivoAnalise m) {
    	MotivoAnalise f = new MotivoAnalise();
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

    @Transactional(readOnly = false)
    public List<MotivoAnalise> getAll() {
    	MotivoAnalise m = new MotivoAnalise();
        try {
			return getDao().recuperarPorAtributoPreenchido(m);
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
