package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.Lottery;
import com.mapper.domain.LotteryCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface LotteryCategoryReadRest extends BaseRest {
	
	@GetMapping("/lottery/lotteryCategoryList.json")
    PageResult<List<LotteryCategory>> lotteryCategoryList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

	@GetMapping("/lottery/getLotteryCategory.json")
	LotteryCategory getLotteryCategory(@RequestParam("id") Integer id);

	@PostMapping("/lottery/queryCategory.json")
	List<LotteryCategory> queryCategory(@RequestParam("id") Integer id);
	
	@PostMapping("/lottery/queryAllCategory.json")
	List<LotteryCategory> queryAllCategory();

	@PostMapping("/lottery/getLottery.json")
	Lottery getLottery(@RequestParam("lotteryId") Integer lotteryId);

	@GetMapping("/lottery/getLotteryCategoryByCategoryId.json")
	LotteryCategory getLotteryCategoryByCategoryId(@RequestParam("categoryId") Integer categoryId);
	
	@GetMapping("/lottery/getLotteryCategoryById.json")
	LotteryCategory getLotteryCategoryById(@RequestParam("id") Integer id);
	
	@PostMapping("/lottery/getLotteryByLotteryId.json")
	Lottery getLotteryByLotteryId(@RequestParam("lotteryId") Integer lotteryId);
}
