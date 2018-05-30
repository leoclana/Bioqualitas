package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "motivoanalise")
public class MotivoAnalise implements Serializable {

	private static final long serialVersionUID = 7007848672940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmotivo")
	private Integer idMotivo = 0;

	@Column(name = "nome", unique = true, nullable = false, length = 50)
	private String nome;

	@Column(name = "ativo")
	private Boolean ativo = true;

	public MotivoAnalise() {
	}

	public Integer getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(Integer id) {
		this.idMotivo = id;
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
		result = prime * result + ((idMotivo == null) ? 0 : idMotivo.hashCode());
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
		MotivoAnalise other = (MotivoAnalise) obj;
		if (idMotivo == null) {
			if (other.idMotivo != null)
				return false;
		} else if (!idMotivo.equals(other.idMotivo))
			return false;
		return true;
	}

}
