package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchItemMapper;

public class ItemMessageAddListener implements MessageListener{

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Override
	public void onMessage(Message message) {
		
		if(message instanceof TextMessage){
			TextMessage msg = (TextMessage)message;
			try {
				Long itemId = Long.parseLong(msg.getText());
				
				//设置延迟事件，避免事务没提交去查数据库
				Thread.sleep(5000);
				SearchItem searchItem = searchItemMapper.searchItemById(itemId);
				
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
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

}
