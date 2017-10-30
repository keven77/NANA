package com.juran.index.mdm.app.dao.bean;

import java.util.List;

/**
 * 作者:xy<br>
 * 功能描述:PopulateCatalog*.json Bean
 */
public class Populate {

    private List<PopulateBean> populate;

    public List<PopulateBean> getPopulateCatalog() {
        return populate;
    }

    public void setPopulateCatalog(List<PopulateBean> populate) {
        this.populate = populate;
    }

    public static class PopulateBean {

        private String tableName;
        private String scope;
        private String createSQL;
        private String querySQL;

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getCreateSQL() {
            return createSQL;
        }

        public void setCreateSQL(String createSQL) {
            this.createSQL = createSQL;
        }

        public String getQuerySQL() {
            return querySQL;
        }

        public void setQuerySQL(String querySQL) {
            this.querySQL = querySQL;
        }
    }
}
