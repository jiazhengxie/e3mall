package com.jzhx.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jzhx.item.pojo.Item;
import com.jzhx.pojo.TbItem;
import com.jzhx.pojo.TbItemDesc;
import com.jzhx.service.ItemService;

@Controller
public class itemController {
//	@RequestMapping("/item/{id}")
//	public String love(@PathVariable Long id){
//		System.out.println("love");
//		System.out.println(id);
//		return "item";
//	}
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		
		//调用服务取商品基本信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		//取商品描述信息
		TbItemDesc itemDesc = itemService.geItemDescById(itemId);
		//把信息传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		//返回逻辑视图
		
		return "item";
	}
}
