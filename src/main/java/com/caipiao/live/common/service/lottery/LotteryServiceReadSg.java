package com.caipiao.live.common.service.lottery;


import com.caipiao.live.common.model.LotterySet;
import com.caipiao.live.common.model.common.PageResult;
import com.caipiao.live.common.model.dto.lottery.HotFavoriteDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryFavoriteDTO;
import com.caipiao.live.common.model.dto.lottery.LotteryInfo;
import com.caipiao.live.common.model.dto.lottery.LotterySgModel;
import com.caipiao.live.common.model.dto.result.BjpksLiangMian;
import com.caipiao.live.common.model.dto.result.SscMissNumDTO;
import com.caipiao.live.common.model.vo.lottery.OptionSelectVo;
import com.caipiao.live.common.mybatis.entity.Lottery;

import java.util.List;
import java.util.Map;

public interface LotteryServiceReadSg {


    /**
     * 根据彩种id查询彩种信息
     *
     * @param lotteryId 彩种id
     * @return
     */
    Lottery selectLotteryById(Integer lotteryId);

    /**
     * 查询所有彩种信息
     *
     * @return
     */
    List<Lottery> selectLotteryList(String categoryType);

    /**
     * 查询所有彩种信息Map集合 key为彩种id  value为彩种对象
     */
    Map<Integer, Lottery> selectLotteryMap(String categoryType);

    /**
     * 根据条件查询彩种列表
     *
     * @param cateId   分类id
     * @param name     彩票名称
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param type     根据彩种类别过滤，非必须
     * @return
     */
    PageResult<List<LotteryDTO>> queryAllLotteryList(Integer cateId, String name, Integer pageNo, Integer pageSize, String type);

    /**
     * 获取内部所有彩种信息
     *
     * @param type dzpceggLotterySgMapper
     * @return
     */
    List<Lottery> queryLotteryByCategoryType(String type);

    List<Lottery> queryLotteryByCategoryId(Integer categoryId);

    /**
     * 获取所有彩种列表 - 购彩,首页
     *
     * @return
     */
    List<Map<String, Object>> queryAllList();

    /**
     * 获取非第三方彩种列表 - 购彩首页
     *
     * @return
     */
    List<Map<String, Object>> queryInternalList();

    List<Map<String, Object>> queryInternalList(String type);

    List<LotterySet> queryInternalAllList(String type);

    /**
     * 获取相应彩种的组选遗漏值
     *
     * @param lotteryId 彩种id
     * @return
     */
    SscMissNumDTO queryMissValByGroup(Integer lotteryId);

    /**
     * 购彩 - 获取时时彩直选遗漏值
     *
     * @param lotteryId 彩种id
     * @param start     开始位置
     * @param end       结束位置
     * @return
     */
    Map<String, SscMissNumDTO> querySscMissVal(Integer lotteryId, Integer start, Integer end);

    /**
     * 购彩 - 获取北京PK10两面遗漏值
     *
     * @return
     */
    Map<String, BjpksLiangMian> queryBjpksLmMissVal();

    /**
     * 分页获取号码异常
     *
     * @param lotteryId 彩种id
     * @param date      日期
     * @param pageNo    页码
     * @param pageSize  每页数量
     * @return
     */
    PageResult<List<LotterySgModel>> queryOpenException(Integer lotteryId, String date, Integer pageNo, Integer pageSize);

    /**
     * 获取开奖对照记录
     *
     * @param lotteryId 彩种id
     * @param date      日期
     * @param status    状态
     * @param issue     期号
     * @param pageNo    页码
     * @param pageSize  每页数量
     * @return
     */
    PageResult<List<LotterySgModel>> queryOpenNumber(Integer lotteryId, String date, String status, String issue, Integer pageNo, Integer pageSize);


    /**
     * 移动端 查询所有彩种玩法赔率
     *
     * @return
     */
    List<LotteryInfo> queryLotteryAllInfo(String type);

    Lottery selectLotteryByLotteryId(Integer lotteryId);

    /**
     * @Title: selectLotteryForName
     * @Description: 通过名称获取到配置信息
     * @author HANS
     * @date 2019年5月11日下午9:35:45
     */
    Lottery selectLotteryForName(String name);

    List<Lottery> lotteryList(Integer categoryId);

    List<Lottery> queryAlllotteryList();

    /**
     * 查询用户收藏的彩票
     *
     * @return
     */
    List<LotteryFavoriteDTO> queryLotteryByLotteryFavorites(Integer uid);

    /**
     * 查询用户默认收藏彩票信息
     *
     * @return
     */
    List<LotteryFavoriteDTO> queryDefaultLotteryFavorites();

    /**
     * 查询所有彩票信息
     *
     * @return
     */
    List<Lottery> queryAllLotteryFromCache();

    /**
     * 查询所有彩票，根据彩种分类
     *
     * @return
     */
    Map<Integer, List<Lottery>> queryLotteryGroupByCategoryId();

    HotFavoriteDTO getFavorite();

    /**
     * 查询直播间彩票
     *
     * @param ids
     * @return
     */
    List<OptionSelectVo> queryLiveLotteryList(String ids);
}
