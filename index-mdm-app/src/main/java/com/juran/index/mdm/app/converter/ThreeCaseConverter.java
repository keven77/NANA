package com.juran.index.mdm.app.converter;

import com.juran.index.mdm.app.domain.ThreeCaseDO;
import com.juran.index.mdm.app.utils.ParamCheckUtil;
import com.juran.index.mdm.app.utils.TimeUtil;
import com.juran.index.mdm.client.bean.response.enums.CaseStyleTypeEnum;
import org.apache.solr.common.SolrInputDocument;

import java.util.ArrayList;
import java.util.List;
/*
 * FileName: CaseIndexResource.java
 * Author:   zhangyihang
 * Date:    2017年10月17日 09:02:12
 * Description: //模块目的、功能描述 <3D案列数据格式转化,和赋值>  <br>
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class ThreeCaseConverter {

    public static List<SolrInputDocument> threeCaseListConverter(List<ThreeCaseDO> threeCaseDOs){
        if(ParamCheckUtil.checkListNull(threeCaseDOs)){
            return null;
        }
        List<SolrInputDocument> docs = new ArrayList<>();
        for(ThreeCaseDO threeCaseDO : threeCaseDOs){
            docs.add(threeCaseListConverter(threeCaseDO));
        }
        return docs;
    }

    public static SolrInputDocument threeCaseListConverter(ThreeCaseDO threeCaseDO){

        SolrInputDocument document = new SolrInputDocument();
        document.addField("designAssetId",threeCaseDO.getDesignAssetId());
        document.addField("designName",threeCaseDO.getDesignName());
        document.addField("restroom",threeCaseDO.getRestroom());
        document.addField("bedroom",CaseStyleTypeEnum.findName(threeCaseDO.getBedroom()) == null ? "other":CaseStyleTypeEnum.findName(threeCaseDO.getBedroom()));
        document.addField("roomArea",threeCaseDO.getRoomArea());
        document.addField("roomType",threeCaseDO.getRoomType());
        document.addField("projectStyle",CaseStyleTypeEnum.findName(threeCaseDO.getProjectStyle()) == null ? "other":CaseStyleTypeEnum.findName(threeCaseDO.getProjectStyle()));
        document.addField("originalAvatar",threeCaseDO.getOriginalAvatar());
        document.addField("thumbnailMainPath",threeCaseDO.getThumbnailMainPath() == null ? null :threeCaseDO.getThumbnailMainPath());
        document.addField("dateModifyed", TimeUtil.queryTime(threeCaseDO.getDateModifyed()));
        document.addField("dateSubmitted", TimeUtil.queryTime(threeCaseDO.getDateModifyed()));
        document.addField("hsDesignerUid",threeCaseDO.getHsDesignerUid());
        if(threeCaseDO.getSort() != null){
            document.addField("sort", Long.parseLong(String.valueOf(threeCaseDO.getSort()).replace("-","")));
        }else{
            document.addField("sort", 0);
        }
        return document;
    }


}
