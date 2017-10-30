package com.juran.index.mdm.app.dao;

import com.google.common.collect.Lists;
import com.juran.index.mdm.app.domain.ThreeCaseDO;
import com.juran.index.mdm.app.utils.ClassUtil;
import com.juran.index.mdm.app.utils.MDQueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CaseTheadPool implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaseTheadPool.class);
    private final int taskNum;
    private final int rowNum;
    private final MongoTemplate mongoTemplate;
    private final CaseDao caseDao;

    public CaseTheadPool(int taskNum, MongoTemplate mongoTemplate, CaseDao caseDao, int rowNum) {
        this.taskNum = taskNum;
        this.rowNum = rowNum;
        this.mongoTemplate = mongoTemplate;
        this.caseDao = caseDao;
    }

    @Override
    public void run() {
        readMongonDB();
    }

    public void readMongonDB() {

        try {
            TimeUnit.SECONDS.sleep(10);
            LOGGER.info("---------task " + taskNum + "正在执行---------" + Thread.currentThread().getName());
            List<ThreeCaseDO> threeCaseResult = new ArrayList<ThreeCaseDO>();
            Map<String, Object> valuesMap = ClassUtil.getClassFieldAndValue(null, Boolean.FALSE);
            Query basicQuery = MDQueryUtil.getDBObjectByValuesAndLike(valuesMap, Lists.newArrayList());
            int start = (taskNum - 1) * 10000;
            for (int i = 1; i <= rowNum / 2000 + 1; i++) {
                int limit = rowNum;
                int offset = start;
                start += 2000;
                if (i > rowNum / 2000) {
                    limit = (rowNum % 2000);
                } else {
                    limit = 2000;
                }
                basicQuery.skip(offset).limit(limit);

                List<ThreeCaseDO> off=mongoTemplate.find(basicQuery, ThreeCaseDO.class);
                threeCaseResult.addAll(off);
                caseDao.dump3DcaseListIntoSolr(threeCaseResult);
                LOGGER.info("执行的数量~~~{},Thread{},=threeCaseResult={}", offset + limit, Thread.currentThread().getName(),off.size());
            }
        } catch (Exception e) {
            LOGGER.error("线程出差{}",e);
        }
        LOGGER.info("---------task " + taskNum + "执行完毕---------");
    }
}
