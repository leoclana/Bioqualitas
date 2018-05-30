package com.util.enums;


public enum RetornoFaces {

	HOME("home"),
	LISTAR("listar"),
	FORMULARIO("form"),
	ALTERARDADOSCADASTRAIS("alterardadoscadastrais"),
	ALTERARSENHA("alterarsenha"),
	LISTARSOLICITACOES("listarsolicitacoes"),
	CADASTRARSOLICITACAO("formsolicitacao");
	
	private final String text;

    /**
     * @param text
     */
    private RetornoFaces(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
