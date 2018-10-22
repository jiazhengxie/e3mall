package com.jzhx.content.service;

import java.util.List;

import com.jzhx.common.pojo.EasyUITreeNode;
import com.jzhx.common.utils.E3Result;

public interface contentCategoryService {
	
	List<EasyUITreeNode> getContentCatList(long parentId);
	E3Result addContentCategory(long parentId,String name);

}
