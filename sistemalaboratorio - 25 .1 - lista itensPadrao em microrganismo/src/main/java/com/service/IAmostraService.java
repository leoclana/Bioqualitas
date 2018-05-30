package com.service;

import java.util.Date;
import java.util.List;

import com.filter.IndicadorFilter;
import com.model.Amostra;
import com.model.Cliente;
import com.model.MotivoAnalise;
import com.model.Solicitacao;

public interface IAmostraService extends IService {

	public abstract void salvar(Amostra amostra);
	public abstract void atualizar(Amostra amostra);
	public abstract void deletar(Amostra amostra);
	public abstract List<Amostra> getAll();
	public abstract List<Amostra> getAllBySolicitacao(Solicitacao s);
	public abstract List<Amostra> getAllByFilter(IndicadorFilter filtro);
	public abstract List<Amostra> getAllByMotivoAnaliseInPeriod(
			MotivoAnalise key, Date d1, Date d2);
	public abstract List<Amostra> getAllByClienteInPeriod(Cliente key, Date d1,
			Date d2);
	public abstract List<Amostra> getAllInTimeByClienteInPeriod(Cliente key,
			Date d1, Date d2);
	public abstract List<Amostra> getAllAprovedByClienteInPeriod(Cliente key,
			Date d1, Date d2);
	public abstract List<Amostra> getAllLateByClienteInPeriod(Cliente obj,
			Date d1, Date d2);
	public abstract List<Amostra> getAllReprovedByClienteInPeriod(Cliente obj,
			Date d1, Date d2);
	public abstract void invalidarAmostra(Amostra amostra,String justificativa,Solicitacao solicitacao);	
	
}
