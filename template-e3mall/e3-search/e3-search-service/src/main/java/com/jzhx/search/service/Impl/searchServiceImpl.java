package com.jzhx.search.service.Impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzhx.common.pojo.SearchResult;
import com.jzhx.search.dao.SearchDao;
import com.jzhx.search.service.SearchService;
/**
 * 商品搜索
 * <p>Title: searchServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class searchServiceImpl implements SearchService{
	@Autowired
	private SearchDao searchDao;
	

	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		// TODO Auto-generated method stub
		//创建一个SOLEquery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(keyword);
		//设置分页条件
		if(page<=0){
			page = 1;
		}
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//设置默认搜素域
		query.set("df", "item_title");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		//调用dao执行查询
		
			SearchResult search = searchDao.search(query);
	
		//计算总页数
		long count = search.getRecordCount();
		int totalPage = (int)(count/rows);
		if(count%rows != 0){
			totalPage+=1;
		}
		search.setTotalPages(totalPage);
		//返回结果
		return search;
	}

}
