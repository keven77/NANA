package com.juran.index.mdm.app.service;

import com.google.common.collect.Lists;
import com.juran.index.mdm.app.dao.CaseDao;
import com.juran.index.mdm.app.dao.CaseTheadPool;
import com.juran.index.mdm.app.dao.bean.ThreeCaseBean;
import com.juran.index.mdm.app.dao.bean.TwoCaseBean;
import com.juran.index.mdm.app.domain.TagsDO;
import com.juran.index.mdm.app.domain.ThreeCaseDO;
import com.juran.index.mdm.app.domain.TwoCaseDO;
import com.juran.index.mdm.app.manager.SolrdumpManager;
import com.juran.index.mdm.app.threadPoolUtils.CustomThreadFactory;
import com.juran.index.mdm.app.threadPoolUtils.RejectedExecutionHandlerImpl;
import com.juran.index.mdm.app.utils.ClassUtil;
import com.juran.index.mdm.app.utils.MDQueryUtil;
import com.juran.index.mdm.client.bean.response.enums.SolrdumpClient;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * FileName: CaseindexService.java
 * Author:   zhangyihang
 * Date:    2017年10月17日 09:02:12
 * Description: //模块目的、功能描述 《2D，3D，标签全量接口 实现类,逻辑处理》
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
@Service
public class CaseindexService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaseindexService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    SolrdumpManager solrdumpManager;

    @Autowired
    CaseDao caseDao;

    private static JSONObject resultJson = new JSONObject();

    public Object fullTagsDump() {
        try {
            List<TagsDO> resListResult = new ArrayList<TagsDO>();
            Map<String, Object> valuesMap = ClassUtil.getClassFieldAndValue(null, Boolean.FALSE);
            Query basicQuery = MDQueryUtil.getDBObjectByValuesAndLike(valuesMap, Lists.newArrayList());
            long totalCount = mongoTemplate.count(basicQuery, TagsDO.class);
            int limit = 10;
            int offset = 0;
            do {
                //查询
                basicQuery.skip(offset).limit(limit);
                resListResult.addAll(mongoTemplate.find(basicQuery, TagsDO.class));
                offset += 10;
                caseDao.dumpTagsListIntoSolr(resListResult);
            } while (resListResult.size() < totalCount);
            resultJson.put("dataCode", 200);
        } catch (Exception e) {
            LOGGER.error("dump fullTagsDump error{}", e);
        }

        return resultJson;
    }

    public Object full2DcaseDump() {
        try {
            List<TwoCaseDO> twoCaseResult = new ArrayList<TwoCaseDO>();
            TwoCaseBean params = new TwoCaseBean();
            Map<String, Object> valuesMap = ClassUtil.getClassFieldAndValue(params, Boolean.FALSE);
            Query basicQuery = MDQueryUtil.getDBObjectByValuesAndLike(valuesMap, Lists.newArrayList());
            long totalCount = mongoTemplate.count(basicQuery, TwoCaseDO.class);
            int limit = 200;
            int offset = 0;
            do {
                //查询
                basicQuery.skip(offset).limit(limit);
                twoCaseResult.addAll(mongoTemplate.find(basicQuery, TwoCaseDO.class));
                offset += 200;
                caseDao.dump2DcaseListIntoSolr(twoCaseResult);
            } while (twoCaseResult.size() < totalCount);
            resultJson.put("dataCode", 200);
        } catch (Exception e) {
            LOGGER.error("dump full2DcaseDump error{}", e);
        }
        return resultJson;
    }


    public Object full3DcaseDump(){
        try{
            List<ThreeCaseDO> threeCaseResult = new ArrayList<ThreeCaseDO>();
            ThreeCaseBean param = new ThreeCaseBean();
            Map<String,Object> valuesMap = ClassUtil.getClassFieldAndValue(param,Boolean.FALSE);
            Query basicQuery = MDQueryUtil.getDBObjectByValuesAndLike(valuesMap,Lists.newArrayList());
            long totalCount = mongoTemplate.count(basicQuery,ThreeCaseDO.class);
            int limit = 300;
            int offset = 0;
            do {
                //查询
                basicQuery.skip(offset).limit(limit);
                threeCaseResult.addAll(mongoTemplate.find(basicQuery, ThreeCaseDO.class));
                offset += 300;
                caseDao.dump3DcaseListIntoSolr(threeCaseResult);
            }while (threeCaseResult.size() < totalCount);
        }catch (Exception e){
            LOGGER.error("dump full3DcaseDump{}",e);
        }
        return "success";
    }


    public Object full3DcaseDump2() {
        try {
            //拒绝策略模式。
            RejectedExecutionHandlerImpl rejectedExecution = new RejectedExecutionHandlerImpl();
            //实现pool的工程模式
            CustomThreadFactory customThreadFactory = new CustomThreadFactory();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 60, 60, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(30), customThreadFactory, rejectedExecution);
            long start = System.currentTimeMillis();
            LOGGER.info("activeCountMain1{} ", Thread.activeCount());
            Map<String, Object> valuesMap = ClassUtil.getClassFieldAndValue(null, Boolean.FALSE);
            Query basicQuery = MDQueryUtil.getDBObjectByValuesAndLike(valuesMap, Lists.newArrayList());
            long totalCount = mongoTemplate.count(basicQuery, ThreeCaseDO.class);
            for (int i = 1; i <= totalCount / 10000 + 1; i++) {
                //读取数据库中的数据,20个线程 起来飞。
                int rowNum = 10000;
                if (i > totalCount / 10000) {
                    rowNum = (int) totalCount % 10000;
                }
                CaseTheadPool caseTheadPool = new CaseTheadPool(i, this.mongoTemplate, this.caseDao, rowNum);
                executor.execute(caseTheadPool);
                Thread.sleep(5000);
                LOGGER.info("线程池中线程数目{}, 队列中等待执行的任务数目{} , 已执行玩别的任务数目{}", executor.getPoolSize(), executor.getQueue().size(), executor.getCompletedTaskCount());
            }
            executor.shutdown();
            while (true) {
                if (executor.getActiveCount() == 0)
                    break;
            }
            LOGGER.info("activeCountMain2 {} ", Thread.activeCount());
            long end = System.currentTimeMillis();
            LOGGER.info("结束{}", (end - start) + " ms");
            resultJson.put("success", 200);
        } catch (Exception e) {
            LOGGER.error("dump full3DcaseDump error{}", e);
        }
        return resultJson;
    }


    public Object delete2Dcase(String ids){
        try {
            solrdumpManager.deleteSolrDataById(SolrdumpClient.TWO_CASE, ids);
        }catch (Exception e){
            LOGGER.error("delete 2dcase 失败{}",e);
        }
        return "success";
    }

    public Object delete3Dcase(String ids){
        try {
            solrdumpManager.deleteSolrDataById(SolrdumpClient.THREE_CASE, ids);
        }catch (Exception e){
            LOGGER.error("delete 3dcase 失败{}",e);
        }
        return "success";
    }

    public Object update3dCase(String id){

        try{
            List<ThreeCaseDO> threeCaseResult = new ArrayList<ThreeCaseDO>();
            Query basicQuery = MDQueryUtil.get3DcasetObjectByValues(id);
            //查询
            threeCaseResult.addAll(mongoTemplate.find(basicQuery, ThreeCaseDO.class));
            caseDao.dump3DcaseListIntoSolr(threeCaseResult);
        }catch (Exception e){
            LOGGER.error("dump update full3DcaseDump{}",e);
        }
        return "success";
    }

    public Object update2dCase(String id){

        try{
            List<TwoCaseDO> twoCaseResult = new ArrayList<TwoCaseDO>();
            Query basicQuery = MDQueryUtil.get2DcaseObjectByValues(id);
            //查询
            twoCaseResult.addAll(mongoTemplate.find(basicQuery, TwoCaseDO.class));
            caseDao.dump2DcaseListIntoSolr(twoCaseResult);
        }catch (Exception e){
            LOGGER.error("dump update full2DcaseDump{}",e);
        }
        return "success";
    }
}

