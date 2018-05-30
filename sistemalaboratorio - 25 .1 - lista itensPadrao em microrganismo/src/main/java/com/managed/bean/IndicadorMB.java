package com.managed.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.model.Amostra;
import com.model.Cliente;
import com.model.MotivoAnalise;
import com.service.IAmostraService;
import com.service.IClienteService;
import com.service.IMotivoService;
import com.util.enums.TipoIndicador;
import com.util.view.SelectOneDataModel;

public class IndicadorMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private CartesianChartModel cartesianModel;
	private PieChartModel pieModel;;

	private TipoIndicador tipoIndicador;
	private MotivoAnalise motivoAnalise;
	private Cliente cliente;

	private Date pBegin;
	private Date pEnd;

	private IClienteService clienteService;
	private IMotivoService motivoService;
	private IAmostraService amostraService;

	public IndicadorMB(IClienteService cs, IMotivoService ms, IAmostraService as) {
		this.clienteService = cs;
		this.motivoService = ms;
		this.amostraService = as;
	}

	public void onTipoIndicadorChange() {
	}

	public String gerar() {
		List<SelectItem> keys = obterChaves();
		
		if ((isIndicador3() || isIndicador4()) && (keys.size() == 1)) {
			gerarPieYinYang(keys.get(0));
			gerarCartesianYinYang(keys.get(0));
		} else {
			gerarPie(keys);
			gerarCartesian(keys);
		}

		return null;
	}

	private void gerarPieYinYang(SelectItem key) {
		pieModel = new PieChartModel();

		String labelYin = getLabel(key.getLabel(), true);
		Number porcentagemYin = porcentagem(key.getValue(), getpBegin(), getpEnd(), true);

		pieModel.set(labelYin, porcentagemYin);

		String labelYang = getLabel(key.getLabel(), false);
		Number porcentagemYang = porcentagem(key.getValue(), getpBegin(), getpEnd(), false);

		pieModel.set(labelYang, porcentagemYang);
	}

	private void gerarCartesianYinYang(SelectItem key) {
		cartesianModel = new CartesianChartModel();

		ChartSeries csYin = new ChartSeries();

		String labelYin = getLabel(key.getLabel(), true);
		csYin.setLabel(labelYin);

		popular(key.getValue(), csYin, true);

		cartesianModel.addSeries(csYin);

		ChartSeries csYang = new ChartSeries();

		String labelYang = getLabel(key.getLabel(), false);
		csYang.setLabel(labelYang);


		popular(key.getValue(), csYang, false);
		
		cartesianModel.addSeries(csYang);
	}

	private void gerarPie(List<SelectItem> keys) {
		pieModel = new PieChartModel();

		String label;
		Number porcentagem;

		for (SelectItem key : keys) {
			if (key == null) {
				continue;
			}
			
			label = getLabel(key.getLabel(), null);
			porcentagem = porcentagem(key.getValue(), getpBegin(), getpEnd(), true);

			pieModel.set(label, porcentagem);

		}
	}

	private void gerarCartesian(List<SelectItem> keys) {
		cartesianModel = new CartesianChartModel();

		ChartSeries cs;
		String label;

		for (SelectItem key : keys) {
			if (key == null) {
				continue;
			}

			cs = new ChartSeries();

			label = getLabel(key.getLabel(), null);
			cs.setLabel(label);

			popular(key.getValue(), cs, true);

			cartesianModel.addSeries(cs);
		}
	}

	private List<SelectItem> obterChaves() {
		List<SelectItem> retorno;
		
		if (isIndicador1()) {
			if (getMotivoAnalise().getIdMotivo() != 0) {
				retorno = new ArrayList<SelectItem>();
				SelectItem si = new SelectItem();
				si.setValue(getMotivoAnalise());
				si.setLabel(getMotivoAnalise().getNome());
				retorno.add(si);
			} else {
				retorno = getMotivoAnaliseList();
			}
		} else {
			if (getCliente().getIdCliente() != 0) {
				retorno = new ArrayList<SelectItem>();
				SelectItem si = new SelectItem();
				si.setValue(getCliente());
				si.setLabel(getCliente().getNome());
				retorno.add(si);
			} else {
				retorno = getClienteList();
			}
		}
		
		SelectItem key  = retorno.get(0);
		if (key.getValue() == null) {
				retorno.remove(0);
		}
		
		return retorno;
	}

	private void popular(Object key, ChartSeries cs, boolean b) {
		Calendar cb = Calendar.getInstance();
		cb.setTime(getpBegin());

		int ia = cb.get(Calendar.YEAR);
		int im = cb.get(Calendar.MONTH);
		int idm = cb.get(Calendar.DAY_OF_MONTH);
		int isa = cb.get(Calendar.WEEK_OF_YEAR);

		Calendar ce = Calendar.getInstance();
		ce.setTime(getpEnd());

		int fa = ce.get(Calendar.YEAR);
		int fm = ce.get(Calendar.MONTH);
		int fdm = ce.get(Calendar.DAY_OF_MONTH);
		int fsa = ce.get(Calendar.WEEK_OF_YEAR);

		if ((fa - ia) > 0) {
			if ((fa - ia) > 1) {
				popular(Calendar.YEAR, ia, fa, key, cs, b);
			} else {
				popular(Calendar.MONTH, ia, im, fa, fm, key, cs, b);
			}
		} else if ((fm - im) > 0) {
			if ((fm - im) > 3) {
				popular(Calendar.MONTH, im, fm, key, cs, b);
			} else {
				popular(Calendar.WEEK_OF_YEAR, isa, fsa, key, cs, b);
			}
		} else {
			popular(Calendar.DAY_OF_MONTH, idm, fdm, key, cs, b);
		}

	}

	private void popular(int p, int i, int f, Object key, ChartSeries cs, boolean b) {
		SimpleDateFormat sdf = getSDF(p);
		
		for (int it = i; it <= f; it++) {
			Date d2 = getD2(p, i, it);			
			cs.set(sdf.format(d2), porcentagem(key, null, d2, b));
		}
	}

	private SimpleDateFormat getSDF(int p) {
		switch (p) {
		case Calendar.YEAR:
			return new SimpleDateFormat("yyyy");
		case Calendar.MONTH:
			return new SimpleDateFormat("MM/yyyy");
		default:
			return new SimpleDateFormat("dd/MM/yyyy");
		}
	}

	private void popular(int p, int ia, int im, int fa, int fm, Object key,
			ChartSeries cs, boolean b) {
		SimpleDateFormat sdf = getSDF(p);
		
		if (Calendar.MONTH == p) {
			int im2 = im + 1;
			int fm2 = 13;
			for (int i = ia; i <= fa; i++) {
				for (int j = im2; j < fm2; j++) {
					Date d2 = getD2(p, im, ia, j, i);
					cs.set(sdf.format(d2), porcentagem(key, null, d2, b));
				}
				im2 = 1;
				if ((fa - i) == 1) {
					fm2 = fm + 1;
				}
			}
		}
	}

	private Date getD2(int p, int i1, int i2) {
		Calendar c = Calendar.getInstance();
		c.setTime(getpBegin());

		int amount = i2 - i1;

		c.roll(p, amount);
		Date d2 = c.getTime();
		return d2;
	}

	private Date getD2(int p, int i1, int j1, int i2, int j2) {
		Calendar c = Calendar.getInstance();
		c.setTime(getpBegin());

		int amount = 1;

		int im = i1 + 1;
		int fm = 13;
		for (int i = j1; i <= j2; i++) {
			for (int j = im; j < fm; j++) {
				amount++;
			}
			im = 1;
			if ((j2 - i) == 1) {
				fm = i2 + 1;
			}
		}

		c.roll(p, amount);
		Date d2 = c.getTime();
		return d2;
	}

	private Number porcentagem(Object key, Date d1, Date d2, boolean b) {
		int filtradas = executaConsulta(key, d1, d2, b);
		int total = executaConsulta(null, d1, d2, b);
		return (filtradas / total) * 100;
	}

	private int executaConsulta(Object obj, Date d1, Date d2, boolean b) {
		List<Amostra> lista = new ArrayList<Amostra>();

		if (isIndicador1()) {
			lista = getAmostraService().getAllByMotivoAnaliseInPeriod(
					(MotivoAnalise) obj, d1, d2);
		} else if (isIndicador2()) {
			lista = getAmostraService().getAllByClienteInPeriod(
					(Cliente) obj, d1, d2);
		} else if (isIndicador3()) {
			if	(b) {
				lista = getAmostraService().getAllInTimeByClienteInPeriod(
					(Cliente) obj, d1, d2);
			}else{
				lista = getAmostraService().getAllLateByClienteInPeriod(
						(Cliente) obj, d1, d2);
			}
		} else if (isIndicador4()) {
			if	(b) {
				lista = getAmostraService().getAllAprovedByClienteInPeriod(
					(Cliente) obj, d1, d2);
			} else {
				lista = getAmostraService().getAllReprovedByClienteInPeriod(
						(Cliente) obj, d1, d2);
			}
		}

		if (lista != null && lista.size() > 0) {
			return lista.size();
		} else {
			return (obj == null) ? 1 : 0;
		}

	}

	private String getLabel(String key, Boolean yin) {

		String inicio = key;

		String fim = "";
		if (yin != null) {
			if (yin) {
				if (isIndicador3()) {
					fim = "- No Prazo";
				} else {
					fim = "- Aprovado";
				}
			} else {
				if (isIndicador3()) {
					fim = "- Atrasado";
				} else {
					fim = "- Reprovado";
				}
			}
		}

		return inicio + fim;
	}
	
	public boolean isIndicador1() {
		return TipoIndicador.INDICADOR1.equals(getTipoIndicador());
	}

	public boolean isIndicador2() {
		return TipoIndicador.INDICADOR2.equals(getTipoIndicador());
	}

	public boolean isIndicador3() {
		return TipoIndicador.INDICADOR3.equals(getTipoIndicador());
	}

	public boolean isIndicador4() {
		return TipoIndicador.INDICADOR4.equals(getTipoIndicador());
	}

	public TipoIndicador[] getTipoIndicadorList() {
		return TipoIndicador.values();
	}

	public List<SelectItem> getMotivoAnaliseList() {
		return SelectOneDataModel.criaComTextoInicialPersolanizado(
				getMotivoService().getAll(), "Todos").getListaSelecao();
	}

	public List<SelectItem> getClienteList() {
		return SelectOneDataModel.criaComTextoInicialPersolanizado(
				getClienteService().getAll(), "Todos").getListaSelecao();
	}

	public CartesianChartModel getCartesianModel() {
		return cartesianModel;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public TipoIndicador getTipoIndicador() {
		return tipoIndicador;
	}

	public void setTipoIndicador(TipoIndicador ti) {
		this.tipoIndicador = ti;
	}

	public Cliente getCliente() {
		if (cliente == null)
			cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente c) {
		this.cliente = c;
	}

	public MotivoAnalise getMotivoAnalise() {
		if (motivoAnalise == null)
			motivoAnalise = new MotivoAnalise();
		return motivoAnalise;
	}

	public void setMotivoAnalise(MotivoAnalise ma) {
		this.motivoAnalise = ma;
	}

	public Date getpBegin() {
		return pBegin;
	}

	public void setpBegin(Date pBegin) {
		this.pBegin = pBegin;
	}

	public Date getpEnd() {
		return pEnd;
	}

	public void setpEnd(Date pEnd) {
		this.pEnd = pEnd;
	}

	private IClienteService getClienteService() {
		return clienteService;
	}
	
	public IMotivoService getMotivoService() {
		return motivoService;
	}

	private IAmostraService getAmostraService() {
		return amostraService;
	}

}
