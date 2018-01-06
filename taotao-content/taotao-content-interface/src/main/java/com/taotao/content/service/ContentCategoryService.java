package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCategoryList(Long parentId);
	
	List<TbContentCategory> getContentCategoryListByParentId(Long parentId);
	
	TaotaoResult addContentCatetory(Long parentId,String name);
	
	TaotaoResult updateContentCategory(Long id,String name);
	
	TaotaoResult updateContentCategory(TbContentCategory contentCategory);
	
	TaotaoResult deleteContentCategory(Long id);
	
	TbContentCategory getTbContentCategoryById(Long id);
}
