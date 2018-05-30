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

import com.util.enums.TipoAnalise;

@Entity 
@Table(name = "itempadrao")
public class ItemPadrao implements Serializable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iditempadrao")
	private Integer idItemPadrao = 0;

	@Column(name = "codigolegislacao")
	private String codigoLegislacao;

//	@Column(name = "toleranciaindicativa")
//	private String toleranciaIndicativa;
	
//	@Column(name = "toleranciarepresentativa_n")
//	private String toleranciaRepresentivaN;

//	@Column(name = "toleranciarepresentativa_c")
//	private String toleranciaRepresentivaC;
	
//	@Column(name = "tolerancialiminf")
//	private String toleranciaLimInf;

//	@Column(name = "tolerancialimsup")
//	private String toleranciaLimSup;

	@Column(name = "tipoanalise")
	private TipoAnalise tipoAnalise;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "idpadrao")
	private Padrao padrao;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "idmicrorganismo")
	private Microrganismo microrganismo;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "idmitemgrupo")
	private ItemGrupo itemGrupo;

	@Column(name = "unidademedida")
	private String unidadeMedida;

//	@Column(name = "limitetolerancia")
//	private String limiteTolerancia;

	@Column(name = "referencia")
	private String referencia;

	public ItemPadrao() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getIdItemPadrao() {
		return idItemPadrao;
	}

	public void setIdItemPadrao(Integer idItemPadrao) {
		this.idItemPadrao = idItemPadrao;
	}

	public String getCodigoLegislacao() {
		return codigoLegislacao;
	}

	public void setCodigoLegislacao(String codigoLegislacao) {
		this.codigoLegislacao = codigoLegislacao;
	}

	public TipoAnalise getTipoAnalise() {
		return tipoAnalise;
	}

	public void setTipoAnalise(TipoAnalise tipoAnalise) {
		this.tipoAnalise = tipoAnalise;
	}

	public Padrao getPadrao() {
		return padrao;
	}

	public void setPadrao(Padrao padrao) {
		this.padrao = padrao;
	}

	public Microrganismo getMicrorganismo() {
		return microrganismo;
	}

	public void setMicrorganismo(Microrganismo microrganismo) {
		this.microrganismo = microrganismo;
	}

	public ItemGrupo getItemGrupo() {
		return itemGrupo;
	}

	public void setItemGrupo(ItemGrupo itemGrupo) {
		this.itemGrupo = itemGrupo;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public String toString() {
		return getPadrao().getNome();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idItemPadrao == null) ? 0 : idItemPadrao.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPadrao other = (ItemPadrao) obj;
		if (idItemPadrao == null) {
			if (other.idItemPadrao != null)
				return false;
		} else if (!idItemPadrao.equals(other.idItemPadrao))
			return false;
		return true;
	}

}
