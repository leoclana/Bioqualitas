package com.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Log;

public interface ILogService extends IService{

	/* (non-Javadoc)
	 * @see com.service.ILogService#add(com.model.Log)
	 */
	public abstract void add(Log Log);

	/* (non-Javadoc)
	 * @see com.service.ILogService#isExiste(com.model.Log)
	 */
	public abstract boolean isExiste(Log Log);

	/* (non-Javadoc)
	 * @see com.service.ILogService#recuperar(java.lang.Integer)
	 */
	public abstract Log recuperar(Integer id);

	/* (non-Javadoc)
	 * @see com.service.ILogService#delete(com.model.Log)
	 */
	public abstract void delete(Log Log);

	/* (non-Javadoc)
	 * @see com.service.ILogService#update(com.model.Log)
	 */
	public abstract void update(Log Log);

	/* (non-Javadoc)
	 * @see com.service.ILogService#getById(com.model.Log)
	 */
	public abstract Log getById(Log Log);

	/* (non-Javadoc)
	 * @see com.service.ILogService#getByNome(com.model.Log)
	 */
	//public abstract List<Log> getByUsuario(Log Log);

	/* (non-Javadoc)
	 * @see com.service.ILogService#getAll()
	 */
	public abstract List<Log> getAll();

}