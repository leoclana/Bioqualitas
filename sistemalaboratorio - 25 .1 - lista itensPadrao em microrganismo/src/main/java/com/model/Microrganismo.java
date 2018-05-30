package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "microrganismo")
public class Microrganismo implements Serializable, ILoggable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmicrorganismo")
	private Integer idMicrorganismo = 0;

	@Column(name = "nome", unique = true, nullable = false, length = 100)
	private String nome;

	@Column(name = "ativo")
	private Boolean ativo = true;

	public List<ItemPadrao> getItensPadrao() {
		return itensPadrao;
	}

	public void setItensPadrao(List<ItemPadrao> itensPadrao) {
		this.itensPadrao = itensPadrao;
	}

	@OneToMany(mappedBy = "microrganismo", targetEntity = ItemPadrao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItemPadrao> itensPadrao;

	public Microrganismo() {
	}

	public Integer getIdMicrorganismo() {
		return idMicrorganismo;
	}

	public void setIdMicrorganismo(Integer id) {
		this.idMicrorganismo = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return getNome();
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
		result = prime * result + ((idMicrorganismo == null) ? 0 : idMicrorganismo.hashCode());
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
		Microrganismo other = (Microrganismo) obj;
		if (idMicrorganismo == null) {
			if (other.idMicrorganismo != null)
				return false;
		} else if (!idMicrorganismo.equals(other.idMicrorganismo))
			return false;
		return true;
	}

	@Override
	public String getIdentifier() {
		return getNome();
	}

}
