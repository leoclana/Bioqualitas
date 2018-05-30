package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Log;
import com.service.ILogService;

@Transactional(readOnly = true)
public class LogService extends Service implements Serializable, ILogService {

    private static final long serialVersionUID = 1L;
    
    public LogService () {
    }

    /* (non-Javadoc)
	 * @see com.service.ILogService#add(com.model.Log)
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#add(com.model.Log)
	 */
    @Transactional(readOnly = false)
    public void add(Log Log) {
        getDao().<Log>adicionar(Log); 		// verifica se existe o ID cadastrado
    }

    /* (non-Javadoc)
	 * @see com.service.ILogService#isExiste(com.model.Log)
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#isExiste(com.model.Log)
	 */
    public boolean isExiste(Log Log) {
        //Verifica se existe usuario user.getNome()
        if ( getById(Log) != null)
            return true;
        else
            return false;
    }

    /* (non-Javadoc)
	 * @see com.service.ILogService#recuperar(java.lang.Integer)
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#recuperar(java.lang.Integer)
	 */
    public Log recuperar(Integer id) {
        return getDao().recuperar(Log.class, id);
    }

    /* (non-Javadoc)
	 * @see com.service.ILogService#delete(com.model.Log)
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#delete(com.model.Log)
	 */
    @Transactional(readOnly = false)
    public void delete(Log Log) {
        getDao().deletar(Log);
    }

    /* (non-Javadoc)
	 * @see com.service.ILogService#update(com.model.Log)
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#update(com.model.Log)
	 */
    @Transactional(readOnly = false)
    public void update(Log l) {
        getDao().<Log>atualizar(l);
    }

    /* (non-Javadoc)
	 * @see com.service.ILogService#getById(com.model.Log)
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#getById(com.model.Log)
	 */
    public Log getById(Log l) {
        return getDao().recuperar(l.getClass(), l.getIdLog());
    }

    /* (non-Javadoc)
	 * @see com.service.ILogService#getByNome(com.model.Log)
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#getByUsuario(com.model.Log)
	 */
    /*public List<Log> getByUsuario(Log Log) {
        return logDAO.recuperarPorUsuario(Log);
    }*/

    /* (non-Javadoc)
	 * @see com.service.ILogService#getAll()
	 */
    /* (non-Javadoc)
	 * @see com.service.impl.ILogService#getAll()
	 */
    public List<Log> getAll() {
    	Log f = new Log();
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
