package com.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Property;

import com.dao.IAmostraDao;
import com.filter.IndicadorFilter;
import com.model.Amostra;
import com.util.CriteriaUtil;
import org.hibernate.criterion.Restrictions;

public class AmostraDaoImpl extends Dao implements
		IAmostraDao, Serializable {

	private static final long serialVersionUID = 1L;

	protected Class<Amostra> getEntityClass() {
		return Amostra.class;
	}
	
	@Override
	public List<Amostra> recuperarPorFiltro(IndicadorFilter filtro) {
		Criteria criteria = getSession().createCriteria(Amostra.class, "a");
		criteria.createAlias("solicitacao", "s", Criteria.INNER_JOIN);

		if (filtro.isAtivo()) {
			criteria.add(Property.forName("a.ativo").eq(filtro.isAtivo()));
		}

		if (filtro.getCliente() != null) {
			criteria.add(Property.forName("s.cliente").eq(filtro.getCliente()));
		} else {
			criteria.add(Property.forName("a.motivoAnalise").eq(
					filtro.getMotivoAnalise()));
		}

		CriteriaUtil.adicionaDatasDeAte(filtro.getDataInicioSolic(),
				filtro.getDataFimSolic(), "s.dataSolicitacao", criteria);

		if (filtro.isInTime() != null) {
			criteria.add(Property.forName("a.noPrazo").eq(filtro.isInTime()));
		} else {
			criteria.add(Property.forName("a.noPrazo").isNotNull());
		}

		if (filtro.isPositive() != null) {
			criteria.add(Property.forName("a.positivo").eq(filtro.isPositive()));
		} else {
			criteria.add(Property.forName("a.positivo").isNotNull());
		}

		return (List<Amostra>) criteria.list();
	}

	@Override
	public Amostra getById(Integer id){
		Criteria criteria = getSession().createCriteria(Amostra.class);
		criteria.add(Restrictions.eq("idAmostra",id));
		return (Amostra)criteria.uniqueResult();
	}

}
