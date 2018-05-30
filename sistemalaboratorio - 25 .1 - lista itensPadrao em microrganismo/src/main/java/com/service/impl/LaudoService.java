package com.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dao.ILaudoDao;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IDao;
import com.model.Laudo;
import com.service.ILaudoService;
import com.util.enums.TipoAnalise;

@Transactional(readOnly = true)
public class LaudoService extends Service implements Serializable, ILaudoService {

	private static final long serialVersionUID = 1L;

	public IDao dao;

	public ILaudoDao laudoDao;

	public LaudoService() {
	}

	@Transactional(readOnly = false)
	public void add(Laudo m) {
		getDao().<Laudo> adicionar(m); // verifica se existe o ID cadastrado
	}

	public boolean isExiste(Laudo m) {
		// Verifica se existe Motivo Motivo.getNome()
		return !getByNome(m).isEmpty();
	}

	public Laudo recuperar(Integer id) {
		return getDao().recuperar(Laudo.class, id);
	}

	public List<Laudo> recuperarPorAtributo(Laudo laudo) {
		List<Laudo> retorno = new ArrayList<Laudo>();
		try {
			retorno = (List<Laudo>)getDao().recuperarPorAtributoPreenchido(laudo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Transactional(readOnly = false)
	public void delete(Laudo m) {
		getDao().deletar(m);
	}

	@Transactional(readOnly = false)
	public void update(Laudo m) {
		getDao().<Laudo> atualizar(m);
	}

	public Laudo getById(Laudo m) {
		return getDao().recuperar(m.getClass(), m.getIdLaudo());
	}

	public Laudo getById(Integer id) {
		return getLaudoDao().laudoById(id);
	}

	public List<Laudo> getByNome(Laudo m) {
		Laudo f = new Laudo();
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

	public List<Laudo> getByResultadoTipoAnalise(String r, TipoAnalise t) {
		Laudo f = new Laudo();
		if (!"".equals(r))
			f.setResultado(r);
		f.setTipoAnalise(t);
		f.setAtivo(true);
		try {
			return getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Laudo> getAll() {
		Laudo m = new Laudo();
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

	public ILaudoDao getLaudoDao() {
		return laudoDao;
	}

	public void setLaudoDao(ILaudoDao laudoDao) {
		this.laudoDao = laudoDao;
	}
}
