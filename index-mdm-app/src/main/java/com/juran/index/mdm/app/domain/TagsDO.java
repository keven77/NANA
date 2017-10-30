package com.juran.index.mdm.app.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@ApiModel(description = "标签集合")
@Getter
@Setter
@Document(collection = "dictionary")
public class TagsDO implements Serializable {

    private static final long serialVersionUID = 4542283069017814368L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "标签名字")
    private String name;

    @ApiModelProperty(value = "标签属性值")
    private String value;

    @ApiModelProperty(value = "标签属于类型")
    private String type;

    @ApiModelProperty(value = "排序字段")
    private int sort;

    @ApiModelProperty(value = "状态")
    private int status;

}
