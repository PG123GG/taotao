package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门户首页展示
 * @author Administrator
 *
 */
@Controller
public class PageController {
	
	@RequestMapping(value="/")
	public String showIndex(){
		
		return "index";
	}
	
	@RequestMapping(value="/{page}")
	public String showPage(@PathVariable String page){
		
		return page;
	}
	
}
