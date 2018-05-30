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
@Table(name = "itemgrupo")
public class ItemGrupo implements Serializable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iditemgrupo")
	private Integer idItemGrupo = 0;

	@Column(name = "nome", nullable = false, length = 100)
	//@Column(name = "nome", unique = true, nullable = false, length = 100)
	private String nome;

	@Column(name = "descricao", length = 500)
	private String descricao;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "idgrupo")
	private Grupo grupo;

	public ItemGrupo() {
	}

	public Integer getIdItemGrupo() {
		return idItemGrupo;
	}

	public void setIdItemGrupo(Integer id) {
		this.idItemGrupo = id;
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
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
		result = prime * result + ((idItemGrupo == null) ? 0 : idItemGrupo.hashCode());
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
		ItemGrupo other = (ItemGrupo) obj;
		if (idItemGrupo == null) {
			if (other.idItemGrupo != null)
				return false;
		} else if (!idItemGrupo.equals(other.idItemGrupo))
			return false;
		return true;
	}

}
