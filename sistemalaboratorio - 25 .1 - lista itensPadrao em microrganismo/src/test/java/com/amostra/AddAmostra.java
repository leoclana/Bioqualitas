package com.amostra;

import inicializacao.InicializacaoSpring;

import java.util.List;

import javax.faces.model.SelectItem;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.model.CondicaoAmostra;
import com.service.ICondicaoAmostraService;
import com.util.Messages;
import com.util.view.SelectOneDataModel;

public class AddAmostra {

	private static ICondicaoAmostraService condicaoAmostraService;
	private  List<CondicaoAmostra> condicaoAmostraList;
	

	@BeforeClass
	public static void setUpClass() {
		InicializacaoSpring.setUpClass();
		setCondicaoAmostraService((ICondicaoAmostraService) InicializacaoSpring.ctx
				.getBean("CondicaoAmostraService"));
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
		listarCondicaoAmostra();
	}



	private void listarCondicaoAmostra() {

		try {

			List<CondicaoAmostra> listCondicaoAmostra =  getCondicaoAmostraService().getAll();
			
			if (listCondicaoAmostra != null && !listCondicaoAmostra.isEmpty()){
				for (CondicaoAmostra condicaoAmostra : listCondicaoAmostra) {
					System.out.println("listarCondicaoAmostra ==> " + condicaoAmostra.getNome());
				}
			}
			
			setCondicaoAmostraList(getCondicaoAmostraService().getAll());
			
			List<SelectItem> selectItemList = SelectOneDataModel.criaComTextoInicialPersolanizado(getCondicaoAmostraList(), "Todos").getListaSelecao();
			
			
		} catch (Exception e) {
			Messages.addErrorTextMessage("ERRO NA LISTAGEM DA CONDICAO DE AMOSTRA -->"
					+ e.getMessage());
		}
	}


	public static ICondicaoAmostraService getCondicaoAmostraService() {
		return condicaoAmostraService;
	}

	public static void setCondicaoAmostraService(
			ICondicaoAmostraService condicaoAmostraService) {
		AddAmostra.condicaoAmostraService = condicaoAmostraService;
	}

	public List<CondicaoAmostra> getCondicaoAmostraList() {
		return condicaoAmostraList;
	}

	public void setCondicaoAmostraList(List<CondicaoAmostra> condicaoAmostraList) {
		this.condicaoAmostraList = condicaoAmostraList;
	}

}
