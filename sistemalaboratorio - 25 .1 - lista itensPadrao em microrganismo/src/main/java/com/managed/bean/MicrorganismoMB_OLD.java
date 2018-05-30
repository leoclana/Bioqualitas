package com.managed.bean;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.model.Grupo;
import com.model.ItemGrupo;
import com.model.ItemPadrao;
import com.model.Microrganismo;
import com.service.IGrupoService;
import com.service.IItemGrupoService;
import com.service.IItemPadraoService;
import com.service.IMicrorganismoService;
import com.service.IPadraoService;
import com.service.impl.GrupoService;
import com.util.Messages;
import com.util.enums.Acoes;
import com.util.enums.RetornoFaces;
import com.util.enums.TipoAnalise;
import com.util.view.SelectOneDataModel;

public class MicrorganismoMB_OLD extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private ItemPadrao itemPadrao;
	
	private List<ItemPadrao> itemPadraoList;
	private List<ItemPadrao> itemPadraoListFiltered;
	
	private List<TipoAnalise> tipoAnaliseList;

	private Integer grupoId;
	private Integer itemGrupoId;
	private Microrganismo microrganismo;
	
	private boolean tipoAnaliseAlimento;
	
	private SelectOneDataModel<Integer> comboGrupo;
	private SelectOneDataModel<Integer> comboItemGrupo;
	
	private IGrupoService grupoService; 
	private IItemGrupoService itemGrupoService;
	private IPadraoService padraoService; 
	private IItemPadraoService itemPadraoService;
	private IMicrorganismoService microrganismoService;

	public MicrorganismoMB_OLD(IGrupoService grupoService,
			IItemGrupoService itemGrupoService, IPadraoService padraoService, IItemPadraoService itemPadraoService,
			IMicrorganismoService microrganismoService) {

		setGrupoService(grupoService);
		setItemGrupoService(itemGrupoService);
		setPadraoService(padraoService);
		setItemPadraoService(itemPadraoService);
		setMicrorganismoService(microrganismoService);
		
		inicializarCampos();
	}

	private void inicializarCampos() {
		limpar();		
		refreshLista();
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
	
		return voltar();
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

	public String voltar() {
		refreshLista();
		getSessaoDoUsuario().setAcaoCorrente(Acoes.LISTAR.toString());
		return RetornoFaces.LISTAR.toString();
	}

	public void limpar() {
		setItemPadrao(new ItemPadrao());
		
		//setComboGrupo(new SelectOneDataModel<Grupo>(getGrupoService().getAll(), " - ", "getNome", "getDescricao"));
		
		setComboGrupo(SelectOneDataModel.criaComIdDescricaoPersonalizado(getGrupoService().getAll(), "getIdGrupo", " - ", "getNome", "getDescricao"));
	
		if(this.getGrupoId()==null){
			//setComboItemGrupo(new SelectOneDataModel<ItemGrupo>(getItemGrupoService().getAll()));
			setComboItemGrupo(SelectOneDataModel.criaComIdDescricaoPersonalizado(getItemGrupoService().getAll(), "getIdItemGrupo", " - ", "getNome", "getDescricao"));
		}else{
			Grupo paramGrupo = new Grupo();
			paramGrupo.setIdGrupo(this.getGrupoId());
			paramGrupo = grupoService.getById(paramGrupo);
			setComboItemGrupo(SelectOneDataModel.criaComIdDescricaoPersonalizado(getItemGrupoService().getAllByGrupo(paramGrupo), "getIdItemGrupo", " - ", "getNome", "getDescricao"));
		}
	}

	public void onChangeTipoAnalise() {}
	 
//	public void onChangeGrupo(ValueChangeEvent e){
//		System.out.println("Grupo: " + this.getGrupoId().getNome());
//		System.out.println("New value:" + e.getNewValue().toString()); 
//		if (this.getGrupoId()!=null)
//			setComboItemGrupo(new SelectOneDataModel<ItemGrupo>(getItemGrupoService().getAllByGrupo(this.getGrupoId())));
//	}
	
	public void onChangeGrupo() { 
		try {
			System.out.println("---->>>> onChangeGrupo  ---------");
			if (this.getGrupoId()!=null)
				System.out.println("Grupo: " + this.getGrupoId());
				Grupo paramGrupo = new Grupo();
				System.out.println("1");
				paramGrupo.setIdGrupo(this.getGrupoId());
				System.out.println("2");
				paramGrupo = grupoService.getById(paramGrupo);
				System.out.println("3");
				setComboItemGrupo(SelectOneDataModel.criaComIdDescricaoPersonalizado(getItemGrupoService().getAllByGrupo(paramGrupo), "getIdGrupo", " - ", "getNome", "getDescricao"));
				System.out.println("4");
		}catch (Exception e){
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}

	}	
	
	public void atualizarLista(ActionEvent actionEvent){
		System.out.println("ATUALIZANDO LISTA");
		setItemPadraoList(new ArrayList<ItemPadrao>());
		ItemGrupo itemGrupo = new ItemGrupo();
		itemGrupo.setIdItemGrupo(this.getItemGrupoId());
		getItemPadraoList().addAll(getItemPadraoService().getAllByItemGrupo(itemGrupo));
	}

	private void refreshLista() {
		setItemPadraoList(new ArrayList<ItemPadrao>());
		getItemPadraoList().addAll(getItemPadraoService().getAll());
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

	public Integer getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(Integer grupoId) {
		this.grupoId = grupoId;
	}

	public Integer getItemGrupoId() {
		return itemGrupoId;
	}

	public void setItemGrupoId(Integer itemGrupoId) {
		this.itemGrupoId = itemGrupoId;
	}

	public ItemPadrao getItemPadrao() {
		if (itemPadrao == null) setItemPadrao(new ItemPadrao());
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

	public IGrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(IGrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public IItemGrupoService getItemGrupoService() {
		return itemGrupoService;
	}

	public void setItemGrupoService(IItemGrupoService itemGrupoService) {
		this.itemGrupoService = itemGrupoService;
	}

	public IPadraoService getPadraoService() {
		return padraoService;
	}

	public void setPadraoService(IPadraoService padraoService) {
		this.padraoService = padraoService;
	}

	public IItemPadraoService getItemPadraoService() {
		return itemPadraoService;
	}

	public void setItemPadraoService(IItemPadraoService itemPadraoService) {
		this.itemPadraoService = itemPadraoService;
	}

	public IMicrorganismoService getMicrorganismoService() {
		return microrganismoService;
	}

	public void setMicrorganismoService(IMicrorganismoService microrganismoService) {
		this.microrganismoService = microrganismoService;
	}

	public TipoAnalise[] getTipoAnaliseList() {
		return TipoAnalise.values();
	}

	public SelectOneDataModel<Integer> getComboGrupo() {
		return comboGrupo;
	}

	public void setComboGrupo(SelectOneDataModel<Integer> comboGrupo) {
		this.comboGrupo = comboGrupo;
	}

	public SelectOneDataModel<Integer> getComboItemGrupo() {
		return comboItemGrupo;
	}

	public void setComboItemGrupo(SelectOneDataModel<Integer> comboItemGrupo) {
		this.comboItemGrupo = comboItemGrupo;
	}

	public boolean isTipoAnaliseAlimento() {
		
		if (itemPadrao.getTipoAnalise()==null) {
			this.tipoAnaliseAlimento = true;
			return true;
		}
		
		if(this.itemPadrao.getTipoAnalise()==TipoAnalise.ALIMENTO) {
			this.tipoAnaliseAlimento = true;
			return true;
		}
		else {
			this.tipoAnaliseAlimento = false;
			return false;
		}
				
	}

	public void setTipoAnaliseAlimento(boolean tipoAnaliseAlimento) {
		this.tipoAnaliseAlimento = tipoAnaliseAlimento;
	}

	public Microrganismo getMicrorganismo() {
		return microrganismo;
	}

	public void setMicrorganismo(Microrganismo microrganismo) {
		this.microrganismo = microrganismo;
	}
	
	
}

