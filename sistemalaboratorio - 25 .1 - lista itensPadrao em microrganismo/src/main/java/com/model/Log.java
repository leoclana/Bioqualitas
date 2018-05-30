package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "log")
public class Log implements Serializable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idlog")
	private Integer idLog=0;

	@Column(name = "descricao", nullable = false, length = 128)
	private String descricao;

	@Temporal(TemporalType.DATE)
    private Date data;

//	@ManyToOne(optional = true, fetch = FetchType.EAGER)
//	@JoinColumn(name = "idusuario")
//    private Usuario usuario;

	public Log () {
		
	}

	public Integer getIdLog() {
		return idLog;
	}


	public void setIdLog(Integer id) {
		this.idLog = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}

	@Override
	public String toString() {
		return getData().toString() + " " + getDescricao();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLog == null) ? 0 : idLog.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Log other = (Log) obj;
		if (idLog == null) {
			if (other.idLog != null)
				return false;
		} else if (!idLog.equals(other.idLog))
			return false;
		return true;
	}


}
