package com.util;

import javax.faces.context.FacesContext;

import org.springframework.web.jsf.FacesContextUtils;

public class SpringUtil {
    
	@SuppressWarnings("unchecked")
	public static <T> T getSpringBean(String beanName) {
		return (T) FacesContextUtils.getRequiredWebApplicationContext(
				FacesContext.getCurrentInstance()).getBean(beanName);
	}

}
