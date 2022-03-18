package com.caipiao.live.common.service.lottery;


import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.lottery.LhcPlayInfoDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayAllDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryPlayInfoDTO;
import com.caipiao.live.common.model.dto.lottery.PcddPlayInfoDTO;
import com.caipiao.live.common.model.vo.lottery.BetRestrictVo;
import com.caipiao.live.common.mybatis.entity.LhcGodType;
import com.caipiao.live.common.mybatis.entity.LotteryPlay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LotteryPlayServiceReadSg{

	/**
	 * 根据分类Id查询玩法列表
	 *
	 * @param cateId 分类id
	 * @return
	 */
	List<LotteryPlayDTO> playList(Integer cateId);

	/**
	 * 根据playId查询玩法
	 *
	 * @param playId 分类id
	 * @return
	 */
	LotteryPlay getPlayOneByPlayId(Integer playId);

	/**
	 * 获取父级
	 * @param lotteryId
	 * @return
	 */
	LotteryPlay getParentPlaybyId(Integer lotteryId);
	/**
	 * 获取父级信息
	 *
	 * @param id 分类id
	 * @return
	 */
	List<LotteryPlay> getPlays(Integer id);

	/**
	 * 根据分类id查询玩法信息
	 *
	 * @param cateId 分类id
	 * @return
	 */
	List<LotteryPlay> getPlaysByCateId(Integer cateId);

	/**
	 * 根据id查询玩法信息
	 *
	 * @param id 玩法id
	 * @return
	 */
	LotteryPlay queryPlayById(Integer id);

	/**
	 * 根据父级id查找子集信息
	 *
	 * @param parentId 父级id
	 * @return
	 */
	List<LotteryPlay> queryPlayByParentId(Integer parentId);

	/**
	 * 根据分类id获取一级玩法列表
	 *
	 * @param cateId 分类id
	 * @return
	 */
	List<LotteryPlay> getPlaysFirstLevelByCateId(Integer cateId);

	/**
	 * 根据分类id获取一级玩法列表
	 *
	 * @param cateId 分类id
	 * @return
	 */
	List<LotteryPlayDTO> getPlaysFirstLevelDtoByCateId(Integer cateId);

	/**
	 * 根据分类id获取二级玩法列表
	 *
	 * @param lotteryId 分类id
	 * @return
	 */
	List<LotteryPlay> getPlaysSecondLevelByLotteryId(Integer lotteryId);

	/**
	 * 查询玩法配置信息
	 *
	 * @param playId 玩法id
	 * @return
	 */
	ResultInfo<Map<String, LotteryPlayInfoDTO>> queryPlayInfoById(Integer lotteryId, Integer playId);

	/**
	 * 获取PC蛋蛋具体玩法赔率信息
	 *
	 * @param playId    玩法id
	 * @param lotteryId 彩种id
	 * @return
	 */
	ResultInfo<PcddPlayInfoDTO> queryPcddPlayOdds(Integer playId, Integer lotteryId);

	/**
	 * 获取六合彩具体玩法赔率信息
	 *
	 * @param playId    玩法id
	 * @param lotteryId 彩种id
	 * @return
	 */
	ResultInfo<List<LhcPlayInfoDTO>> queryLhcBuy(Integer playId, Integer lotteryId);

	/**
	 * 根据分类id和玩法等级获取玩法列表
	 *
	 * @param cateId 分类id
	 * @param level  玩法等级
	 * @return
	 */
	List<LotteryPlay> getPlaysByCateIdAndLevel(Integer cateId, Integer level);

	/**
	 * 获取所有未删除的玩法集合
	 *
	 * @return
	 */
	List<LotteryPlay> selectPlayList();

	/**
	 * 获取所有未删除的玩法集合
	 *
	 * @return
	 */
	Map<Integer, LotteryPlay> selectPlayMap();

	/**
	 * 根据分类id获取一级玩法列表
	 *
	 * @param lotteryId 分类id
	 * @return
	 */
	List<LotteryPlay> getPlaysFirstLevelByLotteryId(Integer lotteryId);

	List<LotteryPlay> getPlaysFirstLevelByLotteryAndParentId(Integer lotteryId, Integer parentId);

	List<LotteryPlay> getPlaysByLotteryIdAndLevel(Integer lotteryId, Integer level);

	LotteryPlay selectPlayByPlayTagId(Integer playTagId);

	PageResult<List<LhcGodType>> getWebGodType(Integer pageNo, Integer pageSize);

	/**
	 * 查询彩种下的所有玩法，根据彩票ID分组
	 *
	 * @param categoryIds 彩种 ID列表
	 * @return Map [ lotteryId, Map [ parentPlayId, Map [ childPlayId, lotteryPlay ]
	 *         ] ]
	 */
	Map<Integer, Map<Integer, Map<Integer, LotteryPlay>>> selectAllLotteryPlayByCategoryIds(List<Integer> categoryIds);

	/**
	 * 查询指定彩种下的彩票的所有玩法
	 *
	 * @param categoryIds
	 * @return Map[LotteryId, List[LotteryPlayAllDTO]]
	 */
	Map<Integer, List<LotteryPlayAllDTO>> selectAllLotteryPlayDTOByCategoryIds(List<Integer> categoryIds);

	List<LotteryPlay> getPlayByLotteryList(Integer lotteryId);

	List<BetRestrictVo> betRestrictList(Integer lotteryId);

	ResultInfo<BigDecimal> qaueryBetRestrict(Integer lotteryId);

}
