package com.juran.index.mdm.app.dao.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@ApiModel(description = "2D案列")
@Getter
@Setter
@Document(collection = "d3Case")
public class ThreeCaseBean implements Serializable {

    private static final long serialVersionUID = 7290347374007022037L;

    @ApiModelProperty(value = "精选案例")
    private String caseType = "brilliant";

    @ApiModelProperty(value = "删除状态")
    private int deleteStatus = 0;
}
