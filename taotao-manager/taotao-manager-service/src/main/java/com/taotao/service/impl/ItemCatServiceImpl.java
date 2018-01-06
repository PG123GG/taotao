package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/*
	 * 根据父节点ID查询子节点
	 * (non-Javadoc)
	 * @see com.taotao.service.ItemCatService#getItemCatList(long)
	 */
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		TbItemCatExample catExample = new TbItemCatExample();
		//设置财讯条件
		Criteria criteria = catExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(catExample);
		
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for(TbItemCat itemCat : list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}

}
