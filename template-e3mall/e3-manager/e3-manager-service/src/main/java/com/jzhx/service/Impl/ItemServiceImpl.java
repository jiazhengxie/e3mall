package com.jzhx.service.Impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzhx.common.jedis.JedisClient;
import com.jzhx.common.jedis.JedisClientCluster;
import com.jzhx.common.pojo.EasyUIDataGridResult;
import com.jzhx.common.utils.E3Result;
import com.jzhx.common.utils.IDUtils;
import com.jzhx.common.utils.JsonUtils;
import com.jzhx.mapper.TbItemDescMapper;
import com.jzhx.mapper.TbItemMapper;
import com.jzhx.pojo.TbItem;
import com.jzhx.pojo.TbItemDesc;
import com.jzhx.pojo.TbItemExample;
import com.jzhx.service.ItemService;
import com.jzhx.pojo.TbItemExample.Criteria;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;
	
	@Override
	public TbItem getItemById(long itemId) {
		// TODO Auto-generated method stub
		//查询缓存
				try {
					String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
					if(StringUtils.isNotBlank(json)) {
						TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
						return tbItem;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//缓存中没有，查询数据库
				//根据主键查询
				//TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
				TbItemExample example = new TbItemExample();
				Criteria criteria = example.createCriteria();
				//设置查询条件
				criteria.andIdEqualTo(itemId);
				//执行查询
				List<TbItem> list = itemMapper.selectByExample(example);
				if (list != null && list.size() > 0) {
					//把结果添加到缓存
					try {
						jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(list.get(0)));
						//设置过期时间
						jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return list.get(0);
				}
				return null;
}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// TODO Auto-generated method stub
		//设置分页信息
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);;
		//取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}
	@Override
	public E3Result addItem(TbItem item, String desc) {
		// TODO Auto-generated method stub
		//先生成商品ID
		final long itemId = IDUtils.genItemId();
		//补全页面属性
		 item.setId(itemId);
		 //1-正常  2-下架  3-删除
		 item.setStatus((byte)1);
		 item.setCreated(new Date());
		 item.setUpdated(new Date());
		//向商品表插入数据
		 itemMapper.insert(item);
		//创建商品描述表对应的pojo对象
		 TbItemDesc itemDesc = new TbItemDesc();
		//补全属性
		 itemDesc.setItemId(itemId);
		 itemDesc.setItemDesc(desc);
		 itemDesc.setCreated(new Date());
		 itemDesc.setUpdated(new Date());
		//向商品不描述表插入数据
		 itemDescMapper.insert(itemDesc);
		 //发送一个商品添加消息
		 jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage createTextMessage = session.createTextMessage(itemId+"");
				return createTextMessage;
			}
		});
		//返回成功
		return E3Result.ok();
	}
	@Override
	public TbItemDesc geItemDescById(long itemId) {
		// TODO Auto-generated method stub
		//查询缓存
				try {
					String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
					if(StringUtils.isNotBlank(json)) {
						TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
						return tbItemDesc;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
				//把结果添加到缓存
				try {
					jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
					//设置过期时间
					jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return itemDesc;
			}
	
}