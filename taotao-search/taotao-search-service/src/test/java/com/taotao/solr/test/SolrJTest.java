package com.taotao.solr.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	//@Test
	public void testSolrJAdd(){
		
		//创建SolrServer对象，指定solr服务器的地址,默认连接conllection1，http://192.168.1.106:8080/solr/collection1
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.106:8080/solr");
		//创建一个文档对象SolrDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档中天加域，必须有id，且在schema.xml中定义
		document.addField("id", "test0003");
		document.addField("item_title", "测试商品0002title");
		//把文档添加到索引库
		try {
			solrServer.add(document);
			//提交事务
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//@Test
	public void testSolrDel() {
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.106:8080/solr");
		try {
			solrServer.deleteById("测试商品ID");
			solrServer.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//@Test
	public void testSolrQueryDel() {
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.106:8080/solr");
		try {
			solrServer.deleteByQuery("item_title:tit");
			solrServer.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void testSelect(){
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.106:8080/solr");
		
		SolrQuery query = new SolrQuery();
		//设置搜索条件
		query.set("q","手机");
		//query.setQuery("手机");
		query.setStart(0);
		query.setRows(10);
		
		//设置搜索域
		query.set("df", "item_image");
		
		try {
			//执行查询,获得一个response对象
			 QueryResponse response = solrServer.query(query);
			 System.out.println("取出来的总数 = "+response.getResults().size());
			 //取得查询结果，为一个SolrDocumentList对象,继承ArrayList
			 SolrDocumentList document = response.getResults();
			 System.out.println("查询的总数 = "+document.getNumFound());
			 
			 for(SolrDocument doc : document){
				 System.out.println("id : "+doc.get("id"));
				 System.out.println("item_title : "+doc.get("item_title"));
				 System.out.println("item_sell_point : "+doc.get("item_sell_point"));
				 System.out.println("item_price : "+doc.get("item_price"));
				 System.out.println("item_image : "+doc.get("item_image"));
				 System.out.println("item_category_name : "+doc.get("item_category_name"));
				 System.out.println("=============================================");
			 }
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
