package com.juran.index.mdm.app.contants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 作者:xy<br>
 * 功能描述:populate 常量
 */
@Component
@ConfigurationProperties(prefix = "populateConfigContants")
public class PopulateConfigContants {
    private Map<String, String> populateJsons;

    private Map<String, String> solrMap;
    private List<String> solrMasterIps;
    private List<String> solrSalveIps;

    public Map<String, String> getPopulateJsons() {
        return populateJsons;
    }

    public void setPopulateJsons(Map<String, String> populateJsons) {
        this.populateJsons = populateJsons;
    }

    public Map<String, String> getSolrMap() {
        return solrMap;
    }

    public void setSolrMap(Map<String, String> solrMap) {
        this.solrMap = solrMap;
    }

    public List<String> getSolrMasterIps() {
        return solrMasterIps;
    }

    public void setSolrMasterIps(List<String> solrMasterIps) {
        this.solrMasterIps = solrMasterIps;
    }

    public List<String> getSolrSalveIps() {
        return solrSalveIps;
    }

    public void setSolrSalveIps(List<String> solrSalveIps) {
        this.solrSalveIps = solrSalveIps;
    }
}
