package com.caipiao.core.library.rest.write.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.rest.BaseRest;

@FeignClient(name = BUSINESS_SERVER)
public interface BonusCategoryWriteRest extends BaseRest {
	
	@PostMapping("/bonus/updatebonuscategory.json")
	boolean  update(@RequestParam("id") Integer id, @RequestParam("name") String name);

	@PostMapping("/bonus/insertbonuscategory.json")
	boolean insert(@RequestParam("name") String name);

	@PostMapping("/bonus/deletebonuscategory.json")
	boolean delete(@RequestParam("id") Integer id);
}
