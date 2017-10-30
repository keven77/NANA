package com.juran.index.mdm.app.converter;

import com.juran.index.mdm.app.domain.TwoCaseDO;
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
 * Description: //模块目的、功能描述 <2D案列数据格式转化,和赋值>  <br>
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TwoCaseConverter {

    public static List<SolrInputDocument> twoCaseListConverter(List<TwoCaseDO> twoCaseDOs){
        if(ParamCheckUtil.checkListNull(twoCaseDOs)){
            return null;
        }
        List<SolrInputDocument> docs = new ArrayList<>();
        for(TwoCaseDO twoCaseDO : twoCaseDOs){
            docs.add(twoCaseListConverter(twoCaseDO));
        }
        return docs;
    }

    public static SolrInputDocument twoCaseListConverter(TwoCaseDO twoCaseDO){

        SolrInputDocument document = new SolrInputDocument();
        document.addField("assetId",twoCaseDO.getAssetId());
        document.addField("communityName",twoCaseDO.getCommunityName());
        document.addField("restroom",twoCaseDO.getRestroom());
        document.addField("bedroom",CaseStyleTypeEnum.findName(twoCaseDO.getBedroom()) == null ? "other":CaseStyleTypeEnum.findName(twoCaseDO.getBedroom()));
        document.addField("roomArea",twoCaseDO.getRoomArea());
        document.addField("roomType",twoCaseDO.getRoomType());
        document.addField("projectStyle",CaseStyleTypeEnum.findName(twoCaseDO.getProjectStyle())  == null ? "other":CaseStyleTypeEnum.findName(twoCaseDO.getBedroom()));
        document.addField("originalAvatar",twoCaseDO.getOriginalAvatar());
        document.addField("thumbnailMainPath",twoCaseDO.getThumbnailMainPath() == null ? null :twoCaseDO.getThumbnailMainPath());
        document.addField("createDate", TimeUtil.queryTime(twoCaseDO.getCreateDate()));
        document.addField("hsDesignerUid",twoCaseDO.getHsDesignerUid());
        if(twoCaseDO.getSort() != null){
            document.addField("sort", Long.parseLong(String.valueOf(twoCaseDO.getSort()).replace("-","")));
        }else{
            document.addField("sort", 0);
        }
        return document;
    }


}
