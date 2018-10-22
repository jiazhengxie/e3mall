package com.jzhx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzhx.common.utils.E3Result;
import com.jzhx.content.service.ContentService;
import com.jzhx.pojo.TbContent;
import com.jzhx.pojo.TbContentExample;

@Controller
public class ContentController {
	@Autowired
	private ContentService ContentService;
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent content){
		E3Result e3Result = ContentService.addContent(content);
		return e3Result;
	}
}
