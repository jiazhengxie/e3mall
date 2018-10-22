package com.jzhx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzhx.common.utils.E3Result;
import com.jzhx.search.service.searchItemService;

/**
 * 导入商品数据到索引库
 * <p>Title: SearchItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class SearchItemController {
	
	@Autowired
	private searchItemService searchItemService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList(){
		
		E3Result result = searchItemService.importAllItems();
		return result;
	}
	

}
