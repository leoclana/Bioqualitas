package com.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messages {

	private static final String PATH_MENSAGENS = "/mensagens";

	private static ResourceBundle bundle;

	public static String getBundleMessage(String codigo) {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle(PATH_MENSAGENS);
		}
		String message;
		message = bundle.getString(codigo);

		return message;
	}

	public static String getBundleMessage(String codigo, String... placeholders) {
		String resolvedMsg = MessageFormat.format(getBundleMessage(codigo),
				placeholders);
		return resolvedMsg;
	}

	/**
	 * 
	 * @param codigo
	 *            - Codigo do message.properties
	 * 
	 */
	public static void addErrorBundleMessage(String codigo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				getBundleMessage(codigo), "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addErrorBundleMessage(String codigo, String... placeholders) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				getBundleMessage(codigo, placeholders), "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addErrorTextMessage(String texto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, texto,
				"");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addInfoBundleMessage(String codigo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				getBundleMessage(codigo), "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addInfoTextMessage(String texto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, texto,
				"");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	

	public static FacesMessage getMsgErro(String codigo) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR,
				getBundleMessage(codigo), "");

	}

}
