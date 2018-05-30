package com.dao;

import java.util.List;

import com.filter.IndicadorFilter;
import com.model.Amostra;

public interface IAmostraDao extends IDao {
	public List<Amostra> recuperarPorFiltro(IndicadorFilter filtro);
	public Amostra getById(Integer id);
}
