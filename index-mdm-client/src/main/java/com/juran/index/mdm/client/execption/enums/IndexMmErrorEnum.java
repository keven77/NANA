package com.juran.index.mdm.client.execption.enums;

/**
 * 
 * @author 王云龙
 * @date 2017年5月8日 下午1:38:23
 * @version 1.0
 * @description 主材相关错误枚举
 *
 */
public enum IndexMmErrorEnum {

	INDEX_MM_ALL_MUST_ERROR(101410000,"异常"),
	INDEX_MM_DELETE_ALL_MUST_HAS_EXECUTE(101410001,"在删除主材所有索引的时候必须输入execute"),
	INDEX_MM_DELETE_ALL_EXECUTE_ERROR(101410002,"在删除主材所有索引的时候输入的execute不合法"),
	INDEX_MM_DELETE_ALL_EXECUTE_NO(101410003,"在删除主材所有索引的时候输入的execute为no，如果确定需要执行该功能，请输入yes"),
	INDEX_MM_DELETE_ALL_SOLR_RESPONSE_ERROR(101410004,"在删除主材所有索引的时候Solr返回的状态为不成功"),
	INDEX_MM_DELETE_ALL_SOLR_SERVER_ERROR(101410005,"在删除主材所有索引的时候发生SolrServerException"),
	INDEX_MM_DELETE_ALL_SOLR_IO_ERROR(101410006,"在删除主材所有索引的时候发生IOException"),
	INDEX_MM_DELETE_ALL_SOLR_COMMIT_RESPONSE_ERROR(101410007,"在删除主材所有索引后进行commit的时候Solr返回的状态为不成功"),
	
	INDEX_MM_IMPORT_SOLR_SERVER_ERROR(101410101,"在构建主材所有索引的时候发生SolrServerException"),
	INDEX_MM_IMPORT_SOLR_IO_ERROR(101410102,"在构建主材所有索引的时候发生IOException"),
	INDEX_MM_IMPORT_SOLR_COMMIT_RESPONSE_ERROR(101410103,"在构建主材所有索引后进行commit的时候Solr返回的状态为不成功"),
	
	INDEX_MM_IMPORT_ENTITY_MUST_HASH_ENTITY(101410104,"在构建主材具体ENTITY索引时必须输入entity名称");

	private final int errorCode;
	private final String errorMsg;
	
	private IndexMmErrorEnum(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public static IndexMmErrorEnum valueOf(int errorCode) {  
        for (IndexMmErrorEnum accountError : IndexMmErrorEnum.values()) {  
            if (accountError.errorCode == errorCode) {  
                return accountError;
            }  
        }  
        throw new IllegalArgumentException("No matching constant for [" + errorCode + "]");
    }  	
	
	@Override
	public String toString() {
		return Integer.toString(this.errorCode);
	}
	
}
