package com.util.enums;


public enum AcoesLog {

	enviouparaanalise("enviou para analise"),
	enviouparaaprovacao("enviou para aprova��o"),
	liberouparacliente("liberou para o cliente"),
	adicionou("adicionou"), 
	atualizou("atualizou"),
	deletou("deletou"), 
	invalidou("invalidou"), 
	resetouSenha("resetou a senha");
	
	private final String text;

    /**
     * @param text
     */
    private AcoesLog(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
