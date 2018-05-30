package com.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.util.enums.StatusSolicitacaoEnum;

@Entity
@Table(name="solicitacao")
public class Solicitacao implements Serializable, ILoggable {

	private static final long serialVersionUID = 5990103273131562661L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsolicitacao")
	private Integer idSolicitacao = 0;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "idcliente")
	private Cliente cliente;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "idformapagamento")
	private FormaPagamento formaPagamento;
	
	@Column(name = "status")
	private StatusSolicitacaoEnum status;
	
    @Column(name = "datasolicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;
	
    @Column(name = "dataliberacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLiberacao;
    
	@Column(name = "ativo")
	private Boolean ativo;
	
    @Column(name="strNumero")
    private String strNumero;
    
    @Column(name="countAlimento")
    private Integer countAlimento = 0;

    @Column(name="countManupulador")
    private Integer countManupulador = 0;
    
    @Column(name="countSuperficie")
    private Integer countSuperficie = 0;
    
    @Column(name="countAgua")
    private Integer countAgua = 0;
    
    @Column(name="countAguaMineral")
    private Integer countAguaMineral = 0;
   
    @Column(name="countAr")
    private Integer countAr = 0;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "solicitacao", targetEntity = Amostra.class)
	private Set<Amostra> amostras;
    
    
	public Solicitacao() {
		this.ativo=true;
		this.status= StatusSolicitacaoEnum.SOLICITADO;
		this.dataSolicitacao = new Date();
	}

	public Integer getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(Integer idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public StatusSolicitacaoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusSolicitacaoEnum status) {
		this.status = status;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getStrNumero() {
		return strNumero;
	}

	public void setStrNumero(String strNumero) {
		this.strNumero = strNumero;
	}

	public Integer getCountAlimento() {
		return countAlimento;
	}

	public void setCountAlimento(Integer countAlimento) {
		this.countAlimento = countAlimento;
	}

	public Integer getCountManupulador() {
		return countManupulador;
	}

	public void setCountManupulador(Integer countManupulador) {
		this.countManupulador = countManupulador;
	}

	public Integer getCountSuperficie() {
		return countSuperficie;
	}

	public void setCountSuperficie(Integer countSuperficie) {
		this.countSuperficie = countSuperficie;
	}

	public Integer getCountAgua() {
		return countAgua;
	}

	public void setCountAgua(Integer countAgua) {
		this.countAgua = countAgua;
	}

	public Integer getCountAguaMineral() {
		return countAguaMineral;
	}

	public void setCountAguaMineral(Integer countAguaMineral) {
		this.countAguaMineral = countAguaMineral;
	}

	public Integer getCountAr() {
		return countAr;
	}

	public void setCountAr(Integer countAr) {
		this.countAr = countAr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idSolicitacao == null) ? 0 : idSolicitacao.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solicitacao other = (Solicitacao) obj;
		if (idSolicitacao == null) {
			if (other.idSolicitacao != null)
				return false;
		} else if (!idSolicitacao.equals(other.idSolicitacao))
			return false;
		return true;
	}

	@Override
	public String getIdentifier() {
		return getStrNumero();
	}

	public String getDataSolicitacaoFormat(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(dataSolicitacao != null) {
			return dateFormat.format(dataSolicitacao);
		} else{
			return  null;
		}
	}

	public String getDataLiberacaoFormat(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(dataLiberacao != null) {
			return dateFormat.format(dataLiberacao);
		} else{
			return  null;
		}
	}

	public String getDataPrazoFormat(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(prazo != null) {
			return dateFormat.format(prazo);
		} else{
			return  null;
		}
	}

	public Set<Amostra> getAmostras() {
		return amostras;
	}

	public void setAmostras(Set<Amostra> amostras) {
		this.amostras = amostras;
	}

	@Transient
	private Date prazo;

	public Date getPrazo(){
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}
}
