package com.juran.index.mdm.app.pool;

import com.juran.index.mdm.app.common.ConfigLoader;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 〈一句话功能简述 :利用Apache的commons pool 〉<br>
 * 〈dump〉
 *
 * @author zhangyihang 2017年10月16日 10:56:08
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class BasisCloudServerFactory implements PooledObjectFactory<CloudSolrClient> {
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(BasisCloudServerFactory.class);

	private String collectionName;
	private String host;
	private ConfigLoader configLoader;

	public BasisCloudServerFactory(String collectionName){
		this.collectionName = collectionName;
	}
	
	BasisCloudServerFactory(String collectionName, String host,ConfigLoader configLoader ){
		this.collectionName = collectionName;
		this.host=host;
		this.configLoader=configLoader;
	}
	
	@Override
	public PooledObject<CloudSolrClient> makeObject() throws Exception {
		CloudSolrClient csolrserver = getNewCloudSolrClient(collectionName,host);
		PooledObject<CloudSolrClient> pobj = new DefaultPooledObject<CloudSolrClient>(csolrserver);
		return pobj;
	}

	@Override
	public void destroyObject(PooledObject<CloudSolrClient> p) throws Exception {
		p = null;
	}

	@Override
	public boolean validateObject(PooledObject<CloudSolrClient> p) {
		CloudSolrClient cserver = p.getObject();
		try {
			SolrPingResponse rresose = cserver.ping();
			
			int rstate = rresose.getStatus();
			
			if(rstate==0){
				return true;
			}else{
				log.error("CloudSolrClient :["+cserver+"] is not alive will remove it ");
			}
		} catch (SolrServerException e) {
			log.error("", e);
			return false;
		} catch (IOException e) {
			log.error("", e);
			return false;
		}
		return true;
	}

	@Override
	public void activateObject(PooledObject<CloudSolrClient> p) throws Exception {
		
	}

	@Override
	public void passivateObject(PooledObject<CloudSolrClient> p) throws Exception {
		
	}
	
	private CloudSolrClient getNewCloudSolrClient(String collection, String host) {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_SO_TIMEOUT,configLoader.getSocketTimeout());
		params.set(HttpClientUtil.PROP_CONNECTION_TIMEOUT,  configLoader.getConnTimeout());
//		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, Integer.MAX_VALUE);
//		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, Integer.MAX_VALUE);
//		params.set(HttpClientUtil.PROP_USE_RETRY, ConfigLoader.getProperty(HttpClientUtil.PROP_USE_RETRY));
		params.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true);
		// params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS,
		// ConfigLoader.getProperty(HttpClientUtil.PROP_MAX_CONNECTIONS));
		// params.set(HttpClientUtil.PROP_BASIC_AUTH_USER,
		// ConfigLoader.getProperty(HttpClientUtil.PROP_MAX_CONNECTIONS));
		HttpClient client = HttpClientUtil.createClient(params);
		LBHttpSolrClient lbServer = new LBHttpSolrClient(client);
		CloudSolrClient CloudSolrClient = new CloudSolrClient(host, lbServer);
		CloudSolrClient.setZkClientTimeout(configLoader.getZkClientTimeout());
		CloudSolrClient.setZkConnectTimeout(configLoader.getZkConnectTimeout());
		if (collection != null) {
			CloudSolrClient.setDefaultCollection(collection);
		}

		CloudSolrClient.connect();

		return CloudSolrClient;
	}


}

