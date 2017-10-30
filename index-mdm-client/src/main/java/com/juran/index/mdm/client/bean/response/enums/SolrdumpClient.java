/*
 * FileName: ExaminType.java
 * Author:   liubb
 * Date:     2016年3月26日 下午3:04:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.juran.index.mdm.client.bean.response.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author zhangyihang 2017年10月16日 10:37:47
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum SolrdumpClient {
    
    THREE_CASE("3D案列", 1, "Juran_MC_2_3DCase"),
    TWO_CASE("2D案列", 2, "Juran_MC_2_2DCase"),
    TAGS("标签列表", 3, "Juran_MC_2_Tags");

    private String name;
    private int id;
    private String collection;

    SolrdumpClient(String name, int id, String collection) {
        this.name = name;
        this.id = id;
        this.collection =  collection;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCollection() {
        return collection;
    }

    
    public static SolrdumpClient getClientByCollection(String collection) {
        if(StringUtils.isBlank(collection)){
            return null;
        }
        for (SolrdumpClient solrdumpClient : SolrdumpClient.values()) {
            if (solrdumpClient.getCollection().equals(collection)) {
                return solrdumpClient;
            }
        }
        return null;
    }
    
    
    public static SolrdumpClient getClientById(int id) {
        for (SolrdumpClient solrdumpClient : SolrdumpClient.values()) {
            if (solrdumpClient.getId() == id) {
                return solrdumpClient;
            }
        }
        return null;
    }

}
