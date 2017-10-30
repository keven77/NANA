package com.juran.index.mdm.app.pool;

import com.alibaba.fastjson.JSON;
import com.juran.index.mdm.app.common.ConfigLoader;
import com.juran.index.mdm.client.execption.NoSuchSolrException;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;


public class BasisCloudSolrServicePool implements InitializingBean {
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(BasisCloudSolrServicePool.class);
	public static int SOLRPOONUM = 20;

	private String collectionName = "";
	private String host = "";
	private boolean isConfig = false;

	private ObjectPool<CloudSolrClient> solrserverpool;

	@Autowired
	public ConfigLoader configLoader;



	public BasisCloudSolrServicePool(boolean isConfig, String host, String collectionName) throws Exception {
		//initSolrServers(isConfig, host, index);
		this.collectionName=collectionName;
		this.host=host;
		this.isConfig=isConfig;
	}
	
	private void initSolrServers() throws Exception {
		log.info("start to init,"+ JSON.toJSONString(configLoader)+":"+host);
		if (isConfig)
			SOLRPOONUM =configLoader.getSolrpoonum();
		
		log.info("start to init solrserver pool solrpoonum ["+SOLRPOONUM+"]");

		
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();

		config.setMaxIdle(SOLRPOONUM);
		config.setMinIdle(configLoader.getMinidle());
		config.setMaxWaitMillis(configLoader.getConnTimeout());

		log.info("put "+collectionName+" to pool");
		PooledObjectFactory<CloudSolrClient> factory = new BasisCloudServerFactory(collectionName, host, configLoader);
		solrserverpool =  new GenericObjectPool<CloudSolrClient>(factory, config);
		
		
		log.info("end to init solrserver pool solrpoonum ["+SOLRPOONUM+"] zkhost is ["+host+"] collection name is ["+collectionName+"]");
	}
	
	public ObjectPool<CloudSolrClient> getCloudServerPool() throws NoSuchSolrException {
		if(solrserverpool==null){
			log.error("has no "+collectionName+" pool,please register it in solr.property");
			throw new NoSuchSolrException("has no "+collectionName+" pool,please register it in solr.property", 501);
		}else{
			return solrserverpool;
		}
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		//log.info("start to init2,"+ JSON.toJSONString(configLoader)+":"+host);
	}
}
