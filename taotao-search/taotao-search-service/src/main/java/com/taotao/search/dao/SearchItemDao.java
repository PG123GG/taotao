package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;

@Repository
public class SearchItemDao {

	@Autowired
	private SolrServer solrServer;
	
	
	public SearchResult search(SolrQuery query){
		SolrDocumentList document = null;
		QueryResponse response = null;
		SearchResult result = new SearchResult();
		List<SearchItem> items = new ArrayList<>();
		
		try {
			response = solrServer.query(query);
			
			document = response.getResults();
			
			Long numFound = document.getNumFound();
			
			result.setPagerows(numFound);
			
			for(SolrDocument doc : document){
				SearchItem item = new SearchItem();
				
				item.setId(doc.get("id").toString());
				item.setSellPoint(doc.get("item_sell_point").toString());
				item.setPrice((Long)doc.get("item_price"));
				item.setImage(doc.get("item_image").toString());
				item.setCategoryName(doc.get("item_category_name").toString());
				item.setItemDesc(doc.get("item_desc").toString());
				//取高亮显示
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				List<String> list = highlighting.get(doc.get("id")).get("item_title");
				String itemTitle = "";
				if (list != null && list.size() >0) {
					itemTitle = list.get(0);
				} else {
					itemTitle = (String) doc.get("item_title");
				}
				item.setTitle(itemTitle);
				
				items.add(item);
			}
			
			result.setItemList(items);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
	
}
