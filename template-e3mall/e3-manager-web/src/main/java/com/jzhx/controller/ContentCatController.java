package com.jzhx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzhx.common.pojo.EasyUITreeNode;
import com.jzhx.common.utils.E3Result;
import com.jzhx.content.service.contentCategoryService;

/**
 * 内容分类管理
 * <p>Title: ContentCatController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ContentCatController {
	
	@Autowired
	private contentCategoryService contentCategoryService;
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue="0")long parentId){
		System.out.println(parentId+"....");
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		
		return list;
		
	}
	/**
	 * 添加分类节点
	 */
	
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCategory(Long parentId,String name){
		//调用服务来添加节点
		return contentCategoryService.addContentCategory(parentId, name);
	}

}
