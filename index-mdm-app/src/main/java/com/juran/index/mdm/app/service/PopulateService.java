package com.juran.index.mdm.app.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juran.core.exception.bean.ErrorMsgBean;
import com.juran.core.utils.http.HttpSendUtil;
import com.juran.core.utils.http.bean.HttpResponse;
import com.juran.core.utils.json.JsonUtil;
import com.juran.index.mdm.app.contants.IndexConstants;
import com.juran.index.mdm.app.contants.PopulateConfigContants;
import com.juran.index.mdm.app.contants.SolrContants;
import com.juran.index.mdm.app.dao.PopulateDao;
import com.juran.index.mdm.app.dao.bean.Populate;
import com.juran.index.mdm.app.utils.StrHandleUtil;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 作者:xy<br>
 * 功能描述:PopulateCatalog 逻辑处理
 */
@Service
public class PopulateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PopulateService.class);

    @Autowired
    private PopulateDao populateDao;

    @Autowired
    private PopulateConfigContants populateConfigContants;


    /**
     * 作者:xy<br>
     * 功能描述:full populateCatalog 处理
     */
    public Object fullPopulate() {
        boolean index = false;
        Map<String, String> populateJsons = populateConfigContants.getPopulateJsons();
        List<Map<String, String>> lsitIndex = populateDao.queryIndexByCoreName(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY);
        if (lsitIndex != null && lsitIndex.size() > 0) {
            ErrorMsgBean errorMsgBean = new ErrorMsgBean();
            errorMsgBean.setErrorMsg("正在刷新索引，稍后再试！");
            return errorMsgBean;
        }
        if (populateJsons != null && populateJsons.size() > 0) {
            try {
                populateDao.insertIndex(ImmutableMap.of("indexType", SolrContants.Command.FULL_IMPORT, "coreName", SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY));
                index = true;
                executorSql(jsonStr2PopulateCatalog(populateJsons.get(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY)), IndexConstants.WIDE_TABLE_SCOPE_FULL);
                executorSql(jsonStr2PopulateCatalog(populateJsons.get(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGGROUP)), IndexConstants.WIDE_TABLE_SCOPE_FULL);
                executorSql(jsonStr2PopulateCatalog(populateJsons.get(SolrContants.SolrCoreName.JURAN_MC_2_BRAND)), IndexConstants.WIDE_TABLE_SCOPE_FULL);
                processorCatgroupPath();

                Map<String, String> params = ImmutableMap.of("command", SolrContants.Command.FULL_IMPORT, "commit", "true");

                dataimportSolr(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY, SolrContants.RequestHandler.DATA_IMPORT, params);
                dataimportSolr(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGGROUP, SolrContants.RequestHandler.DATA_IMPORT, params);
                dataimportSolr(SolrContants.SolrCoreName.JURAN_MC_2_BRAND, SolrContants.RequestHandler.DATA_IMPORT, params);
                Thread.sleep(20000);
            } catch (Exception e) {
                LOGGER.error("--fullPopulate--populateJsons={},error={}", populateJsons, e);
                ErrorMsgBean errorMsgBean = new ErrorMsgBean();
                errorMsgBean.setErrorMsg(e.getMessage());
                return errorMsgBean;
            } finally {
                if (index) {
                    populateDao.deleteIndexByCoreName(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY);
                }
            }
        }
        return true;
    }

    /**
     * 作者:xy<br>
     * 功能描述:solr catentry增量
     */
    public Object deltaCatentry() {
        boolean index = false;
        try {
            List<Map<String, String>> modifyCatentryKey = populateDao.queryDeltaCatentryByAction(null);
            if (modifyCatentryKey != null && modifyCatentryKey.size() > 0) {
                List<Map<String, String>> lsitIndex = populateDao.queryIndexByCoreName(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY);
                if (lsitIndex != null && lsitIndex.size() > 0) {
                    return false;
                }
                populateDao.insertIndex(ImmutableMap.of("indexType", SolrContants.Command.DELTA_IMPORT, "coreName", SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY));
                index = true;
                Map<String, String> populateJsons = populateConfigContants.getPopulateJsons();
                executorSql(jsonStr2PopulateCatalog(populateJsons.get(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY)), IndexConstants.WIDE_TABLE_SCOPE_DELTA);
                processorCatgroupPath();//2.商品对应目录

                Map<String, String> params = ImmutableMap.of("command", SolrContants.Command.DELTA_IMPORT, "commit", "true");
                boolean modifyResult = dataimportSolr(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY, SolrContants.RequestHandler.DATA_IMPORT, params);
                LOGGER.info("--deltaCatentry--modifyResult={},modifyCatentryKey={}", modifyResult, modifyCatentryKey);
                if (modifyResult) {
                    Thread.sleep(3000);
                    populateDao.deleteDeltaCatentryByKey(modifyCatentryKey);
                }
            }
        } catch (Exception e) {
            LOGGER.error("--deltaCatentry--solrCoreName={},error={}", SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY, e);
            ErrorMsgBean errorMsgBean = new ErrorMsgBean();
            errorMsgBean.setErrorMsg(e.getMessage());
            return errorMsgBean;
        } finally {
            if (index) {
                populateDao.deleteIndexByCoreName(SolrContants.SolrCoreName.JURAN_MC_2_CATALOGENTRY);
            }
        }
        return true;
    }

    /**
     * 作者:xy<br>
     * 功能描述:solr brand增量
     */
    public Object deltaBrand() {
        boolean index = false;
        try {
            List<Map<String, String>> modifyBrandId = populateDao.queryDeltaBrandByAction(null);
            if (modifyBrandId != null && modifyBrandId.size() > 0) {
                List<Map<String, String>> lsitIndex = populateDao.queryIndexByCoreName(SolrContants.SolrCoreName.JURAN_MC_2_BRAND);
                if (lsitIndex != null && lsitIndex.size() > 0) {
                    return false;
                }
                populateDao.insertIndex(ImmutableMap.of("indexType", SolrContants.Command.DELTA_IMPORT, "coreName", SolrContants.SolrCoreName.JURAN_MC_2_BRAND));
                index = true;
                Map<String, String> params = ImmutableMap.of("command", SolrContants.Command.DELTA_IMPORT, "commit", "true");
                boolean modifyResult = dataimportSolr(SolrContants.SolrCoreName.JURAN_MC_2_BRAND, SolrContants.RequestHandler.DATA_IMPORT, params);
                LOGGER.info("--deltaBrand--modifyResult={},modifyCatentryKey={}", modifyResult, modifyBrandId);
                if (modifyResult) {
                    Thread.sleep(2000);
                    populateDao.deleteDeltaBrandByBrandId(modifyBrandId);
                }
            }
        } catch (Exception e) {
            LOGGER.error("--deltaBrand--solrCoreName={},error={}", SolrContants.SolrCoreName.JURAN_MC_2_BRAND, e);
            ErrorMsgBean errorMsgBean = new ErrorMsgBean();
            errorMsgBean.setErrorMsg(e.getMessage());
            return errorMsgBean;
        } finally {
            if (index) {
                populateDao.deleteIndexByCoreName(SolrContants.SolrCoreName.JURAN_MC_2_BRAND);
            }
        }
        return true;
    }

    /**
     * 作者:xy<br>
     * 功能描述:dataimportCatalog command
     */
    private boolean dataimportSolr(String coreName, String requestHandler, Map<String, String> params) throws Exception {
        boolean result = false;
        try {
            Map<String, String> solrMap=populateConfigContants.getSolrMap();
            List<String> masterIps = populateConfigContants.getSolrMasterIps();
            for (String masetrIp : masterIps) {
                String urlMaster = SolrContants.urlBase.replaceAll("\\{IP\\}", masetrIp).replaceAll("\\{coreName\\}",solrMap.get(coreName)).concat(requestHandler);
                //master
                HttpResponse httpResponse = HttpSendUtil.sendHttpGet(urlMaster, params);
                LOGGER.info("--dataimportSolr--urlMaster={},params={},httpResponse={}", urlMaster, params, httpResponse);
                if (httpResponse.getStatus() == HttpStatus.OK.value()) {
                    //slave
                    /*List<String> slaveIps = populateConfigContants.getSolrSalveIps();
                    for (String slaveIp : slaveIps) {
                        String urlSlave = SolrContants.urlBase.replaceAll("\\{IP\\}", slaveIp).replaceAll("\\{coreName\\}",solrMap.get(coreName)).concat(SolrContants.RequestHandler.REPLICATION);
                        HttpResponse replicationResponse = HttpSendUtil.sendHttpGet(urlSlave, ImmutableMap.of("command", SolrContants.Command.FETCHINDEX));
                        LOGGER.info("--dataimportSolr--urlSlave={},params={},replicationResponse={}", urlSlave, ImmutableMap.of("command", SolrContants.Command.FETCHINDEX), replicationResponse);
                    }*/
                    result = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("--dataimportSolr--coreName={},requestHandler={},params={},e={}", coreName, requestHandler, params, e);
            throw new RuntimeException("dataimportSolr:".concat(coreName).concat(requestHandler).concat(",error:").concat(e.getMessage()));
        }
        return result;
    }


    /**
     * 作者:xy
     * 功能描述: executorSql
     */
    private boolean executorSql(Populate populate, String scope) throws Exception {
        boolean result = true;
        if (populate != null) {
            String tableName = null;
            try {
                List<Populate.PopulateBean> listPopulateCatalogGroupBean = populate.getPopulateCatalog();
                for (Populate.PopulateBean populateCatalogGroupBean : listPopulateCatalogGroupBean) {
                    if (StringUtils.isEmpty(populateCatalogGroupBean.getScope()) || populateCatalogGroupBean.getScope().equals(scope)) {
                        tableName = populateCatalogGroupBean.getTableName();
                        Long startTime = System.currentTimeMillis();
                        LOGGER.info("++executorSql++start++tableName={}", tableName);
//                        String dropSql = "DROP TABLE IF EXISTS ".concat(tableName);
                        String dropSql = "TRUNCATE ".concat(tableName);
                        int dropNum = populateDao.executorOperateSql(dropSql);
                        Long dropTime = System.currentTimeMillis();
                        LOGGER.info("--executorSql--dropNum={},time={}", dropNum, dropTime - startTime);
//                        String createSql = populateCatalogGroupBean.getCreateSQL();
                        //                      int creatNum = populateDao.executorOperateSql(createSql);
                        //                    Long creatTime = System.currentTimeMillis();
                        //                  LOGGER.info("--executorSql--creatNum={},time={}", creatNum, creatTime - dropTime);
                        List<Map<String, Object>> listMap = populateDao.executorSelectSql(populateCatalogGroupBean.getQuerySQL());
                        Long selectTime = System.currentTimeMillis();
                        LOGGER.info("--executorSql--selectNum={},time={}", listMap == null ? 0 : listMap.size(), selectTime - dropTime);
                        if (listMap != null && listMap.size() > 0) {
                            String insertBatchSql = "INSERT INTO ".concat(tableName).concat(" ({#COLUMN#}) VALUES {#VALUUE#}");
                            List<String> colnums = Lists.newArrayList();
                            List<String> batchValues = Lists.newArrayList();
//                        String[] dbtable=tableName.split("\\.");
//                        List<String> listColumnName = populateCatalogDao.executorSelectSqlToList("select COLUMN_NAME from information_schema.COLUMNS where table_name = '"+dbtable[1]+"' and table_schema = '"+dbtable[0]+"';");
                            boolean col = true;
                            for (Map<String, Object> map : listMap) {
                                List<String> values = Lists.newArrayList();
             /*               for(String columnName:listColumnName){
                                Object value=map.get(columnName);
                                if(value==null){
                                    values.add(null);
                                }else {
                                    values.add(String.valueOf(value).replaceAll("'","\\\\'"));
                                }
                            }*/
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if (col) {
                                        colnums.add(entry.getKey());
                                    }
                                    values.add(String.valueOf(entry.getValue()).replaceAll("'", "\\\\'"));
                                }
                                col = false;
                                batchValues.add("'".concat(StringUtils.join(values.toArray(), "','")).concat("'"));
                            }
                            insertBatchSql = insertBatchSql.replace("{#COLUMN#}", StringUtils.join(colnums.toArray(), ",")).replace("{#VALUUE#}", "(".concat(StringUtils.join(batchValues.toArray(), "),(")).concat(")"));
                            int insertBatchNum = populateDao.executorInsertSql(insertBatchSql);
                            Long insertTime = System.currentTimeMillis();
                            LOGGER.info("--executorSql--insertBatchNum={},time={}", insertBatchNum, insertTime - selectTime);
                        }
                        LOGGER.info("++executorSql++end++tableName={},time={}", tableName, System.currentTimeMillis() - startTime);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("--executorSql--tableName={},error={}", tableName, e);
                throw new RuntimeException("tableName:".concat(tableName).concat(",error:").concat(e.getMessage()));
            }
        }
        return result;
    }


    /**
     * 作者:xy<br>
     * 功能描述:json2Bean
     */
    private Populate jsonStr2PopulateCatalog(String fileName) {
        Populate populate = null;
        try {
            String str = StrHandleUtil.in2Str(fileName, "UTF-8");
            populate = JsonUtil.toObject(str, Populate.class);
        } catch (Exception e) {
            LOGGER.error("--executorSql--error={}", e);
        }
        return populate;
    }

    public void processorCatgroupPath() {
        Long startTime = System.currentTimeMillis();
        String tableName1 = "solrtemp1.TI_CATGROUP_PATH_0";
        String dropSql1 = "TRUNCATE ".concat(tableName1);
        int dropNum1 = populateDao.executorOperateSql(dropSql1);

        String tableName2 = "solrtemp1.TI_APGROUP_0";
        String dropSql2 = "TRUNCATE ".concat(tableName2);
        int dropNum2 = populateDao.executorOperateSql(dropSql2);

        Long dropTime = System.currentTimeMillis();
        LOGGER.info("--processorCatgroupPath--dropNum={},time={}", dropNum1 + dropNum2, dropTime - startTime);
//        TI_CATENTRY_0.CATENTRY_KEY
        String queryCatentryCatGroup = "SELECT TI_CATENTRY_0.CATENTRY_KEY,CATGPENREL.CATGROUP_KEY,'4' AS CATALOG_KEY FROM solrtemp1.TI_CATENTRY_0 TI_CATENTRY_0 JOIN mdmdb2.CATGPENREL CATGPENREL ON TI_CATENTRY_0.CATENTRY_KEY=CATGPENREL.CATENTRY_KEY WHERE CATGPENREL.CATALOG_KEY=4 AND TI_CATENTRY_0.CATENTTYPE_ID='spu' UNION SELECT TI_CATENTRY_0.CATENTRY_KEY,CATGRPOPTREL.CATGROUP_KEY,'1' AS CATALOG_KEY FROM solrtemp1.TI_CATENTRY_0 TI_CATENTRY_0 JOIN mdmdb2.CATGPENREL CATGPENREL ON TI_CATENTRY_0.CATENTRY_KEY=CATGPENREL.CATENTRY_KEY JOIN mdmdb2.CATGRPOPTREL CATGRPOPTREL ON CATGRPOPTREL.REL_CATGROUP_KEY=CATGPENREL.CATGROUP_KEY WHERE CATGPENREL.CATALOG_KEY=2 AND TI_CATENTRY_0.CATENTTYPE_ID='spu'";

        List<Map<String, Object>> catentryCatGroupList = populateDao.executorSelectSql(queryCatentryCatGroup);
        Map<String, String> mapCatGroups = Maps.newHashMap();
        if (catentryCatGroupList != null && catentryCatGroupList.size() > 0) {
            for (Map<String, Object> map : catentryCatGroupList) {
                String catgroup_key = String.valueOf(map.get("CATGROUP_KEY"));
                if (mapCatGroups.containsKey(catgroup_key)) {
                    continue;
                }
                String catalog_key = String.valueOf(map.get("CATALOG_KEY"));
                mapCatGroups.put(catgroup_key, catalog_key.concat("_").concat(catgroup_key));
                processorCatgroupParentPath(mapCatGroups, catgroup_key, catgroup_key, catalog_key);
            }
        }
        if (mapCatGroups != null && mapCatGroups.size() > 0) {
            populateDao.insertCatgroupPath(mapCatGroups);
            populateDao.insertCatgroupCatentry();
        }
        LOGGER.info("--processorCatgroupPath--time={}", System.currentTimeMillis() - startTime);
    }

    public void processorCatgroupParentPath(Map<String, String> mapCatGroups, String catgroup_key, String catgroup_key_parent, String catalog_key) {
        String queryCatentryCatGroup = "SELECT catgrprel.CATGROUP_KEY_PARENT  FROM mdmdb2.catgrprel catgrprel WHERE  catgrprel.CATGROUP_KEY_CHILD=".concat(catgroup_key_parent).concat(";");
        List<String> catentryCatGroupList = populateDao.executorSelectSqlToList(queryCatentryCatGroup);
        if (catentryCatGroupList != null && catentryCatGroupList.size() > 0) {
            String catentryCatGroupParent = catentryCatGroupList.get(0);
            String catgroupParentPath = mapCatGroups.get(catgroup_key).concat(";").concat(catalog_key).concat("_").concat(catentryCatGroupParent);
            mapCatGroups.put(catgroup_key, catgroupParentPath);
            processorCatgroupParentPath(mapCatGroups, catgroup_key, catentryCatGroupParent, catalog_key);
        }
    }
}
