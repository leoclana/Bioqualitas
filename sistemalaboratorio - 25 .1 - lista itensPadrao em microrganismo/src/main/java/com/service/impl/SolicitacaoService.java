package com.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.support.DaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.dao.impl.AmostraDaoImpl;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.model.Amostra;
import com.model.Cliente;
import com.model.Configuracao;
import com.model.Solicitacao;
import com.model.Usuario;
import com.report.ReportMain;
import com.service.IConfiguracaoService;
import com.service.ISolicitacaoService;
import com.service.IUsuarioService;
import com.service.mail.IMailService;
import com.util.enums.AcoesLog;
import com.util.enums.StatusSolicitacaoEnum;

@Transactional(readOnly = true)
public class SolicitacaoService extends Service implements Serializable,
		ISolicitacaoService {

	private static final long serialVersionUID = 1L;

	private IMailService mailSend;
	private IUsuarioService usuarioService;
	private IConfiguracaoService configuracaoService;

	@Override
	@Transactional(readOnly = false)
	public void salvar(Solicitacao solicitacao) {
		solicitacao.setStrNumero(gerarNumeracao());
		getDao().<Solicitacao> adicionar(solicitacao);
		logar(AcoesLog.adicionou, solicitacao);
	}

	@Override
	@Transactional(readOnly = false)
	public void atualizar(Solicitacao solicitacao) {
		getDao().<Solicitacao> atualizar(solicitacao);
		logar(AcoesLog.atualizou, solicitacao);
	}

	@Override
	@Transactional(readOnly = false)
	public void deletar(Solicitacao solicitacao) {
		Solicitacao solicicatacaoPersistida = getDao().recuperar(
				solicitacao.getClass(), solicitacao.getIdSolicitacao());
		solicicatacaoPersistida.setAtivo(false);
		getDao().<Solicitacao> atualizar(solicicatacaoPersistida);
		logar(AcoesLog.deletou, solicicatacaoPersistida);
	}

	@Override
	public List<Solicitacao> getAll() {
		List<Solicitacao> retorno = null;
		Solicitacao solicitacaoPorAmostra = new Solicitacao();
		solicitacaoPorAmostra.setAtivo(true);
		solicitacaoPorAmostra.setDataSolicitacao(null);
		solicitacaoPorAmostra.setStatus(null);
		try {
			retorno =  retirarduplicados(getDao().<Solicitacao>recuperarPorAtributoPreenchido(solicitacaoPorAmostra));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	private List<Solicitacao> retirarduplicados(List<Solicitacao> lista){
		LinkedHashSet listahash = new LinkedHashSet(lista);
		List<Solicitacao> retorno = new ArrayList<Solicitacao>(listahash);
		return retorno;
	}

	@Override
	public List<Solicitacao> getAllByCliente(Cliente cliente) {
		Solicitacao solicitacaoPorAmostra = new Solicitacao();
		solicitacaoPorAmostra.setAtivo(true);
		solicitacaoPorAmostra.setDataSolicitacao(null);
		solicitacaoPorAmostra.setCliente(cliente);
		solicitacaoPorAmostra.setStatus(null);
		try {
			return retirarduplicados(getDao().<Solicitacao> recuperarPorAtributoPreenchido(solicitacaoPorAmostra));
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
	public void enviarParaAnalise(Solicitacao solicitacao) {
		solicitacao.setStatus(StatusSolicitacaoEnum.EMANALISE);
		if (solicitacao.getIdSolicitacao() != null) {
			getDao().<Solicitacao> atualizar(solicitacao);
		} else {
			solicitacao.setDataSolicitacao(new Date());
			getDao().<Solicitacao> adicionar(solicitacao);
		}
		List<Usuario> userListTecnico = getUsuarioService().getTecnicos();
		if (userListTecnico != null) {
			getMailSend().enviarParaAnalise(userListTecnico,
					solicitacao.getIdSolicitacao().toString());
		}
		logar(AcoesLog.enviouparaanalise, solicitacao);
	}

	@Override
	@Transactional(readOnly = false)
	public void enviarParaAprovacao(Solicitacao solicitacao) {
		solicitacao.setStatus(StatusSolicitacaoEnum.EMAPROVACAO);
		getDao().<Solicitacao> atualizar(solicitacao);
		List<Usuario> userAdministradores = getUsuarioService()
				.getAdministradores();
		if (userAdministradores != null) {
			getMailSend().enviarParaAprovacao(userAdministradores,
					solicitacao.getIdSolicitacao().toString());
		}
		logar(AcoesLog.enviouparaaprovacao, solicitacao);
	}

	@Override
	@Transactional(readOnly = false)
	public void liberarParaCliente(Solicitacao solicitacao, HttpServletRequest r) {
		solicitacao.setStatus(StatusSolicitacaoEnum.LIBERADA);
		getDao().<Solicitacao> atualizar(solicitacao);
		Usuario contatoPrincipal = getUsuarioService()
				.buscaPorClienteContatoPrincipal(solicitacao.getCliente());
		if (contatoPrincipal != null) {
			List<Usuario> contatoPrincipalList = new ArrayList<Usuario>();
			contatoPrincipalList.add(contatoPrincipal);
			if (contatoPrincipalList != null) {

				ByteArrayResource[] pdfOutputStream = null;
				try {
					pdfOutputStream = getRelatorioSolicitacao(
							solicitacao, r);

					getMailSend().liberarParaCliente(contatoPrincipalList,
							solicitacao.getIdSolicitacao().toString(),
							pdfOutputStream);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					 getMailSend().liberarParaCliente(contatoPrincipalList,
					 solicitacao.getIdSolicitacao().toString());
				}
			}
		}
		logar(AcoesLog.liberouparacliente, solicitacao);
	}

	public ByteArrayResource[] getRelatorioSolicitacao(Solicitacao s,
			HttpServletRequest r) throws DocumentException, IllegalArgumentException, IllegalAccessException {
		ArrayList<ByteArrayResource> lista = new ArrayList<ByteArrayResource>();

		Amostra filtro = new Amostra();
		filtro.setSolicitacao(s);
		for (Amostra a : getDao().recuperarPorAtributoPreenchido(filtro)) {

			ByteArrayOutputStream baos = getPdfOutPutStream(s, a, r);
			String nomePDF = "Resultado_" + s.getIdentifier();
			lista.add(new ByteArrayResource(baos.toByteArray(), nomePDF));
		}

		return (ByteArrayResource[]) lista.toArray();
	}

	private ByteArrayOutputStream getPdfOutPutStream(Solicitacao s, Amostra a,
			HttpServletRequest r) throws DocumentException {

		if (a == null) {
			System.out.println("AMOSTRA IS NULL");
			return null;
		}
		if (s == null) {
			System.out.println("Solicitacao IS NULL");
			return null;
		}
		if (r == null) {
			System.out.println("Request IS NULL");
			return null;
		}
		
		// TODO: verificar melhor o funcionamento do ReportMain com BELEZA
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);

		ReportMain rpt = new ReportMain();
		rpt.setRequest(r);
		rpt.setWriter(writer);
		document.setPageSize(PageSize.A4);
		rpt.setDocument(document);
		
		String strTipoRelatorio = a.getTipoAnalise().toString();
		rpt.setTexto("Tipo de relatório: " + strTipoRelatorio);
		rpt.setStrTipoRelatorio(strTipoRelatorio);
		
		rpt.setAmostra(a);
		rpt.setSolicitacao(s);
		AmostraService as = new AmostraService();
		as.setDao(getDao());
		as.setAmostraDAO(new AmostraDaoImpl());
		rpt.setAmostraService(as);

		rpt.createReport();

		return baos;
	}

	private String gerarNumeracao() {

		StringBuilder strNum = new StringBuilder();
		Calendar now = Calendar.getInstance();
		Integer ano = new Integer(now.get(Calendar.YEAR));

		String strAno = ano.toString().substring(2);
		ano = Integer.valueOf(strAno);

		Configuracao configuracao = getConfiguracaoService().getConfiguracao();

		if (configuracao != null) {
			Integer anoConfiguracao = configuracao.getAnoCorrente();
			Integer numeroSolicitacao = configuracao.getNumeroSolicitacao();

			if (anoConfiguracao != null) {
				if (!ano.equals(anoConfiguracao)) {
					configuracao.setAnoCorrente(ano);
				}
			} else { // valor nao definido no banco
				configuracao.setAnoCorrente(ano);
			}

			if (numeroSolicitacao != null) {
				numeroSolicitacao++;
				configuracao.setNumeroSolicitacao(numeroSolicitacao);
			} else { // valor nao definido no banco
				numeroSolicitacao = 1;
				configuracao.setNumeroSolicitacao(numeroSolicitacao);
			}

			DecimalFormat nf = new DecimalFormat("00000");
			strNum.append(ano).append(nf.format(numeroSolicitacao));

			getConfiguracaoService().atualizar(configuracao);
		}

		return strNum.toString();
	}

	private IMailService getMailSend() {
		return mailSend;
	}

	public void setMailSend(IMailService mailSend) {
		this.mailSend = mailSend;
	}

	private IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public IConfiguracaoService getConfiguracaoService() {
		return configuracaoService;
	}

	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

}
