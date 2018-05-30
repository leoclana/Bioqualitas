package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "formapagamento")
public class FormaPagamento implements Serializable {

	private static final long serialVersionUID = 7007849362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idforma")
	private Integer idForma = 0;

	@Column(name = "nome", unique = true, nullable = false, length = 50)
	private String nome;

	@Column(name = "ativo")
	private Boolean ativo = true;

	public FormaPagamento() {
	}

	public Integer getIdForma() {
		return idForma;
	}

	public void setIdForma(Integer id) {
		this.idForma = id;
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
		result = prime * result + ((idForma == null) ? 0 : idForma.hashCode());
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
		FormaPagamento other = (FormaPagamento) obj;
		if (idForma == null) {
			if (other.idForma != null)
				return false;
		} else if (!idForma.equals(other.idForma))
			return false;
		return true;
	}

}
