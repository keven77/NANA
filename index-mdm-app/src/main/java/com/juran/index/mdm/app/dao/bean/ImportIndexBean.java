package com.juran.index.mdm.app.dao.bean;

import com.juran.index.mdm.app.contants.SolrContants.RequestHandler;

public class ImportIndexBean {
	private String requestHandler=RequestHandler.DATA_IMPORT;
	private String command;
	private boolean clean=false;
	private boolean commit=true;
	private boolean optimize=false;
	
	private String entity;

	public String getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(String requestHandler) {
		this.requestHandler = requestHandler;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public boolean isClean() {
		return clean;
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}

	public boolean isCommit() {
		return commit;
	}

	public void setCommit(boolean commit) {
		this.commit = commit;
	}

	public boolean isOptimize() {
		return optimize;
	}

	public void setOptimize(boolean optimize) {
		this.optimize = optimize;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	
}
