package com.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemAddMessageListener implements MessageListener{

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${FTL_OUT_PATH}")
	private String FTL_OUT_PATH;
	
	@Override
	public void onMessage(Message message) {
		
		if (message instanceof TextMessage) {
			
			try {
				TextMessage textMessage = (TextMessage)message;
				String strId = textMessage.getText();
				if(StringUtils.isNotBlank(strId)){
					Long itemId = Long.parseLong(strId);
					
					Thread.sleep(2000);
					TbItem tbItem = itemService.getItemById(itemId);
					TbItemDesc itemDesc = itemService.getItemDescById(itemId);
					
					Item item = new Item(tbItem);
					//获得Configuration对象
					Configuration configuration = freeMarkerConfigurer.getConfiguration();
					//加载指定模板对象
					Template template = configuration.getTemplate("item.ftl");
					Map data = new HashMap<>();
					data.put("tbItem", tbItem);
					data.put("itemDesc", itemDesc);
					//指定模板的输出路径
					Writer out = new FileWriter(new File(FTL_OUT_PATH+itemId+".html"));
					//生成静态页面
					template.process(data, out);
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
