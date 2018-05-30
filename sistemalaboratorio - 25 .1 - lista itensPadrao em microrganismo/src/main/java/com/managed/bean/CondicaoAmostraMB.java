package com.managed.bean;

import java.io.Serializable;
import java.util.List;

import com.model.CondicaoAmostra;
import com.service.ICondicaoAmostraService;
import com.util.Messages;

public class CondicaoAmostraMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private CondicaoAmostra condicaoAmostra;

	private List<CondicaoAmostra> condicaoAmostraList;

	private ICondicaoAmostraService condicaoAmostraService;

	public CondicaoAmostraMB(ICondicaoAmostraService fS) {
		setCondicaoAmostraService(fS);
		refreshLista();
	}
	
	public String criar() {
		limpar();
		
		return super.criar();
	}
	
	public String listar(){
		refreshLista();
		return super.listar();
	}

	public String deletar() {
		getCondicaoAmostraService().delete(getCondicaoAmostra());
		refreshLista();
		return null;
	}
	
	private void refreshLista() {
		setCondicaoAmostraList(getCondicaoAmostraService().getAll());
	}

	public String salvar() {
	
		// se o funcao existir atualiza
		if (getCondicaoAmostra().getIdCondicaoAmostra() != 0) {
			getCondicaoAmostraService().update(getCondicaoAmostra());
			return listar();
			// valida se existe p/adicionar
		} else if (!getCondicaoAmostraService().isExiste(getCondicaoAmostra())) {
			getCondicaoAmostraService().add(getCondicaoAmostra());
			return listar();
		} else {
			Messages.addErrorBundleMessage("cadastroUsuario.existente");
			return null;
		}
	}

	public void limpar() {
		setCondicaoAmostra(new CondicaoAmostra());
	}

	public String getLabelCadastro() {
		if (getCondicaoAmostra().getIdCondicaoAmostra() == 0) {
			return Messages.getBundleMessage("cadastroUsuario.label.titulo");
		} else {
			return Messages
					.getBundleMessage("cadastroUsuario.label.alteraUsuario");
		}
	}

	public CondicaoAmostra getCondicaoAmostra() {
		if (condicaoAmostra == null)	setCondicaoAmostra(new CondicaoAmostra());
		return condicaoAmostra;
	}

	public void setCondicaoAmostra(CondicaoAmostra m) {
		this.condicaoAmostra = m;
	}

	public List<CondicaoAmostra> getCondicaoAmostraList() {
		return condicaoAmostraList;
	}

	public void setCondicaoAmostraList(List<CondicaoAmostra> list) {
		if (condicaoAmostraList == null) condicaoAmostraList = condicaoAmostraService.getAll();
		this.condicaoAmostraList = list;
	}

	public ICondicaoAmostraService getCondicaoAmostraService() {
		return condicaoAmostraService;
	}

	public void setCondicaoAmostraService(
			ICondicaoAmostraService condicaoAmostraService) {
		this.condicaoAmostraService = condicaoAmostraService;
	}



}
