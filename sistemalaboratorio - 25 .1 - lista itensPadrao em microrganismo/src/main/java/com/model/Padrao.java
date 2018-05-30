package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "padrao")
public class Padrao implements Serializable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpadrao")
	private Integer idPadrao = 0;

	@Column(name = "nome", unique = true, nullable = false, length = 100)
	private String nome;

	@Column(name = "descricao")
	private String descricao;
	
	public Padrao() {
	}

	public Integer getIdPadrao() {
		return idPadrao;
	}

	public void setIdPadrao(Integer id) {
		this.idPadrao = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		result = prime * result + ((idPadrao == null) ? 0 : idPadrao.hashCode());
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
		Padrao other = (Padrao) obj;
		if (idPadrao == null) {
			if (other.idPadrao != null)
				return false;
		} else if (!idPadrao.equals(other.idPadrao))
			return false;
		return true;
	}

}
