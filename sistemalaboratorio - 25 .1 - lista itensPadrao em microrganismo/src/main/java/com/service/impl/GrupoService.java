package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Grupo;
import com.service.IGrupoService;

@Transactional(readOnly = true)
public class GrupoService extends Service implements Serializable, IGrupoService {

    private static final long serialVersionUID = 1L;
    
    public GrupoService () {
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#add(com.model.Grupo)
	 */
    @Transactional(readOnly = false)
    public void add(Grupo Grupo) {
    	getDao().<Grupo>adicionar(Grupo); 		// verifica se existe o ID cadastrado
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#isExiste(com.model.Grupo)
	 */
    public boolean isExiste(Grupo grupo) {
        //Verifica se existe Grupo Grupo.getNome()
        if (getByNome(grupo) != null ) {
            return true;
        } else {
        	return false;
        }
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#recuperar(java.lang.Integer)
	 */
    public Grupo recuperar(Integer id) {
        return getDao().recuperar(Grupo.class, id);
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#delete(com.model.Grupo)
	 */
    @Transactional(readOnly = false)
    public void delete(Grupo grupo) {
        getDao().deletar(grupo);
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#update(com.model.Grupo)
	 */
    @Transactional(readOnly = false)
    public void update(Grupo grupo) {
        getDao().<Grupo>atualizar(grupo);
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#getById(com.model.Grupo)
	 */
    public Grupo getById(Grupo grupo) {
        return getDao().recuperar(grupo.getClass(), grupo.getIdGrupo());
    }
    
    public Grupo getByIdGrupo(Grupo grupo) {
    	Grupo f = new Grupo();
    	f.setIdGrupo(grupo.getIdGrupo());
    	
    	List<Grupo> resultado = null;
    	try {
			resultado = getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if ((resultado != null) && (!resultado.isEmpty()))
			return resultado.get(0);
		else
			return null;
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#getByNome(com.model.Grupo)
	 */
    public Grupo getByNome(Grupo grupo) {
    	Grupo f = new Grupo();
    	f.setNome(grupo.getNome());
    	
    	List<Grupo> resultado = null;
    	try {
			resultado = getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if ((resultado != null) && (!resultado.isEmpty()))
			return resultado.get(0);
		else
			return null;
        
    }

    /* (non-Javadoc)
	 * @see com.service.IGrupoService#getAll()
	 */
    public List<Grupo> getAll() {
    	Grupo f = new Grupo();
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
