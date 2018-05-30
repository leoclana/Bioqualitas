package com.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "cliente")
public class Cliente implements Serializable, ILoggable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcliente")
	private Integer idCliente=0;

	@Column(name = "cpfcnpj", unique = true, nullable = false, length = 18)
	private String cpfcnpj;

	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	private String tipopessoa="0";
	private String tipoinscricao="0";
	private String inscricao;
	
	private String alias;
	private String email;
	
	private String dddtelefone;
	private String telefone;
	
	private String endereco;
	
	private String end_logradouro;
	private String end_numero;
	private String end_complemento;
	private String end_bairro;
	private String end_cidade;
	private String end_estado;
	private String end_cep;
	

	@Column(name = "ativo")
	private Boolean ativo=true;
	

	@OneToMany(mappedBy = "cliente")
    private Set<Usuario> usuarios;
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
	private Set<Solicitacao> solicitacoes = null;//new HashSet<Solicitacao>(0);

	public Cliente () {

	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Integer getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(Integer id) {
		this.idCliente = id;
	}

	public String getCpfcnpj() {
		return this.cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
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

	public String getDddtelefone() {
		return dddtelefone;
	}

	public void setDddtelefone(String dddtelefone) {
		this.dddtelefone = dddtelefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getEnd_logradouro() {
		return end_logradouro;
	}

	public void setEnd_logradouro(String end_logradouro) {
		this.end_logradouro = end_logradouro;
	}

	public String getEnd_numero() {
		return end_numero;
	}

	public void setEnd_numero(String end_numero) {
		this.end_numero = end_numero;
	}

	public String getEnd_complemento() {
		return end_complemento;
	}

	public void setEnd_complemento(String end_complemento) {
		this.end_complemento = end_complemento;
	}

	public String getEnd_bairro() {
		return end_bairro;
	}

	public void setEnd_bairro(String end_bairro) {
		this.end_bairro = end_bairro;
	}

	public String getEnd_cidade() {
		return end_cidade;
	}

	public void setEnd_cidade(String end_cidade) {
		this.end_cidade = end_cidade;
	}

	public String getEnd_estado() {
		return end_estado;
	}

	public void setEnd_estado(String end_estado) {
		this.end_estado = end_estado;
	}

	public String getEnd_cep() {
		return end_cep;
	}

	public void setEnd_cep(String end_cep) {
		this.end_cep = end_cep;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getTipopessoa() {
		return tipopessoa;
	}

	public void setTipopessoa(String tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	public String getTipoinscricao() {
		return tipoinscricao;
	}

	public void setTipoinscricao(String tipoinscricao) {
		this.tipoinscricao = tipoinscricao;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	public Set<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Set<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	@Override
	public String toString() {
		return getNome();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}

	@Override
	public String getIdentifier() {
		return getCpfcnpj();
	}

}
