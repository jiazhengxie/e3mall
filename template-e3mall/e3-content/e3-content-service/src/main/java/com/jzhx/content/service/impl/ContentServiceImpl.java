package com.jzhx.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.jzhx.common.jedis.JedisClient;
import com.jzhx.common.utils.E3Result;
import com.jzhx.common.utils.JsonUtils;
import com.jzhx.content.service.ContentService;
import com.jzhx.mapper.TbContentMapper;
import com.jzhx.pojo.TbContent;
import com.jzhx.pojo.TbContentExample;
import com.jzhx.pojo.TbContentExample.Criteria;

import redis.clients.jedis.JedisCluster;
/**
 * 内容管理
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;

	@Override
	public E3Result addContent(TbContent content) {
		// TODO Auto-generated method stub
		//将内容数据插入内容表
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		//缓存同步 删除缓存中对应的数据
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		
		return E3Result.ok();
	}
    //根据分类id查询内容分类页表
	@Override
	public List<TbContent> getContentListByCid(long cid) {
		//查询缓存
		try {
			String json = jedisClient.hget(CONTENT_LIST, cid+"");
			if(StringUtils.isNoneBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//有  直接响应
		//没有查询数据库
		// TODO Auto-generated method stub
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        try {
        	jedisClient.hset(CONTENT_LIST, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		return list;
	}

}
