package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.model.*;
import com.service.IAmostraService;
import com.service.ICondicaoAmostraService;
import com.service.IItemPadraoService;
import com.service.ILaudoService;
import com.service.IMicrorganismoService;
import com.service.IMotivoService;
import com.service.IResultadoService;
import com.util.JsfUtil;
import com.util.Messages;
import com.util.enums.ClassificacaoAnalise;
import com.util.enums.RetornoFaces;
import com.util.enums.TipoAnalise;
import com.util.view.SelectOneDataModel;

public class AmostraMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private IAmostraService amostraService;
	private IResultadoService resultadoService;
	private IItemPadraoService itemPadraoService;
	private IMicrorganismoService microrganismoService;

	private ICondicaoAmostraService condicaoAmostraService;
	private IMotivoService motivoService;
	private ILaudoService laudoService;

	private Amostra amostra;
	private Microrganismo microrganismo;
	private Laudo laudotmp;

	private List<Resultado> resultadoList;
	private List<Resultado> resultadoCompList;

	public AmostraMB(IAmostraService aS, IResultadoService rS,
			IItemPadraoService ipS, IMicrorganismoService mS,
			ICondicaoAmostraService cas, IMotivoService mots,
			ILaudoService laudoS) {

		setAmostraService(aS);
		setResultadoService(rS);
		setItemPadraoService(ipS);
		setMicrorganismoService(mS);
		setMotivoService(mots);
		setCondicaoAmostraService(cas);
		setLaudoService(laudoS);

		@SuppressWarnings("unchecked")
		List<Resultado> r = (List<Resultado>) JsfUtil.getSessionValue("resultadoList");
		if (r != null) {
			setResultadoList(r);
		}

		@SuppressWarnings("unchecked")
		List<Resultado> rc = (List<Resultado>) JsfUtil.getSessionValue("resultadoComparativoList");
		if (rc != null) {
			setResultadoCompList(rc);
		}

		Amostra a = (Amostra) JsfUtil.getSessionValue("amostra");
		if (a != null) {
			setAmostra(a);
		}
	}

	public void onEditRow(Resultado resultado) {
		// TODO : mudar para Integer => tolerancia indicativa e tolerancia
		// indicativa comp no restulado e no item padrao
		// TODO : adicionar campo unidade de medida no resultado e no item
		// padrao alterar carga para atuallizar as informacoes dessa forma
		// TODO : adicionar coluna de unidade de medida na tela de amostra e no
		// cadastro de microrganismo

		// Descomentar depois que tolerancia indicativa estiver como integer
		// if (resultado.getToleranciaIndicativa() != null) {
		// resultado.setPositivo(resultado.getToleranciaIndicativa()<
		// resultado.getToleranciaIndicativaComp());
		// }
	}

	public String salvar() {
		if (getAmostra().getIdAmostra() != 0) {

			getAmostraService().atualizar(getAmostra());
			atualizaListaResultado(getAmostra(), true);
		} else {

			Solicitacao s = (Solicitacao) JsfUtil.getSessionValue("solicitacao");
			if (s != null) {
				getAmostra().setSolicitacao(s);
				getAmostraService().salvar(getAmostra());
				atualizaListaResultado(getAmostra(), true);
			}
		}
		return voltar();
	}

	public String voltar() {
		JsfUtil.setSessionValue("amostra", null);
		JsfUtil.setSessionValue("resultadoList", null);
		JsfUtil.setSessionValue("resultadoComparativoList", null);
		return RetornoFaces.LISTAR.toString();
	}

	public void resultadoPorCodLegislacao() {
		if (getAmostra().getCodLegislacao() != null) {
			List<ItemPadrao> lista = getItemPadraoService().getAllByCodigoLegislacao(getAmostra().getCodLegislacao());
			if (lista != null && !lista.isEmpty())
				preencheListaResultado(lista);
			JsfUtil.setSessionValue("resultadoList", getResultadoList());
		}
	}

	private void atualizaListaResultado(Amostra amostra, boolean ativo) {
		List<Resultado> resultadoList = getResultadoList();

		List<Resultado> resultadoCompList = getResultadoCompList();
		if (resultadoCompList != null) {
			resultadoList.addAll(resultadoCompList);
		}

		if (resultadoList != null && !resultadoList.isEmpty()) {
			getResultadoService().atualizaListaResultado(amostra, resultadoList, ativo);
		}
	}

	public void calcular() {
		List<Resultado> resultadoList = getResultadoList();
		if (resultadoList != null && !resultadoList.isEmpty()) {
			Amostra a = getAmostra();
			boolean bolFlagResult = true;

			for (Resultado resultado : resultadoList) {
				if (resultado.getPositivo() == null) {
					bolFlagResult = false;
					Messages.addErrorTextMessage("O resultado do microrganismo "
							+ resultado.getMicrorganismo().getNome()
							+ " não foi fornecido!");
					break;
				} else if (!resultado.getPositivo()) {
					bolFlagResult = false;
					a.setNota(ClassificacaoAnalise.C.name());
					break;
				}
			}
			if (bolFlagResult)
				a.setNota(ClassificacaoAnalise.A.name());
			//
			// TODO : define o laudo no combo
			List<Laudo> laudoList = new ArrayList<Laudo>();
			laudoList = getLaudoService().getByResultadoTipoAnalise(
					a.getNota(), a.getTipoAnalise());

			if ((laudoList != null) && (!laudoList.isEmpty())) {
				if (laudoList.size() == 1) {
					//getAmostra().setLaudo(laudoList.get(0));
					getAmostra().setLaudo(laudoList.get(0).getNome());//toString());
					getAmostra().setObservacao(laudoList.get(0).getNome());//toString());
				} else {

				}
			}

			// comboLaudo.objetoSelecionado = retorno.get(0);
			// TODO : define o texto da combo na text area
			// textAreaLaudo.Texto = combolaudo.objetoSeleciontado.ToString();
			// } else {
			// comboLaudo.lista = retorno;
			// }
		} else {
			Messages.addErrorTextMessage("Amostra não possui resultados!");
		}
	}

	public List<SelectItem> getLaudoList() {
		List<Laudo> laudoList = new ArrayList<Laudo>();
//		String resultado = getAmostra().getNota();
		TipoAnalise tipoAnalise = getAmostra().getTipoAnalise();
//		if ("".equals(resultado) && "".equals(tipoAnalise)) {
//			laudoList = getLaudoService().getByResultadoTipoAnalise(resultado, tipoAnalise);
//		}
		if(tipoAnalise != null){
			laudoList = laudoService.getByResultadoTipoAnalise("", tipoAnalise);
		}else{
			laudoList = laudoService.getAll();
		}

		return SelectOneDataModel.criaComObjetoSelecionado(laudoList, null).getListaSelecao();
	}

	public void onLaudoChange() {
		Amostra amostra = getAmostra();
		if(laudotmp != null){
			amostra.setLaudo(laudotmp.getNome());
		}
	}

	public void onTipoAnaliseChange() {
		if(getAmostra().getIdAmostra() == 0) {
			Amostra amostranew = new Amostra();
			amostranew.setIdAmostra(getAmostra().getIdAmostra());
			amostranew.setTipoAnalise(getAmostra().getTipoAnalise());
			setAmostra(amostranew);
		}

		if (getAmostra().getTipoAnalise() != null) {
			if (!isTipoAnaliseAlimento()) {
				List<ItemPadrao> lista = getItemPadraoService().getAllByTipoAnalise(getAmostra().getTipoAnalise());
				preencheListaResultado(lista);
			}

			// TODO : define os laudos no combo
			// comboLaudo.lista = laudoService.getLaudos(a.getTipoAnalise());

		}
	}

	public void onAnaliseComparativaChange() {
		if (getAmostra().getComparativa()) {
			populaListaComparativa();
			getAmostra().setAntissepsia(false);
		} else {
			setResultadoCompList(new ArrayList<Resultado>());
		}
	}

	private void populaListaComparativa() {
		setResultadoCompList(new ArrayList<Resultado>(getResultadoList()));
		for (Resultado resultado : getResultadoCompList()) {
			resultado.setComparativo(true);
		}
	}

	public boolean isAnaliseComparativa() {
		if (isTipoAnaliseManipulador()) {
			if (getAmostra().getComparativa() != null)
				if (getAmostra().getComparativa().booleanValue()) {
					// getAmostra().setAntissepsia(false);
					return true;
				} else {
					// getAmostra().setAntissepsia(true);
					return false;
				}
		}
		return false;
	}

	public boolean isExibeAntissepsia() {
		if (isTipoAnaliseManipulador()
				&& (getAmostra().getComparativa() != null && !getAmostra()
						.getComparativa().booleanValue()))
			return true;
		return false;

	}

	private void preencheListaResultado(List<ItemPadrao> listaTemporaria) {
		setResultadoList(new ArrayList<Resultado>());
		if (!listaTemporaria.isEmpty()) {
			for (ItemPadrao itemPadrao : listaTemporaria) {
				Resultado resultado = new Resultado();
				resultado.setCodigoLegislacao(itemPadrao.getCodigoLegislacao());
				resultado.setMicrorganismo(itemPadrao.getMicrorganismo());
//				resultado.setToleranciaIndicativaComp(itemPadrao.getToleranciaIndicativa());
//				resultado.setToleranciaLimInfComp(itemPadrao.getToleranciaLimInf());
//				resultado.setToleranciaLimSupComp(itemPadrao.getToleranciaLimSup());
//				resultado.setToleranciaRepresentivaCComp(itemPadrao.getToleranciaRepresentivaC());
//				resultado.setToleranciaRepresentivaNComp(itemPadrao.getToleranciaRepresentivaN());
				resultado.setPatrao(true);

				resultado.setUnidadeMedida(itemPadrao.getUnidadeMedida());
//				resultado.setLimiteTolerancia(itemPadrao.getLimiteTolerancia());

				getResultadoList().add(resultado);
			}
		}

	}

	public List<SelectItem> completeText(String query) {
		Microrganismo m = new Microrganismo();
		m.setNome(query);
		List<SelectItem> mList = SelectOneDataModel.criaComObjetoSelecionado(
				getMicrorganismoService().getByNome(m), null).getListaSelecao();
		return mList;
	}

	public void adicionarMicrorganismo() {

		if (getMicrorganismo() == null) {
			Messages.addErrorTextMessage("Microganismo deve ser informado!");
			return;
		}

		Resultado r = new Resultado();
		r.setMicrorganismo(getMicrorganismo());
		r.setAtivo(true);

		//if (getResultadoList().contains(r) && r.getIdResultado()!=0) {
		if(!validaMicroorganismoDuplicado(getMicrorganismo())){
			Messages.addErrorTextMessage("Microganismo existente!");
			return;
		}

		if (getAmostra().getIdAmostra() != 0) {
			r.setAmostra(getAmostra());
		}
		getResultadoList().add(r);

		if (getAmostra().getComparativa()) {
			Resultado rComp = new Resultado();
			rComp.setMicrorganismo(getMicrorganismo());
			rComp.setAtivo(true);
			rComp.setComparativo(true);

			if (getAmostra().getIdAmostra() != 0) {
				r.setAmostra(getAmostra());
			}
			getResultadoCompList().add(r);
		}
	}

	public boolean validaMicroorganismoDuplicado(Microrganismo microrganismo){
		boolean retorno = true;
		for(Resultado resultado : getResultadoList()){
			if(resultado.getMicrorganismo().equals(microrganismo)){
				retorno = false;
			}
		}
		return retorno;
	}

	public void deletarMicrorganismo(Resultado r) {
		resultadoList.remove(r);
		setResultadoList(resultadoList);
	}
	private Resultado resultadodelet;
	public Resultado getResultadodelet() {
		return resultadodelet;
	}
	public void setResultadodelet(Resultado resultadodelet) {
		this.resultadodelet = resultadodelet;
	}
	public void deletarMicrorganismo() {
		resultadoList.remove(resultadodelet);
		setResultadoList(resultadoList);
	}

	public void faznada(){

	}

	private boolean isTipoAnaliseNull() {
		return (getAmostra().getTipoAnalise() == null ||
				getAmostra().getTipoAnalise() == TipoAnalise.SELECIONEAQUI);
	}

	public boolean isTipoAnaliseAlimento() {
		return !isTipoAnaliseNull() && getAmostra().getTipoAnalise() == TipoAnalise.ALIMENTO;
	}

	public boolean isTipoAnaliseManipulador() {
		return !isTipoAnaliseNull()	&& (getAmostra().getTipoAnalise() == TipoAnalise.MANIPULADOR);
	}

	public boolean isTipoAnaliseSuperficie() {
		return !isTipoAnaliseNull() && (getAmostra().getTipoAnalise() == TipoAnalise.SUPERFICIE);
	}

	public boolean isTipoAnaliseAgua() {
		return !isTipoAnaliseNull() && (getAmostra().getTipoAnalise() == TipoAnalise.AGUA);
	}

	public boolean isTipoAnaliseAguaMineral() {
		return !isTipoAnaliseNull() && (getAmostra().getTipoAnalise() == TipoAnalise.AGUAMINERAL);
	}

	public boolean isTipoAnaliseAr() {
		return !isTipoAnaliseNull() && (getAmostra().getTipoAnalise() == TipoAnalise.AR);
	}

	public boolean isMotivoAnaliseOutros() {
		return isTipoAnaliseAlimento() &&
				(getAmostra().getMotivoAnalise() != null &&
				 "outros".equals(getAmostra().getMotivoAnalise().getNome().toString().toLowerCase()));
	}

	public boolean isCondicaoAmostraOutros() {
		//** id do CondicaoAmostra = Outro e 4
//		CondicaoAmostra condicaoAmostraoutro = condicaoAmostraService.recuperar(4);
//		return isTipoAnaliseAlimento()
//				&& (getAmostra().getCondicoes() != null
//				//&& "outros".equals(getAmostra().getCondicao().getNome().toString().toLowerCase()))
//				&& getAmostra().getCondicoes().contains(condicaoAmostraoutro));
		return false;

	}

	public TipoAnalise[] getTipoAnaliseList() {
		return TipoAnalise.values();
	}

	public List<SelectItem> getMotivoAnaliseList() {
		return SelectOneDataModel.criaComObjetoSelecionado(
				getMotivoService().getAll(), null).getListaSelecao();
	}

	public List<SelectItem> getMicrorganismoList() {
		return SelectOneDataModel.criaComObjetoSelecionado(
				getMicrorganismoService().getAll(), null).getListaSelecao();
	}

	public ClassificacaoAnalise[] getClassificacaoAnaliseList() {
		return ClassificacaoAnalise.values();
	}

	public List<SelectItem> getCondicaoAmostraList() {
		return SelectOneDataModel.criaComObjetoSelecionadoSemTextoSelecioneAqui(
				getCondicaoAmostraService().getAll(), null).getListaSelecao();
	}

	public String getLabelCabecalho() {
		if (getAmostra().getIdAmostra() == 0) {
			return Messages
					.getBundleMessage("amostra.form.cabecalho.cadastrar");
		} else {
			return Messages.getBundleMessage("amostra.form.cabecalho.alterar");
		}
	}

	public Amostra getAmostra() {
		if (amostra == null)
			setAmostra(new Amostra());
		return amostra;
	}

	public void setAmostra(Amostra amostra) {
		JsfUtil.setSessionValue("amostra", amostra);
		this.amostra = amostra;
	}

	public Microrganismo getMicrorganismo() {
		return microrganismo;
	}

	public void setMicrorganismo(Microrganismo microrganismo) {
		this.microrganismo = microrganismo;
	}

	public Laudo getLaudotmp() {
		return laudotmp;
	}

	public void setLaudotmp(Laudo laudotmp) {
		this.laudotmp = laudotmp;
	}

	public List<Resultado> getResultadoList() {
		if (resultadoList == null)
			setResultadoList(new ArrayList<Resultado>());
		return resultadoList;
	}

	public void setResultadoList(List<Resultado> resultadoList) {
		JsfUtil.setSessionValue("resultadoList", resultadoList);
		this.resultadoList = resultadoList;
	}

	public List<Resultado> getResultadoCompList() {
		return resultadoCompList;
	}

	public void setResultadoCompList(List<Resultado> resultadoCompList) {
		JsfUtil.setSessionValue("resultadoComparativoList", resultadoCompList);
		this.resultadoCompList = resultadoCompList;
	}

	private IAmostraService getAmostraService() {
		return amostraService;
	}

	private void setAmostraService(IAmostraService amostraService) {
		this.amostraService = amostraService;
	}

	private IResultadoService getResultadoService() {
		return resultadoService;
	}

	private void setResultadoService(IResultadoService resultadoService) {
		this.resultadoService = resultadoService;
	}

	private IMotivoService getMotivoService() {
		return motivoService;
	}

	private void setMotivoService(IMotivoService motivoService) {
		this.motivoService = motivoService;
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

	private void setMicrorganismoService(
			IMicrorganismoService microrganismoService) {
		this.microrganismoService = microrganismoService;
	}

	public ICondicaoAmostraService getCondicaoAmostraService() {
		return condicaoAmostraService;
	}

	public void setCondicaoAmostraService(
			ICondicaoAmostraService condicaoAmostraService) {
		this.condicaoAmostraService = condicaoAmostraService;
	}

	public ILaudoService getLaudoService() {
		return laudoService;
	}

	public void setLaudoService(ILaudoService laudoService) {
		this.laudoService = laudoService;
	}

}
