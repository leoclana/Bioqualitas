package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.util.enums.TipoAnalise;

@Entity
@Table(name = "laudo")
public class Laudo implements Serializable {

	private static final long serialVersionUID = -1939259272677688419L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idlaudo")
	private Integer idLaudo = 0;

	@Column(name = "tipoanalise")
	@Enumerated(EnumType.STRING)
	private TipoAnalise tipoAnalise;

	@Column(name = "resultado")
	private String resultado;
	
	@Column(name = "ativo")
	private boolean ativo = true;

	@Column(name = "nome", columnDefinition = "VARCHAR(2000)")
	private String nome;

//	@Column(name = "nome1")
//	private String nome1;
//
//	@Column(name = "nome2")
//	private String nome2;
//
//	@Column(name = "nome3")
//	private String nome3;
//
//	@Column(name = "nome4")
//	private String nome4;
//
//	@Column(name = "nome5")
//	private String nome5;
//
//	@Column(name = "nome6")
//	private String nome6;
//
//	@Column(name = "nome7")
//	private String nome7;

	public Laudo() {
	}

	public Integer getIdLaudo() {
		return idLaudo;
	}

	public void setIdLaudo(Integer idLaudo) {
		this.idLaudo = idLaudo;
	}

	public String getNome() {
		//return nome + getNome1() + getNome2() + getNome3() + getNome4()	+ getNome5() + getNome6() + getNome7();
		return nome;
	}

	public void setNome(String nome) {

//		System.out.println(nome.length());
//		System.out.println(nome);
//
//		if (nome.length() < 199) {
//			this.nome = nome;
//
//		} else if (nome.length() > 199) {
//			this.nome = nome.substring(0,199);
//
//			if (nome.length() < 299) {
//				setNome1(nome.substring(200, nome.length()));
//
//			} else if (nome.length() > 299) {
//				setNome1(nome.substring(200, 299));
//
//				if (nome.length() < 399) {
//					setNome2(nome.substring(300, nome.length()));
//
//				} else if (nome.length() > 399) {
//					setNome2(nome.substring(300, 399));
//
//					if (nome.length() < 499) {
//						setNome3(nome.substring(400, nome.length()));
//					} else if (nome.length() > 499) {
//						setNome3(nome.substring(400, 499));
//
//						if (nome.length() < 599) {
//							setNome4(nome.substring(500, nome.length()));
//						} else if (nome.length() > 599){
//							setNome4(nome.substring(500, 599));
//
//							if (nome.length() < 699){
//								setNome5(nome.substring(600, nome.length()));
//							} else if (nome.length() > 699) {
//								setNome5(nome.substring(600, 699));
//
//								if (nome.length() < 799) {
//									setNome6(nome.substring(700, nome.length()));
//								} else if (nome.length() > 799) {
//									setNome6(nome.substring(700, 799));
//
//									if (nome.length() < 899) {
//										setNome7(nome.substring(800, nome.length()));
//									} else if (nome.length() > 899) {
//										setNome7(nome.substring(800, 899));
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		this.nome = nome;
	}

//	public String getNome1() {
//		return nome1;
//	}
//
//	public void setNome1(String nome1) {
//		this.nome1 = nome1;
//	}
//
//	public String getNome2() {
//		return nome2;
//	}
//
//	public void setNome2(String nome2) {
//		this.nome2 = nome2;
//	}
//
//	public String getNome3() {
//		return nome3;
//	}
//
//	public void setNome3(String nome3) {
//		this.nome3 = nome3;
//	}
//
//	public String getNome4() {
//		return nome4;
//	}
//
//	public void setNome4(String nome4) {
//		this.nome4 = nome4;
//	}
//
//	public String getNome5() {
//		return nome5;
//	}
//
//	public void setNome5(String nome5) {
//		this.nome5 = nome5;
//	}
//
//	public String getNome6() {
//		return nome6;
//	}
//
//	public void setNome6(String nome6) {
//		this.nome6 = nome6;
//	}
//
//	public String getNome7() {
//		return nome7;
//	}
//
//	public void setNome7(String nome7) {
//		this.nome7 = nome7;
//	}

	public TipoAnalise getTipoAnalise() {
		return tipoAnalise;
	}

	public void setTipoAnalise(TipoAnalise tipoAnalise) {
		this.tipoAnalise = tipoAnalise;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		if(nome != null) {
			int i = (getNome().length() > 80 ? 80 : getNome().length());
			return getNome().substring(0, i);
		}else{
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
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
		Laudo other = (Laudo) obj;
		if (ativo != other.ativo)
			return false;
		return true;
	}

}
