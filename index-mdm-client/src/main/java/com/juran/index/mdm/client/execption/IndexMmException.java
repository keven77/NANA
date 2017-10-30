package com.juran.index.mdm.client.execption;

import java.util.UUID;

import com.juran.core.exception.ParentException;
import com.juran.core.exception.bean.ErrorMsgBean;
import com.juran.index.mdm.client.execption.enums.IndexMmErrorEnum;

/**
 * 
 * @author 王云龙
 * @date 2017年1月18日 下午1:38:07
 * @version 1.0
 * @description 搜索主材相关异常
 *
 */
public class IndexMmException extends ParentException {

	private static final long serialVersionUID = -6215751052263475161L;
	
	public IndexMmException(ErrorMsgBean errorMsgBean) {
		this.setErrorMsgBean(errorMsgBean);
	}

	public IndexMmException(IndexMmErrorEnum indexMmErrorEnum) {
		setErrorCode(indexMmErrorEnum.getErrorCode());
		setErrorMsg(indexMmErrorEnum.getErrorMsg());
		setErrorId(UUID.randomUUID().toString());
	}

}
