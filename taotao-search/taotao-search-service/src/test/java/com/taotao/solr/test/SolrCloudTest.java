package com.taotao.solr.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void testSolrCloud(){
		//单机版
		SolrServer server = new HttpSolrServer("solr服务器地址");
		//集群版
		CloudSolrServer server2 = new CloudSolrServer("solr服务器地址1,solr服务器地址2...");
		
		//设置默认的Collection
		server2.setDefaultCollection("");
	}
}
