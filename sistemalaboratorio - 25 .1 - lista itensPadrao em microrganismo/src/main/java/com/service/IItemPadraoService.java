package com.service;

import java.util.List;

import com.model.ItemGrupo;
import com.model.ItemPadrao;
import com.util.enums.TipoAnalise;

public interface IItemPadraoService extends IService{

	public abstract void add(ItemPadrao ItemPadrao);

	public abstract boolean isExiste(ItemPadrao ItemPadrao);

	public abstract ItemPadrao recuperar(Integer id);

	public abstract void delete(ItemPadrao ItemPadrao);

	public abstract void update(ItemPadrao ItemPadrao);

	public abstract ItemPadrao getById(ItemPadrao ItemPadrao);

	public abstract ItemPadrao getByNome(ItemPadrao ItemPadrao);

	public abstract List<ItemPadrao> getAll();
	
	public abstract List<ItemPadrao> getAllByItemGrupo(ItemGrupo itemGrupo);
	
	public abstract List<ItemPadrao> getAllByTipoAnalise(TipoAnalise tipoAnalise);
	
	public abstract List<ItemPadrao> getAllByCodigoLegislacao(String codigoLegislacao);
	
	
	

}