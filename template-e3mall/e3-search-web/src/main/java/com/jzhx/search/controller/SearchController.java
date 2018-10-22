package com.jzhx.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jzhx.common.pojo.SearchResult;
import com.jzhx.search.service.SearchService;

/**
 * 商品搜索
 * <p>Title: SearchController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;
	
	@RequestMapping("/search")
	public String searchItemList(String keyword,@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		//转乱码
			keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
		
		SearchResult search = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", search.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", search.getRecordCount());
		model.addAttribute("itemList", search.getItemList());
		return "search";
	}

}
