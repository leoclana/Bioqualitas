package com.util.enums;
import com.util.Messages;

public enum TipoAnalise {

	SELECIONEAQUI(Messages.getBundleMessage("padrao.selectItem.selecione")),
	ALIMENTO(Messages.getBundleMessage("amostra.form.label.tipoanalise.alimento")),
	MANIPULADOR(Messages.getBundleMessage("amostra.form.label.tipoanalise.manipulador")),
	SUPERFICIE(Messages.getBundleMessage("amostra.form.label.tipoanalise.superficie")),
	AGUA(Messages.getBundleMessage("amostra.form.label.tipoanalise.agua")),
	AGUAMINERAL(Messages.getBundleMessage("amostra.form.label.tipoanalise.aguamineral")),
	AR(Messages.getBundleMessage("amostra.form.label.tipoanalise.ar"));
	
	private final String text;

    /**
     * @param text
     */
    private TipoAnalise(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
