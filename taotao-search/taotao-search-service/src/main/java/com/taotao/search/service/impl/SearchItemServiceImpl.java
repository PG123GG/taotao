package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Override
	public TaotaoResult importItemsToIndex() {
		List<SearchItem> list = null;
		//查询数据
		try {
			list = searchItemMapper.searchItemList();
			for(SearchItem searchItem : list) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSellPoint());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategoryName());
				document.addField("item_desc", searchItem.getItemDesc());
				//添加，提交
				solrServer.add(document);
				solrServer.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "导入失败");
		}
		return TaotaoResult.ok(list);
	}

}
