package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.utils.IDUtils;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	//使用topic去发布商品ID
	@Resource(name="itemAddtopic")
	private Destination destination;
	@Autowired
	private JedisClient jedis;
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${ITEM_EXPIRE}")
	private Integer ITEM_EXPIRE;
	
	@Override
	public TbItem getItemById(long itemId) {
		//查询redis缓存
		try {
			String json = jedis.get(ITEM_INFO + ":" + itemId +":BASE");
			if(StringUtils.isNotBlank(json)){
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//没有缓存再查找数据库
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		//添加到redis缓存
		try {
			jedis.set(ITEM_INFO + ":" + itemId +":BASE", JsonUtils.objectToJson(item));
			//设置缓存过期时间为一天
			jedis.expire(ITEM_INFO + ":" + itemId +":BASE", ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult(pageInfo.getTotal(), list);
		return dataGridResult;
	}

	@Override
	public TaotaoResult insertItem(TbItem item, String desc) {
		final Long id = IDUtils.genItemId();
		item.setId(id);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);
		//商品描述信息
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDescMapper.insert(itemDesc);
		//在此处发布商品添加事件
		jmsTemplate.send(destination,new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(id+"");
				return textMessage;
			}
		});
		return TaotaoResult.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		//查询redis缓存
		try {
			String json = jedis.get(ITEM_INFO + ":" + itemId +":DESC");
			if(StringUtils.isNotBlank(json)){
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		//添加到redis缓存
		try {
			jedis.set(ITEM_INFO + ":" + itemId +":DESC", JsonUtils.objectToJson(tbItemDesc));
			//设置缓存过期时间为一天
			jedis.expire(ITEM_INFO + ":" + itemId +":DESC", ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tbItemDesc;
	}



}
