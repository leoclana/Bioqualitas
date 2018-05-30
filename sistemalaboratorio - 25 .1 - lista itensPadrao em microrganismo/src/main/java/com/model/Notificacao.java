package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable {
	
	private static final long serialVersionUID = 2972563413375508239L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idnotificacao")
	private Integer idNotificacao = 0;

	@Column(name="titulo")
	private String titulo;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="data")
	private String data;
	
	@Column(name="status")
	private String status;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "notificacao_usuario", 
	joinColumns = { @JoinColumn(name = "idnotificacao") }, 
	inverseJoinColumns = { @JoinColumn(name = "idusuario") })
	private List<Usuario> usuarios;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "notificacao_usuarioArquivados", 
	joinColumns = { @JoinColumn(name = "idnotificacao") }, 
	inverseJoinColumns = { @JoinColumn(name = "idusuario") })
	private List<Usuario> usuariosArquivados;

	public Integer getIdNotificacao() {
		return idNotificacao;
	}

	public void setIdNotificacao(Integer idNotificacao) {
		this.idNotificacao = idNotificacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getUsuariosArquivados() {
		return usuariosArquivados;
	}

	public void setUsuariosArquivados(List<Usuario> usuariosArquivados) {
		this.usuariosArquivados = usuariosArquivados;
	}
	
}
