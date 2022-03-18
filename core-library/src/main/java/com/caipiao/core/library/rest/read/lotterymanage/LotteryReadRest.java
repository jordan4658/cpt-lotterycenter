package com.caipiao.core.library.rest.read.lotterymanage;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.caipiao.core.library.dto.result.BjpksLiangMian;
import com.caipiao.core.library.dto.result.SscMissNumDTO;
import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.lotterymanage.LotteryDTO;
import com.caipiao.core.library.vo.lotterymanage.LotterySet;
import com.caipiao.core.library.vo.lotterymanage.lottery.LotteryInfo;
import com.mapper.domain.Lottery;

@FeignClient(name = BUSINESS_READ)
public interface LotteryReadRest extends BaseRest {
	
    /**
     * 获取彩种列表 - 购彩首页
     *
     * @return
     */
    @GetMapping("/lottery/test.json")
    String test();

    /**
     * 根据主键查询对象
     *
     * @param id 主键
     * @return
     */
    @GetMapping("/lottery/queryLotteryById.json")
    Lottery queryLotteryById(@RequestParam(name = "id") Integer id);

    /**
     * 根据条件查询彩种列表
     *
     * @param cateId   分类id
     * @param name     彩票名称
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @GetMapping("/lottery/queryLotteryList.json")
    PageResult<List<LotteryDTO>> queryLotteryList(@RequestParam(name = "cateId", required = false) Integer cateId,
                                                  @RequestParam(name = "name", required = false) String name,
                                                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    /**
     * 获取全部彩种信息
     *
     * @return
     */
    @GetMapping("/lottery/queryAllLottery.json")
    List<Lottery> queryAllLottery();

    /**
     * 获取所有彩种信息
     *
     * @return
     */
    @GetMapping("/lottery/selectLotteryList.json")
    List<Lottery> selectLotteryList();

    /**
     * 获取彩种列表 - 购彩首页
     *
     * @return
     */
    @GetMapping("/lottery/queryList.json")
    List<Map<String, Object>> queryList();
    
    /**
     * 获取彩种设置
     *
     * @return
     */
    @GetMapping("/lottery/queryAllList.json")
    List<LotterySet> queryAllList();

    /**
     * 购彩 - 获取相应彩种的组选遗漏值
     *
     * @param lotteryId 彩种id
     * @return
     */
    @PostMapping("/lottery/queryMissValByGroup.json")
    SscMissNumDTO queryMissValByGroup(@RequestParam(name = "lotteryId") Integer lotteryId);

    /**
     * 购彩 - 获取时时彩直选遗漏值
     *
     * @param lotteryId 彩种id
     * @param start     开始位置
     * @param end       结束位置
     * @return
     */
    @PostMapping("/lottery/querySscMissVal.json")
    Map<String, SscMissNumDTO> querySscMissVal(@RequestParam(name = "lotteryId") Integer lotteryId, @RequestParam(name = "start") Integer start, @RequestParam(name = "end") Integer end);

    /**
     * 购彩 - 获取北京PK10两面遗漏值
     *
     * @return
     */
    @PostMapping("/lottery/queryBjpksLmMissVal.json")
    Map<String, BjpksLiangMian> queryBjpksLmMissVal();

    /**
     * 获取开奖异常记录
     *
     * @param lotteryId 彩种id
     * @param date 日期
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/lottery/queryOpenException.json")
    PageResult<List<LotterySgModel>> queryOpenException(@RequestParam(name = "lotteryId") Integer lotteryId, @RequestParam(name = "date") String date, @RequestParam(name = "pageNo") Integer pageNo, @RequestParam(name = "pageSize") Integer pageSize);

    /**
     * 获取开奖异常记录
     *
     * @param lotteryId 彩种id
     * @param date 日期
     * @param status 状态
     * @param issue 期号
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return
     */
    @PostMapping("/lottery/queryOpenNumber.json")
    PageResult<List<LotterySgModel>> queryOpenNumber(@RequestParam(name = "lotteryId") Integer lotteryId, @RequestParam(name = "date") String date,
                                                     @RequestParam("status") String status, @RequestParam("issue") String issue,
                                                     @RequestParam(name = "pageNo") Integer pageNo, @RequestParam(name = "pageSize") Integer pageSize);
    
	/**
	 * 移动端 查询所有彩种玩法赔率
	 */
    @GetMapping("/lottery/queryLotteryInfoAll.json")
    List<LotteryInfo> queryLotteryInfoAll();
}
