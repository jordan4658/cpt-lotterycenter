package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryInformationType;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;

import com.caipiao.live.common.model.dto.result.BjpksGuanYaHeCountDTO;
import com.caipiao.live.common.model.dto.result.BjpksGuanyaHeDTO;
import com.caipiao.live.common.model.dto.result.BjpksHistoryDTO;
import com.caipiao.live.common.model.vo.BjpksSgVO;
import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.model.vo.MapIntegerVO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.ThereIntegerVO;
import com.caipiao.live.common.model.vo.ThereMemberVO;
import com.caipiao.live.common.model.vo.lottery.LhcCountVO;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.model.vo.lottery.ThereMemberListVO;
import com.caipiao.live.common.mybatis.entity.BjpksLotterySg;
import com.caipiao.live.common.mybatis.entity.BjpksLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.BjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperbean.BjpksBeanMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.BjpksLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.BjpksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JspksLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.StringUtils;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.BjpksUtils;
import com.caipiao.live.common.util.lottery.JspksSgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 北京PK10
 *
 * @author lzy
 * @create 2018-07-30 16:52
 **/
@Service
public class BjpksLotteryReadSgServiceImpl implements BjpksLotterySgServiceReadSg {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BjpksLotterySgMapper bjpksLotterySgMapper;
    @Autowired
    private BjpksLotterySgMapperExt bjpksLotterySgMapperExt;
    @Autowired
    private BjpksBeanMapper bjpksBeanMapper;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private JspksLotterySgServiceReadSg jspksLotterySgService;

    @Override
    public ResultInfo<List<MapListVO>> todayNumber(String type) {
        List<MapListVO> voList = new ArrayList<>();

        // 校验参数
        if (!LotteryInformationType.BJPKS_JRHM.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = bjpksBeanMapper.selectNumberByDate(date + "%");
        // 判空
        if (CollectionUtils.isEmpty(sgs)) {
            return ResultInfo.ok(voList);
        }

        // 今日号码
        voList = BjpksUtils.todayNumber(sgs);
        return ResultInfo.ok(voList);
    }

    @Override
    public ResultInfo<List<MapListVO>> lengRe(String type, Integer issue) {
        if (issue == null) {
            issue = 20;
        }
        if (issue > 100) {
            issue = 100;
        }

        //北京pk10冷热分析
        if (LotteryInformationType.BJPKS_LRFX.equals(type)) {
            List<String> sg = bjpksBeanMapper.selectNumberLimitDesc(issue);
            if (sg == null) {
                sg = new ArrayList<>(0);
            }

            List<MapListVO> listVOs = BjpksUtils.lengRe(sg);
            return ResultInfo.ok(listVOs);
        }

        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<List<ThereMemberVO>> liangMianC(String type) {
        //北京pk10两面长龙
        if (LotteryInformationType.BJPKS_LMCL.equals(type)) {
            String date = TimeHelper.date("yyyy-MM-dd");
            List<String> sg = bjpksBeanMapper.selectNumberByDate(date + "%");
            if (sg == null) {
                sg = new ArrayList<>(0);
            }

            List<ThereMemberVO> listVOs = BjpksUtils.liangMianC(sg);
            return ResultInfo.ok(listVOs);
        }

        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<List<LhcCountVO>> liangMianCWeb(String type, Integer issue, Integer count) {
        //北京pk10两面长龙
        if (LotteryInformationType.BJPKS_LMCL.equals(type)) {
            if (issue == null) {
                issue = 30;
            }
            List<String> sg = bjpksBeanMapper.selectNumberLimitDesc(issue);
            if (sg == null) {
                sg = new ArrayList<>(0);
            }

            if (count == null) {
                count = 0;
            }
            List<LhcCountVO> result = BjpksUtils.liangMianCWeb(sg, count);
            return ResultInfo.ok(result);
        }

        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }


    @Override
    public ResultInfo<Map<String, ThereMemberListVO>> luzhuG(String type, String date) {
        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        //北京pk10冠军和路珠
        if (LotteryInformationType.BJPKS_GJLZ.equals(type)) {
            List<String> sg = bjpksBeanMapper.selectNumberByDate(date + "%");
            if (sg == null) {
                sg = new ArrayList<>(0);
            }

            Map<String, ThereMemberListVO> result = BjpksUtils.luzhuG(sg);
            return ResultInfo.ok(result);
        }

        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<Map<String, ThereMemberListVO>> luzhuQ(String type, String date) {
        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        List<String> sg = bjpksBeanMapper.selectNumberByDate(date + "%");
        if (sg == null) {
            sg = new ArrayList<>(0);
        }
        //北京pk10前后路珠
        if (LotteryInformationType.BJPKS_QHLZ.equals(type)) {
            Map<String, ThereMemberListVO> result = BjpksUtils.luzhuQ(sg);
            return ResultInfo.ok(result);
        }
        //北京PK10两面路珠之大小, 之单双, 之龙虎
        if (LotteryInformationType.BJPKS_LMLZ_DX.equals(type) || LotteryInformationType.BJPKS_LMLZ_DS.equals(type) || LotteryInformationType.BJPKS_LMLZ_LH.equals(type)) {
            Map<String, ThereMemberListVO> result = BjpksUtils.luzhuLiangMian(sg, type);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNo, Integer pageSize) {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andNumberIsNotNull();
        if (!StringUtils.isBlank(date)) {
            criteria.andIdealTimeLike(date + "%");
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);

        if (bjpksLotterySgs == null) {
            bjpksLotterySgs = new ArrayList<>(0);
        }

        // 北京PK10历史开奖
        if (LotteryInformationType.BJPKS_LSKJ.equals(type)) {
            List<KjlsVO> result = BjpksUtils.historySg(bjpksLotterySgs);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
//        bjpksCriteria.andNumberIsNotNull();
        //        criteria.andWanIsNotNull();
        bjpksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<BjpksLotterySg> bjpksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.BJPKS_SG_HS_LIST)) {
            BjpksLotterySgExample exampleOne = new BjpksLotterySgExample();
            BjpksLotterySgExample.Criteria bjpksCriteriaOne = exampleOne.createCriteria();
            bjpksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<BjpksLotterySg> bjpksLotterySgsOne = bjpksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.BJPKS_SG_HS_LIST, bjpksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            bjpksLotterySgs = redisTemplate.opsForList().range(RedisKeys.BJPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);
        }

//        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = BjpksUtils.lishiSg(bjpksLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<List<MapListVO>> numNoOpen() {
        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = bjpksBeanMapper.selectNumberByDateDesc(date + "%");
        List<MapListVO> listVOs = BjpksUtils.numNoOpen(sgs);
        return ResultInfo.ok(listVOs);
    }

    @Override
    public ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount() {

        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = bjpksBeanMapper.selectNumberByDate(date + "%");

        //北京PK10冠亚和统计
        Map<String, List<ThereIntegerVO>> result = BjpksUtils.guanYaCount(sgs);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCountbySize(int size) {

        List<String> sgs = bjpksBeanMapper.selectNumberLimitDesc(size);

        //北京PK10冠亚和统计
        Map<String, List<ThereIntegerVO>> result = BjpksUtils.guanYaCount(sgs);
        return ResultInfo.ok(result);
    }


    @Override
    public ResultInfo<List<BjpksSgVO>> getSgTrend(Integer issue) {
        if (issue == null) {
            issue = 40;
        }
        List<BjpksSgVO> bjpksLotterySgs = bjpksBeanMapper.selectLimitDesc(issue);
        return ResultInfo.ok(bjpksLotterySgs);
    }

    @Override
    public ResultInfo<Map<String, ArrayList<MapIntegerVO>>> lianMianYl(String type, String way, Integer number) {
        if (number == null) {
            number = 0;
        } else if (number > 10 || number < 1) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        } else {
            number = number - 1;
        }
        int limit = 179 * 30;  // 一天开179期
        if ("3".equals(way)) {
            limit = 179 * 30 * 3 + 1;
        } else if ("6".equals(way)) {
            limit = 179 * 30 * 6 + 3;
        }

        List<String> sgList = bjpksBeanMapper.selectNumberLimitDesc(limit);

        if (sgList == null) {
            sgList = new ArrayList<>(0);
        }

        //北京PK10两面遗漏之大小
        if (LotteryInformationType.BJPKS_LMYL_DX.equals(type)) {
            Map<String, ArrayList<MapIntegerVO>> result = BjpksUtils.noOpenLiangMianDx(sgList, number);
            return ResultInfo.ok(result);
        }

        //北京PK10两面遗漏之单双
        if (LotteryInformationType.BJPKS_LMYL_DS.equals(type)) {
            Map<String, ArrayList<MapIntegerVO>> result = BjpksUtils.noOpenLiangMianDs(sgList, number);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<Map<Integer, ArrayList<Integer>>> lianMianYlWeb(String type, String way) {
        int limit = 179 * 30;  // 一天开179期
        if ("3".equals(way)) {
            limit = 179 * 30 * 3 + 1;
        } else if ("6".equals(way)) {
            limit = 179 * 30 * 6 + 3;
        }

        List<String> sgList = bjpksBeanMapper.selectNumberLimitDesc(limit);

        if (sgList == null) {
            sgList = new ArrayList<>(0);
        }

        //北京PK10两面遗漏之大小
        if (LotteryInformationType.BJPKS_LMYL_DX.equals(type)) {
            HashMap<Integer, ArrayList<Integer>> result = BjpksUtils.noOpenLiangMianDxWeb(sgList);
            return ResultInfo.ok(result);
        }

        //北京PK10两面遗漏之单双
        if (LotteryInformationType.BJPKS_LMYL_DS.equals(type)) {
            Map<Integer, ArrayList<Integer>> result = BjpksUtils.noOpenLiangMianDsWeb(sgList);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<Map<String, Object>> sgDetails(String sgIssue) {
        if (StringUtils.isBlank(sgIssue)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueEqualTo(sgIssue);
        BjpksLotterySg bjpksLotterySg = bjpksLotterySgMapper.selectOneByExample(example);
        Map<String, Object> map = BjpksUtils.sgDetails(bjpksLotterySg);
        return ResultInfo.ok(map);
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullPkResult();
        try {
            // 缓存中取开奖数量
            String openRedisKey = RedisKeys.BJPKS_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("openStatus", LotteryResultStatus.AUTO);
                map.put("paramTime", TimeHelper.date("yyyy-MM-dd") + "%");
                openCount = bjpksLotterySgMapperExt.openCountByExample(map);
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }

            if (openCount != null) {
                result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
                // 获取开奖总期数
                Integer sumCount = CaipiaoSumCountEnum.BJPKS.getSumCount();
                // 计算当日剩余未开奖次数
                result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            }
            String nextRedisKey = RedisKeys.BJPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.BJPKS.getRedisTime();
            BjpksLotterySg nextBjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextBjpksLotterySg == null) {
                nextBjpksLotterySg = this.getBjpksSg();
                redisTemplate.opsForValue().set(nextRedisKey, nextBjpksLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.BJPKS_RESULT_VALUE;
            BjpksLotterySg bjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (bjpksLotterySg == null) {
                // 查询最近一期信息
                bjpksLotterySg = this.getBjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, bjpksLotterySg);
            }

            if (nextBjpksLotterySg != null) {
                String nextIssue = nextBjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextBjpksLotterySg.getIssue();
                String txffnextIssue = bjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextBjpksLotterySg = this.getBjpksSg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextBjpksLotterySg, redisTime, TimeUnit.MINUTES);
                        // 查询最近一期信息
                        bjpksLotterySg = this.getBjpksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, bjpksLotterySg);
                    }
                }

                if (bjpksLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), bjpksLotterySg == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), bjpksLotterySg == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getNumber());
                }

                if (nextBjpksLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextBjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextBjpksLotterySg.getIdealTime()) / 1000L);
                }
            } else {

                if (bjpksLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), bjpksLotterySg == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), bjpksLotterySg == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.BJPKS.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    /**
     * @return BjpksLotterySg
     * @Title: getBjpksLotterySg
     * @Description: 查询当前开奖数据
     * @author admin
     * @date 2019年5月1日下午3:51:58
     */
    public BjpksLotterySg getBjpksLotterySg() {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        BjpksLotterySg bjpksLotterySg = bjpksLotterySgMapper.selectOneByExample(example);
        return bjpksLotterySg;
    }

    /**
     * @return BjpksLotterySg
     * @Title: getBjpksSg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年4月29日下午9:21:07
     */
    public BjpksLotterySg getBjpksSg() {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        example.setOrderByClause("issue ASC");
        BjpksLotterySg nextBjpksLotterySg = bjpksLotterySgMapper.selectOneByExample(example);
        return nextBjpksLotterySg;
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfoWeb() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "北京PK10");

        // 查询最近一期信息
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        example.setOrderByClause("ideal_time DESC");
        BjpksLotterySg bjpksLotterySg = bjpksLotterySgMapper.selectOneByExample(example);
        if (bjpksLotterySg != null) {
            result.put("issue", bjpksLotterySg.getIssue());
            result.put("number", bjpksLotterySg.getNumber());
            result.put("time", bjpksLotterySg.getTime());
        } else {
            result.put("issue", null);
            result.put("number", null);
            result.put("time", null);
        }
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> getNowIssueAndTime() {
        Map<String, Object> result = new HashMap<>();

        // 查询最近一期信息
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        example.setOrderByClause("ideal_time DESC");
        BjpksLotterySg bjpksLotterySg = bjpksLotterySgMapper.selectOneByExample(example);
        String time = bjpksLotterySg.getTime();
        result.put("issue", BjpksUtils.nextIssue(time, bjpksLotterySg.getIssue()) + "");
        long nextIssueTime = BjpksUtils.nextIssueTime();
        // 获取当前期开奖时间
        result.put("time", nextIssueTime / 1000L);
        return ResultInfo.ok(result);
    }


    /**
     * 获取web资讯：北京PK10历史开奖
     *
     * @param pageNo  当前第几页
     * @param pageSize 页大小
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getPK10HistoryLottery(Integer pageNo, Integer pageSize, String date) {
        Map<String, Object> result = new HashMap<>();
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andNumberIsNotNull();
        if (StringUtils.isNotBlank(date)) {
            criteria.andTimeLike(date + "%");
        }
        Integer total = bjpksLotterySgMapper.countByExample(example);

        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`ideal_time` desc");
        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);

        //定义list集合存放冠亚和
        List<Map<String, Object>> listCrown = new ArrayList<>();

        //定义list集合存放龙虎
        List<Map<String, Object>> listDragonandTiger = new ArrayList<>();

        //定义存放冠亚和
        Map<String, Object> map;

        //定义存放龙虎map
        Map<String, Object> map2;
        for (BjpksLotterySg listBs : bjpksLotterySgs) {
            map = new HashMap<>();
            String number = listBs.getNumber();
            String[] split = number.split(",");
            //循环并把前两位相加
            int count = Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
            map.put("count", count);//和

            //冠亚和计算
            if (count > 11 && count % 2 == 0) {
                map.put("big", "大");
                map.put("two", "双");
            } else if (count < 11 && count % 2 != 0) {
                map.put("smile", "小");
                map.put("single", "单");
            } else if (count > 11 && count % 2 != 0) {
                map.put("big", "大");
                map.put("single", "单");
            } else if (count < 11 && count % 2 == 0) {
                map.put("smile", "小");
                map.put("single", "双");
            } else if (count == 11) {
                map.put("make", "和");//前两位数相加的和为11的为为和
                map.put("single", "单");
            }
            listCrown.add(map);

            //1-5龙虎
            map2 = new HashMap<>();
            for (int i = 0; i < split.length - 5; i++) {
                int vv = Integer.parseInt(split[i]);
                int aa = Integer.parseInt(split[split.length - 1 - i]);

                if (vv > aa) {
                    map2.put("tiger" + i, "虎");
                } else {
                    map2.put("dragonand" + i, "龙");
                }
            }
            listDragonandTiger.add(map2);
        }


        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        result.put("total", total);


        result.put("bjpksLotterySgs", bjpksLotterySgs);
        result.put("listCrown", listCrown);
        result.put("listDragonandTiger", listDragonandTiger);

        return ResultInfo.ok(result);
    }


    /**
     * 资讯：冠亚和统计
     *
     * @param pageSize
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getGuanYaCount(Integer pageSize) {
        Map<String, Object> result = new HashMap<>();

        //根据条件查询
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        example.setOrderByClause("`time` DESC");
        example.setOffset(0);
        example.setLimit(pageSize);
        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bjpksLotterySgs)) {
            result.put("listBjpksLotterySgs", bjpksLotterySgs);
            return ResultInfo.ok(result);
        }

        //存放冠亚和走势的list
        List<BjpksGuanyaHeDTO> listBJPKSGH = new ArrayList<>();
        int singleCount = 0;
        int twoCount = 0;
        int bigCount = 0;
        int smallCount = 0;

        //单双、大小总数
        int singleTotal = 0;
        int twoCountTotal = 0;
        int bigCountTotal = 0;
        int smallCountTotal = 0;

        BjpksGuanyaHeDTO dtoCount;

        //冠亚和走势
        for (int i = bjpksLotterySgs.size() - 1; i >= 0; i--) {
            dtoCount = new BjpksGuanyaHeDTO();
            BjpksLotterySg bjpks = bjpksLotterySgs.get(i);
            dtoCount.setIssue(bjpks.getIssue());
            String[] number = bjpks.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);
            //存冠亚和
            dtoCount.setGuanYaHe(guanYahe);

            //计算单双、大小、遗漏
            if (guanYahe % 2 == 0 && guanYahe > 11) {//和值为双 并且大于11  单和小分别加一，其他两个初始为1
                singleCount++;
                smallCount++;
                twoCount = 0;
                bigCount = 0;
                twoCountTotal++;
                bigCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe > 11) {
                twoCount++;
                smallCount++;
                singleCount = 0;
                bigCount = 0;
                singleTotal++;
                bigCountTotal++;
            } else if (guanYahe % 2 == 0 && guanYahe <= 11) {
                singleCount++;
                bigCount++;
                twoCount = 0;
                smallCount = 0;
                twoCountTotal++;
                smallCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe <= 11) {
                twoCount++;
                bigCount++;
                singleCount = 0;
                smallCount = 0;
                singleTotal++;
                smallCountTotal++;
            }


            dtoCount.setSmall(smallCount);
            dtoCount.setBig(bigCount);
            dtoCount.setSingle(singleCount);
            dtoCount.setTwo(twoCount);


            //统计遗漏值
            dtoCount.setNumber3(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 3));
            dtoCount.setNumber4(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 4));
            dtoCount.setNumber5(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 5));
            dtoCount.setNumber6(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 6));
            dtoCount.setNumber7(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 7));
            dtoCount.setNumber8(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 8));
            dtoCount.setNumber9(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 9));
            dtoCount.setNumber10(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 10));
            dtoCount.setNumber11(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 11));
            dtoCount.setNumber12(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 12));
            dtoCount.setNumber13(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 13));
            dtoCount.setNumber14(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 14));
            dtoCount.setNumber15(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 15));
            dtoCount.setNumber16(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 16));
            dtoCount.setNumber17(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 17));
            dtoCount.setNumber18(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 18));
            dtoCount.setNumber19(BjpksUtils.getBjpksMissingCount(i, bjpksLotterySgs, 19));

            listBJPKSGH.add(dtoCount);


        }

        Collections.reverse(listBJPKSGH);//倒倒序排列

        //存放冠亚和的list
        List<BjpksGuanyaHeDTO> statisticsGuanyaHe = new ArrayList<>();

        /**
         * 统计【出现总次数】
         */
        BjpksGuanyaHeDTO sumOpentCount = new BjpksGuanyaHeDTO();
        sumOpentCount.setIssue("出现总次数");
        sumOpentCount.setNumber3(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 3));
        sumOpentCount.setNumber4(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 4));
        sumOpentCount.setNumber5(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 5));
        sumOpentCount.setNumber6(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 6));
        sumOpentCount.setNumber7(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 7));
        sumOpentCount.setNumber8(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 8));
        sumOpentCount.setNumber9(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 9));
        sumOpentCount.setNumber10(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 10));
        sumOpentCount.setNumber11(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 11));
        sumOpentCount.setNumber12(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 12));
        sumOpentCount.setNumber13(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 13));
        sumOpentCount.setNumber14(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 14));
        sumOpentCount.setNumber15(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 15));
        sumOpentCount.setNumber16(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 16));
        sumOpentCount.setNumber17(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 17));
        sumOpentCount.setNumber18(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 18));
        sumOpentCount.setNumber19(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 19));
        sumOpentCount.setSingleTotal(singleTotal);
        sumOpentCount.setBigCountTotal(bigCountTotal);
        sumOpentCount.setSmallCountTotal(smallCountTotal);
        sumOpentCount.setTwoCountTotal(twoCountTotal);


        /**
         * 统计【最大遗漏值】
         */
        int max0 = 0, max1 = 0, max2 = 0, max3 = 0, max4 = 0, max5 = 0, max6 = 0, max7 = 0, max8 = 0, max9 = 0, max10 = 0, max11 = 0, max12 = 0, max13 = 0, max14 = 0, max15 = 0, max16 = 0, max17 = 0, max18 = 0, max19 = 0, max20 = 0;

        List<Integer> single1 = new ArrayList<>();//存放所有单
        List<Integer> two1 = new ArrayList<>();//存放所以双
        List<Integer> small1 = new ArrayList<>();//存放所有小
        List<Integer> big1 = new ArrayList<>();//存放所有大
        for (BjpksGuanyaHeDTO bjpk : listBJPKSGH) {
            if (!bjpk.getNumber3().equals(bjpk.getGuanYaHe()) && bjpk.getNumber3() > max0) {
                max0 = bjpk.getNumber3();
            }
            if (!bjpk.getNumber4().equals(bjpk.getGuanYaHe()) && bjpk.getNumber4() > max1) {
                max1 = bjpk.getNumber4();
            }
            if (!bjpk.getNumber5().equals(bjpk.getGuanYaHe()) && bjpk.getNumber5() > max2) {
                max2 = bjpk.getNumber5();
            }
            if (!bjpk.getNumber6().equals(bjpk.getGuanYaHe()) && bjpk.getNumber6() > max3) {
                max3 = bjpk.getNumber6();
            }
            if (!bjpk.getNumber7().equals(bjpk.getGuanYaHe()) && bjpk.getNumber7() > max4) {
                max4 = bjpk.getNumber7();
            }
            if (!bjpk.getNumber8().equals(bjpk.getGuanYaHe()) && bjpk.getNumber8() > max5) {
                max5 = bjpk.getNumber8();
            }
            if (!bjpk.getNumber9().equals(bjpk.getGuanYaHe()) && bjpk.getNumber9() > max6) {
                max6 = bjpk.getNumber9();
            }
            if (!bjpk.getNumber10().equals(bjpk.getGuanYaHe()) && bjpk.getNumber10() > max7) {
                max7 = bjpk.getNumber10();
            }
            if (!bjpk.getNumber11().equals(bjpk.getGuanYaHe()) && bjpk.getNumber11() > max8) {
                max8 = bjpk.getNumber11();
            }
            if (!bjpk.getNumber12().equals(bjpk.getGuanYaHe()) && bjpk.getNumber12() > max9) {
                max9 = bjpk.getNumber12();
            }
            if (!bjpk.getNumber13().equals(bjpk.getGuanYaHe()) && bjpk.getNumber13() > max10) {
                max10 = bjpk.getNumber13();
            }
            if (!bjpk.getNumber14().equals(bjpk.getGuanYaHe()) && bjpk.getNumber14() > max11) {
                max11 = bjpk.getNumber14();
            }
            if (!bjpk.getNumber15().equals(bjpk.getGuanYaHe()) && bjpk.getNumber15() > max12) {
                max12 = bjpk.getNumber15();
            }
            if (!bjpk.getNumber16().equals(bjpk.getGuanYaHe()) && bjpk.getNumber16() > max13) {
                max13 = bjpk.getNumber16();
            }
            if (!bjpk.getNumber17().equals(bjpk.getGuanYaHe()) && bjpk.getNumber17() > max14) {
                max14 = bjpk.getNumber17();
            }
            if (!bjpk.getNumber18().equals(bjpk.getGuanYaHe()) && bjpk.getNumber18() > max15) {
                max15 = bjpk.getNumber18();
            }
            if (!bjpk.getNumber19().equals(bjpk.getGuanYaHe()) && bjpk.getNumber19() > max16) {
                max16 = bjpk.getNumber19();
            }

            single1.add(bjpk.getSingle());
            two1.add(bjpk.getTwo());
            small1.add(bjpk.getSmall());
            big1.add(bjpk.getSmall());

        }


        BjpksGuanyaHeDTO guanYaHeMissinMax = new BjpksGuanyaHeDTO();
        guanYaHeMissinMax.setIssue("最大遗漏值 ");
        guanYaHeMissinMax.setNumber3(max0);
        guanYaHeMissinMax.setNumber4(max1);
        guanYaHeMissinMax.setNumber5(max2);
        guanYaHeMissinMax.setNumber6(max3);
        guanYaHeMissinMax.setNumber7(max4);
        guanYaHeMissinMax.setNumber8(max5);
        guanYaHeMissinMax.setNumber9(max6);
        guanYaHeMissinMax.setNumber10(max7);
        guanYaHeMissinMax.setNumber11(max8);
        guanYaHeMissinMax.setNumber12(max9);
        guanYaHeMissinMax.setNumber13(max10);
        guanYaHeMissinMax.setNumber14(max11);
        guanYaHeMissinMax.setNumber15(max12);
        guanYaHeMissinMax.setNumber16(max13);
        guanYaHeMissinMax.setNumber17(max14);
        guanYaHeMissinMax.setNumber18(max15);
        guanYaHeMissinMax.setNumber19(max16);
        guanYaHeMissinMax.setBigCountTotal(Collections.max(big1));//最大遗漏值--大
        guanYaHeMissinMax.setTwoCountTotal(Collections.max(two1));//最大遗漏值--双
        guanYaHeMissinMax.setSmallCountTotal(Collections.max(small1));//最大遗漏值--小
        guanYaHeMissinMax.setSingleTotal(Collections.max(single1));//最大遗漏值--单


        //开出的总次数
        statisticsGuanyaHe.add(sumOpentCount);
        //最大遗漏值
        statisticsGuanyaHe.add(guanYaHeMissinMax);


        result.put("statisticsGuanyaHe", statisticsGuanyaHe);//冠亚和
        result.put("listBJPKSGH", listBJPKSGH);//冠亚和走势list

        return ResultInfo.ok(result);
    }


    /**
     * web首页：今日统计
     *
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getBjpksTodayStatistics() {
        Map<String, Object> result = new HashMap<>();
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andNumberIsNotNull();
        criteria.andTimeLike(TimeHelper.date("yyyy-MM-dd") + "%");
        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(bjpksLotterySgs)) {
            result.put("bjpksLotterySgs", bjpksLotterySgs);
            return ResultInfo.ok(result);
        }

        List<BjpksGuanyaHeDTO> guanyaheDTO = new ArrayList<>();


        //统计没有出的单双、大小
        int singleNoOut = 0;
        int twoCountNoOut = 0;
        int bigCountNoOut = 0;
        int smallCountNoOut = 0;

        //单双、大小总数
        int singleTotal = 0;
        int twoCountTotal = 0;
        int bigCountTotal = 0;
        int smallCountTotal = 0;

        BjpksGuanyaHeDTO dtoCount;
        //冠亚和走势
        for (int i = 0; i < bjpksLotterySgs.size(); i++) {
            dtoCount = new BjpksGuanyaHeDTO();
            BjpksLotterySg bjpks = bjpksLotterySgs.get(i);
            dtoCount.setIssue(bjpks.getIssue());
            String[] number = bjpks.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);
            //存冠亚和
            dtoCount.setGuanYaHe(guanYahe);

            //计算单双、大小、遗漏
            if (guanYahe % 2 == 0 && guanYahe > 11) {//和值为双 并且大于11
                twoCountTotal++;
                bigCountTotal++;
                singleNoOut++;
                smallCountNoOut++;
            } else if (guanYahe % 2 != 0 && guanYahe > 11) {
                singleTotal++;
                bigCountTotal++;
                twoCountNoOut++;
                smallCountNoOut++;
            } else if (guanYahe % 2 == 0 && guanYahe <= 11) {
                twoCountTotal++;
                smallCountTotal++;
                singleNoOut++;
                bigCountNoOut++;
            } else if (guanYahe % 2 != 0 && guanYahe <= 11) {
                singleTotal++;
                smallCountTotal++;
                twoCountNoOut++;
                bigCountNoOut++;
            }

        }


        List<BjpksGuanyaHeDTO> listTotal = new ArrayList<>();
        /**
         * 统计【冠亚和出现总次数】
         */
        BjpksGuanyaHeDTO sumOpentCount = new BjpksGuanyaHeDTO();
        sumOpentCount.setIssue("出现总次数");
        sumOpentCount.setNumber3(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 3));
        sumOpentCount.setNumber4(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 4));
        sumOpentCount.setNumber5(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 5));
        sumOpentCount.setNumber6(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 6));
        sumOpentCount.setNumber7(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 7));
        sumOpentCount.setNumber8(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 8));
        sumOpentCount.setNumber9(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 9));
        sumOpentCount.setNumber10(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 10));
        sumOpentCount.setNumber11(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 11));
        sumOpentCount.setNumber12(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 12));
        sumOpentCount.setNumber13(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 13));
        sumOpentCount.setNumber14(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 14));
        sumOpentCount.setNumber15(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 15));
        sumOpentCount.setNumber16(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 16));
        sumOpentCount.setNumber17(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 17));
        sumOpentCount.setNumber18(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 18));
        sumOpentCount.setNumber19(BjpksUtils.getSumOpenCount(bjpksLotterySgs, 19));
        sumOpentCount.setSingleTotal(singleTotal); //单出现的总次数
        sumOpentCount.setBigCountTotal(bigCountTotal);//大出现的总次数
        sumOpentCount.setSmallCountTotal(smallCountTotal);//小出现的总次数
        sumOpentCount.setTwoCountTotal(twoCountTotal);//双出现的总次数
        sumOpentCount.setIssueCount(bjpksLotterySgs.size());//双出现的总次数
        listTotal.add(sumOpentCount);


        List<BjpksGuanyaHeDTO> listNoOutTotal = new ArrayList<>();
        /**
         * 统计【冠亚和出现总次数】
         */
        BjpksGuanyaHeDTO noOutOpentCount = new BjpksGuanyaHeDTO();
        noOutOpentCount.setIssue("遗漏的总次数");
        noOutOpentCount.setNumber3(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 3));
        noOutOpentCount.setNumber4(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 4));
        noOutOpentCount.setNumber5(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 5));
        noOutOpentCount.setNumber6(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 6));
        noOutOpentCount.setNumber7(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 7));
        noOutOpentCount.setNumber8(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 8));
        noOutOpentCount.setNumber9(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 9));
        noOutOpentCount.setNumber10(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 10));
        noOutOpentCount.setNumber11(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 11));
        noOutOpentCount.setNumber12(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 12));
        noOutOpentCount.setNumber13(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 13));
        noOutOpentCount.setNumber14(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 14));
        noOutOpentCount.setNumber15(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 15));
        noOutOpentCount.setNumber16(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 16));
        noOutOpentCount.setNumber17(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 17));
        noOutOpentCount.setNumber18(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 18));
        noOutOpentCount.setNumber19(BjpksUtils.getNoOutOpenCount(bjpksLotterySgs, 19));
        noOutOpentCount.setSingleTotal(singleNoOut); //单遗漏的总次数
        noOutOpentCount.setBigCountTotal(bigCountNoOut);//大遗漏的总次数
        noOutOpentCount.setSmallCountTotal(smallCountNoOut);//小遗漏的总次数
        noOutOpentCount.setTwoCountTotal(twoCountNoOut);//双遗漏的总次数
        noOutOpentCount.setIssueCount(bjpksLotterySgs.size());//双遗漏的总次数
        listNoOutTotal.add(noOutOpentCount);

        result.put("listTotal", listTotal);
        result.put("listNoOutTotal", listNoOutTotal);
        return ResultInfo.ok(result);
    }


    /**
     * web首页彩票北京PK10今日历史开奖
     *
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getBjpksTodayLotteryHistory() {
        Map<String, Object> result = new HashMap<>();
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        example.setOffset(0);
        example.setLimit(10);
        example.setOrderByClause("`time` DESC");
        List<BjpksLotterySg> bjpksRecommends = bjpksLotterySgMapper.selectByExample(example);
        result.put("bjpksTodayLotteryHistory", bjpksRecommends);
        return ResultInfo.ok(result);
    }

    @Override
    public BjpksLotterySg queryBjpksLastSg() {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        example.setOrderByClause("`time` DESC");
        return bjpksLotterySgMapper.selectOneByExample(example);
    }


    /**
     * web端pk10开奖资讯详情
     *
     * @param pageNo  第几页
     * @param pageSize 每页条数
     * @param date     日期（选填）
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> bjpksTodayAndHistoryNews(Integer pageNo, Integer pageSize, String date) {
        Map<String, Object> result = new HashMap<>();
        List<BjpksHistoryDTO> lsitBj = new ArrayList<>();


        Date nowTime = new Date();
        if (StringUtils.isEmpty(date)) {
            date = DateUtils.formatDate(nowTime, DateUtils.FORMAT_YYYY_MM_DD);
        }
        result.put("totalTime", date);
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andNumberIsNotNull();
        criteria.andTimeLike(date + "%");
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`time` ASC");
        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);

        BjpksHistoryDTO dtoCount;
        for (BjpksLotterySg list : bjpksLotterySgs) {
            dtoCount = new BjpksHistoryDTO();

            dtoCount.setIssue(list.getIssue());
            dtoCount.setNumber(list.getNumber());
            String[] number = list.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);
            dtoCount.setGuanYaHe(guanYahe);

            //计算单双、大小、次数
            if (guanYahe % 2 == 0 && guanYahe > 11) {
                dtoCount.setBigOrSmall("大");
                dtoCount.setSingleOrTwo("双");
            } else if (guanYahe % 2 != 0 && guanYahe > 11) {
                dtoCount.setBigOrSmall("大");
                dtoCount.setSingleOrTwo("单");
            } else if (guanYahe % 2 == 0 && guanYahe <= 11) {
                dtoCount.setBigOrSmall("小");
                dtoCount.setSingleOrTwo("双");
            } else if (guanYahe % 2 != 0 && guanYahe <= 11) {
                dtoCount.setBigOrSmall("小");
                dtoCount.setSingleOrTwo("单");
            }
            lsitBj.add(dtoCount);
        }


        Map<String, Object> stringObjectMap = this.queryTotal(date);

        result.put("listBjpks", lsitBj);
        result.put("stringObjectMap", stringObjectMap);
        result.put("issueCount", bjpksLotterySgs.size());

        return ResultInfo.ok(result);
    }


    /**
     * 统计大小单双，3-19出现的总次数
     *
     * @param date
     * @return
     */
    @Override
    public Map<String, Object> queryTotal(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();

        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andTimeLike(date + "%");
        criteria.andNumberIsNotNull();
        List<BjpksLotterySg> bjpksLotterySgs = bjpksLotterySgMapper.selectByExample(example);

        int singleTotal = 0;
        int twoCountTotal = 0;
        int bigCountTotal = 0;
        int smallCountTotal = 0;
        int[] openArr = new int[17]; //统计出现次数
        for (BjpksLotterySg list : bjpksLotterySgs) {
            String[] number = list.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);

            //统计出现次数
            openArr[guanYahe - 3] += 1;

            //计算单双、大小、次数
            if (guanYahe % 2 == 0 && guanYahe > 11) {
                bigCountTotal++;
                twoCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe > 11) {
                bigCountTotal++;
                singleTotal++;
            } else if (guanYahe % 2 == 0 && guanYahe <= 11) {
                smallCountTotal++;
                twoCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe <= 11) {
                smallCountTotal++;
                singleTotal++;
            }

        }
        BjpksGuanYaHeCountDTO heCount = new BjpksGuanYaHeCountDTO();
        heCount.setBig(bigCountTotal);
        heCount.setSmall(smallCountTotal);
        heCount.setSingle(singleTotal);
        heCount.setTwo(twoCountTotal);

        result.put("total", heCount);
        result.put("openArr", openArr);
        return result;
    }

    /* (non Javadoc)
     * @Title: getBjPksSgLong
     * @Description: 获取北京PK10长龙
     * @return
     * @see com.caipiao.live.read.issue.service.result.BjpksLotterySgService#getBjPksSgLong()
     */
    @Override
    public List<Map<String, Object>> getBjPksSgLong() {
        List<Map<String, Object>> bjpksLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.BJPKS_ALGORITHM_VALUE;
            List<BjpksLotterySg> bjpksLotterySgList = (List<BjpksLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(bjpksLotterySgList)) {
                bjpksLotterySgList = this.selectOpenIssueList();
                redisTemplate.opsForValue().set(algorithm, bjpksLotterySgList, 10, TimeUnit.SECONDS);
            }
            // 大小长龙
            List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(bjpksLotterySgList);
            bjpksLongMapList.addAll(bigAndSmallLongList);
            // 单双长龙
            List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(bjpksLotterySgList);
            bjpksLongMapList.addAll(sigleAndDoubleLongList);
            // 龙虎长龙
            List<Map<String, Object>> dragonAndTigleLongList = this.getTrigleAndDragonLong(bjpksLotterySgList);
            bjpksLongMapList.addAll(dragonAndTigleLongList);
            bjpksLongMapList = this.addNextIssueInfo(bjpksLongMapList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#BjpksLotterySgServiceImpl_getJspksSgLong_error:", e);
        }
        return bjpksLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: 返回
     * @author HANS
     * @date 2019年5月26日下午2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> bjpksLongMapList) {
        List<Map<String, Object>> bjpksResultLongMapList = new ArrayList<Map<String, Object>>();

        if (!CollectionUtils.isEmpty(bjpksLongMapList)) {
            // 获取缓存中下期数据
            String nextRedisKey = RedisKeys.BJPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.BJPKS.getRedisTime();
            BjpksLotterySg nextBjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextBjpksLotterySg == null) {
                nextBjpksLotterySg = this.getBjpksSg();
                redisTemplate.opsForValue().set(nextRedisKey, nextBjpksLotterySg, 10, TimeUnit.SECONDS);
            }

            if (nextBjpksLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.BJPKS_RESULT_VALUE;
            BjpksLotterySg bjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (bjpksLotterySg == null) {
                // 查询最近一期信息
                bjpksLotterySg = this.getBjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, bjpksLotterySg);
            }
            String nextIssue = nextBjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextBjpksLotterySg.getIssue();
            String txffnextIssue = bjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // 如果长龙期数与下期期数相差太大长龙不存在
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }

            for (Map<String, Object> longMap : bjpksLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextBjpksLotterySg.getIssue());
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextBjpksLotterySg.getIdealTime()) / 1000L);
                bjpksResultLongMapList.add(longMap);
            }
        }
        return bjpksResultLongMapList;
    }

    /**
     * @Title: getBigAndSmallLong
     * @Description: 获取大小的长龙数据
     * @author HANS
     * @date 2019年5月18日下午6:46:37
     */
    public List<Map<String, Object>> getBigAndSmallLong(List<BjpksLotterySg> bjpksLotterySgList) {
        List<Map<String, Object>> bjpksBigLongMapList = new ArrayList<Map<String, Object>>();
        //冠亚和大小
        Map<String, Object> gyhDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagType());
        //冠军大小
        Map<String, Object> gjbDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJBIG.getTagType());
        //亚军大小
        Map<String, Object> yjbDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJBIG.getTagType());
        //第三名大小
        Map<String, Object> dsmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMBIG.getTagType());
        //第四名大小
        Map<String, Object> dfmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMBIG.getTagType());
        //第五名大小
        Map<String, Object> dwmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMBIG.getTagType());
        //第六名大小
        Map<String, Object> dlmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDLMBIG.getTagType());
        //第七名大小
        Map<String, Object> dqmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDQMBIG.getTagType());
        //第八名大小
        Map<String, Object> dmmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDMMBIG.getTagType());
        //第九名大小
        Map<String, Object> djmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDJMBIG.getTagType());
        //第十名大小
        Map<String, Object> dtmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDTMBIG.getTagType());

        // 计算后的数据放入返回集合
        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(gyhDragonMap);
        }

        if (gjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(gjbDragonMap);
        }

        if (yjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(yjbDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksBigLongMapList.add(dtmDragonMap);
        }
        return bjpksBigLongMapList;
    }

    /**
     * @Title: getDoubleAndSingleLong
     * @Description: 获取单双长龙的数据
     * @author HANS
     * @date 2019年5月18日下午6:47:11
     */
    public List<Map<String, Object>> getDoubleAndSingleLong(List<BjpksLotterySg> bjpksLotterySgList) {
        List<Map<String, Object>> bjpksDoubleLongMapList = new ArrayList<Map<String, Object>>();
        //冠亚和单双
        Map<String, Object> gyhDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGYHDOUBLE.getTagType());
        //冠军单双
        Map<String, Object> gjDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJDOUBLE.getTagType());
        //亚军单双
        Map<String, Object> syjDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJDOUBLE.getTagType());
        //第三名单双
        Map<String, Object> dsmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMDOUBLE.getTagType());
        //第四名单双
        Map<String, Object> dfmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMDOUBLE.getTagType());
        //第五名单双
        Map<String, Object> dwmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMDOUBLE.getTagType());
        //第六名单双
        Map<String, Object> dlmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDLMDOUBLE.getTagType());
        //第七名单双
        Map<String, Object> dqmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDQMDOUBLE.getTagType());
        //第八名单双
        Map<String, Object> dmmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDMMDOUBLE.getTagType());
        //第九名单双
        Map<String, Object> djmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDJMDOUBLE.getTagType());
        //第十名单双
        Map<String, Object> dtmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDTMDOUBLE.getTagType());

        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(gyhDragonMap);
        }

        if (gjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(gjDragonMap);
        }

        if (syjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(syjDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksDoubleLongMapList.add(dtmDragonMap);
        }
        return bjpksDoubleLongMapList;
    }

    /**
     * @Title: getTrigleAndDragonLong
     * @Description: 获取龙虎长龙的数据
     * @author HANS
     * @date 2019年5月18日下午6:47:50
     */
    public List<Map<String, Object>> getTrigleAndDragonLong(List<BjpksLotterySg> bjpksLotterySgList) {
        List<Map<String, Object>> bjpksTrigLongMapList = new ArrayList<Map<String, Object>>();
        //冠军龙虎
        Map<String, Object> gjtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJTIDRAGON.getTagType());
        //亚军龙虎
        Map<String, Object> yjtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJTIDRAGON.getTagType());
        //第三名龙虎
        Map<String, Object> dsmtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMTIDRAGON.getTagType());
        //第四名龙虎
        Map<String, Object> dfmtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMTIDRAGON.getTagType());
        //第五名龙虎
        Map<String, Object> dwmtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMTIDRAGON.getTagType());

        if (gjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksTrigLongMapList.add(gjtiDragonMap);
        }

        if (yjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksTrigLongMapList.add(yjtiDragonMap);
        }

        if (dsmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksTrigLongMapList.add(dsmtiDragonMap);
        }

        if (dfmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksTrigLongMapList.add(dfmtiDragonMap);
        }

        if (dwmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            bjpksTrigLongMapList.add(dwmtiDragonMap);
        }
        return bjpksTrigLongMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: 公共方法，获取长龙数据
     * @author HANS
     * @date 2019年5月18日下午7:04:47
     */
    public Map<String, Object> getDragonInfo(List<BjpksLotterySg> bjpksLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(bjpksLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < bjpksLotterySgList.size(); index++) {
                    BjpksLotterySg bjpksLotterySg = bjpksLotterySgList.get(index);
                    String numberString = bjpksLotterySg.getNumber();
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateResult(type, numberString);

                    if (StringUtils.isEmpty(bigOrSmallName)) {
                        break;
                    }
                    // 把第一个结果加入SET集合
                    if (index == Constants.DEFAULT_INTEGER) {
                        dragonSet.add(bigOrSmallName);
                    }
                    // 如果第一个和第二个开奖结果不一样，统计截止
                    if (index == Constants.DEFAULT_ONE) {
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了不再统计
                            break;
                        }
                        continue;
                    }
                    // 规则：连续3个开奖一样
                    if (index == Constants.DEFAULT_TWO) {
                        // 第三个数据
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了不再统计
                            break;
                        }
                        dragonSize = Constants.DEFAULT_THREE;
                        continue;
                    }
                    // 如果大于3个以上，继续统计，直到结果不一样
                    if (index > Constants.DEFAULT_TWO) {
                        // 大/小统计
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了不再统计
                            break;
                        }
                        dragonSize++;
                    }
                }
                // 最近一期开奖数据
                BjpksLotterySg bjpksLotterySg = bjpksLotterySgList.get(Constants.DEFAULT_INTEGER);
                // 组织返回数据
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, bjpksLotterySg, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TenpksLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: 返回前端数据
     * @author HANS
     * @date 2019年5月18日下午7:11:28
     */
    public Map<String, Object> organizationDragonResultMap(int type, BjpksLotterySg bjpksLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // 有龙情况下查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // 获取德州PK10    两面 赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.BJPKS.getTagCnName(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getPlayName(),
                    CaipiaoTypeEnum.BJPKS.getTagType(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagType() + "");

            List<String> dragonList = new ArrayList<String>(dragonSet);
            // 玩法赔率
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 174) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGBIG);
            } else if (type == 175) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 176) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 177) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 178) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 179) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 180) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDLMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDLMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDLMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 181) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDQMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDQMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDQMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 182) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDMMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDMMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDMMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 183) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDJMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDJMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDJMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 184) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDTMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDTMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDTMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 185) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGYHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGYHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGYHDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGDOUBLE);
            } else if (type == 186) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 187) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 188) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 189) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 190) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 191) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDLMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDLMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDLMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 192) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDQMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDQMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDQMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 193) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDMMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDMMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDMMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 194) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDJMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDJMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDJMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 195) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDTMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDTMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDTMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 196) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSGJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 197) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSYJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 198) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDSMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 199) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDFMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 200) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.BJPKSDWMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            }
            // 把获取的赔率加入到返回MAP中
            longDragonMap.putAll(oddsListMap);
            // 加入其它返回值
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.BJPKS.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.BJPKS.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#BjpksLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: 按照玩法计算结果
     * @author HANS
     * @date 2019年5月18日上午11:22:00
     */
    public String calculateResult(int type, String numberString) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 174:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠亚和大小
                break;
            case 175:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠军大小
                break;
            case 176:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//亚军大小
                break;
            case 177:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第三名大小
                break;
            case 178:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第四名大小
                break;
            case 179:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第五名大小
                break;
            case 180:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第六名大小
                break;
            case 181:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第七名大小
                break;
            case 182:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第八名大小
                break;
            case 183:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第九名大小
                break;
            case 184:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第十名大小
                break;
            case 185:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠亚和单双
                break;
            case 186:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠军单双
                break;
            case 187:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//亚军单双
                break;
            case 188:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第三名单双
                break;
            case 189:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第四名单双
                break;
            case 190:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第五名单双
                break;
            case 191:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第六名单双
                break;
            case 192:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第七名单双
                break;
            case 193:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第八名单双
                break;
            case 194:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第九名单双
                break;
            case 195:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第十名单双
                break;
            case 196:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//冠军龙虎
                break;
            case 197:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//亚军龙虎
                break;
            case 198:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第三名龙虎
                break;
            case 199:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第四名龙虎
                break;
            case 200:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第五名龙虎
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: selectOpenIssueList
     * @Description: 获取
     * @author HANS
     * @date 2019年5月18日下午4:56:25
     */
    public List<BjpksLotterySg> selectOpenIssueList() {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        return bjpksLotterySgMapper.selectByExample(example);
    }
}
