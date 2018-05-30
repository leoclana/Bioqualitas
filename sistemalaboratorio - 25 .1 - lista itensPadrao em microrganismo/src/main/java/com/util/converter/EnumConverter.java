package com.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.util.Util;


public class EnumConverter implements Converter {


    	@Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {

            Class enumType = component.getValueExpression("value").getType(
                    context.getELContext());

            Enum e = null;
            if (Util.isNaoNuloOuVazio(value)) {
                e = Enum.valueOf(enumType, value.trim());
            }

            return e;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object object) throws ConverterException {

            if (object == null) {
                return null;
            }
            Enum type = (Enum) object;
            return type.toString();
        }

}
