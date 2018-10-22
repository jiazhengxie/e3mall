package com.jzhx.content.service;

import java.util.List;

import com.jzhx.common.utils.E3Result;
import com.jzhx.pojo.TbContent;

public interface ContentService {
	
	E3Result addContent(TbContent content);
	List<TbContent> getContentListByCid(long cid);

}
