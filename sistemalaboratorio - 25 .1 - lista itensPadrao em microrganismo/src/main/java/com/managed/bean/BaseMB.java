package com.managed.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.web.jsf.FacesContextUtils;

import com.util.JsfUtil;
import com.util.enums.Acoes;
import com.util.enums.RetornoFaces;

public abstract class BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	public String getTimeZone() {
		return Calendar.getInstance().getTimeZone().getID();
	}

	protected void error(final String message) {
		FacesContext.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message,
						message));
	}

	protected void info(final String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}

	@SuppressWarnings("unchecked")
	protected <T> T getSpringBean(final String nome) {
		return (T) FacesContextUtils.getRequiredWebApplicationContext(
				FacesContext.getCurrentInstance()).getBean(nome);
	}

	protected ISessaoDoUsuario getSessaoDoUsuario() {
		ISessaoDoUsuario sdu = (ISessaoDoUsuario)JsfUtil.getSessionValue("SessaoDoUsuario");
		if (sdu == null) {
			sdu = new SessaoDoUsuario();
			JsfUtil.setSessionValue("SessaoDoUsuario", sdu);
		}
		return sdu;
	}

	
	public String criar() {	
		getSessaoDoUsuario().setAcaoCorrente(Acoes.CRIAR.toString());
		return RetornoFaces.FORMULARIO.toString();
	}

	public String editar() {
		getSessaoDoUsuario().setAcaoCorrente(Acoes.EDITAR.toString());
		return RetornoFaces.FORMULARIO.toString();
	}

	public String visualizar() {
		getSessaoDoUsuario().setAcaoCorrente(Acoes.VISUALIZAR.toString());
		return RetornoFaces.FORMULARIO.toString();
	}

	public String listar() {
		getSessaoDoUsuario().setAcaoCorrente(Acoes.LISTAR.toString());
		return RetornoFaces.LISTAR.toString();
	}
	
}
