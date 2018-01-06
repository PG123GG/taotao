package com.taotao.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.pojo.TbContentCategory;

/**
 * 内容分类管理
 * @author Administrator
 *
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping(value="/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0") Long parentId){
	
		return contentCategoryService.getContentCategoryList(parentId);
	
	}
	
	@RequestMapping(value="/content/category/create")
	@ResponseBody
	public TaotaoResult addCaontentCategory(Long parentId,String name){
		TaotaoResult taotaoResult = contentCategoryService.addContentCatetory(parentId, name);
		return taotaoResult;
	}
	
	@RequestMapping(value="/content/category/update")
	@ResponseBody
	public TaotaoResult updateCaontentCategory(Long id,String name){
		return contentCategoryService.updateContentCategory(id, name);
	}
	
	@RequestMapping(value="/content/category/delete")
	@ResponseBody
	public TaotaoResult deleteCaontentCategory(Long id){
		TbContentCategory contentCategory = contentCategoryService.getTbContentCategoryById(id);
		Long parentId = contentCategory.getParentId();
		if(!contentCategory.getIsParent()){
			contentCategoryService.deleteContentCategory(id);
		}else{
			return TaotaoResult.build(400, "父节点不能删除", contentCategory);
		}
		//查看父节点是否还有子节点
		List<TbContentCategory> list = contentCategoryService.getContentCategoryListByParentId(parentId);
		if(list==null || list.size()<=0){
			TbContentCategory parentCategory = contentCategoryService.getTbContentCategoryById(parentId);
			parentCategory.setIsParent(false);
			parentCategory.setUpdated(new Date());
			contentCategoryService.updateContentCategory(parentCategory);
		}
		return TaotaoResult.build(200, "删除成功");
	}
}

