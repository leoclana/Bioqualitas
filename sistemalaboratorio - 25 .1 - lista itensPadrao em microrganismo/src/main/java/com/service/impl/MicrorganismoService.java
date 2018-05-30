package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Microrganismo;
import com.service.IMicrorganismoService;
import com.util.enums.AcoesLog;

@Transactional(readOnly = true)
public class MicrorganismoService extends Service implements Serializable, IMicrorganismoService {

    private static final long serialVersionUID = 1L;

    public MicrorganismoService () {
    }

    /* (non-Javadoc)
	 * @see com.service.IMicrorganismoService#add(com.model.Microrganismo)
	 */
    @Transactional(readOnly = false)
    public void add(Microrganismo m) {
        getDao().<Microrganismo>adicionar(m); 	
        // verifica se existe o ID cadastrado
        

		logar(AcoesLog.adicionou, m);
    }

    /* (non-Javadoc)
	 * @see com.service.IMicrorganismoService#isExiste(com.model.Microrganismo)
	 */
    public boolean isExiste(Microrganismo m) {
        //Verifica se existe Microrganismo Microrganismo.getNome()
    	List<Microrganismo> ret = getByNome(m);
        if (ret == null || ret.isEmpty())
        	return false;
        else
            return true;
    }

    /* (non-Javadoc)
	 * @see com.service.IMicrorganismoService#recuperar(java.lang.Integer)
	 */
    public Microrganismo recuperar(Integer id) {
        return getDao().recuperar(Microrganismo.class, id);
    }

    /* (non-Javadoc)
	 * @see com.service.IMicrorganismoService#delete(com.model.Microrganismo)
	 */
    @Transactional(readOnly = false)
    public void delete(Microrganismo m) {
        getDao().deletar(m);
        

		logar(AcoesLog.deletou, m);
    }

    /* (non-Javadoc)
	 * @see com.service.IMicrorganismoService#update(com.model.Microrganismo)
	 */
    @Transactional(readOnly = false)
    public void update(Microrganismo m) {
        getDao().<Microrganismo>atualizar(m);
        

		logar(AcoesLog.atualizou, m);
    }

    /* (non-Javadoc)
	 * @see com.service.IMicrorganismoService#getById(com.model.Microrganismo)
	 */
    public Microrganismo getById(Microrganismo m) {
        return getDao().recuperar(m.getClass(), m.getIdMicrorganismo());
    }

    /* (non-Javadoc)
	 * @see com.service.IMicrorganismoService#getByNome(com.model.Microrganismo)
	 */
    public List<Microrganismo> getByNome(Microrganismo Microrganismo) {
    	Microrganismo f = new Microrganismo();
    	f.setNome(Microrganismo.getNome());
    	f.setAtivo(true);

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
	 * @see com.service.IMicrorganismoService#getAll()
	 */
    public List<Microrganismo> getAll() {
    	Microrganismo f = new Microrganismo();
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
