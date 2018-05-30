package com.util.enums;

public enum TipoIndicador {

	INDICADOR1("Número de análises por “Motivo da Análise” sobre total de análise no período"),
	INDICADOR2("Amostras do cliente no período sobre total de amostras no período"),
	INDICADOR3("Atrasos de resultados de análises de amostras do cliente no período"),
	INDICADOR4("Resultados de amostras dentro ou fora do padrão do cliente no período");
	
	private final String text;

    /**
     * @param text
     */
    private TipoIndicador(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
