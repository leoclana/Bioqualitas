package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.model.Notificacao;
import com.model.Usuario;
import com.service.INotificacaoService;
import com.util.enums.Acoes;
import com.util.enums.RetornoFaces;

public class NotificacaoMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado;
	private Notificacao notificacao;
	private INotificacaoService notificacaoService;

	private List<Notificacao> notificacaoList;

	public NotificacaoMB(INotificacaoService notificacaoService) {
		setNotificacaoService(notificacaoService);
		setUsuarioLogado(getSessaoDoUsuario().getUsuario());
		inicializarCampos();
	}

	public String voltar() {
		getSessaoDoUsuario().setAcaoCorrente("");
		return RetornoFaces.HOME.toString();
	}

	private void limpar() {
		setNotificacao(new Notificacao());
	}

	private void inicializarCampos() {
		limpar();
		refreshLista();
	}

	private void refreshLista() {
		setNotificacaoList(new ArrayList<Notificacao>());
		getNotificacaoList().addAll(getNotificacaoService().getAll(getUsuarioLogado()));		
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public INotificacaoService getNotificacaoService() {
		return notificacaoService;
	}

	public void setNotificacaoService(INotificacaoService notificacaoService) {
		this.notificacaoService = notificacaoService;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Notificacao> getNotificacaoList() {
		return notificacaoList;
	}

	public void setNotificacaoList(List<Notificacao> notificacaoList) {
		this.notificacaoList = notificacaoList;
	}


}
