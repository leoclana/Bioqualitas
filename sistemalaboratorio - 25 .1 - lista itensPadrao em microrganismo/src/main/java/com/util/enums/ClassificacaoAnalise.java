package com.util.enums;
import com.util.Messages;

public enum ClassificacaoAnalise {
	
	SELECIONEAQUI(Messages.getBundleMessage("padrao.selectItem.selecione")),
	A(Messages.getBundleMessage("amostra.form.label.classificacaoanalise.a")),
	B(Messages.getBundleMessage("amostra.form.label.classificacaoanalise.b")),
	C(Messages.getBundleMessage("amostra.form.label.classificacaoanalise.c"));
	
	private final String text;

    private ClassificacaoAnalise(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
