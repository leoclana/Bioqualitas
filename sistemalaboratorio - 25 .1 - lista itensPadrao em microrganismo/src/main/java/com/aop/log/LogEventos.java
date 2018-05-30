/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aop.log;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;


//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.jsf.FacesContextUtils;

import com.managed.bean.ISessaoDoUsuario;
import com.model.Log;
import com.model.Usuario;
import com.service.impl.LogService;

//@Component
//@Aspect
public class LogEventos {

    @Autowired
    private LogService logService;


    /* (non-Javadoc)
	 * @see com.aop.log.ILogEventos#logAlvo(org.aspectj.lang.ProceedingJoinPoint)
	 */
    //@Around("execution(* com.service.*.*(..)) && @annotation(com.aop.log.Log)")
    public Object logAlvo(/*ProceedingJoinPoint*/Object pjp) throws Throwable {
        return logarAcao(pjp);
    }

    /* (non-Javadoc)
	 * @see com.aop.log.ILogEventos#logarAcao(org.aspectj.lang.ProceedingJoinPoint)
	 */
    public Object logarAcao(/*ProceedingJoinPoint*/Object pjp) throws Throwable {

    	//Object ret;
        Object ret = pjp;

        Log log = new Log();

        //log.setUsuario(this.getUsuarioLogado());
        log.setData(new Date());

        try {
            //ret = pjp.proceed();
            //logando acao realizada

            //log.setDescricao(pjp.getSignature().getDeclaringType() +" ||| "+ pjp.getSignature().getName() +" ||| "+ pjp.getArgs());
            logService.add(log);
            
        } catch (Throwable ex) {
            Logger.getLogger(LogEventos.class.getName()).log(Level.SEVERE, null, ex);
            
            //log.setDescricao(pjp.getSignature().getDeclaringType() +" ||| "+ pjp.getSignature().getName() +" ||| "+ pjp.getArgs()+" ||| "+ ex.getMessage());            
            logService.add(log);
            
            throw ex;
        }
        return ret;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    /* (non-Javadoc)
	 * @see com.aop.log.ILogEventos#getUsuarioLogado()
	 */
    public Usuario getUsuarioLogado() {

        ISessaoDoUsuario sessao = (ISessaoDoUsuario) FacesContextUtils.getRequiredWebApplicationContext(
                FacesContext.getCurrentInstance()).getBean("SessaoDoUsuario");

        return sessao.getUsuario();

    }
}
