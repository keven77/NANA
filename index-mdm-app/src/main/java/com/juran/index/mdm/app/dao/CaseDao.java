package com.juran.index.mdm.app.dao;

import com.juran.index.mdm.app.domain.TagsDO;
import com.juran.index.mdm.app.domain.ThreeCaseDO;
import com.juran.index.mdm.app.domain.TwoCaseDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CaseDao {
    /**
     *
     * 功能描述: <br>
     * 〈desgin -app 全量导入〉
     * @param beans
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    boolean dumpTagsListIntoSolr(List<TagsDO> tagsDOs);


    boolean dump2DcaseListIntoSolr(List<TwoCaseDO> twoCaseDOs);


    boolean dump3DcaseListIntoSolr(List<ThreeCaseDO> threeCaseDOs);
}
