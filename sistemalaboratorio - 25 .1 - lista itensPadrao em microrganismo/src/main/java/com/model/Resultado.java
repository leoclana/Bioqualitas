package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name = "resultado")
public class Resultado implements Serializable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idresultado")
	private Integer idResultado = 0;

	@Column(name = "codigolegislacao")
	private String codigoLegislacao;
	
	@Column(name = "toleranciaindicativa")
	private String toleranciaIndicativa;
	
	@Column(name = "toleranciarepresentativa_n")
	private String toleranciaRepresentivaN;

	@Column(name = "toleranciarepresentativa_c")
	private String toleranciaRepresentivaC;	
	
	@Column(name = "tolerancialiminf")
	private String toleranciaLimInf;

	@Column(name = "tolerancialimsup")
	private String toleranciaLimSup;

	@Column(name = "toleranciaindicativa_comp")
	private String toleranciaIndicativaComp;
	
	@Column(name = "toleranciarepresentativa_n_comp")
	private String toleranciaRepresentivaNComp;

	@Column(name = "toleranciarepresentativa_c_comp")
	private String toleranciaRepresentivaCComp;	
	
	@Column(name = "tolerancialiminf_comp")
	private String toleranciaLimInfComp;

	@Column(name = "tolerancialimsup_comp")
	private String toleranciaLimSupComp;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "amostra")
	private Amostra amostra;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "microrganismo")
	private Microrganismo microrganismo;

	@Column(name = "ativo")
	private Boolean ativo=true;

	@Column(name = "comparativo")
	private Boolean comparativo;
	
	@Column(name = "positivo")
	private Boolean positivo;
	
	@Column(name = "padrao")
	private Boolean patrao;
	
	@Column(name = "unidademedida")
	private String unidadeMedida;

	@Column(name = "limitetolerancia")
	private String limiteTolerancia;

	public Resultado() {
	}

	public Integer getIdResultado() {
		return idResultado;
	}

	public void setIdResultado(Integer idResultado) {
		this.idResultado = idResultado;
	}

	public String getCodigoLegislacao() {
		return codigoLegislacao;
	}

	public void setCodigoLegislacao(String codigoLegislacao) {
		this.codigoLegislacao = codigoLegislacao;
	}

	public String getToleranciaIndicativaComp() {
		return toleranciaIndicativaComp;
	}

	public void setToleranciaIndicativaComp(String toleranciaIndicativaComp) {
		this.toleranciaIndicativaComp = toleranciaIndicativaComp;
	}

	public String getToleranciaRepresentivaNComp() {
		return toleranciaRepresentivaNComp;
	}

	public void setToleranciaRepresentivaNComp(String toleranciaRepresentivaNComp) {
		this.toleranciaRepresentivaNComp = toleranciaRepresentivaNComp;
	}

	public String getToleranciaRepresentivaCComp() {
		return toleranciaRepresentivaCComp;
	}

	public void setToleranciaRepresentivaCComp(String toleranciaRepresentivaCComp) {
		this.toleranciaRepresentivaCComp = toleranciaRepresentivaCComp;
	}

	public String getToleranciaLimInfComp() {
		return toleranciaLimInfComp;
	}

	public void setToleranciaLimInfComp(String toleranciaLimInfComp) {
		this.toleranciaLimInfComp = toleranciaLimInfComp;
	}

	public String getToleranciaLimSupComp() {
		return toleranciaLimSupComp;
	}

	public void setToleranciaLimSupComp(String toleranciaLimSupComp) {
		this.toleranciaLimSupComp = toleranciaLimSupComp;
	}

	public String getToleranciaIndicativa() {
		return toleranciaIndicativa;
	}

	public void setToleranciaIndicativa(String toleranciaIndicativa) {
		this.toleranciaIndicativa = toleranciaIndicativa;
	}

	public String getToleranciaRepresentivaN() {
		return toleranciaRepresentivaN;
	}

	public void setToleranciaRepresentivaN(String toleranciaRepresentivaN) {
		this.toleranciaRepresentivaN = toleranciaRepresentivaN;
	}

	public String getToleranciaRepresentivaC() {
		return toleranciaRepresentivaC;
	}

	public void setToleranciaRepresentivaC(String toleranciaRepresentivaC) {
		this.toleranciaRepresentivaC = toleranciaRepresentivaC;
	}

	public String getToleranciaLimInf() {
		return toleranciaLimInf;
	}

	public void setToleranciaLimInf(String toleranciaLimInf) {
		this.toleranciaLimInf = toleranciaLimInf;
	}

	public String getToleranciaLimSup() {
		return toleranciaLimSup;
	}

	public void setToleranciaLimSup(String toleranciaLimSup) {
		this.toleranciaLimSup = toleranciaLimSup;
	}

	public Microrganismo getMicrorganismo() {
		return microrganismo;
	}

	public void setMicrorganismo(Microrganismo microrganismo) {
		this.microrganismo = microrganismo;
	}

	public Amostra getAmostra() {
		return amostra;
	}

	public void setAmostra(Amostra amostra) {
		this.amostra = amostra;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getComparativo() {
		return comparativo;
	}

	public void setComparativo(Boolean comparativo) {
		this.comparativo = comparativo;
	}
	
	public Boolean getPositivo() {
		return positivo;
	}

	public void setPositivo(Boolean positivo) {
		this.positivo = positivo;
	}

	public Boolean getPatrao() {
		return patrao;
	}

	public void setPatrao(Boolean patrao) {
		this.patrao = patrao;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public String getLimiteTolerancia() {
		return limiteTolerancia;
	}

	public void setLimiteTolerancia(String limiteTolerancia) {
		this.limiteTolerancia = limiteTolerancia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idResultado == null) ? 0 : idResultado.hashCode());
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
		Resultado other = (Resultado) obj;
		if (idResultado == null) {
			if (other.idResultado != null)
				return false;
		} else if (!idResultado.equals(other.idResultado))
			return false;
		return true;
	}

}
