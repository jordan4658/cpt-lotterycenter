package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.model.common.ResultInfo;

import java.util.List;
import java.util.Map;

public interface LotterySgServiceReadSg {

    /**
     * app开奖模块首页信息
     * @return
     */
    ResultInfo<Map<String,Object>> getNewestSgInfo();

	ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize, Integer id);

	ResultInfo<Map<String, Object>> lishitjsscSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> lishijspksSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> lishifivepksSg(Integer pageNo, Integer pageSize);
	
	ResultInfo<Map<String, Object>> lishitenpksSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> lishifivelhcSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> lishionelhcSg(Integer pageNo, Integer pageSize);
	
	ResultInfo<Map<String, Object>> lishisslhcSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> lishitensscSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> lishifivesscSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> lishijssscSg(Integer pageNo, Integer pageSize);

	ResultInfo<Map<String, Object>> getNewestSgInfobyids(String ids);

	/**
	 * @Title: getSgLongDragons
	 * @Description: 获取开奖长龙数据
	 * @return ResultInfo<List<Map<String,Object>>>
	 * @author HANS
	 * @date 2019年5月9日下午21:25:02
	 */
	ResultInfo<List<Map<String, Object>>> getSgLongDragons();

    ResultInfo<Map<String, Object>> getNewestSgScInfobyids(String ids);
}
