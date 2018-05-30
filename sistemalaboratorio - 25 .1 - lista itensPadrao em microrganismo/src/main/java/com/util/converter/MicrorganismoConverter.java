package com.util.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.model.Microrganismo;
import com.service.IMicrorganismoService;
import com.util.SpringUtil;

public class MicrorganismoConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		if(value != null && value.trim().length() > 0) {
            try {
              //  Microrganismo microrganismoService = (Microrganismo) fc.getExternalContext().getApplicationMap().get("IMicrorganismoService");
            	IMicrorganismoService microrganismoService =	(IMicrorganismoService)  SpringUtil.getSpringBean("MicrorganismoService");
            	 Microrganismo m = new Microrganismo();
            	 m.setIdMicrorganismo(Integer.getInteger(value));
            	 return microrganismoService.getById(m);
            } catch(NumberFormatException e) {
            	throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no converter Microrganismo", "Converter Microrganismo")); 
         //       throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		 if(value != null) {
	            return ((Microrganismo) value).getIdMicrorganismo().toString();
	        }
	        else {
	            return null;
	        }
	}

}
