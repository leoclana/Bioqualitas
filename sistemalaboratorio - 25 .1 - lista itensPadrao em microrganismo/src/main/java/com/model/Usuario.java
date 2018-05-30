package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable, ILoggable {
	private static final long serialVersionUID = -8772516708049621911L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario")
	private Integer idUsuario = 0;

	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	@Column(name = "email", unique = true, length = 50)
	private String email;

	@Column(name = "senha")
	private String senha;
	
	@Column(name = "ativo")
	private Boolean ativo;

	@Column(name = "contatoprincipal")
	private Boolean contatoPrincipal;

	@Column(name = "dddtelcelular")
	private String dddTelCelular;

	@Column(name = "telcelular")
	private String telCelular;

	@Column(name = "dddtelcomercial")
	private String dddTelComercial;

	@Column(name = "telcomercial")
	private String telComercial;
	
	@Column(name = "ramaltelcomercial")
	private String ramalTelComercial;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "idcliente")
	private Cliente cliente;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_perfil", 
	joinColumns = { @JoinColumn(name = "idusuario") }, 
	inverseJoinColumns = { @JoinColumn(name = "idperfil") })
	private List<Perfil> perfis;

	public Usuario() {
		setAtivo(true);
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getContatoPrincipal() {
		return contatoPrincipal;
	}

	public void setContatoPrincipal(Boolean contatoPrincipal) {
		this.contatoPrincipal = contatoPrincipal;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer id) {
		this.idUsuario = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDddTelCelular() {
		return dddTelCelular;
	}

	public void setDddTelCelular(String dddTelCelular) {
		this.dddTelCelular = dddTelCelular;
	}

	public String getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	public String getDddTelComercial() {
		return dddTelComercial;
	}

	public void setDddTelComercial(String dddTelComercial) {
		this.dddTelComercial = dddTelComercial;
	}

	public String getTelComercial() {
		return telComercial;
	}

	public void setTelComercial(String telComercial) {
		this.telComercial = telComercial;
	}

	public String getRamalTelComercial() {
		return ramalTelComercial;
	}

	public void setRamalTelComercial(String ramalTelComercial) {
		this.ramalTelComercial = ramalTelComercial;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

	@Override
	public String getIdentifier() {
		return getNome();
	}

}
