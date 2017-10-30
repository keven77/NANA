package com.juran.index.mdm.app.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "3D案列")
@Getter
@Setter
@Document(collection = "d3Case")
public class ThreeCaseDO implements Serializable {

    private static final long serialVersionUID = 4580075776918710552L;

    @ApiModelProperty(value = "id")
    private long designAssetId;

    @ApiModelProperty(value = "案例标题")
    private String designName;

    @ApiModelProperty(value = "卫")
    private String restroom;

    @ApiModelProperty(value = "室")
    private String bedroom;

    @ApiModelProperty(value = "面积")
    private double roomArea;

    @ApiModelProperty(value = "厅")
    private String roomType;

    @ApiModelProperty(value = "风格")
    private String projectStyle;

    @ApiModelProperty(value = "设计师头像")
    private String originalAvatar;

    @ApiModelProperty(value = "封面图")
    private String thumbnailMainPath;

    @ApiModelProperty(value = "修改时间")
    private Date dateModifyed;

    @ApiModelProperty(value = "创建时间")
    private Date dateSubmitted;

    @ApiModelProperty(value = "设计师id")
    private String hsDesignerUid;

    @ApiModelProperty(value = "排序")
    private String sort;

}
