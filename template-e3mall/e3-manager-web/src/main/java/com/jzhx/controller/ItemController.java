package com.jzhx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzhx.common.pojo.EasyUIDataGridResult;
import com.jzhx.common.utils.E3Result;
import com.jzhx.pojo.TbItem;
import com.jzhx.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping(value = "/item/{id}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long id){
		return itemService.getItemById(id);
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		//调用服务
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	//商品添加
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item,String desc){
		E3Result result = itemService.addItem(item, desc);
		return result;
	}
//	商品修改
//	
//	@RequestMapping(value="/rest/item/update",method=RequestMethod.POST)
//	@ResponseBody
//	public E3Result editItem(TbItem item,String desc){
//		E3Result result = itemService.addItem(item, desc);
//		return result;
//	}
}
