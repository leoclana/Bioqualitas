package com.managed.bean;

import java.io.Serializable;

import com.model.Configuracao;
import com.service.IConfiguracaoService;
import com.util.enums.RetornoFaces;

public class ConfiguracaoMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Configuracao configuracao;
	private IConfiguracaoService configuracaoService;

	public ConfiguracaoMB(IConfiguracaoService cs) {
		setConfiguracaoService(cs);
		setConfiguracao(getConfiguracaoService());
	}
	
	public String salvar() {
		if(getConfiguracao().getIdConfiguracao()!=null && getConfiguracao().getIdConfiguracao()!=0 ){
			getConfiguracaoService().atualizar(configuracao);
		}else{
			getConfiguracaoService().salvar(configuracao);
		}
		return voltar();
	}
	
	public String voltar() {
		getSessaoDoUsuario().setAcaoCorrente("");
		return RetornoFaces.HOME.toString();
	}
	
	public Configuracao getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(IConfiguracaoService iConfiguracaoService) {
		Configuracao configBanco = iConfiguracaoService.getConfiguracao();
			if(configBanco!=null){
				this.configuracao = configBanco;
			}else{
				this.configuracao = new Configuracao();
			}
	}

	public IConfiguracaoService getConfiguracaoService() {
		return configuracaoService;
	}

	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}
	

}
