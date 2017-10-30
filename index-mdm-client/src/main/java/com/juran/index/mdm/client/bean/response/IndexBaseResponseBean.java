package com.juran.index.mdm.client.bean.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "基础搜索响应")
@Getter
@Setter
public class IndexBaseResponseBean implements  Serializable{

	/**  
	* @Fields field:field:{todo} 
	*/  
	private static final long serialVersionUID = 3050619975564752801L;
	
	@ApiModelProperty(value = "是否已完成")
	private String recordSetComplete;
	
	@ApiModelProperty(value = "本页返回结果的数量")
	private Integer recordSetCount;
	
	@ApiModelProperty(value = "本页起始数字")
	private Integer recordSetStartNumber;
	
	@ApiModelProperty(value = "结果总数量")
	private Long recordSetTotal;
	
	@ApiModelProperty(value = "结果命中总数量")
	private Integer recordSetTotalMatches;
	
	@ApiModelProperty(value = "资源名称")
	private String resourceName;
	
	@ApiModelProperty(value = "资源编号")
	private String resourceId;

}

	