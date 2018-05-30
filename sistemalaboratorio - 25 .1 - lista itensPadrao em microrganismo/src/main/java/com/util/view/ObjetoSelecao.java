package com.util.view;

import java.util.ArrayList;
import java.util.List;

public class ObjetoSelecao<T> {

    private final Class<?> tipo;
    
//    private static ObjetoSelecao interno; 
    
    private T objeto;
    
    private boolean selecionado = false;
    
    public ObjetoSelecao(Class<T> tipo, T obj) {
        this.tipo = tipo;
        objeto = obj;
    }
    
    public ObjetoSelecao(T obj) {
        this.tipo = obj.getClass();
        objeto = obj;
    }
    
    public ObjetoSelecao(Class<T> tipo) {
        this.tipo = tipo;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public T getObjeto() {
        return objeto;
    }

    public void setObjeto(T objeto) {
        this.objeto = objeto;
    }
    /**
     * 
     * @param List<T>
     * @return uma lista de ObjetoSelecao do tipo T  a partir de uma lista qualquer
     */
    public static <T> List<ObjetoSelecao<T>> getListaSelecao(List<T> listaSelecao){
        final List<ObjetoSelecao<T>> lista = new ArrayList(listaSelecao.size());
        for (T obj : listaSelecao) {
            lista.add(new ObjetoSelecao(obj.getClass(), obj));
        }
        return lista;
    }
    
    /**
     * 
     * @param List de ObjetoSelecao tipo T
     * @return retorna um (List de ObjetoSelecao tipo T) com os selecionados
     */
    public static <T> List<ObjetoSelecao<T>> getListaSelecaoSelecionados(List<ObjetoSelecao<T>> listaSelecao){
        final List<ObjetoSelecao<T>> lista = new ArrayList(listaSelecao.size());
        for (ObjetoSelecao obj : listaSelecao) {
            if(obj.selecionado) lista.add(obj);
        }
        return lista;
    }
    
    /**
     * 
     * @param List de ObjetoSelecao tipo T 
     * @return retorna um (List de T) dos objetos selecionados
     */
    public static <T> List<T> getListaObjetosSelecionados(List<ObjetoSelecao<T>> listaSelecao){
        final List<T> lista = new ArrayList<T>(listaSelecao.size());
        for (ObjetoSelecao<T> obj : listaSelecao) {
            if(obj.selecionado) lista.add(obj.objeto);
        }
        return lista;
    }
    
    /**
     * 
     * @param List de ObjetoSelecao tipo T
     * @return retorna um (List de T) completa dos objetos decorados
     */
    public static <T> List<T> getListaObjetosCompleta(List<ObjetoSelecao<T>> listaSelecao){
        final List<T> lista = new ArrayList<T>(listaSelecao.size());
        for (ObjetoSelecao<T> obj : listaSelecao) {
            lista.add(obj.objeto);
        }
        return lista;
    }

    /**
     * 
     * @param T
     * @return faz o equals do objeto decorado
     */


    @Override
    public boolean equals(Object obj) {
        return objeto.equals(((ObjetoSelecao<T>)obj).objeto);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.objeto != null ? this.objeto.hashCode() : 0);
        return hash;
    }

}
