package com.jzhx.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzhx.common.pojo.EasyUITreeNode;
import com.jzhx.common.utils.E3Result;
import com.jzhx.content.service.contentCategoryService;
import com.jzhx.mapper.TbContentCategoryMapper;
import com.jzhx.pojo.TbContentCategory;
import com.jzhx.pojo.TbContentCategoryExample;
import com.jzhx.pojo.TbContentCategoryExample.Criteria;
import com.jzhx.pojo.TbContentExample;

@Service
public class contentCategoryServiceImpl implements contentCategoryService {
	
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		// TODO Auto-generated method stub
		//根据parentId查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
		//转换成EasyUITreeNode列表
		List<EasyUITreeNode> nodeList =new ArrayList<>();
		for (TbContentCategory tbContentCategory : catList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到列表
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		// TODO Auto-generated method stub
		//创建一个pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		//设置pojo属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//1正常   2删除
		contentCategory.setStatus(1);
		//默认排序是1
		contentCategory.setSortOrder(1);
		//新添加的节点一定是叶子节点
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入数据库
		contentCategoryMapper.insert(contentCategory);
		//判断父节点的isparent   如果不是true改为true 
		//根据parentid查询父节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			//更新到数据库
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回结果  返回E3Result
		
		return E3Result.ok(contentCategory);
	}

}
