package com.managed.bean;

import java.io.Serializable;
import java.util.List;

import com.model.FormaPagamento;
import com.service.IFormaService;
import com.util.Messages;

public class FormaMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormaPagamento forma;

	private List<FormaPagamento> formaList;

	private IFormaService formaService;

	public FormaMB(IFormaService fS) {
		setFormaService(fS);

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
		getFormaService().delete(getForma());
		refreshLista();
		return null;
	}
	
	private void refreshLista() {
		setFormaList(getFormaService().getAll());
	}

	public String salvar() {
	
		// se o funcao existir atualiza
		if (getForma().getIdForma() != 0) {
			getFormaService().update(getForma());
			return listar();
			// valida se existe p/adicionar
		} else if (!getFormaService().isExiste(getForma())) {
			getFormaService().add(getForma());
			return listar();
		} else {
			Messages.addErrorBundleMessage("cadastroUsuario.existente");
			return null;
		}
	}

	public void limpar() {
		setForma(new FormaPagamento());
	}

	public String getLabelCadastro() {
		if (getForma().getIdForma() == 0) {
			return Messages.getBundleMessage("cadastroUsuario.label.titulo");
		} else {
			return Messages
					.getBundleMessage("cadastroUsuario.label.alteraUsuario");
		}
	}

	public FormaPagamento getForma() {
		if (forma == null)	setForma(new FormaPagamento());
		return forma;
	}

	public void setForma(FormaPagamento m) {
		this.forma = m;
	}

	public List<FormaPagamento> getFormaList() {
		return formaList;
	}

	public void setFormaList(List<FormaPagamento> list) {
		if (formaList == null) formaList = formaService.getAll();
		this.formaList = list;
	}

	private IFormaService getFormaService() {
		return this.formaService;
	}

	private void setFormaService(IFormaService formaService) {
		this.formaService = formaService;
	}

}
