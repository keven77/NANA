/*
 * FileName: SolrClientMap.java
 * Author:   liubb
 * Date:     2016年5月14日 下午12:08:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.juran.index.mdm.app.manager;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈初始化client〉
 *
 * @author zhangyihang
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SolrClientInitManager {

    private static final Logger logger = LoggerFactory.getLogger(SolrClientInitManager.class);

    private String zkAddress;

    private int zkClientTimeout;

    private int zkConnectTimeout;
    
    // 初始化client
    private CloudSolrClient client;

    public SolrClientInitManager(String zkAddress, int zkClientTimeout, int zkConnectTimeout){
        this.zkAddress = zkAddress;
        this.zkClientTimeout = zkClientTimeout;
        this.zkConnectTimeout = zkConnectTimeout;
        this.client = initClient();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈获取client〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public SolrClient getClient() {
        return client;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈初始化〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public CloudSolrClient initClient() {
        CloudSolrClient server = null;
        try {
            server = new CloudSolrClient(zkAddress);
            server.setParser(new XMLResponseParser());
            // server.setDefaultCollection(solrdumpClient.getCollection());
            server.setZkClientTimeout(zkClientTimeout);
            server.setZkConnectTimeout(zkConnectTimeout);
            server.connect();
            logger.info("SolrClientManager init solrClient success");
        } catch (Exception e) {
            logger.error("SolrClientManager init solrClient error:{}",
                    e);
            if (null != server) {
                try {
                    server.close();
                } catch (IOException e1) {
                    logger.error("SolrClientManager close error:{}", e1);
                }
            }
        }
        return server;
    }

}
