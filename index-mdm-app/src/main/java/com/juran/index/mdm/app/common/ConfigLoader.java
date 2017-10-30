package com.juran.index.mdm.app.common;

import org.slf4j.LoggerFactory;

public class ConfigLoader {


	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

	private String zkAddress;

	private int zkClientTimeout;

	private int zkConnectTimeout;


	private int solrpoonum;

	private int minidle;

	private int maxConnections;


	private int socketTimeout;

	private int connTimeout;

	private int maxConnectionsPerHost;

	private int retry;

	/*public ConfigLoader(String zkAddress, int zkClientTimeout, int zkConnectTimeout,
						int solrpoonum, int minidle, int maxConnections,
						int socketTimeout, int connTimeout, int maxConnectionsPerHost, int  retry){

		this.zkAddress=zkAddress;
		this.zkClientTimeout=zkClientTimeout;
		this.zkConnectTimeout=zkConnectTimeout;

		this.solrpoonum=solrpoonum;
		this.minidle=minidle;
		this.maxConnections=maxConnections;

		this.socketTimeout=socketTimeout;
		this.connTimeout=connTimeout;
		this.maxConnectionsPerHost=maxConnectionsPerHost;
		this.retry=retry;

	}*/

	public String getZkAddress() {
		return zkAddress;
	}

	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public int getZkClientTimeout() {
		return zkClientTimeout;
	}

	public void setZkClientTimeout(int zkClientTimeout) {
		this.zkClientTimeout = zkClientTimeout;
	}

	public int getZkConnectTimeout() {
		return zkConnectTimeout;
	}

	public void setZkConnectTimeout(int zkConnectTimeout) {
		this.zkConnectTimeout = zkConnectTimeout;
	}

	public int getSolrpoonum() {
		return solrpoonum;
	}

	public void setSolrpoonum(int solrpoonum) {
		this.solrpoonum = solrpoonum;
	}

	public int getMinidle() {
		return minidle;
	}

	public void setMinidle(int minidle) {
		this.minidle = minidle;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}
	
}
