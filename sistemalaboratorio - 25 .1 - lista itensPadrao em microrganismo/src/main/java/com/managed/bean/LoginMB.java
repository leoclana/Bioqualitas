package com.managed.bean;

import java.io.Serializable;
import java.util.HashSet;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.model.Funcao;
import com.model.Perfil;
import com.model.Usuario;
import com.service.IPerfilService;
import com.service.IUsuarioService;
import com.util.Messages;
import com.util.enums.RetornoFaces;

public class LoginMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String BIOQUALITAS_USER = "bioqualitas";
	private Usuario usuario;

	private IUsuarioService usuarioService;
	private IPerfilService perfilService;

	public LoginMB(IUsuarioService uS, IPerfilService pS) {
		setUsuarioService(uS);
		setPerfilService(pS);

		setUsuario(new Usuario());
	}

	public String efetuaLogin() {

		try {

			if (getUsuario().getEmail() == null
					|| getUsuario().getSenha() == null) {
				Messages.addErrorBundleMessage("login.userEpassw.required");
				return null;
			}

			Usuario u = getUsuarioService().buscaPorLogin(getUsuario().getEmail(),getUsuario().getSenha());
			if (u != null) {
				preparaSessaoDoUsuario(u);
			} else {
				Messages.addErrorBundleMessage("login.invalido");
				return null;
			}

		} catch (Exception e) {

			// TODO Gravar o log do erro com a descricao e.printStackTrace()
			// informar usuario de erro para contactar os amds
			
			e.printStackTrace();

			return null;

		}

		return RetornoFaces.HOME.toString();
	}

	public String logOut() {
	
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
	
		context.invalidateSession();
	
		getSessaoDoUsuario().limpar();
	
		setUsuario(new Usuario());
	
		return "/pages/login?faces-redirect=true";
		
	}

	private void preparaSessaoDoUsuario(Usuario u) {
		ISessaoDoUsuario sdu = getSessaoDoUsuario();
		
		if(BIOQUALITAS_USER.equalsIgnoreCase(u.getCliente().getNome()))
			sdu.setUsuarioBioqualitas(true);
		
		sdu.setUsuario(getUsuarioService().getByIdComLista(u));

		sdu.setPerfis(new HashSet<String>());
		sdu.setFuncoes(new HashSet<String>());
		for (Perfil p : sdu.getUsuario().getPerfis()) {
			if (p.getAtivo()) {
				sdu.getPerfis().add(p.toString());

				p = getPerfilService().getByIdComLista(p);
				for (Funcao f : p.getFuncoes())
					if (f.getAtivo())
						sdu.getFuncoes().add(f.toString());
			}
		}
		
		//JsfUtil.setSessionValue("SessaoDoUsuario", sdu);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private IUsuarioService getUsuarioService() {
		return this.usuarioService;
	}

	private void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	private IPerfilService getPerfilService() {
		return perfilService;
	}

	private void setPerfilService(IPerfilService perfilService) {
		this.perfilService = perfilService;
	}

}
