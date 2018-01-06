package com.taotao.protal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.commom.utils.JsonUtils;
import com.taotao.common.pojo.Ad1Node;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 前台页面展示
 * @author Administrator
 *
 */
@Controller
public class IndexController {

/*	@Autowired
	private ContentService contentService;

	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	
	@RequestMapping(value="/index")
	public String index(Model model){
		
		List<TbContent> list = contentService.getContentList(AD1_CATEGORY_ID);
		List<Ad1Node> ad1Nodes = new ArrayList<>();
		for(TbContent content : list){
			Ad1Node ad1Node = new Ad1Node();
			ad1Node.setAlt(content.getTitle());
			ad1Node.setHeight(Integer.valueOf(AD1_HEIGHT));
			ad1Node.setHeightB(Integer.valueOf(AD1_HEIGHT_B));
			ad1Node.setWidth(Integer.valueOf(AD1_WIDTH));
			ad1Node.setWidthB(Integer.valueOf(AD1_WIDTH_B));
			ad1Node.setHref(content.getUrl());
			ad1Node.setSrc(content.getPic());
			ad1Node.setSrcB(content.getPic2());
			ad1Nodes.add(ad1Node);
		}
		model.addAttribute("ad1", JsonUtils.objectToJson(ad1Nodes));
		return "index";
	}*/
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		//根据cid查询轮播图内容列表
		List<TbContent> contentList = contentService.getContentList(AD1_CATEGORY_ID);
			//getContentByCid(AD1_CATEGORY_ID);
		//把列表转换为Ad1Node列表
		List<Ad1Node> ad1Nodes = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			Ad1Node node = new Ad1Node();
			node.setAlt(tbContent.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setHref(tbContent.getUrl());
			//添加到节点列表
			ad1Nodes.add(node);
		}
		//把列表转换成json数据
		String ad1Json = JsonUtils.objectToJson(ad1Nodes);
		//把json数据传递给页面
		model.addAttribute("ad1", ad1Json);
		return "index";
	}
}
