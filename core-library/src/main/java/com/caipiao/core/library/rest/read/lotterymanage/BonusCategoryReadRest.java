package com.caipiao.core.library.rest.read.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.PageResult;
import com.mapper.domain.BonusCategory;

@FeignClient(name = BUSINESS_READ)
public interface BonusCategoryReadRest {

	/**
	 * 根据id查询投注限制分类信息
	 * @param id 投注限制分类id
	 * @return
	 */
	@GetMapping("/bonus/selectById.json")
	BonusCategory  selectById(@RequestParam(value = "id") Integer id);

	/**
	 * 分页查询投注限制列表
	 * @param pageNum 页码
	 * @param pageSize 每页数量
	 * @return
	 */
	@GetMapping("/bonus/listBonusCategory.json")
    PageResult<List<BonusCategory>> listBonusCategory(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize);

	/**
	 * 查询所有投注限制分类
	 * @return
	 */
	@GetMapping("/bonus/queryAllBonusCategory.json")
	List<BonusCategory> queryAllBonusCategory();

}
