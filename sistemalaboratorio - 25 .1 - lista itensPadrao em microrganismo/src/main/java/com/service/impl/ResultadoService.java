package com.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.model.Amostra;
import com.model.Resultado;
import com.service.IResultadoService;

@Transactional(readOnly = true)
public class ResultadoService extends Service implements Serializable, IResultadoService {


	private static final long serialVersionUID = 1L;

	public ResultadoService () {
	}

	@Transactional(readOnly = false)
	public void salvar(Resultado r) {
		getDao().<Resultado>adicionar(r); 		// verifica se existe o ID cadastrado
	}

	public boolean isExiste(Resultado r) {
		//Verifica se existe Resultado Resultado.getNome()
		if (recuperar(r.getIdResultado()) != null )
			return true;
		else
			return false;
	}

	public Resultado recuperar(Integer id) {
		return getDao().recuperar(Resultado.class, id);
	}

	@Transactional(readOnly = false)
	public void deletar(Resultado r) {
		getDao().deletar(r);
	}

	@Transactional(readOnly = false)
	public void atualizar(Resultado r) {
		getDao().<Resultado>atualizar(r);
	}

	public Resultado getById(Resultado r) {
		return getDao().recuperar(r.getClass(), r.getIdResultado());
	}

	public List<Resultado> getByAmostra(Amostra amostra) {
		if (amostra.getIdAmostra()==0) 
			return null;
		Resultado r = new Resultado();
		r.setAmostra(amostra);
		r.setAtivo(true);

		List<Resultado> resultado = null;
		try {
			resultado = getDao().<Resultado>recuperarPorAtributoPreenchido(r);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ((resultado != null) && (!resultado.isEmpty())) {

			//**tirar duplicados**
			HashSet<Resultado> duplicidades = new HashSet<Resultado>();
			duplicidades.addAll(resultado);
			resultado = new ArrayList<Resultado> (duplicidades);
			//******//
			return resultado;
		}else
			return null;
	}

	@Override
	public List<Resultado> getCompByAmostra(Amostra amostra) {
		Resultado r = new Resultado();
		r.setAmostra(amostra);
		r.setComparativo(true);

		List<Resultado> resultado = null;
		try {
			resultado = getDao().<Resultado>recuperarPorAtributoPreenchido(r);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ((resultado != null) && (!resultado.isEmpty()))
			return resultado;
		else
			return null;
	}

	public List<Resultado> getAll() {
		Resultado f = new Resultado();

		try {
			return getDao().<Resultado>recuperarPorAtributoPreenchido(f);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	@Override
	@Transactional(readOnly = false)
	public void atualizaListaResultado(Amostra amostra,	List<Resultado> resultadoList, boolean ativo) {
		List<Resultado> resultadoListPersistido = getByAmostra(amostra);
		if (resultadoListPersistido != null && !resultadoListPersistido.isEmpty()) {
			for (Resultado resultadoPersistido : resultadoListPersistido) {
				if (!resultadoList.contains(resultadoPersistido)){
					resultadoPersistido.setAtivo(false);			
					atualizar(resultadoPersistido);
				}
			}
		}

		for (Resultado resultado : resultadoList) {
			resultado.setAmostra(amostra);
			resultado.setAtivo(ativo);
			if(resultado.getIdResultado()!=0){
				atualizar(resultado);
			}	else{ 
				salvar(resultado);
			}
		}
	}
}
