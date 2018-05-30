package com.service;

import java.util.List;

import com.model.Amostra;
import com.model.Resultado;

public interface IResultadoService extends IService{
 
	public abstract void salvar(Resultado Resultado);
	public abstract void atualizar(Resultado Resultado);
	public abstract void deletar(Resultado Resultado);
	public abstract boolean isExiste(Resultado Resultado);
	public abstract Resultado recuperar(Integer id);
	public abstract Resultado getById(Resultado Resultado);
	public abstract List<Resultado> getAll();
	public abstract List<Resultado> getByAmostra(Amostra Amostra);
	public abstract List<Resultado> getCompByAmostra(Amostra amostra);
	public abstract void atualizaListaResultado(Amostra amostra, List<Resultado>  resultadoList,  boolean ativo);
	

}