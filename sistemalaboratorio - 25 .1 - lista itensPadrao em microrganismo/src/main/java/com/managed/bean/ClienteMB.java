package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.model.Cliente;
import com.model.Usuario;
import com.service.IClienteService;
import com.service.ISolicitacaoService;
import com.service.IUsuarioService;
import com.util.Messages;
import com.util.enums.Acoes;
import com.util.enums.Estados;
import com.util.enums.RetornoFaces;

public class ClienteMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Usuario contatoPrincipal;

	private List<Cliente> clienteList;
	private List<Cliente> clienteListFiltered;
	
	private List<Estados> estadosList;

	private IClienteService clienteService;
	private IUsuarioService usuarioService;
	private ISolicitacaoService solicitacaoService;

	public ClienteMB(IClienteService clienteService, IUsuarioService usuarioService, ISolicitacaoService solicitacaoService) {
		setClienteService(clienteService);
		setUsuarioService(usuarioService);
		setSolicitacaoService(solicitacaoService);
		inicializarCampos();
	}

	public String inicializar(){
		inicializarCampos();
		return "/pages/adm/listaclientes";
	}

	public String criar() {
		limpar();
		return super.criar();
	}

		private void inicializarCampos() {
		limpar();
		refreshLista();
	}

	public String salvar() {
	
		// se o cliente existir atualiza
		if (!getSessaoDoUsuario().isCriar()) {
	
			getClienteService().atualizar(getCliente());
	
		} else {
			// valida se existe p/adicionar
			if (!getClienteService().isExisteCliente(getCliente())) {
	
				getClienteService().adicionar(getCliente());
	
			} else {
				Messages.addErrorBundleMessage("cadastrocliente.erro.existente");
				return null;
			}
		}
	
		return voltar();
	}

	public String deletar() {
		try {
			getClienteService().delete(getCliente());
		} catch (Exception e) {
			Messages.addErrorBundleMessage(
					"cadastroCliente.operacao.erro.naofoipossivelapagar",
					getCliente().getNome());
		}
		refreshLista();
		return null;
	}

	public String voltar() {
		setContatoPrincipal(null);
		refreshLista();
		return super.listar();
	}

	public void limpar() {
		setCliente(new Cliente());
		setContatoPrincipal(null);		
	}

	private void refreshLista() {
		setClienteList(new ArrayList<Cliente>());
		getClienteList().addAll(getClienteService().getAll());

		for(Cliente cliente : getClienteList()){
			cliente.setSolicitacoes(new HashSet(solicitacaoService.getAllByCliente(cliente)));
			cliente.setUsuarios(new HashSet(usuarioService.buscaPorCliente(cliente)));
		}
	}

	public boolean existeContatoPrincipal() {
		boolean result = false;
		try{
			 result = this.getContatoPrincipal()==null ? false : true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	public Estados[] getEstadosList() {
		return Estados.values();
	}

	public String getLabelCabecalho() {
		if (getCliente().getIdCliente() == 0) {
			return Messages
					.getBundleMessage("cliente.form.cabecalho.cadastrar");
		} else {
			return Messages
					.getBundleMessage("cliente.form.cabecalho.alterar");
		}
	}

	public Cliente getCliente() {
		if (cliente == null) setCliente(new Cliente());
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getContatoPrincipal() {
		if (contatoPrincipal == null && this.cliente != null) 
			setContatoPrincipal(getUsuarioService()
					.buscaPorClienteContatoPrincipal(getCliente()));
		return contatoPrincipal;
	}

	public void setContatoPrincipal(Usuario contatoPrincipal) {
		this.contatoPrincipal = contatoPrincipal;
	}

	public List<Cliente> getClienteList() {
		return clienteList;
	}

	public void setClienteList(List<Cliente> clienteList) {
		this.clienteList = clienteList;
	}

	public List<Cliente> getClienteListFiltered() {
		return clienteListFiltered;
	}

	public void setClienteListFiltered(List<Cliente> clienteListFiltered) {
		this.clienteListFiltered = clienteListFiltered;
	}

	private IClienteService getClienteService() {
		return clienteService;
	}

	private void setClienteService(IClienteService clienteService) {
		this.clienteService = clienteService;
	}

	private IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	private void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public ISolicitacaoService getSolicitacaoService() {
		return solicitacaoService;
	}

	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	public void tipoPessoaChange(){
		//** Se "Tipo pessoa" for "Juridica(0)" e "Inscricao" for "Isento(2)" : setar "Inscricao" como "Municipal(0)"
		if(getCliente().getTipopessoa() != null && getCliente().getTipopessoa().equals("0")){
			if (cliente.getTipoinscricao()!=null && cliente.getTipoinscricao().equals("2")){
				cliente.setTipoinscricao("0");
			}
		}//** Se "Tipo pessoa" for "Fisica(1)" : setar "Inscricao" como "Isento(2)"
		else if(getCliente().getTipopessoa() != null && getCliente().getTipopessoa().equals("1")){
			cliente.setTipoinscricao("2");
		}
	}

}
