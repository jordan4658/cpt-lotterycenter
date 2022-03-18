package com.caipiao.core.library.rest.read.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.start.BeatVo;


@FeignClient(name = BUSINESS_READ)
public interface SscPlaySettingReadRest extends BaseRest {
	
	
	
	 @GetMapping("/sscSetting/getBeatList.json")
	 List<BeatVo>  getBeatList(@RequestParam("id") Long id);
	

}
