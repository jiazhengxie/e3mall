package com.jzhx.search.service;

import com.jzhx.common.pojo.SearchResult;

public interface SearchService  {
	
	SearchResult search(String keyword,int page,int rows) throws Exception;

}
