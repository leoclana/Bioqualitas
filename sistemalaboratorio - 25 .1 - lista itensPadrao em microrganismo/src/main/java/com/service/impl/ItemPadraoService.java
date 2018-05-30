package com.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.ItemGrupo;
//import com.model.Grupo;
//import com.model.ItemGrupo;
import com.model.ItemPadrao;
import com.service.IItemPadraoService;
import com.util.enums.AcoesLog;
import com.util.enums.TipoAnalise;

@Transactional(readOnly = true)
public class ItemPadraoService extends Service implements Serializable, IItemPadraoService {

    private static final long serialVersionUID = 1L;
    
    public ItemPadraoService () {
    }

    @Transactional(readOnly = false)
    public void add(ItemPadrao ip) {
        getDao().<ItemPadrao>adicionar(ip); 
        
        // verifica se existe o ID cadastrado
        

		logar(AcoesLog.adicionou, ip);
    }

    public boolean isExiste(ItemPadrao ip) {
        //Verifica se existe ItemPadrao ItemPadrao.getNome()
        if (getByNome(ip) != null )
            return true;
        else
            return false;
    }

    public ItemPadrao recuperar(Integer id) {
        return getDao().recuperar(ItemPadrao.class, id);
    }

    @Transactional(readOnly = false)
    public void delete(ItemPadrao ip) {
        getDao().deletar(ip);
        

		logar(AcoesLog.deletou, ip);
    }

    @Transactional(readOnly = false)
    public void update(ItemPadrao ip) {
        getDao().<ItemPadrao>atualizar(ip);
        

		logar(AcoesLog.atualizou, ip);
    }

    public ItemPadrao getById(ItemPadrao ip) {
        return getDao().recuperar(ip.getClass(), ip.getIdItemPadrao());
    }

    public ItemPadrao getByNome(ItemPadrao ip) {
    	ItemPadrao f = new ItemPadrao();
    	f.setPadrao(ip.getPadrao());
        List<ItemPadrao> resultado = null;

        try {
        	resultado = getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if ((resultado != null) && (!resultado.isEmpty()))
			return resultado.get(0);
		else
			return null;

    }

    public List<ItemPadrao> getAll() {
    	ItemPadrao f = new ItemPadrao();
        try {
			return getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
	public List<ItemPadrao> getAllByItemGrupo(ItemGrupo ig){
		ItemPadrao f = new ItemPadrao();
		f.setItemGrupo(ig);
        try {
			return getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;	
	}
    
	public List<ItemPadrao> getAllByTipoAnalise(TipoAnalise ta) {
		ItemPadrao f = new ItemPadrao();
		f.setTipoAnalise(ta);
        try {
			return getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
    
	public List<ItemPadrao> getAllByCodigoLegislacao(String cl) {
		ItemPadrao f = new ItemPadrao();
		f.setCodigoLegislacao(cl);
        try {
			return getDao().recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}

}
