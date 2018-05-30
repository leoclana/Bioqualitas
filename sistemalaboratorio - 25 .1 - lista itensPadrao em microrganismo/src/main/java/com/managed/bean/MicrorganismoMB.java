package com.managed.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.model.Grupo;
import com.model.ItemGrupo;
import com.model.ItemPadrao;
import com.model.Microrganismo;
import com.service.IGrupoService;
import com.service.IItemGrupoService;
import com.service.IItemPadraoService;
import com.service.IMicrorganismoService;
import com.service.IPadraoService;
import com.util.Messages;
import com.util.enums.Acoes;
import com.util.enums.RetornoFaces;
import com.util.enums.TipoAnalise;
import com.util.view.SelectOneDataModel;



public class MicrorganismoMB extends BaseMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ItemPadrao itemPadrao;
	
	private List<ItemPadrao> itemPadraoList;
	private List<ItemPadrao> itemPadraoListFiltered;

	private Microrganismo microrganismo;
	
	private SelectOneDataModel<Grupo> comboGrupo;
	private SelectOneDataModel<ItemGrupo> comboItemGrupo;
	
	private IGrupoService grupoService; 
	private IItemGrupoService itemGrupoService;
	private IPadraoService padraoService; 
	private IItemPadraoService itemPadraoService;
	private IMicrorganismoService microrganismoService;

	public MicrorganismoMB(IGrupoService grupoService,
			IItemGrupoService itemGrupoService, IPadraoService padraoService, IItemPadraoService itemPadraoService,
			IMicrorganismoService microrganismoService) {

		setGrupoService(grupoService);
		setItemGrupoService(itemGrupoService);
		setPadraoService(padraoService);
		setItemPadraoService(itemPadraoService);
		setMicrorganismoService(microrganismoService);
		
		inicializarCampos();
	}

	public String criar(){
		limpar();
		return super.criar();
	}
	
	public String editar(){
		preparaTela();
		return super.editar();
	}
	
	public String visualizar(){
		preparaTela();
		return super.visualizar();
	}
	
	private void preparaTela(){

		if (isTipoAnaliseAlimento()) {
			setComboGrupo(SelectOneDataModel.criaComObjetoSelecionado(getGrupoService().getAll(),
					getItemPadrao().getItemGrupo().getGrupo()));
			setComboItemGrupo(SelectOneDataModel.criaComObjetoSelecionado(getItemGrupoService().getAllByGrupo(getComboGrupo().getObjetoSelecionado()), 
					getItemPadrao().getItemGrupo()));
		}
		
		setMicrorganismo(getItemPadrao().getMicrorganismo());
	}

	public String listar() {
		refreshLista();
		return super.listar();
	}
	
	public String salvar() {
	
		// se o cliente existir atualiza
		if (!getSessaoDoUsuario().isCriar()) {
			getItemPadraoService().update(getItemPadrao());
	
		} else {
			// valida se existe p/adicionar
			if (!getItemPadraoService().isExiste(getItemPadrao())) {
				getItemPadraoService().add(getItemPadrao());
			} else {
				Messages.addErrorBundleMessage("microrganismo.erro.existente");
				return null;
			}
		}
	
		return listar();
	}

	public String deletar() {
		try {
			getItemPadraoService().delete(getItemPadrao());
		} catch (Exception e) {
			Messages.addErrorBundleMessage(
					"microrganismo.operacao.erro.naofoipossivelapagar",
					getItemPadrao().getMicrorganismo().getNome());
		}
		refreshLista();
		return null;
	}

	public void limpar() {
		setItemPadrao(new ItemPadrao());
		setComboGrupo(new SelectOneDataModel<Grupo>(getGrupoService().getAll()));
		setComboItemGrupo(new SelectOneDataModel<ItemGrupo>());
	
	}

	private void inicializarCampos() {
		limpar();		
		refreshLista();
	}

	private void refreshLista() {
		setItemPadraoList(new ArrayList<ItemPadrao>());
		getItemPadraoList().addAll(getItemPadraoService().getAll());
	}	

	public void onGrupoChange(){
		System.out.println("onGrupoChange+onGrupoChange+onGrupoChange+onGrupoChange+onGrupoChange+onGrupoChange");
		if (!getComboGrupo().getSelecao().isEmpty()){
			Grupo grupo = getComboGrupo().getObjetoSelecionado();
			//Grupo  grupo = (Grupo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("grupo");
			List<ItemGrupo> lista = getItemGrupoService().getAllByGrupo(grupo);
			setComboItemGrupo(SelectOneDataModel.criaComObjetoSelecionado(lista, null));
		}
	}

	public TipoAnalise[] getTipoAnaliseList() {
		return TipoAnalise.values();
	}

	public boolean isTipoAnaliseAlimento() {
		return ((getItemPadrao().getTipoAnalise()==null)||(getItemPadrao().getTipoAnalise()==TipoAnalise.ALIMENTO));		
	}

	public String getLabelCabecalho() {
		if (getItemPadrao().getIdItemPadrao() == 0) {
			return Messages
					.getBundleMessage("microrganismo.form.cabecalho.cadastrar");
		} else {
			return Messages
					.getBundleMessage("microrganismo.form.cabecalho.alterar");
		}
	}
	
	public ItemPadrao getItemPadrao() {
		if ( itemPadrao == null) setItemPadrao(new ItemPadrao());
		return itemPadrao;
	}

	public void setItemPadrao(ItemPadrao itemPadrao) {
		this.itemPadrao = itemPadrao;
	}

	public List<ItemPadrao> getItemPadraoList() {
		return itemPadraoList;
	}

	public void setItemPadraoList(List<ItemPadrao> itemPadraoList) {
		this.itemPadraoList = itemPadraoList;
	}

	public List<ItemPadrao> getItemPadraoListFiltered() {
		return itemPadraoListFiltered;
	}

	public void setItemPadraoListFiltered(List<ItemPadrao> itemPadraoListFiltered) {
		this.itemPadraoListFiltered = itemPadraoListFiltered;
	}

	public Microrganismo getMicrorganismo() {
		return microrganismo;
	}

	public void setMicrorganismo(Microrganismo microrganismo) {
		this.microrganismo = microrganismo;
	}

	public SelectOneDataModel<Grupo> getComboGrupo() {
		if (comboGrupo == null) {
			comboGrupo = new SelectOneDataModel<Grupo>(getGrupoService().getAll());
		}
		return comboGrupo;
	}

	public void setComboGrupo(SelectOneDataModel<Grupo> comboGrupo) {
		this.comboGrupo = comboGrupo;
	}

	public SelectOneDataModel<ItemGrupo> getComboItemGrupo() {
		if (comboItemGrupo == null) {
			comboItemGrupo = new SelectOneDataModel<ItemGrupo>();
		}
		return comboItemGrupo;
	}

	public void setComboItemGrupo(SelectOneDataModel<ItemGrupo> comboItemGrupo) {
		this.comboItemGrupo = comboItemGrupo;
	}

	private IGrupoService getGrupoService() {
		return grupoService;
	}

	private void setGrupoService(IGrupoService grupoService) {
		this.grupoService = grupoService;
	}

	private IItemGrupoService getItemGrupoService() {
		return itemGrupoService;
	}

	private void setItemGrupoService(IItemGrupoService itemGrupoService) {
		this.itemGrupoService = itemGrupoService;
	}

	private IPadraoService getPadraoService() {
		return padraoService;
	}

	private void setPadraoService(IPadraoService padraoService) {
		this.padraoService = padraoService;
	}

	private IItemPadraoService getItemPadraoService() {
		return itemPadraoService;
	}

	private void setItemPadraoService(IItemPadraoService itemPadraoService) {
		this.itemPadraoService = itemPadraoService;
	}

	private IMicrorganismoService getMicrorganismoService() {
		return microrganismoService;
	}

	private void setMicrorganismoService(IMicrorganismoService microrganismoService) {
		this.microrganismoService = microrganismoService;
	}
	
	
}
	