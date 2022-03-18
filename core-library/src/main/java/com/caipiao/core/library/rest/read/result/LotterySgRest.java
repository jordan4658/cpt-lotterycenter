package com.caipiao.core.library.rest.read.result;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.model.ResultInfo;

@FeignClient(name = BUSINESS_READ)
public interface LotterySgRest {

	/**
	 * app开奖模块首页信息
	 * 
	 * @return
	 */
	@PostMapping("/sg/getNewestSgInfo.json")
	ResultInfo<Map<String, Object>> getNewestSgInfo();

	/**
	 * web开奖资讯首页信息
	 * 
	 * @return
	 */
	@PostMapping("/sg/getNewestSgInfoWeb.json")
	ResultInfo<Map<String, Object>> getNewestSgInfoWeb();

	/**
	 * web首页：免费推荐【重庆时时彩、腾讯分分彩】
	 * 
	 * @return
	 */
	@PostMapping("/sg/getCqsscAndTxffcRecommend.json")
	ResultInfo<Map<String, Object>> getCqsscAndTxffcRecommend();

	/**
	 * web首页：高频彩【重庆时时彩、新疆时时彩、北京PK10、pc蛋蛋】
	 */
	@PostMapping("/sg/getHighFrequencyColor.json")
	ResultInfo<Map<String, Object>> getHighFrequencyColor();

	/** 
	* app获取多个彩种的当前开奖信息
	* @return
	*/ 
	@PostMapping("/sg/lishiSg.json")
	ResultInfo<Map<String, Object>> lishiSg(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, @RequestParam("id") Integer id);

	@PostMapping("/sg/getNewestSgInfobyids.json")
	ResultInfo<Map<String, Object>> getNewestSgInfobyids(@RequestParam("ids") String ids);
}
