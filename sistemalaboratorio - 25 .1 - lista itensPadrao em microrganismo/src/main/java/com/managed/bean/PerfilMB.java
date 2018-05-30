package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.model.Funcao;
import com.model.Perfil;
import com.model.Usuario;
import com.service.IFuncaoService;
import com.service.IPerfilService;
import com.util.Messages;
import com.util.Util;
import com.util.enums.Funcoes;
import com.util.enums.RetornoFaces;
import com.util.view.SelectManyDataModel;
import com.util.view.SelectOneDataModel;

public class PerfilMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Perfil perfil;

	private List<Perfil> perfilList;

	private SelectManyDataModel<Funcao> manyCheckBoxFuncoes;
	
	private IPerfilService perfilService;
	private IFuncaoService funcaoService;


	public PerfilMB(IPerfilService perfilService, IFuncaoService funcaoService) {
		setPerfilService(perfilService);
		setFuncaoService(funcaoService);
		
		refreshLista();
	}
	
	public String criar() {
		limpar();
		setManyCheckBoxFuncoes(new SelectManyDataModel<Funcao>((ArrayList<Funcao>) getFuncaoService().getAll()));
	
		return super.criar();
	}
	
	public String editar(){
		setPerfil(getPerfilService().getByIdComLista(perfil));
		setManyCheckBoxFuncoes(new SelectManyDataModel<Funcao>((ArrayList<Funcao>) getFuncaoService().getAll(),
				Util.getNomes(getPerfil().getFuncoes().toArray())));
		
		return super.editar();
	}

	public String deletar(Perfil perfil) {
		getPerfilService().delete(perfil);
		
		refreshLista();
		return null;
	}

	public String listar(){
		refreshLista();
		return super.listar();
	}
	
	private void refreshLista() {
		setPerfilList(new ArrayList<Perfil>());
		getPerfilList().addAll(getPerfilService().getAll());
	}

	public String salvar() {
		
		getPerfil().setFuncoes(new ArrayList<Funcao>(getManyCheckBoxFuncoes().getObjetosSelecionados()));
		
		// se o perfil existir atualiza
		if (getPerfil().getIdPerfil() != 0) {
			getPerfilService().update(getPerfil());
			return listar();
			// valida se existe p/adicionar
		} else if (!getPerfilService().isExiste(getPerfil())) {
			getPerfilService().add(getPerfil());
			return listar();
		} else {
			Messages.addErrorBundleMessage("cadastroUsuario.existente");
			return null;
		}
	}

	public void limpar() {
		setPerfil(new Perfil());
	}

	public String getLabelCadastro() {
		if (getPerfil().getIdPerfil() == 0) {
			return Messages.getBundleMessage("cadastroUsuario.label.titulo");
		} else {
			return Messages
					.getBundleMessage("cadastroUsuario.label.alteraUsuario");
		}
	}

	public Perfil getPerfil() {
		if (perfil == null) setPerfil(new Perfil());
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfilList() {
		return perfilList;
	}

	public void setPerfilList(List<Perfil> PerfilList) {
		this.perfilList = PerfilList;
	}

	public SelectManyDataModel<Funcao> getManyCheckBoxFuncoes() {	
		if (manyCheckBoxFuncoes == null) {
			setManyCheckBoxFuncoes(new SelectManyDataModel<Funcao>((ArrayList<Funcao>) getFuncaoService().getAll()));
		}
		return manyCheckBoxFuncoes;
	}

	public void setManyCheckBoxFuncoes(SelectManyDataModel<Funcao> manyCheckBoxFuncoes) {
		this.manyCheckBoxFuncoes = manyCheckBoxFuncoes;
	}

	private IPerfilService getPerfilService() {
		return this.perfilService;
	}

	private void setPerfilService(IPerfilService PerfilService) {
		this.perfilService = PerfilService;
	}

	private IFuncaoService getFuncaoService() {
		return funcaoService;
	}

	private void setFuncaoService(IFuncaoService funcaoService) {
		this.funcaoService = funcaoService;
	}
}
