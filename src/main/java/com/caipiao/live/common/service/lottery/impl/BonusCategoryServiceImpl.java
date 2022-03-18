package com.caipiao.live.common.service.lottery.impl;


import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.service.lottery.BonusCategoryService;
import com.caipiao.live.common.mybatis.entity.BonusCategory;
import com.caipiao.live.common.mybatis.entity.BonusCategoryExample;
import com.caipiao.live.common.mybatis.mapper.BonusCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusCategoryServiceImpl  implements BonusCategoryService {
	
	@Autowired
	private BonusCategoryMapper bonusCategoryMapper;
	
	@Override
	public BonusCategory selectById(Integer id) {
		return this.bonusCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult<List<BonusCategory>> listLotteryCategory(Integer pageNo, Integer pageSize) {
		BonusCategoryExample bce= new BonusCategoryExample();
		int count =this.bonusCategoryMapper.countByExample(bce);
		PageResult<List<BonusCategory>> pageResult = PageResult.getPageResult(pageNo, pageSize, count);
		if(count > 0) {
			bce.setOffset((pageNo-1)*pageSize);
			bce.setLimit(pageSize);
			List<BonusCategory> list= this.bonusCategoryMapper.selectByExample(bce);
			pageResult.setData(list);
		}
		return pageResult;
	}

	@Override
	public List<BonusCategory> queryAllBonusCategory() {
		return bonusCategoryMapper.selectByExample(null);
	}

}
