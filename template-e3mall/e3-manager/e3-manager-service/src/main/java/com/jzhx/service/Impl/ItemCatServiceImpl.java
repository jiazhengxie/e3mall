package com.jzhx.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzhx.common.pojo.EasyUITreeNode;
import com.jzhx.mapper.TbItemCatMapper;
import com.jzhx.pojo.TbContentCategoryExample.Criteria;
import com.jzhx.pojo.TbItemCat;
import com.jzhx.pojo.TbItemCatExample;
import com.jzhx.service.ItemCatService;


/**
 * 商品分类管理
 * <p>Title: ItemCatServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private TbItemCatMapper ItemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatlist(long parentId) {
		// TODO Auto-generated method stub
		//根据parentId查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		com.jzhx.pojo.TbItemCatExample.Criteria criteria = example .createCriteria();
//		com.jzhx.pojo.TbItemCatExample.Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		 List<TbItemCat> list = ItemCatMapper.selectByExample(example);
		
		//创建返回结果list
		List<EasyUITreeNode> resultList = new ArrayList<>();
		//把列表转换成EasyUITreeNode列表
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到结果列表 
			resultList.add(node);
			
		}
		//返回结果
		return resultList;
	}	

}
