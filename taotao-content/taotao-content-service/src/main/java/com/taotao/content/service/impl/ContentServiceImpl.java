package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.commom.utils.JsonUtils;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	
	@Value("${TBCONTENT_LIST}")
	private String TBCONTENT_LIST;
	
	@Override
	public TaotaoResult addContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		//删除对应的缓存信息
		jedisClient.hdel(TBCONTENT_LIST, content.getCategoryId().toString());
		return TaotaoResult.ok(content);
	}

	@Override
	public EasyUIDataGridResult searchContenByCId(Long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> list = contentMapper.selectByExample(example);
		return new EasyUIDataGridResult((long) list.size(), list);
	}

	@Override
	public List<TbContent> getContentList(Long id) {
		//先查询缓存
		//查询缓存不能影响代码逻辑
		try {
			String json = jedisClient.hget(TBCONTENT_LIST, id+"");
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//没有缓存就查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);		
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//把结果放到缓存中，且不能影响代码逻辑
		try {
			jedisClient.hset(TBCONTENT_LIST, id+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回结果
		return list;
	}
	
	
	

}
