package com.jzhx.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.Highlighter.Highlight;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzhx.common.pojo.SearchItem;
import com.jzhx.common.pojo.SearchResult;

/**
 * 商品搜索
 * <p>Title: SearchDao</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */

@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	public SearchResult search(SolrQuery query) throws Exception{
		//根据条件 查询索引库
		QueryResponse query2 = solrServer.query(query);
		//查询结果
		SolrDocumentList results = query2.getResults();
		long numFound = results.getNumFound();
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		//取 高亮显示
		Map<String, Map<String, List<String>>> highlighting = query2.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : results) {
			SearchItem item =new SearchItem();
			item.setId((String)solrDocument.get("id"));
			item.setCategory_name((String)solrDocument.get("item_category_name"));
			item.setImage((String)solrDocument.get("item_image"));
			item.setPrice((long)solrDocument.get("item_price"));
			
			item.setSell_point((String)solrDocument.get("item_sell_point"));
			//高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list!= null && list.size()>0){
				title=list.get(0);
			}else {
				title=(String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			itemList.add(item);
		}
		result.setItemList(itemList);
		
		return result;
	}

}
