package com.util.converter;

import com.model.Laudo;
import com.service.ILaudoService;
import com.util.SpringUtil;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class LaudoConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		if(value != null && value.trim().length() > 0) {
            try {
              	ILaudoService laudoService =	(ILaudoService)  SpringUtil.getSpringBean("LaudoService");
				//Laudo laudo = new Laudo();
				//laudo.setIdLaudo(Integer.getInteger(value));
				//return laudoService.getById(laudo);
				if(value.length()>3){
					return null;
				}else {
					return laudoService.getById(Integer.valueOf(value));
				}
            } catch(NumberFormatException e) {
            	throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no converter Laudo", "Converter Laudo"));
            }
        }
        else {
            return null;
        }
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		 if(value != null) {
	            return ((Laudo) value).getIdLaudo().toString();
	        }
	        else {
	            return null;
	        }
	}

}
