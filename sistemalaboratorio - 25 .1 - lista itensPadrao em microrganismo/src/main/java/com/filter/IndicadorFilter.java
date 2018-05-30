package com.filter;

import java.util.Date;

import com.model.Cliente;
import com.model.MotivoAnalise;

public class IndicadorFilter {

	private boolean ativo = true;

	private MotivoAnalise motivoAnalise;

	private Cliente cliente;

	private Date dataInicioSolic;
	private Date dataFimSolic;

	private Boolean inTime;
	private Boolean positive;

	public IndicadorFilter() {
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public MotivoAnalise getMotivoAnalise() {
		return motivoAnalise;
	}

	public void setMotivoAnalise(MotivoAnalise motivoAnalise) {
		this.motivoAnalise = motivoAnalise;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataInicioSolic() {
		return dataInicioSolic;
	}

	public void setDataInicioSolic(Date dataInicioSolic) {
		this.dataInicioSolic = dataInicioSolic;
	}

	public Date getDataFimSolic() {
		return dataFimSolic;
	}

	public void setDataFimSolic(Date dataFimSolic) {
		this.dataFimSolic = dataFimSolic;
	}

	public Boolean isInTime() {
		return inTime;
	}
	
	public void setInTime(Boolean b) {
		inTime = b;
	}

	public Boolean isPositive() {
		return positive;
	}

	public void setPositive(Boolean b) {
		positive = b;
	}
}
