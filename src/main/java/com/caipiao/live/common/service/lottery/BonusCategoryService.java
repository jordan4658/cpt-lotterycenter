package com.caipiao.live.common.service.lottery;


import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.mybatis.entity.BonusCategory;

import java.util.List;

public interface BonusCategoryService {
	
	
	/**
	 * 根据主键查询对象
	 * @param id 主键
	 * @return
	 */
	BonusCategory selectById(Integer id);

	/**
	 * 分页查询投注限制列表
	 * @param pageNo 页码
	 * @param pageSize 每页数量
	 * @return
	 */
    PageResult<List<BonusCategory>> listLotteryCategory(Integer pageNo, Integer pageSize);

	/**
	 * 查询所有投注限制分类
	 * @return
	 */
	List<BonusCategory> queryAllBonusCategory();

}
