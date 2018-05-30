package com.service;

import com.model.Configuracao;

public interface IConfiguracaoService extends IService{
	public abstract Configuracao getConfiguracao();
	public abstract void salvar(Configuracao configuracao);
	public abstract void atualizar(Configuracao configuracao);
}
