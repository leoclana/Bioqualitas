package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "condicaoamostra")
public class CondicaoAmostra implements Serializable {

	private static final long serialVersionUID = -5517725821594859583L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcondicaoamostra")
	private Integer idCondicaoAmostra = 0;

	@Column(name = "nome", unique = true, nullable = false, length = 50)
	private String nome;

	@Column(name = "ativo")
	private Boolean ativo = true;

	public CondicaoAmostra() {
	}

	public Integer getIdCondicaoAmostra() {
		return idCondicaoAmostra;
	}

	public void setIdCondicaoAmostra(Integer idCondicaoAmostra) {
		this.idCondicaoAmostra = idCondicaoAmostra;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idCondicaoAmostra == null) ? 0 : idCondicaoAmostra.hashCode());
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
		CondicaoAmostra other = (CondicaoAmostra) obj;
		if (idCondicaoAmostra == null) {
			if (other.idCondicaoAmostra != null)
				return false;
		} else if (!idCondicaoAmostra.equals(other.idCondicaoAmostra))
			return false;
		return true;
	}

}
