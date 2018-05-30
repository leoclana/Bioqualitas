package com.managed.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.model.*;
import com.service.*;
import com.util.enums.TipoAnalise;
import org.primefaces.context.RequestContext;

import com.util.JsfUtil;
import com.util.Messages;
import com.util.enums.Acoes;
import com.util.enums.RetornoFaces;
import com.util.enums.StatusSolicitacaoEnum;
import com.util.view.SelectOneDataModel;

public class SolicitacaoMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Solicitacao solicitacao;
	private List<Amostra> amostraList;
	private Amostra[] amostrasSelecionadas;
	private Amostra amostraSelecionada;

	private List<Solicitacao> solicitacaoList;
	private List<Solicitacao> solicitacaoListFiltered;
	private SelectOneDataModel<Cliente> comboCliente;

	private ISolicitacaoService solicitacaoService;
	private IAmostraService amostraService;
	private IResultadoService resultadoService;

	private IClienteService clienteService;
	private IFormaService formaService;
	
	private IConfiguracaoService configuracaoService;

	private String justificativa;

	public SolicitacaoMB(ISolicitacaoService sS, IAmostraService aS,
			IClienteService cS, IFormaService fS, IResultadoService rS, IConfiguracaoService configuracaoService) {

		setSolicitacaoService(sS);
		setAmostraService(aS);
		setClienteService(cS);
		setFormaService(fS);
		setResultadoService(rS);
		setConfiguracaoService(configuracaoService);

		Solicitacao s = (Solicitacao) JsfUtil.getSessionValue("solicitacao");
		if (s != null)
			setSolicitacao(s);
	}

	public String listar() {
		super.listar();
		limpaSessao();
		return RetornoFaces.LISTARSOLICITACOES.toString();
	}

	public String criar() {
		super.criar();
		limpaSessao();
		limparCamposTela();
		return RetornoFaces.CADASTRARSOLICITACAO.toString();
	}

	public String editar() {
		preencheTela();
		return super.editar();
	}

	public String visualizar() {
		preencheTela();
		return super.visualizar();
	}

	public String analisar() {
		preencheTela();

		getSessaoDoUsuario().setAcaoCorrente(Acoes.ANALISAR.toString());
		return RetornoFaces.FORMULARIO.toString();
	}

	public String deletar() {
		try {
			getSolicitacaoService().deletar(getSolicitacao());
		} catch (Exception e) {
			Messages.addErrorBundleMessage(
					"solicitacao.operacao.erro.naofoipossivelapagar",
					getSolicitacao().getIdSolicitacao().toString());
		}
		refreshLista();
		return null;
	}

	public boolean podeEditarSolicitacao(Solicitacao solicitacao) {
		return getSessaoDoUsuario().isPodeEditarSolicitacao()
				&& solicitacao.getStatus() == StatusSolicitacaoEnum.SOLICITADO;
	}

	public boolean podeAnalisarSolicitacao(Solicitacao solicitacao) {
		return getSessaoDoUsuario().isPodeAnalisarSolicitacao()
				&& solicitacao.getStatus() == StatusSolicitacaoEnum.EMANALISE;
	}

	public boolean podeDeletarSolicitacao(Solicitacao solicitacao) {
		return getSessaoDoUsuario().isPodeDeletarSolicitacao()
				&& solicitacao.getStatus() != StatusSolicitacaoEnum.LIBERADA;
	}

	public boolean isPodeCadastrarAmostra() {
		return getSessaoDoUsuario().isPodeCadastrarAmostra()
				&& getSolicitacao().getStatus() == StatusSolicitacaoEnum.SOLICITADO
				&& getSessaoDoUsuario().isEditar();
	}

	public boolean isPodeEditarAmostra() {
		return getSessaoDoUsuario().isPodeEditarAmostra()
				&& getSolicitacao().getStatus() == StatusSolicitacaoEnum.SOLICITADO;
	}

	public boolean isPodeDeletarAmostra() {
		return getSessaoDoUsuario().isPodeDeletarAmostra()
				&& getSessaoDoUsuario().isEditar();
	}

	public boolean isPodeAnalisarAmostra() {
		return getSessaoDoUsuario().isPodeAnalisarAmostra()
				&& getSessaoDoUsuario().isAnalisar();
	}

	public boolean isPodeLiberarCliente() {
		return getSessaoDoUsuario().isAdministrador()
				&& getSolicitacao().getStatus() == StatusSolicitacaoEnum.EMAPROVACAO;
	}

	public boolean isPodeImprimir() {
		return getSolicitacao().getStatus() == StatusSolicitacaoEnum.LIBERADA;
	}

	public boolean amostraValida(Amostra amostra) {
		return amostra.getInvalida() == null || !amostra.getInvalida();
	}

	public String salvar() {
		if (getSolicitacao().getIdSolicitacao() != 0) {
			getSolicitacaoService().atualizar(getSolicitacao());
		} else {
			getSolicitacaoService().salvar(getSolicitacao());
		}

		return voltar();
	}

	public String voltar() {
		refreshLista();

		limpaSessao();

		getSessaoDoUsuario().setAcaoCorrente(Acoes.LISTAR.toString());
		return RetornoFaces.LISTAR.toString();
	}

	public String enviarParaAnalise() {
		getSolicitacaoService().enviarParaAnalise(getSolicitacao());
		return voltar();
	}

	public String concluirAnalise() {
		getSolicitacaoService().enviarParaAprovacao(getSolicitacao());
		return voltar();
	}

	public String liberarParaCliente() {
		getSolicitacaoService().liberarParaCliente(getSolicitacao(), 
				(HttpServletRequest)FacesContext
				.getCurrentInstance()
				.getExternalContext()
					.getRequest());
		return voltar();
	}

	public String imprimir() {
		return null;
	}

	/**
	 * metodo utilizado na cria��o, edi��o e analise da amostra
	 */
	public String criarAmostra() {
		JsfUtil.setSessionValue("solicitacao", getSolicitacao());

		return RetornoFaces.FORMULARIO.toString();
	}

	public String analisarAmostra() {
		getSessaoDoUsuario().setAcaoCorrente(Acoes.ANALISAR.toString());
		return editarAmostra();
	}

	public String visualizarAmostra() {
		getSessaoDoUsuario().setAcaoCorrente(Acoes.VISUALIZAR.toString());
		return editarAmostra();
	}

	/**
	 * metodo utilizado na edi��o , visualiza��o, invalidar e analise da amostra
	 */
	public String editarAmostra() {

		JsfUtil.setSessionValue("solicitacao", getSolicitacao());
		Amostra a = null;

		if (getAmostraSelecionada() != null) {
			a = getAmostraSelecionada();
		} else if (getAmostrasSelecionadas() != null
				&& getAmostrasSelecionadas().length == 1) {
			a = getAmostrasSelecionadas()[0];
		} else {
			String msg;

			if (getAmostrasSelecionadas() != null
					&& getAmostrasSelecionadas().length > 1)
				msg = "Para esta a��o selecione apenas uma amostra.";
			else
				msg = "Selecione alguma amostra.";

			Solicitacao s = (Solicitacao) JsfUtil
					.getSessionValue("solicitacao");
			if (s != null)
				setSolicitacao(s);

			preencheTela();

			Messages.addErrorTextMessage(msg);
			return null;
		}

		JsfUtil.setSessionValue("amostra", a);

		List<Resultado> resultadoList = getResultadoService().getByAmostra(a);
		if (resultadoList != null && !resultadoList.isEmpty()) {
			JsfUtil.setSessionValue("resultadoList", resultadoList);
		}

		List<Resultado> resultadoListComp = getResultadoService().getCompByAmostra(a);
		if (resultadoListComp != null && !resultadoListComp.isEmpty()) {
			JsfUtil.setSessionValue("resultadoComparativoList",	resultadoListComp);
		}

		return RetornoFaces.FORMULARIO.toString();
	}

	public void preparaInvalidarAmostra() {
		editarAmostra();
	}

	public String invalidarAmostra() {

		boolean sucesso = true;
		final RequestContext context = RequestContext.getCurrentInstance();

		if (getAmostraSelecionada() != null)
			getAmostraService().invalidarAmostra(getAmostraSelecionada(),
					getJustificativa(), getSolicitacao());
		else if (getAmostrasSelecionadas() != null
				&& getAmostrasSelecionadas().length == 1)
			getAmostraService().invalidarAmostra(getAmostrasSelecionadas()[0],
					getJustificativa(), getSolicitacao());
		else {
			sucesso = false;
			String msg;

			if (getAmostrasSelecionadas() != null
					&& getAmostrasSelecionadas().length > 1)
				msg = "Para esta ação selecione apenas uma amostra.";
			else
				msg = "Selecione alguma amostra.";

			preencheTela();
			Messages.addErrorTextMessage(msg);
			return null;
		}

		context.addCallbackParam("sucesso", sucesso);
		return null;
	}

	public String deletarAmostras() {
		if (getAmostrasSelecionadas() != null
				&& getAmostrasSelecionadas().length > 0) {
			for (Amostra amostra : getAmostrasSelecionadas()) {
				try {
					getAmostraService().deletar(amostra);
					getAmostraList().remove(amostra);
				} catch (Exception e) {
					Messages.addErrorBundleMessage(
							"amostra.operacao.erro.naofoipossivelapagar",
							amostra.getTipoAnalise().name());
				}
			}
		}
		return null;
	}

	private void limparCamposTela() {
		setComboCliente(SelectOneDataModel.criaComObjetoSelecionado(
				getClienteService().getAll(), null));
		setSolicitacao(new Solicitacao());
		if (!getSessaoDoUsuario().isUsuarioBioqualitas())
			getSolicitacao().setCliente(
					getSessaoDoUsuario().getUsuario().getCliente());
		setAmostraList(new ArrayList<Amostra>());
	}

	private void limpaSessao() {
		JsfUtil.setSessionValue("solicitacao", null);
		JsfUtil.setSessionValue("amostra", null);
	}

	private void preencheTela() {
		if (getSolicitacao().getIdSolicitacao() != 0) {
			setComboCliente(SelectOneDataModel
					.criaComObjetoSelecionado(getClienteService().getAll(),
							getSolicitacao().getCliente()));

			setAmostraList(getAmostraService().getAllBySolicitacao(
					getSolicitacao()));
		}
	}

	private void refreshLista() {
		setSolicitacaoList(new ArrayList<Solicitacao>());
		if (getSessaoDoUsuario().isUsuarioBioqualitas()) {
			getSolicitacaoList().addAll(getSolicitacaoService().getAll());
		} else {
			getSolicitacaoList().addAll(
					getSolicitacaoService().getAllByCliente(
							getSessaoDoUsuario().getUsuario().getCliente()));
		}

		//** calcula o PRAZO **
		Configuracao configuracao = configuracaoService.getConfiguracao();
		for(Solicitacao solicitacao: getSolicitacaoList()){
			int qtddias=-1;
			for(Amostra amostra : solicitacao.getAmostras()){
				Date prazo = new Date();
				prazo.setYear(solicitacao.getDataSolicitacao().getYear());
				prazo.setMonth(solicitacao.getDataSolicitacao().getMonth());
				prazo.setDate(solicitacao.getDataSolicitacao().getDate());

				if(amostra.getTipoAnalise().equals(TipoAnalise.ALIMENTO)) {
					qtddias = configuracao.getAlimento();
				}
				if(amostra.getTipoAnalise().equals(TipoAnalise.AGUA)) {
					qtddias = configuracao.getAgua();
				}
				if(amostra.getTipoAnalise().equals(TipoAnalise.AGUAMINERAL)) {
					qtddias = configuracao.getAguaMineral();
				}
				if(amostra.getTipoAnalise().equals(TipoAnalise.AR)) {
					qtddias = configuracao.getAr();
				}
				if(amostra.getTipoAnalise().equals(TipoAnalise.MANIPULADOR)) {
					qtddias = configuracao.getManipulador();
				}
				if(amostra.getTipoAnalise().equals(TipoAnalise.SUPERFICIE)) {
					qtddias = configuracao.getSuperficie();
				}

				int dia_da_semana;
				for(int i = 1; i <= qtddias; i++) {
					prazo.setDate(prazo.getDate() + 1);
					dia_da_semana = prazo.getDay();
					//** Se dia da semana for domingo==0
					if (dia_da_semana==0) {
						prazo.setDate(prazo.getDate() + 1);
					}
					//** Se dia da semana for sabado==6
					if (dia_da_semana==6) {
						prazo.setDate(prazo.getDate() + 2);
					}
				}
				if(solicitacao.getPrazo()==null || solicitacao.getPrazo().after(prazo)) {
					solicitacao.setPrazo(prazo);
				}
			}

		}

	}

	public String getLabelCabecalho() {
		if (getSessaoDoUsuario().isCriar()) {
			return Messages
					.getBundleMessage("solicitacao.form.cabecalho.cadastrar");
		} else {
			return Messages
					.getBundleMessage("solicitacao.form.cabecalho.alterar");
		}
	}

	public Solicitacao getSolicitacao() {
		if (solicitacao == null) {
			setSolicitacao(new Solicitacao());
		}
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {

		JsfUtil.setSessionValue("solicitacao", solicitacao);
		this.solicitacao = solicitacao;
	}

	public List<Amostra> getAmostraList() {
		if (amostraList == null) {
			if (getSolicitacao().getIdSolicitacao() != 0) {
				setAmostraList(amostraService
						.getAllBySolicitacao(getSolicitacao()));
			} else {
				setAmostraList(new ArrayList<Amostra>());
			}
		}
		return amostraList;
	}

	public void setAmostraList(List<Amostra> amostraList) {
		this.amostraList = amostraList;
	}

	public Amostra[] getAmostrasSelecionadas() {
		return amostrasSelecionadas;
	}

	public void setAmostrasSelecionadas(Amostra[] amostrasSelecionadas) {
		this.amostrasSelecionadas = amostrasSelecionadas;
	}

	public Amostra getAmostraSelecionada() {
		return amostraSelecionada;
	}

	public void setAmostraSelecionada(Amostra amostraSelecionada) {
		this.amostraSelecionada = amostraSelecionada;
	}

	public List<Solicitacao> getSolicitacaoList() {
		if (solicitacaoList == null) {
			refreshLista();
		}
		return solicitacaoList;
	}

	public void setSolicitacaoList(List<Solicitacao> solicitacaoList) {
		this.solicitacaoList = solicitacaoList;
	}

	public List<Solicitacao> getSolicitacaoListFiltered() {
		return solicitacaoListFiltered;
	}

	public void setSolicitacaoListFiltered(
			List<Solicitacao> solicitacaoListFiltered) {
		this.solicitacaoListFiltered = solicitacaoListFiltered;
	}

	public SelectOneDataModel<Cliente> getComboCliente() {
		if (comboCliente == null) {
			setComboCliente(new SelectOneDataModel<Cliente>(getClienteService()
					.getAll()));
		}
		return comboCliente;
	}

	public void setComboCliente(SelectOneDataModel<Cliente> comboCliente) {
		this.comboCliente = comboCliente;
	}

	public List<SelectItem> getFormaPagamentoList() {
		return SelectOneDataModel.criaComObjetoSelecionado(
				getFormaService().getAll(), null).getListaSelecao();
	}

	private ISolicitacaoService getSolicitacaoService() {
		return solicitacaoService;
	}

	private void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	private IAmostraService getAmostraService() {
		return amostraService;
	}

	private void setAmostraService(IAmostraService amostraService) {
		this.amostraService = amostraService;
	}

	private IClienteService getClienteService() {
		return clienteService;
	}

	private void setClienteService(IClienteService clienteService) {
		this.clienteService = clienteService;
	}

	private IFormaService getFormaService() {
		return formaService;
	}

	private void setFormaService(IFormaService formaService) {
		this.formaService = formaService;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public IResultadoService getResultadoService() {
		return resultadoService;
	}

	public void setResultadoService(IResultadoService resultadoService) {
		this.resultadoService = resultadoService;
	}

	public IConfiguracaoService getConfiguracaoService() {
		return configuracaoService;
	}

	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}
}
