package com.juran.index.mdm.app.contants;

public class SolrContants {

	public static  String urlBase="http://{IP}:8080/solr/{coreName}";

	public static class SolrResponseStatus{
		public static final int SUCCESS=0;
	}
	
	public static class RequestHandler{
		public static final String DATA_IMPORT="/dataimport";
		public static final String DATA_UPDATE="/update";
		public static final String REPLICATION="/replication";
	}

	public static class SolrCoreName{
		public static final String JURAN_MC_2_BRAND="brand";
		public static final String JURAN_MC_2_CATALOGENTRY="catentry";
		public static final String JURAN_MC_2_CATALOGGROUP="catalog";
	}
	
	public static class Command{
		public static final String FULL_IMPORT="full-import";
		public static final String DELTA_IMPORT="delta-import";
		public static final String FETCHINDEX="fetchindex";
	}
}
