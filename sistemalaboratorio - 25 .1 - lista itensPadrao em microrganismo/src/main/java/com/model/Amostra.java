package com.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.util.enums.TipoAnalise;

@Entity
@Table(name = "amostra")
public class Amostra implements Serializable, ILoggable {

	private static final long serialVersionUID = 7007848362940823021L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idamostra")
	private Integer idAmostra = 0;

	@Column(name = "usosuperficie")
	private  Integer usoSuperficie = 0;

	@Column(name = "ativo")
	private Boolean ativo = true;
	
	@Column(name = "antissepsia")
	private  Boolean antissepsia;

	@Column(name = "invalida")
	private Boolean invalida;
	
	@Column(name = "comparativa")
	private Boolean comparativa;

	@Column(name = "noprazo")
	private Boolean noPrazo;

	@Column(name = "positivo")
	private Boolean positivo;

	@Column(name = "condicaooutro")
	private String condicaoOutro;

    @Column(name="strNumero")
    private String strNumero;

	@Column(name = "justificativa")
	private String  justificativa;

	@Column(name = "nota")
	private String  nota;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name="laudo")
//	private Laudo laudo;
	@Column(name = "laudo", columnDefinition = "VARCHAR(2000)")
	private String laudo;

	@Column(name = "observacao")
	private String  observacao;

	@Column(name = "tempamostra")
	private String tempAmostra;

	@Column(name = "tempamostracoleta")
	private String tempAmostraColeta;

	@Column(name = "responsavelcoleta")
	private String responsavelColeta;

	@Column(name = "motivoanalisetext")
	private String motivoAnaliseText;

	@Column(name = "localcoleta")
	private String localColeta;

	@Column(name = "descricaoamostra")
	private String descricaoAmostra;

	@Column(name = "nomemanipulador")
	private String nomeManipulador;

	@Column(name = "tarefaexecutada")
	private String tarefaExecutada;

	@Column(name = "codlegislacao")
	private String codLegislacao;

	@Column(name = "hrentrega")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrEntrega;

	@Column(name = "antissepsiadesc")
	private String antissepsiaDesc;

	@Column(name = "hrcoleta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrColeta;

	@Column(name = "datainicioanalise")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioAnalise;

	@Column(name = "dataconclusaoanalise")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataConclusaoAnalise;

	@Column(name = "tipoanalise")
	@Enumerated(EnumType.STRING) 
	private TipoAnalise tipoAnalise;

	@Column(name = "grupoalimentos")
	private Grupo grupoAlimento;

	@Column(name = "alimento")
	private ItemGrupo  alimento;

	@Column(name = "congelado")
	private Boolean  congelado;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "solicitacao")
	private Solicitacao solicitacao;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "motivoanalise")
	private MotivoAnalise motivoAnalise;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "amostra_condicaoamostra",
			joinColumns = { @JoinColumn(name = "idamostra") },
			inverseJoinColumns = { @JoinColumn(name = "idcondicaoamostra") })
	private List<CondicaoAmostra> condicoes;

	public Amostra() {
	}

	public Integer getIdAmostra() {
		return idAmostra;
	}

	public void setIdAmostra(Integer idAmostra) {
		this.idAmostra = idAmostra;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getInvalida() {
		return invalida;
	}

	public void setInvalida(Boolean invalida) {
		this.invalida = invalida;
	}

	public Boolean getComparativa() {
		return comparativa;
	}

	public void setComparativa(Boolean comparativa) {
		this.comparativa = comparativa;
	}

	public String getStrNumero() {
		return strNumero;
	}

	public void setStrNumero(String strNumero) {
		this.strNumero = strNumero;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getNota() {
		return nota;
	}

//	public Laudo getLaudo() {
//		return laudo;
//	}
//
//	public void setLaudo(Laudo laudo) {
//		this.laudo = laudo;
//	}
	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}

	public String getLaudo() {
		return (laudo == null ? "" : laudo) ;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public TipoAnalise getTipoAnalise() {
		return tipoAnalise;
	}

	public void setTipoAnalise(TipoAnalise tipoAnalise) {
		this.tipoAnalise = tipoAnalise;
	}

	public String getCodLegislacao() {
		return codLegislacao;
	}

	public void setCodLegislacao(String codLegislacao) {
		this.codLegislacao = codLegislacao;
	}

	public Grupo getGrupoAlimento() {
		return grupoAlimento;
	}

	public void setGrupoAlimento(Grupo grupoAlimento) {
		this.grupoAlimento = grupoAlimento;
	}

	public ItemGrupo getAlimento() {
		return alimento;
	}

	public void setAlimento(ItemGrupo alimento) {
		this.alimento = alimento;
	}

	public String getTempAmostra() {
		return tempAmostra;
	}

	public void setTempAmostra(String tempAmostra) {
		this.tempAmostra = tempAmostra;
	}

	public String getTempAmostraColeta() {
		return tempAmostraColeta;
	}

	public void setTempAmostraColeta(String tempAmostraColeta) {
		this.tempAmostraColeta = tempAmostraColeta;
	}

	public Date getHrEntrega() {
		return hrEntrega;
	}

	public void setHrEntrega(Date hrEntrega) {
		this.hrEntrega = hrEntrega;
	}

	public String getResponsavelColeta() {
		return responsavelColeta;
	}

	public void setResponsavelColeta(String responsavelColeta) {
		this.responsavelColeta = responsavelColeta;
	}

	public String getMotivoAnaliseText() {
		return motivoAnaliseText;
	}

	public void setMotivoAnaliseText(String motivoAnalise) {
		this.motivoAnaliseText = motivoAnalise;
	}

//	public CondicaoAmostra getCondicao() {
//		return condicao;
//	}
//
//	public void setCondicao(CondicaoAmostra condicao) {
//		this.condicao = condicao;
//	}


	public List<CondicaoAmostra> getCondicoes() {
		return condicoes;
	}

	public void setCondicoes(List<CondicaoAmostra> condicoes) {
		this.condicoes = condicoes;
	}

	public String getCondicaoOutro() {
		return condicaoOutro;
	}

	public void setCondicaoOutro(String condicaoOutro) {
		this.condicaoOutro = condicaoOutro;
	}

	public String getLocalColeta() {
		return localColeta;
	}

	public void setLocalColeta(String localColeta) {
		this.localColeta = localColeta;
	}

	public String getDescricaoAmostra() {
		return descricaoAmostra;
	}

	public void setDescricaoAmostra(String descricaoAmostra) {
		this.descricaoAmostra = descricaoAmostra;
	}

	public String getNomeManipulador() {
		return nomeManipulador;
	}

	public void setNomeManipulador(String nomeManipulador) {
		this.nomeManipulador = nomeManipulador;
	}

	public String getTarefaExecutada() {
		return tarefaExecutada;
	}

	public void setTarefaExecutada(String tarefaExecutada) {
		this.tarefaExecutada = tarefaExecutada;
	}


	public Boolean getAntissepsia() {
		return antissepsia;
	}

	public void setAntissepsia(Boolean antissepsia) {
		this.antissepsia = antissepsia;
		if(!antissepsia){
			antissepsiaDesc = null;
		}
	}

	public String getAntissepsiaDesc() {
		return antissepsiaDesc;
	}

	public void setAntissepsiaDesc(String antissepsiaDesc) {
		this.antissepsiaDesc = antissepsiaDesc;
	}

	public Date getHrColeta() {
		return hrColeta;
	}

	public void setHrColeta(Date hrColeta) {
		this.hrColeta = hrColeta;
	}

	public Integer getUsoSuperficie() {
		return usoSuperficie;
	}

	public void setUsoSuperficie(Integer usoSuperficie) {
		this.usoSuperficie = usoSuperficie;
	}

	public Date getDataInicioAnalise() {
		return dataInicioAnalise;
	}

	public void setDataInicioAnalise(Date dataInicioAnalise) {
		this.dataInicioAnalise = dataInicioAnalise;
	}

	public Date getDataConclusaoAnalise() {
		return dataConclusaoAnalise;
	}

	public void setDataConclusaoAnalise(Date dataConclusaoAnalise) {
		this.dataConclusaoAnalise = dataConclusaoAnalise;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public MotivoAnalise getMotivoAnalise() {
		return motivoAnalise;
	}

	public void setMotivoAnalise(MotivoAnalise motivoAnalise) {
		this.motivoAnalise = motivoAnalise;
	}

	public Boolean getNoPrazo() {
		return noPrazo;
	}

	public void setNoPrazo(Boolean noPrazo) {
		this.noPrazo = noPrazo;
	}

	public Boolean getPositivo() {
		return positivo;
	}

	public void setPositivo(Boolean positivo) {
		this.positivo = positivo;
	}

	public Boolean getCongelado() {
		return congelado;
	}

	public void setCongelado(Boolean congelado) {
		this.congelado = congelado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idAmostra == null) ? 0 : idAmostra.hashCode());
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
		Amostra other = (Amostra) obj;
		if (idAmostra == null) {
			if (other.idAmostra != null)
				return false;
		} else if (!idAmostra.equals(other.idAmostra))
			return false;
		return true;
	}

	@Override
	public String getIdentifier() {
		return getStrNumero();
	}

}
