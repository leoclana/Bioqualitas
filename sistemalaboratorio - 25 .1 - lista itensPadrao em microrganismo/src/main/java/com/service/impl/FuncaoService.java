package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Funcao;
import com.service.IFuncaoService;

@Transactional(readOnly = true)
public class FuncaoService extends Service implements Serializable, IFuncaoService {

    private static final long serialVersionUID = 1L;
    
    public FuncaoService () {
    }

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#add(com.model.Funcao)
	 */
    @Transactional(readOnly = false)
    public void add(Funcao Funcao) {
        getDao().<Funcao>adicionar(Funcao); 		// verifica se existe o ID cadastrado
    }

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#isExiste(com.model.Funcao)
	 */
    public boolean isExiste(Funcao Funcao) {
        //Verifica se existe Funcao Funcao.getNome()
        return getByNome(Funcao).isEmpty();
    }

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#recuperar(java.lang.Integer)
	 */
    public Funcao recuperar(Integer id) {
        return getDao().recuperar(Funcao.class, id);
    }

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#delete(com.model.Funcao)
	 */
    @Transactional(readOnly = false)
    public void delete(Funcao Funcao) {
        getDao().deletar(Funcao);
    }

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#update(com.model.Funcao)
	 */
    @Transactional(readOnly = false)
    public void update(Funcao Funcao) {
        getDao().<Funcao>atualizar(Funcao);
    }

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#getById(com.model.Funcao)
	 */
    public Funcao getById(Funcao Funcao) {
        return getDao().recuperar(Funcao.getClass(), Funcao.getIdFuncao());
    }

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#getByNome(com.model.Funcao)
	 */
    public List<Funcao> getByNome(Funcao Funcao) {
    	Funcao f = new Funcao();
    	f.setNome(Funcao.getNome());
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

    /* (non-Javadoc)
	 * @see com.service.IFuncaoService#getAll()
	 */
    public List<Funcao> getAll() {
    	Funcao f = new Funcao();
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
