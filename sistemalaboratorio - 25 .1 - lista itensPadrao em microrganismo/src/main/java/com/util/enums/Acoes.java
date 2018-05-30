package com.util.enums;

import com.util.Messages;

public enum Acoes {

	LISTAR(Messages.getBundleMessage("acao.listar")),
	VISUALIZAR(Messages.getBundleMessage("acao.visualizar")),
	ANALISAR(Messages.getBundleMessage("acao.analizar")),
	EDITAR(Messages.getBundleMessage("acao.editar")),
	CRIAR(Messages.getBundleMessage("acao.criar")),
	ALTERARDADOSCADASTRAIS(Messages.getBundleMessage("acao.alterardadoscadastrais")),
	ALTERARSENHA(Messages.getBundleMessage("acao.alterarsenha"));
	
	private final String text;

    /**
     * @param text
     */
    private Acoes(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
