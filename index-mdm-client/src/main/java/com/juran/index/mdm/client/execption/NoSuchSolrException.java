package com.juran.index.mdm.client.execption;

/*
 * FileName: SolrdumpManager.java
 * Author:   zhangyihang
 * Date:     2017年10月13日 09:41:32
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class NoSuchSolrException extends Exception {
	private static final long serialVersionUID = -8768160783482959383L;
	private String msg ;
	private int errorCode;
	
	public NoSuchSolrException(String msg, int errorCode) {
		this.errorCode = errorCode;
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return "error code is ["+errorCode+"] error msg is ["+msg+"]";
	}
	
}
