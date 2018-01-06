package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	@Value(value = "${SEARCH_RESULT_ROWS}") 
	private Integer SEARCH_RESULT_ROWS;
	
	@RequestMapping(value="/search")
	public String search(@RequestParam("p")String queryString,
		@RequestParam(defaultValue="0")Integer page,Model model){
		//转码
		try {
			queryString = new String(queryString.getBytes("ios8859-1"), "UTF-8");
			SearchResult searchResult = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
			
			model.addAttribute("query", queryString);
			model.addAttribute("totalPages", searchResult.getPagerows());
			model.addAttribute("itemList", searchResult.getItemList());
			model.addAttribute("page", page);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "search";
	}
}
