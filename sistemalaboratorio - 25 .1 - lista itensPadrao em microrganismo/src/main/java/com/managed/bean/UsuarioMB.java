package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import com.model.Cliente;
import com.model.Perfil;
import com.model.Usuario;
import com.service.IClienteService;
import com.service.IPerfilService;
import com.service.IUsuarioService;
import com.util.Criptografia;
import com.util.Messages;
import com.util.Util;
import com.util.enums.Acoes;
import com.util.enums.Perfis;
import com.util.enums.RetornoFaces;
import com.util.view.SelectManyDataModel;
import com.util.view.SelectOneDataModel;

public class UsuarioMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private String senhaNova;
	private String confirmaSenhaNova;

	private List<Usuario> userList;
	private List<Usuario> userListFiltered;

	private SelectOneDataModel<Cliente> comboCliente;
	private SelectManyDataModel<Perfil> manyCheckBoxPerfil;

	private IUsuarioService usuarioService;
	private IClienteService clienteService;
	private IPerfilService perfilService;

	private Boolean clienteProprietario;

	public UsuarioMB(IUsuarioService usuarioService, IClienteService clienteService, IPerfilService perfilService) {
		setUsuarioService(usuarioService);
		setClienteService(clienteService);
		setPerfilService(perfilService);

		refreshLista();
	}

	public String criar() {
		setUsuario(new Usuario());

		//setComboCliente(SelectOneDataModel.criaComObjetoSelecionado(getClienteService().getAll(), null));
		tratarCliente();

		//setManyCheckBoxPerfil(new SelectManyDataModel<Perfil>((ArrayList<Perfil>) getPerfilService().getAll()));
		trataPerfil();

		return super.criar();
	}

	public String editar() {
		preencheTela();
		return super.editar();
	}

	public String visualizar() {
		preencheTela();
		return super.visualizar();
	}

	private void preencheTela() {
		if (usuario.getIdUsuario() != 0) {
//			if(getSessaoDoUsuario().isAdministrador()){
//				setComboCliente(SelectOneDataModel.criaComObjetoSelecionado(getClienteService().getAll(), getUsuario().getCliente()));
//			}else{
//				List<Cliente> clientes = new ArrayList<Cliente>();
//				clientes.add(getUsuario().getCliente());
//				setComboCliente(SelectOneDataModel.criaComObjetoSelecionado(clientes, getUsuario().getCliente()));
//			}
			tratarCliente();

			setUsuario(usuarioService.getByIdComLista(usuario));
//			if (!getClienteProprietario()) {
//				setPerfilVisualizador();
//			} else {
//				setManyCheckBoxPerfil(new SelectManyDataModel<Perfil>(
//						(ArrayList<Perfil>) getPerfilService().getAll(),
//						Util.getNomes(getUsuario().getPerfis().toArray())));
//			}
			trataPerfil();
		}
	}

	private void tratarCliente(){
		if(getSessaoDoUsuario().isAdministrador()){
			setComboCliente(SelectOneDataModel.criaComObjetoSelecionado(getClienteService().getAll(), getUsuario().getCliente()));
		}else{
			List<Cliente> clientes = new ArrayList<Cliente>();
			clientes.add(getSessaoDoUsuario().getUsuario().getCliente());
			setComboCliente(SelectOneDataModel.criaComObjetoSelecionado(clientes, getUsuario().getCliente()));
		}
	}

	public String listar() {
		refreshLista();
		return super.listar();
	}

	private void refreshLista() {
//		setUserList(new ArrayList<Usuario>());
//
//		//**Busca somente usuarios da propria empresa, se for "ContatoPrincipal" e nao for Adm
//		if(getSessaoDoUsuario().getUsuario().getContatoPrincipal() && !getSessaoDoUsuario().isAdministrador()){
//			getUserList().addAll(getUsuarioService().buscaPorCliente(getSessaoDoUsuario().getUsuario().getCliente()));
//		}else {
//			getUserList().addAll(getUsuarioService().getAll());
//		}
	}

	public String alterardadoscadastrais() {
		getSessaoDoUsuario().setAcaoCorrente(
				Acoes.ALTERARDADOSCADASTRAIS.toString());

		setUsuario(getSessaoDoUsuario().getUsuario());
		preencheTela();

		return RetornoFaces.ALTERARDADOSCADASTRAIS.toString();
	}

	public String alterarSenha() {
		getSessaoDoUsuario().setAcaoCorrente(Acoes.ALTERARSENHA.toString());

		setUsuario(getSessaoDoUsuario().getUsuario());

		return RetornoFaces.ALTERARSENHA.toString();
	}

	public String salvar() {

		if (!preparaParaPersistencia())
			return null;

		if (!persistir())
			return null;

		return voltar();

	}

	private boolean preparaParaPersistencia() {

		if (getUsuario().getContatoPrincipal()) {
			if (getUsuarioService().clientePossuiContatoPrincipal(getUsuario().getCliente())) {
				Usuario contatoPrincipal = getUsuarioService().buscaPorClienteContatoPrincipal(getUsuario().getCliente());
				if (!contatoPrincipal.getIdUsuario().equals(getUsuario().getIdUsuario())) {
					Messages.addErrorBundleMessage(
							"cadastroUsuario.operacao.erro.contatoprincipalexistente",
							getUsuario().getCliente().getNome(),
							contatoPrincipal.getNome());
					getUsuario().setContatoPrincipal(false);
					return false;
				}
			}
		}

		// se for atualizar senha tento atualizar a senha do usuario antes de
		// persistir
		if (getSessaoDoUsuario().isAlterarSenha()) {
			if (!altualizarSenha())
				return false;

			// se for criar ou editar atualizo a lista de perfis no usuario com
			// dados da tela e o cliente com dado da combo cliente
		} else if (getSessaoDoUsuario().isCriar()
				|| getSessaoDoUsuario().isEditar()) {
			getUsuario().setPerfis(
					new ArrayList<Perfil>(getManyCheckBoxPerfil()
							.getObjetosSelecionados()));
		}
		//** Se a edicao de usuario for por um Usuaruo-ContatoPrincipal da enpresa Cliente retornar
		//** seta usuario.cliente com o Cliente do Usuario-Logado :
		if(getSessaoDoUsuario().isEditar() &&
		   getSessaoDoUsuario().getUsuario().getContatoPrincipal() &&
		   !getSessaoDoUsuario().isAdministrador()
		){
			getUsuario().setCliente(getSessaoDoUsuario().getUsuario().getCliente());
		}
		return true;
	}

	private boolean altualizarSenha() {

		Usuario userLogado = getSessaoDoUsuario().getUsuario();

		if (getUsuario().getSenha() == "") {
			Messages.addErrorBundleMessage("login.passw.atual");
			return false;
		} else if (getSenhaNova() == "") {
			Messages.addErrorBundleMessage("login.passw.nova");
			return false;
		} else if (getConfirmaSenhaNova() == "") {
			Messages.addErrorBundleMessage("login.passw.confirm");
			return false;
		} else if (!getSenhaNova().equals(getConfirmaSenhaNova())) {
			Messages.addErrorBundleMessage("login.passw.confirmError");
			return false;
		} else if (!userLogado.getSenha().equals(
				Criptografia.encodePassword(getUsuario().getSenha()))) {
			Messages.addErrorBundleMessage("login.passw.atualInvalida");
			return false;
		} else {
			userLogado.setSenha(Criptografia.encodePassword(getSenhaNova()));
			setUsuario(userLogado);
			return true;
		}
	}

	private boolean persistir() {
		// se nao é criar, atualiza o usuario
		if (!getSessaoDoUsuario().isCriar()) {

			getUsuarioService().atualizar(getUsuario());

			// se é o usuario que esta sendo editado é o mesmoq eu está
			// logado,
			// entao atualiza o usuario logado
			if (getSessaoDoUsuario().getUsuario().equals(getUsuario())) {
				getSessaoDoUsuario().setUsuario(
						getUsuarioService().getByIdComLista(getUsuario()));
			}

		} else {
			// valida se existe p/adicionar
			if (!getUsuarioService().isExisteUsuario(getUsuario())) {

				getUsuarioService().adicionar(getUsuario());
				refreshLista();

			} else {

				Messages.addErrorBundleMessage("cadastroUsuario.existente");
				return false;
			}
		}
		return true;
	}

	public String deletar() {
		try {
			getUsuarioService().delete(getUsuario());
		} catch (Exception e) {
			Messages.addErrorBundleMessage(
					"cadastroUsuario.operacao.erro.naofoipossivelapagar",
					usuario.getNome());
		}
		refreshLista();
		return null;
	}

	public String voltar() {
		if (getSessaoDoUsuario().isAlterarDadosCadastrais()
				|| getSessaoDoUsuario().isAlterarSenha()) {

			getSessaoDoUsuario().setAcaoCorrente("");
			return RetornoFaces.HOME.toString();

		} else {
			return listar();
		}

	}

	public void resetSenha() {
		getUsuarioService().resetSenha(getUsuario());
		Messages.addErrorBundleMessage("cadastroUsuario.botao.reset.mensagem");
	}

	/*
	 * Action handlers
	 */

	public void trataPerfil() {

//		if (!getClienteProprietario()) {
//			setPerfilVisualizador();
//		}

		if (!getClienteProprietario() || getSessaoDoUsuario().getUsuario().getCliente().getIdCliente() != 1) {
			setPerfilVisualizador();
		} else {
			setManyCheckBoxPerfil(new SelectManyDataModel<Perfil>(
					(ArrayList<Perfil>) getPerfilService().getAll(),
					Util.getNomes(getUsuario().getPerfis()!=null ? getUsuario().getPerfis().toArray() : new String[]{} )));
		}
	}

	private void setPerfilVisualizador() {
		//Perfil perfil = new Perfil();
		//perfil.setNome(Perfis.VISUALIZADOR.toString());
		Perfil perfil = perfilService.recuperarPorNome(Perfis.VISUALIZADOR.toString());

		ArrayList<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);
		String[] perfilVisualizadorSelecionado = new String[1];
		perfilVisualizadorSelecionado[0] = Perfis.VISUALIZADOR.toString();
		setManyCheckBoxPerfil(new SelectManyDataModel<Perfil>(
				(ArrayList<Perfil>) perfis, perfilVisualizadorSelecionado));
		getUsuario().setPerfis(perfis);
	}

	public String getLabelCabecalho() {
		if (getSessaoDoUsuario().isCriar()) {
			return Messages
					.getBundleMessage("usuario.form.cabecalho.cadastrar");
		} else {
			return Messages.getBundleMessage("usuario.form.cabecalho.alterar");
		}
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			setUsuario(new Usuario());
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getConfirmaSenhaNova() {
		return confirmaSenhaNova;
	}

	public void setConfirmaSenhaNova(String confirmaSenhaNova) {
		this.confirmaSenhaNova = confirmaSenhaNova;
	}

	public List<Usuario> getUserList() {

		setUserList(new ArrayList<Usuario>());

		//**Busca somente usuarios da propria empresa, se for "ContatoPrincipal" e nao for Adm
		if(getSessaoDoUsuario().getUsuario().getContatoPrincipal() && !getSessaoDoUsuario().isAdministrador()){
			userList.addAll(getUsuarioService().buscaPorCliente(getSessaoDoUsuario().getUsuario().getCliente()));
		}else {
			userList.addAll(getUsuarioService().getAll());
		}

		return userList;
	}

	public void setUserList(List<Usuario> userList) {
		this.userList = userList;
	}

	public List<Usuario> getUserListFiltered() {
		return userListFiltered;
	}

	public void setUserListFiltered(List<Usuario> userListFiltered) {
		this.userListFiltered = userListFiltered;
	}

	public SelectOneDataModel<Cliente> getComboCliente() {
		if (comboCliente == null) {
			setComboCliente(new SelectOneDataModel<Cliente>(getClienteService().getAll()));

		}
		return comboCliente;
	}

	public void setComboCliente(SelectOneDataModel<Cliente> comboCliente) {
		this.comboCliente = comboCliente;
	}

	public SelectManyDataModel<Perfil> getManyCheckBoxPerfil() {
		if (manyCheckBoxPerfil == null) {

			if (!getClienteProprietario()) {

				//Perfil perfil = new Perfil();
				//perfil.setNome(Perfis.VISUALIZADOR.toString());
				Perfil perfil = perfilService.recuperarPorNome(Perfis.VISUALIZADOR.toString());

				ArrayList<Perfil> perfis = new ArrayList<Perfil>();
				perfis.add(perfil);
				String[] perfilVisualizadorSelecionado = new String[1];
				perfilVisualizadorSelecionado[0] = Perfis.VISUALIZADOR.toString();
				setManyCheckBoxPerfil(new SelectManyDataModel<Perfil>(
						(ArrayList<Perfil>) perfis,
						perfilVisualizadorSelecionado));
				getUsuario().setPerfis(perfis);
			} else {
				setManyCheckBoxPerfil(new SelectManyDataModel<Perfil>((ArrayList<Perfil>) getPerfilService().getAll()));
			}
		}
		return manyCheckBoxPerfil;
	}

	public void setManyCheckBoxPerfil(SelectManyDataModel<Perfil> manyCheckBoxPerfil) {
		this.manyCheckBoxPerfil = manyCheckBoxPerfil;
	}

	public Boolean getClienteProprietario() {

		clienteProprietario = true;

		if (getUsuario().getCliente() != null) {
			// BUSCAR DAS CONFIGURACOES 'TECNICAS' O ID DO CLIENTE SELECIONADO
			if (getUsuario().getCliente().getIdCliente() != 1) {
				clienteProprietario = false;
			}
		}

		return clienteProprietario;
	}

	public void setClienteProprietario(Boolean clienteProprietario) {
		this.clienteProprietario = clienteProprietario;
	}

	private IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	private void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	private IClienteService getClienteService() {
		return clienteService;
	}

	private void setClienteService(IClienteService clienteService) {
		this.clienteService = clienteService;
	}

	private IPerfilService getPerfilService() {
		return perfilService;
	}

	private void setPerfilService(IPerfilService perfilService) {
		this.perfilService = perfilService;
	}

}
