package com.util.enums;

public enum Perfis {

	ADMINISTRADOR("administrador"),
	COLETADOR("coletador"),
	TECNICO("tecnico"),
	VISUALIZADOR("visualizador");
	
	private final String text;

    /**
     * @param text
     */
    private Perfis(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
