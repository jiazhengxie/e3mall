package com.jzhx.solrJ;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class solrTest {
	@Test
	public void addDocument() throws Exception{
		//创建一个solr 对象  创建一个连接 参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://101.132.78.7:8080/solr/collection1");
		//创建一个文档对象solrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域   文档中必须包含一个id域 所有域的名称必须在schema.xml中定义
		document.addField("id", "doc01");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 1000);
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	@Test
	public void delete() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://101.132.78.7:8080/solr/collection1");
		solrServer.deleteById("doc01");
		solrServer.commit();
	}
	
	@Test
	public void queryIndex() throws Exception{
		//创建一个solr 对象  创建一个连接 参数solr服务的url
	    SolrServer solrServer = new HttpSolrServer("http://101.132.78.7:8080/solr/collection1");
	    //创建一个solrQuery 对象
	    SolrQuery query = new SolrQuery();
	    //设置查询条件
	    //query.setQuery("*:*");
	    query.set("q", "*:*");
	    //执行查询queryResphone对象
	    QueryResponse query2 = solrServer.query(query);
	    //取文档列表 查询结果总记录数
	    SolrDocumentList results = query2.getResults();
	    System.out.println("查询总结果数:"+results.getNumFound());
	    System.out.println(1);
	    //遍历文档列表 从取域的内容
	    for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
	
	@Test
	public void queryIndexFuza() throws Exception{
		//创建一个solr 对象  创建一个连接 参数solr服务的url
	    SolrServer solrServer = new HttpSolrServer("http://101.132.78.7:8080/solr/collection1");
	    //创建一个solrQuery 对象
	    SolrQuery query = new SolrQuery();
	    //设置查询条件
	    query.setQuery("手机");
	    query.setStart(0);
	    query.setRows(20);
	    query.set("df","item_title");
	    query.setHighlight(true);
	    query.addHighlightField("item_title");
	    query.setHighlightSimplePre("<em>");
	    query.setHighlightSimplePost("</em>");
	    
	    //执行查询queryResphone对象
	    QueryResponse query2 = solrServer.query(query);
	    //取文档列表 查询结果总记录数
	    SolrDocumentList results = query2.getResults();
	    System.out.println("查询总结果数:"+results.getNumFound());
	    //遍历文档列表 从取域的内容
	    for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			//取高亮
			Map<String, Map<String, List<String>>> highlighting = query2.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if(list!=null && list.size()>0){
				title=list.get(0);
			}else {
				title=(String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}

}
