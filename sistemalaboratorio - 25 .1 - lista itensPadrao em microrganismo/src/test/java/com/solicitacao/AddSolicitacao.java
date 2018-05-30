package com.solicitacao;

import inicializacao.InicializacaoSpring;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.model.Cliente;
import com.model.Solicitacao;
import com.service.IClienteService;
import com.service.ISolicitacaoService;
import com.util.Messages;
import com.util.enums.StatusSolicitacaoEnum;

public class AddSolicitacao {

	private static ISolicitacaoService solicitacaoService;
	private static IClienteService clienteService;


	@BeforeClass
	public static void setUpClass() {
		InicializacaoSpring.setUpClass();
		setSolicitacaoService((ISolicitacaoService) InicializacaoSpring.ctx
				.getBean("SolicitacaoService"));
		setClienteService((IClienteService) InicializacaoSpring.ctx
				.getBean("ClienteService"));
	}

	@BeforeMethod
	public void setUp() {
	}

	@AfterTest
	public void afterTest() {
	}

	@Test
	public void main() {
		//adicionaDocSolicitacao();
		listarSolicitacoes();
	}

	private void listarSolicitacoes() {
		try {
			
			List<Solicitacao> solicitacaoList = new ArrayList();
			solicitacaoList = getSolicitacaoService().getAll();
			
			for (Solicitacao solicitacao : solicitacaoList) {
				System.out.println("Id da solicitação: " + solicitacao.getIdSolicitacao().toString());
				System.out.println("Data da solicitação: " + solicitacao.getDataSolicitacao() );
				System.out.println("Status da solicitação: " + solicitacao.getStatus());
			}
			
			
			
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA LISTAGEM DAS SOLICITAÇÕES -->"
					+ e.getMessage());
		}
	}

	private void adicionaDocSolicitacao() {

		try {

			List<Cliente> clientes =  getClienteService().getAll();

			if (clientes != null && clientes.size() == 1) {

				Cliente cliente;
				cliente = clientes.get(0);
				if (cliente == null){
					return;
				}

				Solicitacao solicitacao = new Solicitacao();
				solicitacao.setCliente(cliente);
				solicitacao.setStatus(StatusSolicitacaoEnum.EMAPROVACAO);
				getSolicitacaoService().salvar(solicitacao);

			}
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA CRIAÇÃO DA SOLICITAÇÃO -->"
					+ e.getMessage());
		}
	}

	public static ISolicitacaoService getSolicitacaoService() {
		return solicitacaoService;
	}

	public static void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		AddSolicitacao.solicitacaoService = solicitacaoService;
	}

	public static IClienteService getClienteService() {
		return clienteService;
	}

	public static void setClienteService(IClienteService clienteService) {
		AddSolicitacao.clienteService = clienteService;
	}

}
