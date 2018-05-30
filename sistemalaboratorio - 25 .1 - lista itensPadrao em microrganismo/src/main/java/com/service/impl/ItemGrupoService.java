package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Grupo;
import com.model.ItemGrupo;
import com.service.IItemGrupoService;

@Transactional(readOnly = true)
public class ItemGrupoService extends Service implements Serializable, IItemGrupoService {

    private static final long serialVersionUID = 1L;
    
    public ItemGrupoService () {
    }

    /* (non-Javadoc)
	 * @see com.service.IItemGrupoService#add(com.model.ItemGrupo)
	 */
    @Transactional(readOnly = false)
    public void add(ItemGrupo ItemGrupo) {
        getDao().<ItemGrupo>adicionar(ItemGrupo); 		// verifica se existe o ID cadastrado
    }

    /* (non-Javadoc)
	 * @see com.service.IItemGrupoService#isExiste(com.model.ItemGrupo)
	 */
    public boolean isExiste(ItemGrupo ig) {
        //Verifica se existe ItemGrupo ItemGrupo.getNome()
        if (getByNomeIdGrupo(ig) != null )
            return true;
        else
            return false;
    }

    /* (non-Javadoc)
	 * @see com.service.IItemGrupoService#recuperar(java.lang.Integer)
	 */
    public ItemGrupo recuperar(Integer id) {
        return getDao().recuperar(ItemGrupo.class, id);
    }

    /* (non-Javadoc)
	 * @see com.service.IItemGrupoService#delete(com.model.ItemGrupo)
	 */
    @Transactional(readOnly = false)
    public void delete(ItemGrupo ItemGrupo) {
        getDao().deletar(ItemGrupo);
    }

    /* (non-Javadoc)
	 * @see com.service.IItemGrupoService#update(com.model.ItemGrupo)
	 */
    @Transactional(readOnly = false)
    public void update(ItemGrupo ig) {
        getDao().<ItemGrupo>atualizar(ig);
    }

    /* (non-Javadoc)
	 * @see com.service.IItemGrupoService#getById(com.model.ItemGrupo)
	 */
    public ItemGrupo getById(ItemGrupo ig) {
        return getDao().recuperar(ig.getClass(), ig.getIdItemGrupo());
    }

    /* (non-Javadoc)
	 * @see com.service.IItemGrupoService#getByNome(com.model.ItemGrupo)
	 */
    public ItemGrupo getByNome(ItemGrupo ig) {
    	ItemGrupo f = new ItemGrupo();
    	f.setNome(ig.getNome());
        List <ItemGrupo> resultado = null;

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
	 * @see com.service.IItemGrupoService#getByNomeIdGrupo(com.model.ItemGrupo)
	 */
    public ItemGrupo getByNomeIdGrupo(ItemGrupo ig) {
    	ItemGrupo f = new ItemGrupo();
    	f.setNome(ig.getNome());
    	f.setGrupo(ig.getGrupo());
        List <ItemGrupo> resultado = null;

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
	 * @see com.service.IItemGrupoService#getAll()
	 */
    public List<ItemGrupo> getAll() {
    	ItemGrupo f = new ItemGrupo();
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
    
    public List<ItemGrupo> getAllByGrupo(Grupo g) {
    	ItemGrupo f = new ItemGrupo();
    	if(g!=null)
    		f.setGrupo(g);
    	
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
