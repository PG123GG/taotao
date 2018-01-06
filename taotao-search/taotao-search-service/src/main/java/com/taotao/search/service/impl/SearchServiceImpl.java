
package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchItemDao;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchItemDao searchItemDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) {
		SolrQuery query = new SolrQuery();
		query.set("q", queryString);
		if(page < 1) {
			page = 1;
		}
		if(rows < 1){
			rows = 1;
		}
		query.setStart((page-1)*rows);
		query.setRows(rows);
		
		//设置搜索域
		query.set("df", "item_title");
		
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		
		SearchResult result = searchItemDao.search(query);
		//计算查询结果的总页数
		long recordCount = result.getRecordCount();
		long pages =  recordCount / rows;
		if (recordCount % rows > 0) {
			pages++;
		}
		result.setPagerows(pages);
		return result;
	}

}
