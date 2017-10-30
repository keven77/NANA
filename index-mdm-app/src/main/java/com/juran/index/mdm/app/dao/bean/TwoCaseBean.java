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
@Document(collection = "d2Case")
public class TwoCaseBean implements Serializable {
    private static final long serialVersionUID = -5845076230823972922L;
    @ApiModelProperty(value = "审核状态")
    private int customStringStatus = 1;

    @ApiModelProperty(value = "删除状态")
    private int deleteStatus = 0;
}
