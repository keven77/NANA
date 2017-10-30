package com.juran.index.mdm.app.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juran.core.exception.bean.ErrorMsgBean;
import com.juran.core.utils.json.JsonUtil;
import com.juran.index.mdm.app.contants.IndexConstants;
import com.juran.index.mdm.app.dao.PopulateDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.juran.index.mdm.client.execption.enums.IndexMmErrorEnum.INDEX_MM_ALL_MUST_ERROR;

@Service
public class IndexMmService {
    @Autowired
    private PopulateDao populateDao;

    @Autowired
    private PopulateService populateService;

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexMmService.class);

    /**
     * 作者:xy<br>
     * 功能描述:商品
     */
    public Object notifyCatentry(String jsonParam) {
        try {
            List<Map> list = JsonUtil.toObjectList(jsonParam, Map.class);
            if (list != null && list.size() > 0) {
                List<Map<String, String>> insertList = Lists.newArrayList();

                for (Map<String, String> map : list) {
                    if (StringUtils.isNotEmpty(map.get("catentryId"))) {
                        Map<String, Object> mapKey = populateDao.queryCatentryKeyById(map.get("catentryId"));
                        if (mapKey != null && mapKey.get("catentryKey") != null) {
                            map.put("catentryKey", String.valueOf(mapKey.get("catentryKey")));
                            Map<String, Object> spuKey = populateDao.querySpuKeyBySkuKey(map.get("catentryKey"));
                            if (spuKey != null && spuKey.size() > 0) {
                                Map<String, String> insertSpuMap = Maps.newHashMap();
                                insertSpuMap.put("catentryKey", String.valueOf(spuKey.get("spuKey")));
                                insertSpuMap.put("action", IndexConstants.ACTION_MODIFY);
                                insertList.add(insertSpuMap);
                            }

                        } else {
                            continue;
                        }
                    }

                    Map<String, String> insertMap = Maps.newHashMap();
                    insertMap.put("catentryKey", map.get("catentryKey"));
                    if (IndexConstants.DeltaCatentryAction.MODIFY_LIST.contains(map.get("action"))) {
                        insertMap.put("action", IndexConstants.ACTION_MODIFY);
                    } else if (IndexConstants.DeltaCatentryAction.DELETEL_LIST.contains(map.get("action"))) {
                        insertMap.put("action", IndexConstants.DELETE_DELETE);
                    }
                    insertList.add(insertMap);
                }
                if (insertList != null && insertList.size() > 0) {
                    populateDao.batchNotifyCatentry(insertList);
//                    populateService.deltaCatentry();//2进行delta
                }
            }

        } catch (Exception e) {
            LOGGER.error("--notifyCatentry--jsonParam={},error-{}", jsonParam, e);
            ErrorMsgBean errorMsgBean = new ErrorMsgBean();
            errorMsgBean.setErrorCode(INDEX_MM_ALL_MUST_ERROR.getErrorCode());
            errorMsgBean.setErrorMsg("jsonParam:".concat(jsonParam).concat(",error:").concat(e.getMessage()));
            return errorMsgBean;
        }
        return true;
    }

    /**
     * 作者:xy<br>
     * 功能描述:品牌
     */
    public Object notifyBrand(String jsonParam) {
        try {
            List<Map> list = JsonUtil.toObjectList(jsonParam, Map.class);
            if (list != null && list.size() > 0) {
                List<Map<String, String>> insertList = Lists.newArrayList();
                for (Map<String, String> map : list) {
                    Map<String, String> insertMap = Maps.newHashMap();
                    insertMap.put("brandId",map.get("brandId"));
                    String action =map.get("action");
                    if(IndexConstants.DeltaBrandAction.MODIFY_LIST.contains(action)){//修改
                        insertMap.put("action",IndexConstants.ACTION_MODIFY);
                        populateDao.batchNotifyCatentryByBrandId(IndexConstants.ACTION_MODIFY,map.get("brandId"));
                    }
                    if(IndexConstants.DeltaBrandAction.DELETEL_LIST.contains(action)){
                        insertMap.put("action",IndexConstants.DELETE_DELETE);
                        populateDao.batchNotifyCatentryByBrandId(IndexConstants.DELETE_DELETE,map.get("brandId"));
                    }
                    insertList.add(insertMap);
                }

                if (insertList != null && insertList.size() > 0) {
                    populateDao.batchNotifyBrand(insertList);
//                    populateService.deltaBrand();//2进行delta
                }
            }

        } catch (Exception e) {
            LOGGER.error("--notifyBrand--jsonParam={},error-{}", jsonParam, e);
            ErrorMsgBean errorMsgBean = new ErrorMsgBean();
            errorMsgBean.setErrorCode(INDEX_MM_ALL_MUST_ERROR.getErrorCode());
            errorMsgBean.setErrorMsg("jsonParam:".concat(jsonParam).concat(",error:").concat(e.getMessage()));
            return errorMsgBean;
        }
        return true;
    }
}
