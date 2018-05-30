package com.util.enums;

public enum Estados {

	AC("AC"),
	AL("AL"),
	AP("AP"),
	AM("AM"),
	BA("BA"),
	CE("CE"),
	DF("DF"),
	ES("ES"),
	GO("GO"),
	MA("MA"),
	MT("MT"),
	MS("MS"),
	MG("MG"),
	PA("PA"),
	PB("PB"),
	PR("PR"),
	PE("PE"),
	PI("PI"),
	RR("RR"),
	RO("RO"),
	RJ("RJ"),
	RN("RN"),
	RS("RS"),
	SC("SC"),
	SP("SP"),
	SE("SE"),
	TO("TO");
	
	private final String text;

    /**
     * @param text
     */
    private Estados(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
