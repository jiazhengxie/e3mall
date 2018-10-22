package com.jzhx.service;

import com.jzhx.common.pojo.EasyUIDataGridResult;
import com.jzhx.common.utils.E3Result;
import com.jzhx.pojo.TbItem;
import com.jzhx.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(long id);
	EasyUIDataGridResult getItemList(int page,int rows);
    E3Result addItem(TbItem item, String desc);
    TbItemDesc geItemDescById(long itemId);
}
