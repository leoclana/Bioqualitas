package com.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.dao.IUsuarioDao;
import com.model.Perfil;
import com.model.Usuario;

public class UsuarioDaoImpl extends Dao implements IUsuarioDao, Serializable {

	private static final long serialVersionUID = 1L;

    protected Class<Usuario> getEntityClass() {
        return Usuario.class;
    }

	public Usuario recuperarComLista(Integer idUsuario) {
		
		return (Usuario) getSession().createQuery(
				"select u " +
				"from Usuario u join fetch u.perfis " +
				"where u.idUsuario = "+idUsuario.toString())
				.uniqueResult();
	}

	@Override
	public List<Usuario> recuperarPorPerfil(List<Perfil> perfis) {
		try{
			String hql = "from Usuario u join fetch u.perfis p where p in (:perfil)";
	        Query query = getSession().createQuery(hql); 
	        query.setParameterList("perfil", perfis);
	       List<Usuario> userList = query.list();
	       return  userList;
	       
		}catch(Throwable t){ 
			t.getMessage();
		}
		return new ArrayList<Usuario>(); 
	}
	
}
