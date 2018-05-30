package com.filter;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.managed.bean.ISessaoDoUsuario;
import com.managed.bean.SessaoDoUsuario;
import com.util.JsfUtil;

public class AuthorizationListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	private static final String PAGES_CARGA_XHTML = "/pages/carga.xhtml";
	private static final String PAGES_ADM_ALTERADADOS = "/adm/formusuario.xhtml";
	private static final String PAGES_ADM_SENHA = "/adm/senha.xhtml";
	private static final String PAGES_LOGIN_XHTML = "/pages/login.xhtml";
	private static final String PAGES_CAD_CLIENTE = "/pages/adm/listaclientes.xhtml";
	private static final String PAGES_LIST_CLIENTE = "/pages/adm/formcliente.xhtml";
	private static final String PAGES_ADM = "/adm/";
	public static final String PAGES_LOGIN_FACES_REDIRECT_TRUE = "/pages/login?faces-redirect=true";

	private enum Resultado {
		PODEPASSAR, ACESSOINDEVIDO
	};

	public AuthorizationListener() {
	}
	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public void beforePhase(PhaseEvent event) {
	}

	public void afterPhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();
		
		NavigationHandler handler = facesContext.getApplication().getNavigationHandler();
		
		String currentPage = facesContext.getViewRoot().getViewId();

		//ISessaoDoUsuario sessaoDoUsuario = (ISessaoDoUsuario) FacesContextUtils.getRequiredWebApplicationContext(facesContext).getBean("SessaoDoUsuario");
		ISessaoDoUsuario sessaoDoUsuario = (ISessaoDoUsuario) JsfUtil.getSessionValue("SessaoDoUsuario");
		if (sessaoDoUsuario == null)
			sessaoDoUsuario = new SessaoDoUsuario();

		Resultado resultadoAutorizacao = autorizaAcesso(currentPage, sessaoDoUsuario);

		switch (resultadoAutorizacao) {
		case ACESSOINDEVIDO:

			handler.handleNavigation(facesContext, null, PAGES_LOGIN_FACES_REDIRECT_TRUE);
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			if (sessaoDoUsuario != null)
				sessaoDoUsuario.limpar();

			break;

		default:
			break;
		}

	}

	private Resultado autorizaAcesso(String currentPage, ISessaoDoUsuario sessaoDoUsuario) {
		
		
		boolean isCargaPage = (currentPage.toLowerCase().lastIndexOf(PAGES_CARGA_XHTML) > -1);
		boolean isLoginPage = (currentPage.toLowerCase().lastIndexOf(PAGES_LOGIN_XHTML) > -1);
		boolean isFormUsuario = (currentPage.toLowerCase().lastIndexOf( PAGES_ADM_ALTERADADOS) > -1);
		boolean isPageSenha = (currentPage.toLowerCase().lastIndexOf( PAGES_ADM_SENHA) > -1);
		boolean isCadCliente = (currentPage.toLowerCase().lastIndexOf( PAGES_CAD_CLIENTE) > -1);
		boolean isListCliente = (currentPage.toLowerCase().lastIndexOf( PAGES_LIST_CLIENTE) > -1);
		boolean isAdmPages = (currentPage.toLowerCase().contains(PAGES_ADM));

		if ((isCargaPage) ||
			(isFormUsuario && sessaoDoUsuario.isAlterarDadosCadastrais()) ||
			(isCadCliente && sessaoDoUsuario.isColetador()) ||
			(isListCliente && sessaoDoUsuario.isColetador()) ||
			(isPageSenha) && sessaoDoUsuario.isAlterarSenha()) {

			// se for a página de alterar dadso cadastrais ou troca senha a��o
			// corrente for correspondente... nao faz nada
			return Resultado.PODEPASSAR;

		} else if ((!isLoginPage && !sessaoDoUsuario.isUsuarioLogado()) ||
				//(isAdmPages && !sessaoDoUsuario.isAdministrador())) {
				( (isAdmPages && !sessaoDoUsuario.isAdministrador()) && (isAdmPages && !sessaoDoUsuario.getUsuario().getContatoPrincipal()) ) ) {
			// retorna acesso indevido;
			return Resultado.ACESSOINDEVIDO;
			
		} else {
			// se nao é indevido pode passar
			return Resultado.PODEPASSAR;
		}
		
	}

}
