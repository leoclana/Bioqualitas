package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "configuracao")
public class Configuracao  implements Serializable{

	private static final long serialVersionUID = 1159703043633002836L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idconfiguracao")
	private Integer idConfiguracao = 0;

	@Column(name = "anoCorrente")
	private Integer anoCorrente;
	
    @Column(name="numeroSolicitacao")
    private Integer numeroSolicitacao=0;
	
	@Column(name = "alimento")
	private Integer alimento;
	
	@Column(name = "manipulador")
	private Integer manipulador;
	
	@Column(name = "superficie")
	private Integer superficie;
	
	@Column(name = "agua")
	private Integer agua;
	
	@Column(name = "aguamineral")
	private Integer aguaMineral;
	
	@Column(name = "ar")
	private Integer ar;
	
	@Column(name="encerramentodd")
	private Integer encerramentodd;
	
	@Column(name="encerramentohh")
	private Integer encerramentohh;
	
	
	public Integer getIdConfiguracao() {
		return idConfiguracao;
	}

	public void setIdConfiguracao(Integer idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	public Integer getAnoCorrente() {
		return anoCorrente;
	}

	public void setAnoCorrente(Integer anoCorrente) {
		this.anoCorrente = anoCorrente;
	}

	public Integer getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(Integer numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}

	public Integer getAlimento() {
		return alimento;
	}

	public void setAlimento(Integer alimento) {
		this.alimento = alimento;
	}

	public Integer getManipulador() {
		return manipulador;
	}

	public void setManipulador(Integer manipulador) {
		this.manipulador = manipulador;
	}

	public Integer getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Integer superficie) {
		this.superficie = superficie;
	}

	public Integer getAgua() {
		return agua;
	}

	public void setAgua(Integer agua) {
		this.agua = agua;
	}

	public Integer getAguaMineral() {
		return aguaMineral;
	}

	public void setAguaMineral(Integer aguaMineral) {
		this.aguaMineral = aguaMineral;
	}

	public Integer getAr() {
		return ar;
	}

	public void setAr(Integer ar) {
		this.ar = ar;
	}

	public Integer getEncerramentodd() {
		return encerramentodd;
	}

	public void setEncerramentodd(Integer encerramentodd) {
		this.encerramentodd = encerramentodd;
	}

	public Integer getEncerramentohh() {
		return encerramentohh;
	}

	public void setEncerramentohh(Integer encerramentohh) {
		this.encerramentohh = encerramentohh;
	}

}
