package com.jzhx.search.service.Impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzhx.common.pojo.SearchItem;
import com.jzhx.common.utils.E3Result;
import com.jzhx.search.mapper.ItemMapper;
import com.jzhx.search.service.searchItemService;
/**
 * 索引库维护
 * <p>Title: searchItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class searchItemServiceImpl implements searchItemService{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public E3Result importAllItems() {
		// TODO Auto-generated method stub
		try {
			List<SearchItem> itemList = itemMapper.getItemList();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				
				solrServer.add(document);
			}
			solrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return E3Result.build(500, "数据导入时发生异常");
		}
		
		
		
		
	}

}
