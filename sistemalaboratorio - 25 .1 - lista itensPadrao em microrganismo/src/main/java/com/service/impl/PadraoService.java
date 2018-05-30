package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Padrao;
import com.service.IPadraoService;

@Transactional(readOnly = true)
public class PadraoService extends Service implements Serializable, IPadraoService {

    private static final long serialVersionUID = 1L;
    
    public PadraoService () {
    }

    /* (non-Javadoc)
	 * @see com.service.IItemPadraoService#add(com.model.ItemPadrao)
	 */
    @Transactional(readOnly = false)
    public void add(Padrao Padrao) {
        getDao().<Padrao>adicionar(Padrao); 		// verifica se existe o ID cadastrado
    }

    /* (non-Javadoc)
	 * @see com.service.IItemPadraoService#isExiste(com.model.ItemPadrao)
	 */
    public boolean isExiste(Padrao Padrao) {
        //Verifica se existe ItemPadrao ItemPadrao.getNome()
        if (getByNome(Padrao) != null )
            return true;
        else
            return false;
    }

    /* (non-Javadoc)
	 * @see com.service.IItemPadraoService#recuperar(java.lang.Integer)
	 */
    public Padrao recuperar(Integer id) {
        return getDao().recuperar(Padrao.class, id);
    }

    /* (non-Javadoc)
	 * @see com.service.IItemPadraoService#delete(com.model.ItemPadrao)
	 */
    @Transactional(readOnly = false)
    public void delete(Padrao Padrao) {
        getDao().deletar(Padrao);
    }

    /* (non-Javadoc)
	 * @see com.service.IItemPadraoService#update(com.model.ItemPadrao)
	 */
    @Transactional(readOnly = false)
    public void update(Padrao Padrao) {
        getDao().atualizar(Padrao);
    }

    /* (non-Javadoc)
	 * @see com.service.IItemPadraoService#getById(com.model.ItemPadrao)
	 */
    public Padrao getById(Padrao p) {
        return getDao().recuperar(p.getClass(), p.getIdPadrao());
    }

    /* (non-Javadoc)
	 * @see com.service.IItemPadraoService#getByNome(com.model.ItemPadrao)
	 */
    public Padrao getByNome(Padrao padrao) {
    	Padrao f = new Padrao();
    	f.setNome(padrao.getNome());
    	
        List <Padrao> resultado = null;
        try {
        	resultado = getDao().<Padrao>recuperarPorAtributoPreenchido(f);
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
	 * @see com.service.IItemPadraoService#getAll()
	 */
    public List<Padrao> getAll() {
    	Padrao f = new Padrao();
        try {
			return getDao().<Padrao>recuperarPorAtributoPreenchido(f);
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
