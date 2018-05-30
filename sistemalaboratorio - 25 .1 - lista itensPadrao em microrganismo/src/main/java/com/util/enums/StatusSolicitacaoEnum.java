package com.util.enums;

import java.io.Serializable;

public enum StatusSolicitacaoEnum  implements  Serializable{

	/*	SOLICITADO(Messages.getBundleMessage("solicitacao.status.solicitado")),
	EMANALISE(Messages.getBundleMessage("solicitacao.status.emanalise")),
	ANALISECONCLUIDA(Messages.getBundleMessage("solicitacao.status.analizeconcluida"));*/
	SOLICITADO("Solicitado"),
	EMANALISE("Em An�lise"),
	EMAPROVACAO("Em Aprova��o"),
	LIBERADA("Liberada");
	//CANCELADO("Cancelado");

	private final String text;

	private StatusSolicitacaoEnum(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
