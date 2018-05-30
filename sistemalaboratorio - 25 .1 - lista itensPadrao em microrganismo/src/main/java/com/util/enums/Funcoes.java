package com.util.enums;

public enum Funcoes {

	CADASTRARUSUARIO("cadastrarusuario"),
	EDITARUSUARIO("editarusuario"),
	VISUALIZARUSUARIO("visualizarusuario"),
	PESQUISARUSUARIO("pesquisarusuario"),
	
	CADASTRARCLIENTE("cadastrarcliente"),
	EDITARCLIENTE("editarcliente"),
	VISUALIZARCLIENTE("visualizarcliente"),
	PESQUISARCLIENTE("pesquisarcliente"),
	
	CADASTRARMICRORGANISMO("cadastrarmicrorganismo"),
	EDITARMICRORGANISMO("editarmicrorganismo"),
	VISUALIZARMICRORGANISMO("visualizarmicrorganismo"),
	PESQUISARMICRORGANISMO("pesquisarmicrorganismo"),

	CADASTRARSOLICITACAO("cadastrarsolicitacao"),
	EDITARSOLICITACAO("editarsolicitacao"),
	DELETARSOLICITACAO("deletarsolicitacao"),
	VISUALIZARSOLICITACAO("visualizarsolicitacao"),
	PESQUISARSOLICITACAO("pesquisarsolicitacao"),
	ANALISARSOLICITACAO("analisarsolicitacao"),
	
	CADASTRARAMOSTRA("cadastraramostra"),
	DELETARAMOSTRA("deletaramostra"),
	EDITARAMOSTRA("editaramostra"),
	VISUALIZARAMOSTRA("visualizaramostra"),
	PESQUISARAMOSTRA("pesquisaramostra"),
	ANALISARAMOSTRA("analisaramostra"),

	VISUALIZARINDICADORES("visualizarindicadores"),
	ALTERARCONFIGURACOES("alterarconfiguracoes"),
	
	CONSULTARNOTIFICACOES("consultarnotificacoes"),
	VISUALIZARNOTIFICACAO("visualizarnotificacao");
	
	private final String text;

    /**
     * @param text
     */
    private Funcoes(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
