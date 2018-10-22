package com.jzhx.solrJ;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class solrClusterTest {
	@Test
	public void testAddDocument() throws Exception{
		//创建一个集群的链接，应该使用CloudSolrServer创建
		CloudSolrServer solrServer = new CloudSolrServer("101.132.78.7:2181,101.132.78.7:2182,101.132.78.7:2183");
		//zkHost":zookeeper的地址列表
		//设置一个默认的collection属性
		solrServer.setDefaultCollection("collection2");
		//创建一个文档对象
		SolrInputDocument document  = new SolrInputDocument();
		//想文档中添加域
		document.setField("id", "solrcloud01");
		document.setField("item_title", "测试商品02");
		document.setField("item_price", 123);
		//把文件写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void selectTest() throws Exception{
		CloudSolrServer server = new CloudSolrServer("101.132.78.7:2181,101.132.78.7:2182,101.132.78.7:2183");
		server.setDefaultCollection("collection2");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse query2 = server.query(query);
		SolrDocumentList list = query2.getResults();
		System.out.println("总记录数："+list.getNumFound());
		for (SolrDocument solrDocument : list) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("title"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			
		}
	}

}
