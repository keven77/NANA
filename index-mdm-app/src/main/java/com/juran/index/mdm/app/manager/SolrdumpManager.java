/*
 * FileName: SolrdumpManager.java
 * Author:   zhangyihang
 * Date:     2017年10月13日 09:41:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.juran.index.mdm.app.manager;

import com.alibaba.fastjson.JSONObject;
import com.juran.index.mdm.app.utils.ParamCheckUtil;
import com.juran.index.mdm.client.bean.response.enums.SolrdumpClient;
import com.juran.index.mdm.client.execption.SolrdumpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈dump〉
 *
 * @author zhangyihang 2017年10月16日 10:56:08
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SolrdumpManager {

    private static final Logger logger = LoggerFactory.getLogger(SolrdumpManager.class);

    @Autowired
    SolrClientInitManager solrClientInitManager;

    private static final int SOLR_DUMP_SUCCESS = 0;



    /**
     *
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param solrdumpClient
     * @param doc
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public boolean dumpDataIntoSolr(SolrdumpClient solrdumpClient, SolrInputDocument doc) {
        if (null == doc) {
            return false;
        }
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        docs.add(doc);
        return dumpListDataIntoSolr(solrdumpClient, docs);
    }
    /**
     *
     * 功能描述: <br>
     * 〈往solrdump 数据〉
     *
     * @param solrdumpClient
     * @param docs
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public boolean dumpListDataIntoSolr(SolrdumpClient solrdumpClient, List<SolrInputDocument> docs) {
        if (ParamCheckUtil.checkListNull(docs)) {
            return false;
        }
        boolean dumpSuc = false;
        SolrClient solrClient = null;
        try {
            solrClient = solrClientInitManager.getClient();
            if (null == solrClient) {
                throw new SolrdumpException(solrdumpClient.getName() + " query solrClientError");
            }

            UpdateResponse response = solrClient.add(solrdumpClient.getCollection(), docs);
            if (SOLR_DUMP_SUCCESS == response.getStatus()) {
                dumpSuc = true;
                logger.info("{} parSize:{} solrdump success,ThreadName{}", solrdumpClient.getName(), docs.size(),Thread.currentThread().getName());
            } else {
                logger.info("{} par:{} solrdump fail, return:{}", solrdumpClient.getName(),
                        JSONObject.toJSONString(docs), response);
            }
        } catch (SolrdumpException e) {
            solrClientInitManager.initClient();
            logger.error("{} par:{} get solrClient fail, mes:{}", solrdumpClient.getName(),
                    JSONObject.toJSONString(docs), e);
        } catch (Exception e) {
            logger.error("{} par:{} solrClient submit fail, mes:{}", solrdumpClient.getName(),
                    JSONObject.toJSONString(docs), e);
        } finally {
            if (dumpSuc) {
                try {
                    solrClient.commit(solrdumpClient.getCollection());
                } catch (Exception e) {
                    logger.error("{} par:{} solrClient commit fail, mes:{}", solrdumpClient.getName(),
                            JSONObject.toJSONString(docs), e);
                }
            }
        }
        return dumpSuc;
    }

    /**
     *
     * 功能描述: <br>
     * 〈批量删除〉
     *
     * @param solrdumpClient
     * @param idList
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public boolean deleteSolrDataById(SolrdumpClient solrdumpClient, List<String> idList) {
        boolean dumpSuc = false;
        SolrClient solrClient = null;
        try {
            logger.info("{} par:{} deleteSolrDataById begin", solrdumpClient.getName(),
                    JSONObject.toJSONString(idList));
            solrClient = solrClientInitManager.getClient();
            UpdateResponse response = solrClient.deleteById(solrdumpClient.getCollection(), idList);
            if (SOLR_DUMP_SUCCESS == response.getStatus()) {
                dumpSuc = true;
                logger.info("{} parSize:{} deleteSolrDataById success", solrdumpClient.getName(), idList.size());
            } else {
                logger.info("{} par:{} deleteSolrDataById fail, return:{}", solrdumpClient.getName(),
                        JSONObject.toJSONString(idList), response);
            }
        } catch (Exception e) {
            logger.error("{} par:{} solrClient delete fail, mes:{}", solrdumpClient.getName(),
                    JSONObject.toJSONString(idList), e);
        } finally {
            if (dumpSuc) {
                try {
                    solrClient.commit(solrdumpClient.getCollection());
                } catch (Exception e) {
                    logger.error("{} par:{} solrClient commit fail, mes:{}", solrdumpClient.getName(),
                            JSONObject.toJSONString(idList), e);
                }
            }
        }
        return dumpSuc;
    }

    /**
     *
     * 功能描述: <br>
     * 〈删除〉
     *
     * @param solrdumpClient
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public boolean deleteSolrDataById(SolrdumpClient solrdumpClient, String id) {
        List<String> idList = new ArrayList<String>();
        idList.add(id);
        return deleteSolrDataById(solrdumpClient, idList);
    }

    /**
     *
     * 功能描述: <br>
     * 〈查询〉
     *
     * @param solrdumpClient
     * @param solrQuery
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public QueryResponse queryDataFromSolr(SolrdumpClient solrdumpClient, SolrQuery solrQuery) {
        try {
            SolrClient solrClient = solrClientInitManager.getClient();
            return solrClient.query(solrdumpClient.getCollection(), solrQuery, METHOD.POST);
        } catch (Exception e) {
            logger.error("queryDataFromSolr par:{}, type:{} fail, mes:{}", solrQuery,
                    JSONObject.toJSONString(solrdumpClient), e);
        }
        return null;
    }

    /**
     *
     * 功能描述: <br>
     * 〈更新〉
     *
     * @param solrdumpClient
     * @param doc
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public boolean updateLitter(SolrdumpClient solrdumpClient, SolrInputDocument doc) {
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        docs.add(doc);
        return updateLitter(solrdumpClient, docs);
    }


    /**
     *
     * 功能描述: <br>
     * 〈增量更新〉
     *
     * @param solrdumpClient
     * @param docs
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public boolean updateLitter(SolrdumpClient solrdumpClient, List<SolrInputDocument> docs) {
        boolean dumpSuc = false;
        SolrClient solrClient = null;
        try {
            solrClient = solrClientInitManager.getClient();
            UpdateResponse response = solrClient.add(solrdumpClient.getCollection(), docs);
            if (SOLR_DUMP_SUCCESS == response.getStatus()) {
                dumpSuc = true;
                logger.info("{} updateLitter parSize:{} update success", solrdumpClient.getName(), docs.size());
            } else {
                logger.info("{} par:{} updateLitter fail, return:{}", solrdumpClient.getName(),
                        JSONObject.toJSONString(docs), response);
            }
        } catch (Exception e) {
            logger.error("{} updateLitter parSize:{} update fail, mes:{}", solrdumpClient.getName(), docs.size(), e);
        } finally {
            if (dumpSuc) {
                try {
                    solrClient.commit(solrdumpClient.getCollection());
                } catch (Exception e) {
                    logger.error("{} updateLitter parSize:{} commit fail, mes:{}", solrdumpClient.getName(),
                            docs.size(), e);
                }
            }
        }
        return dumpSuc;
    }



    /**
     *
     * 功能描述: <br>
     * 〈模糊删除〉
     *
     * @param solrdumpClient
     * @param query
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public boolean deleteByQuery(SolrdumpClient solrdumpClient, String query) {
        if(StringUtils.isBlank(query)){
            logger.info("{} deleteByQuery par is null", solrdumpClient.getName());
            return false;
        }
        boolean dumpSuc = false;
        SolrClient solrClient = null;
        try {
            solrClient = solrClientInitManager.getClient();
            UpdateResponse response = solrClient.deleteByQuery(solrdumpClient.getCollection(), query);
            if (SOLR_DUMP_SUCCESS == response.getStatus()) {
                dumpSuc = true;
                logger.info("{} deleteByQuery par:{} delete success", solrdumpClient.getName(), query);
            } else {
                logger.info("{} par:{} deleteByQuery fail, return:{}", solrdumpClient.getName(),
                        query, response);
            }
        } catch (Exception e) {
            logger.error("{} deleteByQuery par:{} delete fail, mes:{}", solrdumpClient.getName(), query, e);
        } finally {
            if (dumpSuc) {
                try {
                    solrClient.commit(solrdumpClient.getCollection());
                } catch (Exception e) {
                    logger.error("{} deleteByQuery par:{} commit fail, mes:{}", solrdumpClient.getName(),
                            query, e);
                }
            }

        }
        return dumpSuc;
    }

    /**
     *
     * 功能描述: <br>
     * 〈优化索引〉
     *
     * @param solrdumpClient
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void optimizeCollection(SolrdumpClient solrdumpClient){
        try{
            SolrClient solrClient = solrClientInitManager.getClient();
            UpdateResponse optimize = solrClient.optimize(solrdumpClient.getCollection());
            logger.info("optimizeCollection par:{} optimize return:{}", solrdumpClient.getName(), optimize);
        }catch(Exception e){
            logger.error("optimizeCollection par:{} optimize error:{}", solrdumpClient.getName(), e);
        }
    }

}
