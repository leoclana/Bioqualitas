package com.service.impl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IAmostraDao;
import com.filter.IndicadorFilter;
import com.model.Amostra;
import com.model.Cliente;
import com.model.MotivoAnalise;
import com.model.Solicitacao;
import com.model.Usuario;
import com.service.IAmostraService;
import com.service.IUsuarioService;
import com.service.mail.IMailService;
import com.util.enums.AcoesLog;
import com.util.enums.TipoAnalise;

@Transactional(readOnly = true)
public class AmostraService extends Service implements Serializable, IAmostraService {

	private static final long serialVersionUID = 1L;

	private IAmostraDao amostraDAO;
	private IUsuarioService usuarioService;
	private IMailService mailSend;

	@Override
	@Transactional(readOnly = false)
	public void salvar(Amostra amostra) {
		amostra.setStrNumero(gerarNumeracao(amostra,true));
		getAmostraDAO().adicionar(amostra);
		logar(AcoesLog.adicionou, amostra);
	}

	@Override
	@Transactional(readOnly = false)
	public void atualizar(Amostra amostra) {
		amostra.setStrNumero(gerarNumeracao(amostra,false));
		getAmostraDAO().atualizar(amostra);
		logar(AcoesLog.atualizou, amostra);
	}

	@Override
	@Transactional(readOnly = false)
	public void deletar(Amostra amostra) {
		Amostra amostraPersistida = getAmostraDAO().recuperar(Amostra.class, 
				amostra.getIdAmostra());
		amostraPersistida.setAtivo(false);
		getAmostraDAO().atualizar(amostraPersistida);
		logar(AcoesLog.deletou, amostra);
	}

	@Override
	public List<Amostra> getAll() {
		Amostra amostraPorAmostra = new Amostra();
		amostraPorAmostra.setAtivo(true);
		amostraPorAmostra.setDataConclusaoAnalise(null);
		amostraPorAmostra.setDataInicioAnalise(null);
		try {
			return getAmostraDAO().<Amostra>recuperarPorAtributoPreenchido(
					amostraPorAmostra);
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
	public List<Amostra> getAllBySolicitacao(Solicitacao solicitacao) {
		Amostra amostraPorAmostra = new Amostra();
		amostraPorAmostra.setAtivo(true);
		amostraPorAmostra.setCongelado(null);
		amostraPorAmostra.setSolicitacao(solicitacao);

		//return getAmostraDAO().recuperarPorAtributoPreenchido(amostraPorAmostra);

		//*** hibernate pasou atrazer duplicado depois que condicaoamostra pasou a ser lista. por isso loop para tirar duplicados.
		List<Amostra> amostras = new ArrayList<Amostra>();
		try {
			List<Amostra> amostrastmp = getAmostraDAO().recuperarPorAtributoPreenchido(amostraPorAmostra);
			Amostra amostraOld = null;
			for(Amostra amostra : amostrastmp){
				if(!amostra.equals(amostraOld)){
					amostras.add(amostra);
					amostraOld = amostra;
				}
			}
			return amostras;
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
	public void invalidarAmostra(Amostra amostra,String justificativa,Solicitacao solicitacao) {
		amostra.setInvalida(true);
		amostra.setJustificativa(justificativa);
		getAmostraDAO().atualizar(amostra);

		List<Usuario> users  = new ArrayList<Usuario>();

		Usuario  contatoPrincipal = getUsuarioService().buscaPorClienteContatoPrincipal(solicitacao.getCliente());
		if (contatoPrincipal !=null){
			users.add(contatoPrincipal);
		}

		List<Usuario> userAdministradores = getUsuarioService().getAdministradores();
		if(userAdministradores!=null ){
			users.addAll(userAdministradores);
		}
		getMailSend().invalidarAmostra(users, amostra.getIdAmostra().toString(), justificativa);
		logar(AcoesLog.invalidou, amostra);
	}

	@Override
	public List<Amostra> getAllByFilter(IndicadorFilter filtro) {
		return this.amostraDAO.recuperarPorFiltro(filtro);
	}


	private String gerarNumeracao(Amostra amostra,boolean metodoSave){

		Solicitacao solicitacao = amostra.getSolicitacao();
		
		StringBuilder strNum = new StringBuilder();
		strNum.append(solicitacao.getStrNumero());
		strNum.append(amostra.getTipoAnalise().toString().toUpperCase().substring(0, 2));
		
		Integer count = new Integer(0) ;

		if( amostra.getTipoAnalise() == TipoAnalise.ALIMENTO){
			count = solicitacao.getCountAlimento();
			if (metodoSave){
				count++;
				solicitacao.setCountManupulador(count);
			}
		} else if (amostra.getTipoAnalise() == TipoAnalise.MANIPULADOR){
			count = solicitacao.getCountManupulador();
			if (metodoSave) {
				count++;
				solicitacao.setCountManupulador(count);
			}
		} else if (amostra.getTipoAnalise() == TipoAnalise.SUPERFICIE){
			count = solicitacao.getCountSuperficie();
			if (metodoSave) {
				count++;
				solicitacao.setCountSuperficie(count);
			}
		} else if(amostra.getTipoAnalise() == TipoAnalise.AGUA){
			count = solicitacao.getCountAgua();
			if (metodoSave){
				count++;
				solicitacao.setCountAgua(count);
			}
		} else if (amostra.getTipoAnalise() == TipoAnalise.AGUAMINERAL){
			count = solicitacao.getCountAguaMineral();
			if (metodoSave) {
				count++;
				solicitacao.setCountAguaMineral(count);
			}
		} else if(amostra.getTipoAnalise() == TipoAnalise.AR){
			count = solicitacao.getCountAr();
			if (metodoSave){
				count++;
				solicitacao.setCountAr(count);
			}
		}

		if (metodoSave) {
			getDao().<Solicitacao>atualizar(solicitacao); 
		}

		DecimalFormat nf = new DecimalFormat("000");
		strNum.append(nf.format(count));
		return strNum.toString();
	}


	@Override
	public List<Amostra> getAllByMotivoAnaliseInPeriod(MotivoAnalise motivo,
			Date d1, Date d2) {
		IndicadorFilter filtro = new IndicadorFilter();

		filtro.setDataInicioSolic(d1);
		filtro.setDataFimSolic(d2);

		if (motivo == null) {
			filtro.setCliente(null);
			filtro.setMotivoAnalise(null);
		} else {
			filtro.setMotivoAnalise(motivo);
			filtro.setCliente(null);
		}

		filtro.setInTime(null);
		filtro.setPositive(null);

		return amostraDAO.recuperarPorFiltro(filtro);
	}

	@Override
	public List<Amostra> getAllByClienteInPeriod(Cliente cliente, Date d1, Date d2) {

		IndicadorFilter filtro = new IndicadorFilter();

		filtro.setDataInicioSolic(d1);
		filtro.setDataFimSolic(d2);

		if (cliente == null) {
			filtro.setCliente(null);
			filtro.setMotivoAnalise(null);
		} else {
			filtro.setCliente(cliente);
			filtro.setMotivoAnalise(null);
		}

		filtro.setInTime(null);
		filtro.setPositive(null);

		return amostraDAO.recuperarPorFiltro(filtro);
	}

	@Override
	public List<Amostra> getAllInTimeByClienteInPeriod(Cliente cliente, Date d1,
			Date d2) {

		IndicadorFilter filtro = new IndicadorFilter();

		filtro.setDataInicioSolic(d1);
		filtro.setDataFimSolic(d2);

		if (cliente == null) {
			filtro.setCliente(null);
			filtro.setMotivoAnalise(null);
		} else {
			filtro.setCliente(cliente);
			filtro.setMotivoAnalise(null);
		}

		filtro.setInTime(true);
		filtro.setPositive(null);

		return amostraDAO.recuperarPorFiltro(filtro);
	}

	@Override
	public List<Amostra> getAllAprovedByClienteInPeriod(Cliente cliente, Date d1,
			Date d2) {

		IndicadorFilter filtro = new IndicadorFilter();

		filtro.setDataInicioSolic(d1);
		filtro.setDataFimSolic(d2);

		if (cliente == null) {
			filtro.setCliente(null);
			filtro.setMotivoAnalise(null);
		} else {
			filtro.setCliente(cliente);
			filtro.setMotivoAnalise(null);
		}

		filtro.setInTime(null);
		filtro.setPositive(true);

		return amostraDAO.recuperarPorFiltro(filtro);
	}

	@Override
	public List<Amostra> getAllLateByClienteInPeriod(Cliente cliente, Date d1,
			Date d2) {

		IndicadorFilter filtro = new IndicadorFilter();

		filtro.setDataInicioSolic(d1);
		filtro.setDataFimSolic(d2);

		if (cliente == null) {
			filtro.setCliente(null);
			filtro.setMotivoAnalise(null);
		} else {
			filtro.setCliente(cliente);
			filtro.setMotivoAnalise(null);
		}

		filtro.setInTime(false);
		filtro.setPositive(null);

		return amostraDAO.recuperarPorFiltro(filtro);
	}

	@Override
	public List<Amostra> getAllReprovedByClienteInPeriod(Cliente cliente, Date d1,
			Date d2) {

		IndicadorFilter filtro = new IndicadorFilter();

		filtro.setDataInicioSolic(d1);
		filtro.setDataFimSolic(d2);

		if (cliente == null) {
			filtro.setCliente(null);
			filtro.setMotivoAnalise(null);
		} else {
			filtro.setCliente(cliente);
			filtro.setMotivoAnalise(null);
		}

		filtro.setInTime(null);
		filtro.setPositive(false);

		return amostraDAO.recuperarPorFiltro(filtro);
	}


	public IAmostraDao getAmostraDAO() {
		return amostraDAO;
	}

	public void setAmostraDAO(IAmostraDao amostraDAO) {
		this.amostraDAO = amostraDAO;
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	private IMailService getMailSend() {
		return mailSend;
	}

	public void setMailSend(IMailService mailSend) {
		this.mailSend = mailSend;
	}

}
