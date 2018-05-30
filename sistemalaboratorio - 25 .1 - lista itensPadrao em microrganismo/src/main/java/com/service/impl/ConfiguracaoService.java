package com.service.impl;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.model.Configuracao;
import com.service.IConfiguracaoService;
import com.util.enums.AcoesLog;

@Transactional(readOnly = true)
public class ConfiguracaoService extends Service implements Serializable, IConfiguracaoService {

	private static final long serialVersionUID = 1L;
	
	public ConfiguracaoService() {
	}
	
	@Override
	public Configuracao getConfiguracao() {
		return getDao().recuperarUnico(Configuracao.class);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void salvar(Configuracao configuracao) {
		getDao().<Configuracao>adicionar(configuracao);

		logar(AcoesLog.adicionou, configuracao);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void atualizar(Configuracao configuracao) {
		getDao().<Configuracao>atualizar(configuracao);		

		logar(AcoesLog.atualizou, configuracao);
	}
}
