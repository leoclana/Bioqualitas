package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.model.Funcao;
import com.model.Usuario;
import com.service.IFuncaoService;
import com.util.Messages;
import com.util.Util;
import com.util.enums.Acoes;
import com.util.enums.RetornoFaces;
import com.util.view.SelectManyDataModel;

public class FuncaoMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcao funcao;

	private List<Funcao> funcaoList;

	private IFuncaoService funcaoService;

	public FuncaoMB(IFuncaoService fS) {
		setFuncaoService(fS);

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

	public String deletar(Funcao funcao) {
		getFuncaoService().delete(funcao);
		refreshLista();
		return null;
	}
	
	private void refreshLista() {
		setFuncaoList(new ArrayList<Funcao>());
		getFuncaoList().addAll(getFuncaoService().getAll());
	}

	public String salvar() {
	
		// se o funcao existir atualiza
		if (getFuncao().getIdFuncao() != 0) {
			getFuncaoService().update(getFuncao());
			return listar();
			// valida se existe p/adicionar
		} else if (!getFuncaoService().isExiste(getFuncao())) {
			getFuncaoService().add(getFuncao());
			return listar();
		} else {
			Messages.addErrorBundleMessage("cadastroUsuario.existente");
			return null;
		}
	}

	public void limpar() {
		setFuncao(new Funcao());
	}

	public String getLabelCadastro() {
		if (getFuncao().getIdFuncao() == 0) {
			return Messages.getBundleMessage("cadastroUsuario.label.titulo");
		} else {
			return Messages
					.getBundleMessage("cadastroUsuario.label.alteraUsuario");
		}
	}

	public Funcao getFuncao() {
		if (funcao == null)	setFuncao(new Funcao());
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public List<Funcao> getFuncaoList() {
		return funcaoList;
	}

	public void setFuncaoList(List<Funcao> list) {
		this.funcaoList = list;
	}

	private IFuncaoService getFuncaoService() {
		return this.funcaoService;
	}

	private void setFuncaoService(IFuncaoService FuncaoService) {
		this.funcaoService = FuncaoService;
	}

}
