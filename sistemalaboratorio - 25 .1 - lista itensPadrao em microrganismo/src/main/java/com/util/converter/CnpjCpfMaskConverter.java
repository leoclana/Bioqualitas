package com.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class CnpjCpfMaskConverter implements Converter {
	
	
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
         String result = value;
         if (value!= null && !value.equals(""))
              result = value.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", "");

         return result;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {

    	String result= value.toString();
    	if (result != null) { 
    		if (result.length() == 11) {
    			result = result.substring(0, 3) + "." + result.substring(3, 6) + "." + result.substring(6, 9) + "-" + result.substring(9, 11);
    		}else{
    			if (result != null && result.length() == 14)
    				result = result.substring(0, 2) + "." + result.substring(2, 5) + "." + result.substring(5, 8) + "/" + result.substring(8, 12) + "-" + result.substring(12, 14);
    		}
    	}
    	return result;
    }
 
}
