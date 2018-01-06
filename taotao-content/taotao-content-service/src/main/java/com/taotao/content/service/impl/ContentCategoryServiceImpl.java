package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类Service
 * @author Administrator
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for(TbContentCategory category : list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	public TaotaoResult addContentCatetory(Long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		Date date = new Date();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setCreated(date);
		contentCategory.setIsParent(false);
		contentCategory.setUpdated(date);
		contentCategory.setSortOrder(1);
		//状态，可选值：1表示正常，2表示删除
		contentCategory.setStatus(1);
		//插入到数据库
		int id = contentCategoryMapper.insert(contentCategory);
		//判断父节点状态,假如没有父节点，则把该节点修改为父节点
		TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCategory.getIsParent()){
			parentCategory.setIsParent(true);
			parentCategory.setUpdated(date);
			contentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}
	
	@Override
	public TaotaoResult updateContentCategory(TbContentCategory contentCategory) {
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(Long id) {
		contentCategoryMapper.deleteByPrimaryKey(id);
		return null;
	}

	@Override
	public TbContentCategory getTbContentCategoryById(Long id) {
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TbContentCategory> getContentCategoryListByParentId(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		return list;
	}

	

}
