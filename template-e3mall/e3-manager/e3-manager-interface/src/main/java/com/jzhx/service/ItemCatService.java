package com.jzhx.service;

import java.util.List;

import com.jzhx.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatlist(long parentId);

}
