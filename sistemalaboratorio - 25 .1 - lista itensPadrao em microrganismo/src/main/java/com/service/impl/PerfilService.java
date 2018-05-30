package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IPerfilDao;
import com.model.Perfil;
import com.service.IPerfilService;

@Transactional(readOnly = true)
public class PerfilService extends Service implements Serializable, IPerfilService {


    private static final long serialVersionUID = 1L;

    public IPerfilDao perfilDAO;
    
    public PerfilService () {
    }

    @Transactional(readOnly = false)
    public void add(Perfil perfil) {
        perfilDAO.adicionar(perfil); 		// verifica se existe o ID cadastrado
    }

    public boolean isExiste(Perfil perfil) {

    	Perfil p = new Perfil();
    	p.setNome(perfil.getNome());
    	
    	List<Perfil> retorno = null;
		try {
			retorno = perfilDAO.recuperarPorAtributoPreenchido(p);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if ((retorno != null) && !retorno.isEmpty())
            return true;
        else
            return false;
    }
    
    public Perfil recuperarPorNome(String nome) {

    	Perfil p = new Perfil();
    	p.setNome(nome);
        p.setAtivo(true);
    	
    	List<Perfil> retorno = null;
		try {
			retorno = perfilDAO.recuperarPorAtributoPreenchido(p);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if ((retorno != null) && !retorno.isEmpty())
            return retorno.get(0);
        else
            return null;
    }

    public Perfil recuperar(Integer id) {
        return perfilDAO.recuperar(Perfil.class, id);
    }

    @Transactional(readOnly = false)
    public void delete(Perfil perfil) {
        perfilDAO.deletar(perfil);
    }

    @Transactional(readOnly = false)
    public void update(Perfil perfil) {
        perfilDAO.atualizar(perfil);
    }

    public Perfil getById(Perfil perfil) {
        return perfilDAO.recuperar(perfil.getClass(), perfil.getIdPerfil());
    }
    
	@Override
	public Perfil getByIdComLista(Perfil perfil) {
		return perfilDAO.recuperarComLista(perfil.getIdPerfil());
	}

    public List<Perfil> getAll() {
		Perfil perfilPorAmostra = new Perfil();
        try {
			return perfilDAO.recuperarPorAtributoPreenchido(perfilPorAmostra);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    public void setPerfilDAO(IPerfilDao PerfilDAO) {
        this.perfilDAO = PerfilDAO;
    }
}
