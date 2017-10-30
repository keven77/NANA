package com.juran.index.mdm.app.dao;

import com.juran.index.mdm.app.converter.TagsConverter;
import com.juran.index.mdm.app.converter.ThreeCaseConverter;
import com.juran.index.mdm.app.converter.TwoCaseConverter;
import com.juran.index.mdm.app.domain.TagsDO;
import com.juran.index.mdm.app.domain.ThreeCaseDO;
import com.juran.index.mdm.app.domain.TwoCaseDO;
import com.juran.index.mdm.app.manager.SolrdumpManager;
import com.juran.index.mdm.app.utils.ParamCheckUtil;
import com.juran.index.mdm.client.bean.response.enums.SolrdumpClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * FileName: CaseIndexResource.java
 * Author:   zhangyihang
 * Date:    2017年10月17日 11:27:34
 * Description: //模块目的、功能描述 <2D，3D，标签全量接口 dumo实现和数据转化>  <br>
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
@Service
public class CaseDaoImple implements CaseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaseTheadPool.class);
    @Autowired
    SolrdumpManager solrdumpManager;


    @Override
    public boolean dumpTagsListIntoSolr(List<TagsDO> tagsDOs) {
        if (ParamCheckUtil.checkListNull(tagsDOs)){
            return false;
        }
        List<SolrInputDocument> docs = TagsConverter.tagsListConverter(tagsDOs);
        return solrdumpManager.dumpListDataIntoSolr(SolrdumpClient.TAGS,docs);
    }

    @Override
    public boolean dump2DcaseListIntoSolr(List<TwoCaseDO> twoCaseDOs) {
        if(ParamCheckUtil.checkListNull(twoCaseDOs)){
            return false;
        }
        List<SolrInputDocument> docs = TwoCaseConverter.twoCaseListConverter(twoCaseDOs);
        return solrdumpManager.dumpListDataIntoSolr(SolrdumpClient.TWO_CASE,docs);
    }

    @Override
    public boolean dump3DcaseListIntoSolr(List<ThreeCaseDO> threeCaseDOs) {
        if(ParamCheckUtil.checkListNull(threeCaseDOs)){
            return false;
        }
        List<SolrInputDocument> docs = ThreeCaseConverter.threeCaseListConverter(threeCaseDOs);
        LOGGER.info("添加进来的doc大小{}",docs.size());
        return solrdumpManager.dumpListDataIntoSolr(SolrdumpClient.THREE_CASE,docs);
    }
}
