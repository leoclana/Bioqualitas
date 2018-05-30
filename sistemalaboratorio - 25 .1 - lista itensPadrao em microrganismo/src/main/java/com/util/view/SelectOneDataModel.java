package com.util.view;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import com.model.Grupo;


public class SelectOneDataModel<T> {

	public static final String TEXTO_SELECIONE_AQUI = "Selecione aqui";

	private String selecao;

	private List<SelectItem> listaSelecao;

	public static <T> SelectOneDataModel<T> criaSemTextoInicial(final Collection<T> itens){
		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.size());

		for (Object elem : itens) {
			listaSelecao.add(new SelectItem(elem, elem.toString()));
		}

		select.setListaSelecao(listaSelecao);

		return select;
	}

	public static <T> SelectOneDataModel<T> criaComTextoInicialPersolanizado(Collection<T> itens, final String LabelPrimeiroElemento){
		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
		listaSelecao.add(new SelectItem(null, LabelPrimeiroElemento));

		for (Object elem : itens) {
			listaSelecao.add(new SelectItem(elem, elem.toString()));
		}
		String selecao = LabelPrimeiroElemento;

		select.setSelecao(selecao);
		select.setListaSelecao(listaSelecao);

		return select;
	}

	public static <T> SelectOneDataModel<T> criaComTextoInicialPersolanizado(T[] itens, final String LabelPrimeiroElemento){
		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.length+1);
		listaSelecao.add(new SelectItem(null, LabelPrimeiroElemento));

		for (Object elem : itens) {
			listaSelecao.add(new SelectItem(elem, elem.toString()));
		}
		String selecao = LabelPrimeiroElemento;

		select.setSelecao(selecao);
		select.setListaSelecao(listaSelecao);

		return select;
	}

/*	public static <T> SelectOneDataModel<T> criaComObjetoSelecionado(Collection<T> itens, T objetoSelecionado){
		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
		listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));
		for (Object elem : itens) {
			listaSelecao.add(new SelectItem(elem, elem.toString()));
		}
		//   String selecao = objetoSelecionado.toString()
				String selecao = objetoSelecionado == null ? "" : objetoSelecionado.toString();
		select.setSelecao(selecao);
		select.setListaSelecao(listaSelecao);
		return select;
	}*/

	public static <T> SelectOneDataModel<T> criaComObjetoSelecionado(Collection<T> itens, T objetoSelecionado){
		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
		listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));
		for (Object elem : itens) {
			listaSelecao.add(new SelectItem(elem, elem.toString()));
		}
		String selecao = objetoSelecionado == null ? "" : objetoSelecionado.toString();
		select.setSelecao(selecao);
		select.setListaSelecao(listaSelecao);
		return select;
		}
	public static <T> SelectOneDataModel<T> criaComObjetoSelecionadoSemTextoSelecioneAqui(Collection<T> itens, T objetoSelecionado){
		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.size());
		//listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));
		for (Object elem : itens) {
			listaSelecao.add(new SelectItem(elem, elem.toString()));
		}
		String selecao = objetoSelecionado == null ? "" : objetoSelecionado.toString();
		select.setSelecao(selecao);
		select.setListaSelecao(listaSelecao);
		return select;
	}

	public static <T> SelectOneDataModel<T> criaComObjetoSelecionado(T[] itens, T objetoSelecionado){
		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.length+1);
		listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));
		for (Object elem : itens) {
		listaSelecao.add(new SelectItem(elem, elem.toString()));
		}
		String selecao = objetoSelecionado == null ? "" : objetoSelecionado.toString();
		select.setSelecao(selecao);
		select.setListaSelecao(listaSelecao);
		return select;
		}
	
	public static <T> SelectOneDataModel<Integer> criaComIdDescricaoPersonalizado(Collection<T> itens, String idMethodName, String separador, String... methods) {

		SelectOneDataModel<T> select = new SelectOneDataModel<T>();
		List<SelectItem> listaSelecao = new ArrayList<SelectItem>(itens.size()+1);

		try{
			String resultado = null;

			if(itens != null){
				listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
			}else{
				listaSelecao = new ArrayList<SelectItem>(1);
			}

			listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));


			for (Object elem : itens) {

				if (methods == null){
					listaSelecao.add(new SelectItem(elem, elem.toString()));
				}else{

					for (String nomeMetodo : methods){
						Method method = (elem.getClass().getMethod(nomeMetodo, null));
						if (method!=null){
							if (resultado == null){
								resultado = (String) method.invoke(elem, null);
							}else{
								resultado = resultado + separador + (String) method.invoke(elem, null);
							}
						}
					}
					listaSelecao.add(new SelectItem(((Grupo)elem).getIdGrupo(), resultado));
					//listaSelecao.add(new SelectItem(elem, resultado));
					resultado = null;

				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		select.setListaSelecao(listaSelecao);
		return (SelectOneDataModel<Integer>) select;
	}

	public SelectOneDataModel() {
		listaSelecao = new ArrayList<SelectItem>(1);
		listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));
		selecao = TEXTO_SELECIONE_AQUI;
	}

	public SelectOneDataModel(Collection<T> itens) {
		if(itens != null){
			listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
		}else{
			listaSelecao = new ArrayList<SelectItem>(1);
		}

		listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));

		for (Object elem : itens) {        	
			listaSelecao.add(new SelectItem(elem, elem.toString()));
		}
	} 


	//Este construtor recupera a lista conforme o construtor acima. Porem este possui um segundo parametro
	//que pode ser utilizado para recuperar outros valores de métodos, passando os nomes dos metodos em uma lista
	//Atualmente o médodo ainda não da suporte a metodos com parametros
	//Os valores serão retornados com o separador passado por parametro 

	public SelectOneDataModel(Collection<T> itens, String separador, String... methods) {
		String resultado = null;

		try{
			if(itens != null){
				listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
			}else{
				listaSelecao = new ArrayList<SelectItem>(1);
			}

			listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));


			for (Object elem : itens) {

				if (methods == null){
					listaSelecao.add(new SelectItem(elem, elem.toString()));
				}else{

					for (String nomeMetodo : methods){
						Method method = (elem.getClass().getMethod(nomeMetodo, null));
						if (method!=null){
							if (resultado == null){
								resultado = (String) method.invoke(elem, null);
							}else{
								resultado = resultado + separador + (String) method.invoke(elem, null);
							}
						}
					}
					listaSelecao.add(new SelectItem(((Grupo)elem).getIdGrupo(), resultado));
					//listaSelecao.add(new SelectItem(elem, resultado));
					resultado = null;

				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @param selecaoInicial - string do item a ser selecionado, mesmo assim existe o TEXTO_SELECIONE_AQUI
	 * @param itens - lista de itens
	 */
	//    public SelectOneDataModel(Collection<T> itens, final String selecaoInicial) {
	//        listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
	//        listaSelecao.add(new SelectItem(null, TEXTO_SELECIONE_AQUI));
	//        
	//        for (Object elem : itens) {
	//            listaSelecao.add(new SelectItem(elem, elem.toString()));
	//        }
	//        selecao = selecaoInicial;
	//    }
	/**
	 * 
	 * @param LabelPrimeiroElemento - string que aparece no lugar do TEXTO_SELECIONE_AQUI
	 * @param itens - lista de itens
	 */
	//    public SelectOneDataModel( final String LabelPrimeiroElemento, Collection<T> itens) {
	//        listaSelecao = new ArrayList<SelectItem>(itens.size()+1);
	//        listaSelecao.add(new SelectItem(null, LabelPrimeiroElemento));
	//        
	//        for (Object elem : itens) {
	//            listaSelecao.add(new SelectItem(elem, elem.toString()));
	//        }
	//        selecao = LabelPrimeiroElemento;
	//    }

	public String getSelecao() {
		return selecao;
	}

	//    public void setSelecao(String selecao) {
	//        if(selecao == null || selecao.isEmpty()){
	//            this.selecao = SelectOneDataModel.TEXTO_SELECIONE_AQUI;
	//        }else{
	//            this.selecao = selecao;
	//        }
	//    }

	public void setSelecao(String selecao) {
		this.selecao = selecao;
	}

	public List<SelectItem> getListaSelecao() {
		return listaSelecao;
	}

	public void setListaSelecao(List<SelectItem> listaSelecao) {
		//        if(!listaSelecao.get(0).getLabel().equals(TEXTO_SELECIONE_AQUI)){
		//            listaSelecao.add(0, new SelectItem(null,TEXTO_SELECIONE_AQUI));
		//        }
		this.listaSelecao = listaSelecao;
	}

	public T getObjetoSelecionado() {
		if(selecao == null) return null;
		for (SelectItem item : listaSelecao) {
			if(item.getLabel().equals(selecao)) return (T) item.getValue();
		}
		return null;
	}

	public int getQuantidadeElementos() {
		int qtd = listaSelecao.size() - 1;
		return (qtd > 0)? qtd : 0;
	}
	//    public T getObjetoSelecionado() {
	//        if(selecao == null || selecao.equals(TEXTO_SELECIONE_AQUI)) 
	//            return null;
	//        for(SelectItem item : listaSelecao){
	//                //System.out.println("########################### item=selecao  :"+item.getValue()+"=?="+selecao);
	//            if(item.getValue() != null && selecao.equals(item.getValue().toString())){
	//                //System.out.println("ENTROU!!!!!!!!");
	//                return (T)item.getValue();
	//            }
	//        }
	//        return null;
	//    }
}
