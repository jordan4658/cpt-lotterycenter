package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.LotteryCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface LotteryCategoryWriteRest extends BaseRest {
	
	@PostMapping("/lottery/insert")
	boolean insert(@RequestBody LotteryCategory lc);

	@PostMapping("/lottery/update")
	boolean update(@RequestBody LotteryCategory lc);

	@PostMapping("/lottery/deleteLotteryCategoryById")
	boolean deleteLotteryCategoryById(@RequestParam("id") Integer id);
}
