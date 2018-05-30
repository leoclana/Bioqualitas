/*
 * SelectManyDataModel.java
 *
 * Created on 23 de Outubro de 2007, 23:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.util.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import javax.faces.model.SelectItem;

public class SelectManyDataModel<T> {
    
    private String[] selecao;
    
    private ArrayList<SelectItem> listaSelecao;
    
    private TreeMap tmModel = new TreeMap();
    
    public SelectManyDataModel(ArrayList<T> itens) {
        listaSelecao = new ArrayList<SelectItem>();
        
        for (Object elem : itens) {
            listaSelecao.add(new SelectItem(elem.toString(), elem.toString()));
            tmModel.put(elem.toString(), elem);
        }
    }
    
    public SelectManyDataModel(ArrayList<T> itens, final String[] selecaoInicial) {
        setListaSelecao(new ArrayList<SelectItem>());
        for (Object elem : itens) {
            getListaSelecao().add(new SelectItem(elem.toString(), elem.toString()));
            tmModel.put(elem.toString(), elem);
        }
        selecao = selecaoInicial;
    }
    
    public ArrayList<SelectItem> getListaSelecao() {
        return listaSelecao;
    }
    
    public void setListaSelecao(ArrayList<SelectItem> listaSelecao) {
        this.listaSelecao = listaSelecao;
    }
    
    public Set<T> getObjetosSelecionados() {
        if(selecao == null) return null;
        Set<T> conj = new HashSet<T>();
        for (int i = 0; i < selecao.length; i++) {
            conj.add((T) tmModel.get(selecao[i]));
        }
        return conj;
    }

    public String[] getSelecao() {
        return selecao;
    }

    public void setSelecao(String[] selecao) {
        this.selecao = selecao;
    }
    
}
