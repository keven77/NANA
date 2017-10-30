package com.juran.index.mdm.app.converter;

import com.juran.index.mdm.app.domain.TagsDO;
import com.juran.index.mdm.app.utils.ParamCheckUtil;
import org.apache.solr.common.SolrInputDocument;

import java.util.ArrayList;
import java.util.List;
/*
 * FileName: CaseIndexResource.java
 * Author:   zhangyihang
 * Date:    2017年10月17日 09:02:12
 * Description: //模块目的、功能描述 <标签数据格式转化,和赋值>  <br>
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TagsConverter {

    public static List<SolrInputDocument> tagsListConverter(List<TagsDO> tagsDOs){
        if(ParamCheckUtil.checkListNull(tagsDOs)){
            return null;
        }
        List<SolrInputDocument> docs = new ArrayList<>();
        for(TagsDO tagsDO : tagsDOs){
            docs.add(tagsListConverter(tagsDO));
        }
        return docs;
    }

    public static SolrInputDocument tagsListConverter(TagsDO tagsDO){

        SolrInputDocument document = new SolrInputDocument();
        document.addField("_id",tagsDO.getId());
        document.addField("name",tagsDO.getName());
        document.addField("value",tagsDO.getValue());
        document.addField("type",tagsDO.getType());
        document.addField("sort",tagsDO.getSort());
        document.addField("status",tagsDO.getStatus());
        return document;
    }
}
