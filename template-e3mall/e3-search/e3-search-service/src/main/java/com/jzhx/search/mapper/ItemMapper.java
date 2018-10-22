package com.jzhx.search.mapper;

import java.util.List;

import com.jzhx.common.pojo.SearchItem;

public interface ItemMapper {
	List<SearchItem> getItemList();
	SearchItem getItemById(long  itemId);

}
