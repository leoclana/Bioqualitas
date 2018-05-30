package com.configuracao;

import inicializacao.InicializacaoSpring;

import java.util.Calendar;
import java.text.DecimalFormat;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.model.Configuracao;
import com.service.IConfiguracaoService;
import com.util.Messages;

public class AlteraConfiguracao {

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
		//		adicionaDocConfiguracao();
		testeAnoConfiguracao();
	}


	private void testeAnoConfiguracao(){

		
		StringBuilder strNum = new StringBuilder();
		Calendar now = Calendar.getInstance();
		Integer ano = new Integer(now.get(Calendar.YEAR));
		
		String strAno = ano.toString().substring(2);
		ano = Integer.valueOf(strAno);

		Configuracao configuracao = getConfigService().getConfiguracao();

		if (configuracao!=null){
			Integer  anoConfiguracao = configuracao.getAnoCorrente();
			Integer  numeroSolicitacao = configuracao.getNumeroSolicitacao();

			if (anoConfiguracao!=null){
				if (!ano.equals(anoConfiguracao)){
					configuracao.setAnoCorrente(ano);
				}
			}else{ //valor nao definido no banco
				configuracao.setAnoCorrente(ano);
			}

			if (numeroSolicitacao!=null ){		
				numeroSolicitacao++;
				configuracao.setNumeroSolicitacao(numeroSolicitacao);
			}else{ //valor nao definido no banco
				numeroSolicitacao = 1;
				configuracao.setNumeroSolicitacao(numeroSolicitacao);
			}
			
			DecimalFormat nf = new DecimalFormat("00000");  
			strNum.append(ano).append(nf.format(numeroSolicitacao));
			System.out.println( strNum.toString());
			
			getConfigService().atualizar(configuracao);
		}
		else{
			adicionaDocConfiguracao();
		}
		
	}


	private void adicionaDocConfiguracao() {
		try {
			Configuracao config  = getConfigService().getConfiguracao();
			if (config==null)
				config = new Configuracao();

			Calendar year = Calendar.getInstance();
			Integer anoCorrente = new Integer(year.get(Calendar.YEAR));
			String strAnoCorrente = new String(anoCorrente.toString());
			anoCorrente =   Integer.valueOf(strAnoCorrente.substring(2));
			config.setAnoCorrente(anoCorrente);
			config.setAgua(10);
			config.setAguaMineral(20);
			config.setAlimento(30);
			config.setAr(40);
			config.setManipulador(50);
			config.setSuperficie(60);
			config.setEncerramentodd(70);
			config.setEncerramentohh(80);

			if(config.getIdConfiguracao()!=0){
				getConfigService().atualizar(config);
			}else{			
				getConfigService().salvar(config);
			}

		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CRIAÇÃO DA CONFIGURAÇÃO -->" + e.getMessage());
		}
	}

	public static IConfiguracaoService getConfigService() {
		return configService;
	}

	public static void setConfigService(IConfiguracaoService configService) {
		AlteraConfiguracao.configService = configService;
	}



}
