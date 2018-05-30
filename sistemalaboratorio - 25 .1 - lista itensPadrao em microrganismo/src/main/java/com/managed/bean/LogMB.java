package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.model.Log;
import com.model.Usuario;
import com.service.ILogService;

public class LogMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Log _log;

	private List<Log> logList;
	private List<Log> logListFiltered;

	private ILogService LogService;

	public LogMB(ILogService LogService) {
		setLogService(LogService);

		refreshLista();
	}
	
	public String listar(){
		refreshLista();
		return super.listar();
	}
	
	private void refreshLista() {
		setLogList(new ArrayList<Log>());
		getLogList().addAll(getLogService().getAll());
	}

	public String getLabelAcao() {
		return "Visualizar";
	}

	public Log getLog() {
		if (_log == null) {
			setLog(new Log());
		}
		return _log;
	}

	public void setLog(Log Log) {
		this._log = Log;
	}

	public List<Log> getLogList() {
		return logList;
	}

	public void setLogList(List<Log> LogList) {
		this.logList = LogList;
	}

	public List<Log> getLogListFiltered() {
		return logListFiltered;
	}

	public void setLogListFiltered(List<Log> logListFiltered) {
		this.logListFiltered = logListFiltered;
	}

	private ILogService getLogService() {
		return this.LogService;
	}

	private void setLogService(ILogService LogService) {
		this.LogService = LogService;
	}
	
}
