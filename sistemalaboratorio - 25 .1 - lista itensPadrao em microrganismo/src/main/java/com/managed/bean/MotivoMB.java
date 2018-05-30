package com.managed.bean;

import java.io.Serializable;
import java.util.List;

import com.model.MotivoAnalise;
import com.service.IMotivoService;
import com.util.Messages;

public class MotivoMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private MotivoAnalise motivo;

	private List<MotivoAnalise> motivoList;

	private IMotivoService motivoService;

	public MotivoMB(IMotivoService fS) {
		setMotivoService(fS);

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
		getMotivoService().delete(getMotivo());
		refreshLista();
		return null;
	}
	
	private void refreshLista() {
		setMotivoList(getMotivoService().getAll());
	}

	public String salvar() {
	
		// se o funcao existir atualiza
		if (getMotivo().getIdMotivo() != 0) {
			getMotivoService().update(getMotivo());
			return listar();
			// valida se existe p/adicionar
		} else if (!getMotivoService().isExiste(getMotivo())) {
			getMotivoService().add(getMotivo());
			return listar();
		} else {
			Messages.addErrorBundleMessage("cadastroUsuario.existente");
			return null;
		}
	}

	public void limpar() {
		setMotivo(new MotivoAnalise());
	}

	public String getLabelCadastro() {
		if (getMotivo().getIdMotivo() == 0) {
			return Messages.getBundleMessage("cadastroUsuario.label.titulo");
		} else {
			return Messages
					.getBundleMessage("cadastroUsuario.label.alteraUsuario");
		}
	}

	public MotivoAnalise getMotivo() {
		if (motivo == null)	setMotivo(new MotivoAnalise());
		return motivo;
	}

	public void setMotivo(MotivoAnalise m) {
		this.motivo = m;
	}

	public List<MotivoAnalise> getMotivoList() {
		return motivoList;
	}

	public void setMotivoList(List<MotivoAnalise> list) {
		if (motivoList == null) motivoList = motivoService.getAll();
		this.motivoList = list;
	}

	private IMotivoService getMotivoService() {
		return this.motivoService;
	}

	private void setMotivoService(IMotivoService motivoService) {
		this.motivoService = motivoService;
	}

}
