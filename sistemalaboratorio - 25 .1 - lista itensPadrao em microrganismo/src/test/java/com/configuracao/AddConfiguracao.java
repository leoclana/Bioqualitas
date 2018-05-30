package com.configuracao;

import inicializacao.InicializacaoSpring;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.model.Configuracao;
import com.service.IConfiguracaoService;
import com.util.Messages;


public class AddConfiguracao {

	private static IConfiguracaoService configService;

	@BeforeClass
	public static void setUpClass() {
		InicializacaoSpring.setUpClass();
		setConfigService((IConfiguracaoService)  InicializacaoSpring.ctx.getBean("ConfiguracaoService"));
	}

	@BeforeMethod
	public void setUp() {
	}

	@AfterTest
	public void afterTest() {
	}

	@Test
	public void main() {
		adicionaDocConfiguracao();
	}


	private void adicionaDocConfiguracao() {
		try {

			Configuracao config = new Configuracao();
			config.setAgua(1);
			config.setAguaMineral(2);
			config.setAlimento(3);
			config.setAr(4);
			config.setManipulador(5);
			config.setSuperficie(6);
			config.setEncerramentodd(7);
			config.setEncerramentohh(8);
			
			getConfigService().salvar(config);
			
			
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CRIAÇÃO DA CONFIGURAÇÃO -->" + e.getMessage());
		}
	}

	public static IConfiguracaoService getConfigService() {
		return configService;
	}

	public static void setConfigService(IConfiguracaoService configService) {
		AddConfiguracao.configService = configService;
	}



}
