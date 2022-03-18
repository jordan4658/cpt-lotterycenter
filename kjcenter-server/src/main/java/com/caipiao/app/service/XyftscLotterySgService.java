package com.caipiao.app.service;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.common.*;
import java.util.List;
import java.util.Map;

public interface XyftscLotterySgService {
	/**
	 * 获取历史开奖
	 *
	 * @param type
	 * @param date
	 * @return
	 */
	ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNum, Integer pageSize);

	/**
	 * 历史开奖
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ResultInfo<Map<String, Object>> lishiSg(String issue, String date, Integer pageNum, Integer pageSize);


	/**
	 * 最近一期的开奖结果
	 *
	 * @return
	 */
	ResultInfo<Map<String, Object>> getNewestSgInfo();

	/**
	 * 最近一期的开奖结果
	 *
	 * @return
	 */
	ResultInfo<Map<String, Object>> getNewestSgInfoWeb();

}
