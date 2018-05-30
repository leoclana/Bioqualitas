package com.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.model.Grupo;


public class GrupoConverter implements Converter {
	

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if(value == null || value.isEmpty()){
            return null;
        }else{
        	
        	return value;
        	
        	
//        	System.out.println("CLASSE: " + value.getClass().getName());
//        	
//            int id = Integer.parseInt(value);
//            System.out.println("ID: " + id);
//            Grupo grupo = new Grupo();
//            grupo.setIdGrupo(id);
//            
//            System.out.println("ERRO 1");
//            GrupoDaoImpl dao = new GrupoDaoImpl();
//            System.out.println("ERRO 2");
//            Grupo grupo2 = (Grupo) dao.recuperarPorAtributosPreenchidos(grupo);
//            System.out.println("ERRO 3");
//            return grupo2;
        	
        	
        	
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Grupo grupo = (Grupo)value;
        if(grupo != null){
            return String.valueOf(grupo.getIdGrupo());
        }else{
            return null;
        }

    }

}
