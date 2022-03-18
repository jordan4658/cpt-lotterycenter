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
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.XyftLotterySg;
import com.caipiao.live.common.mybatis.entity.XyftLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.XyftLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperbean.XyftBeanMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.XyftLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JspksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.XyftLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.JspksSgUtils;
import com.caipiao.live.common.util.lottery.NextIssueTimeUtil;
import com.caipiao.live.common.util.lottery.XyftUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 幸运飞艇
 *
 * @author lzy
 * @create 2018-07-30 16:52
 **/
@Service
public class XyftLotteryReadSgServiceImpl implements XyftLotterySgServiceReadSg {

    private static final Logger logger = LoggerFactory.getLogger(XyftLotteryReadSgServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
    @Autowired
    private XyftLotterySgMapperExt xyftLotterySgMapperExt;
    @Autowired
    private XyftBeanMapper xyftBeanMapper;
    @Autowired
    private JspksLotterySgServiceReadSg jspksLotterySgService;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;

    /**
     * 幸运飞艇今日号码
     *
     * @param type 唯一标识
     * @return
     */
    @Override
    public ResultInfo<List<MapListVO>> todayNumber(String type) {
        List<MapListVO> voList = new ArrayList<>();

        // 校验参数
        if (!LotteryInformationType.BJPKS_JRHM.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        // 获取赛果
        String date = XyftUtils.dateStr();
        List<String> sgList = xyftBeanMapper.selectNumberByDate(date + "%");
        if (CollectionUtils.isEmpty(sgList)) {
            ResultInfo.ok(voList);
        }

        // 今日号码统计
        voList = XyftUtils.todayNumber(sgList);
        return ResultInfo.ok(voList);
    }


//    @Override
//    public ResultInfo<Map<String, ThereMemberListVO>> luzhuG(String type, String date) {
//        if (StringUtils.isBlank(date) || date.equals(TimeHelper.date("yyyy-MM-dd"))) {
//            date = XyftUtils.dateStr();
//        }
//
//        //北京pk10冠军和路珠
//        if (LotteryInformationType.BJPKS_GJLZ.equals(type)) {
//            List<String> sg = this.xyftBeanMapper.selectNumberByDate(date.replace("-", "") + "%");
//            if (sg == null) {
//                sg = new ArrayList<>(0);
//            }
//
//            Map<String, ThereMemberListVO> result = XyftUtils.luzhuG(sg);
//            return ResultInfo.ok(result);
//        }
//
//        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
//    }

//    @Override
//    public ResultInfo<Map<String, ThereMemberListVO>> luzhuQ(String type, String date) {
//        if (StringUtils.isBlank(date) || date.equals(TimeHelper.date("yyyy-MM-dd"))) {
//            date = XyftUtils.dateStr();
//        }
//        List<String> sg = this.xyftBeanMapper.selectNumberByDate(date.replace("-", "") + "%");
//        if (sg == null) {
//            sg = new ArrayList<>(0);
//        }
//
//        //北京pk10前后路珠
//        if (LotteryInformationType.BJPKS_QHLZ.equals(type)) {
//            Map<String, ThereMemberListVO> result = XyftUtils.luzhuQ(sg);
//            return ResultInfo.ok(result);
//        }
//
//        //北京PK10两面路珠之大小, 之单双, 之龙虎
//        if (LotteryInformationType.BJPKS_LMLZ_DX.equals(type) || LotteryInformationType.BJPKS_LMLZ_DS.equals(type) || LotteryInformationType.BJPKS_LMLZ_LH.equals(type)) {
//            Map<String, ThereMemberListVO> result = XyftUtils.luzhuLiangMian(sg, type);
//            return ResultInfo.ok(result);
//        }
//
//        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
//    }

    @Override
    public ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNo, Integer pageSize) {
        // 幸运飞艇历史开奖
        if (!LotteryInformationType.BJPKS_LSKJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andNumberIsNotNull();
        if (StringUtils.isNotBlank(date) && date.equals(TimeHelper.date("yyyy-MM-dd"))) {
            date = XyftUtils.dateStr();
        }

        if (StringUtils.isNotBlank(date)) {
            criteria.andIssueLike(date.replace("-", "") + "%");
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`ideal_time` DESC");
        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(xyftLotterySgs)) {
            xyftLotterySgs = new ArrayList<>();
        }
        List<KjlsVO> result = XyftUtils.historySg(xyftLotterySgs);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria sgCriteria = example.createCriteria();
//        sgCriteria.andNumberIsNotNull();
//        criteria.andWanIsNotNull();
        sgCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//        sgCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`ideal_time` DESC");

        List<XyftLotterySg> xyftLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.XYFT_SG_HS_LIST)) {
            XyftLotterySgExample exampleOne = new XyftLotterySgExample();
            XyftLotterySgExample.Criteria cqsscCriteriaOne = exampleOne.createCriteria();
            cqsscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<XyftLotterySg> xyftLotterySgsOne = xyftLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.XYFT_SG_HS_LIST, xyftLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            xyftLotterySgs = redisTemplate.opsForList().range(RedisKeys.XYFT_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);
        }

//        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = XyftUtils.lishiSg(xyftLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

//    @Override
//    public ResultInfo<List<MapListVO>> numNoOpen() {
//        String date = XyftUtils.dateStr();
//        List<String> sgs = this.xyftBeanMapper.selectNumberByDateDesc(date + "%");
//        List<MapListVO> listVOs = XyftUtils.numNoOpen(sgs);
//        return ResultInfo.ok(listVOs);
//    }

//    @Override
//    public ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount() {
//        String date = XyftUtils.dateStr();
//        List<String> sgs = this.xyftBeanMapper.selectNumberByDate(date + "%");
//
//        //北京PK10冠亚和统计
//        Map<String, List<ThereIntegerVO>> result = XyftUtils.guanYaCount(sgs);
//        return ResultInfo.ok(result);
//    }

    @Override
    public ResultInfo<List<BjpksSgVO>> getSgTrend(Integer size) {
        if (size == null) {
            size = 40;
        }
        List<BjpksSgVO> xyftLotterySgs = this.xyftBeanMapper.selectLimitDesc(size);
        return ResultInfo.ok(xyftLotterySgs);
    }

    @Override
    public ResultInfo<Map<String, Object>> sgDetails(String sgIssue) {
        if (StringUtils.isBlank(sgIssue)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueEqualTo(sgIssue);
        XyftLotterySg xyftLotterySg = xyftLotterySgMapper.selectOneByExample(example);
        Map<String, Object> map = XyftUtils.sgDetails(xyftLotterySg);
        return ResultInfo.ok(map);
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullPkResult();
        try {
            // 缓存中取开奖数量
            String openRedisKey = RedisKeys.XYFEIT_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("openStatus", LotteryResultStatus.AUTO);
                map.put("paramTime", TimeHelper.date("yyyyMMdd")+"%");
                openCount = xyftLotterySgMapperExt.openCountByExample(map);
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }

            if (openCount != null) {
                result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
                // 获取开奖总期数
                Integer sumCount = CaipiaoSumCountEnum.XYFEIT.getSumCount();
                // 计算当日剩余未开奖次数
                result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            }
            String nextRedisKey = RedisKeys.XYFEIT_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.XYFEIT.getRedisTime();
            XyftLotterySg nextXyftLotterySg = (XyftLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextXyftLotterySg == null) {
                nextXyftLotterySg = this.getNextXyftLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextXyftLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.XYFEIT_RESULT_VALUE;
            XyftLotterySg xyftLotterySg = (XyftLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (xyftLotterySg == null) {
                xyftLotterySg = this.getXyftLotterySg();
                redisTemplate.opsForValue().set(redisKey, xyftLotterySg);
            }

            if (nextXyftLotterySg != null) {
                String nextIssue = nextXyftLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextXyftLotterySg.getIssue();
                String txffnextIssue = xyftLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : xyftLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextXyftLotterySg = this.getNextXyftLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextXyftLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        xyftLotterySg = this.getXyftLotterySg();
                        redisTemplate.opsForValue().set(redisKey, xyftLotterySg);
                    }
                }
                if (xyftLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), xyftLotterySg == null ? Constants.DEFAULT_NULL : xyftLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), xyftLotterySg == null ? Constants.DEFAULT_NULL : xyftLotterySg.getNumber());
                }

                if (nextXyftLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextXyftLotterySg.getIssue());
                    String ideatime = nextXyftLotterySg.getIdealTime();
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(ideatime) / 1000L);
                }
            } else {
                if (xyftLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), xyftLotterySg == null ? Constants.DEFAULT_NULL : xyftLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), xyftLotterySg == null ? Constants.DEFAULT_NULL : xyftLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.XYFEIT.getTagType() + "异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    /**
     * @return XyftLotterySg
     * @Title: getXyftLotterySg
     * @Description: 获取当前开奖数据
     * @author admin
     * @date 2019年5月1日下午4:09:43
     */
    public XyftLotterySg getXyftLotterySg() {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        XyftLotterySg xyftLotterySg = xyftLotterySgMapper.selectOneByExample(example);
        return xyftLotterySg;
    }

    /**
     * @return XyftLotterySg
     * @Title: getXyftLotterySg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年4月29日下午9:30:37
     */
    public XyftLotterySg getNextXyftLotterySg() {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        example.setOrderByClause("ideal_time ASC");
        XyftLotterySg nextXyftLotterySg = xyftLotterySgMapper.selectOneByExample(example);
        return nextXyftLotterySg;
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfoWeb() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "幸运飞艇");

        // 查询最近一期信息
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria sgCriteria = example.createCriteria();
        sgCriteria.andNumberIsNotNull();
        example.setOrderByClause("`ideal_time` DESC");
        XyftLotterySg xyftLotterySg = xyftLotterySgMapper.selectOneByExample(example);
        if (xyftLotterySg != null) {
            result.put("issue", xyftLotterySg.getIssue());
            result.put("number", xyftLotterySg.getNumber());
            result.put("time", xyftLotterySg.getTime());
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
        // 获取当前期期号
        result.put("issue", NextIssueTimeUtil.nextIssueXyft());
        // 获取当前期开奖时间
        result.put("time", NextIssueTimeUtil.nextIssueTimeXyft() / 1000L);
        return ResultInfo.ok(result);
    }


    @Override
    public ResultInfo<Map<String, Object>> getXyftHistoryLottery(Integer pageNo, Integer pageSize, String date) {
        Map<String, Object> result = new HashMap<>();
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andNumberIsNotNull();
        if (StringUtils.isNotBlank(date) && date.equals(TimeHelper.date("yyyy-MM-dd"))) {
            date = XyftUtils.dateStr();
        }
        if (StringUtils.isNotBlank(date)) {
            criteria.andIssueLike(date.replace("-", "") + "%");
        }
        Integer total = xyftLotterySgMapper.countByExample(example);

        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`ideal_time` DESC");
        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);

        //定义list集合存放冠亚和
        List<Map<String, Object>> listCrown = new ArrayList<>();

        //定义list集合存放龙虎
        List<Map<String, Object>> listDragonandTiger = new ArrayList<>();

        //定义存放冠亚和
        Map<String, Object> map;

        //定义存放龙虎map
        Map<String, Object> map2;
        for (XyftLotterySg listBs : xyftLotterySgs) {
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


        result.put("xyftLotterySgs", xyftLotterySgs);
        result.put("listCrown", listCrown);
        result.put("listDragonandTiger", listDragonandTiger);

        return ResultInfo.ok(result);
    }


    /**
     * 咨讯:幸运飞艇冠亚和统计
     *
     * @param pageSize
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getXyftGuanYaCount(Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        XyftLotterySgExample example = new XyftLotterySgExample();
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(pageSize + 10);
        XyftLotterySgExample.Criteria sgCriteria = example.createCriteria();
        sgCriteria.andNumberIsNotNull();
        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);


        if (CollectionUtils.isEmpty(xyftLotterySgs)) {
            result.put("listXyftLotterySgs", xyftLotterySgs);
            return ResultInfo.ok(result);
        }

        //存放冠亚和走势的list
        List<BjpksGuanyaHeDTO> listBJPKSGH = new ArrayList<>();

        //存放冠亚和走势的单双、大小
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
        for (int i = xyftLotterySgs.size() - 1; i >= 0; i--) {
            dtoCount = new BjpksGuanyaHeDTO();
            XyftLotterySg bjpks = xyftLotterySgs.get(i);
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
                singleTotal++;
                smallCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe > 11) {
                twoCount++;
                smallCount++;
                singleCount = 0;
                bigCount = 0;
                twoCountTotal++;
                smallCountTotal++;
            } else if (guanYahe % 2 == 0 && guanYahe <= 11) {
                singleCount++;
                bigCount++;
                twoCount = 0;
                smallCount = 0;
                singleTotal++;
                bigCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe <= 11) {
                twoCount++;
                bigCount++;
                singleCount = 0;
                smallCount = 0;
                twoCountTotal++;
                bigCountTotal++;
            }


            dtoCount.setSmall(smallCount);
            dtoCount.setBig(bigCount);
            dtoCount.setSingle(singleCount);
            dtoCount.setTwo(twoCount);


            //统计遗漏值
            dtoCount.setNumber3(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 3));
            dtoCount.setNumber4(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 4));
            dtoCount.setNumber5(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 5));
            dtoCount.setNumber6(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 6));
            dtoCount.setNumber7(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 7));
            dtoCount.setNumber8(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 8));
            dtoCount.setNumber9(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 9));
            dtoCount.setNumber10(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 10));
            dtoCount.setNumber11(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 11));
            dtoCount.setNumber12(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 12));
            dtoCount.setNumber13(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 13));
            dtoCount.setNumber14(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 14));
            dtoCount.setNumber15(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 15));
            dtoCount.setNumber16(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 16));
            dtoCount.setNumber17(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 17));
            dtoCount.setNumber18(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 18));
            dtoCount.setNumber19(XyftUtils.getXyftMissingCount(i, xyftLotterySgs, 19));

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
        sumOpentCount.setNumber3(XyftUtils.getSumOpenCount(xyftLotterySgs, 3));
        sumOpentCount.setNumber4(XyftUtils.getSumOpenCount(xyftLotterySgs, 4));
        sumOpentCount.setNumber5(XyftUtils.getSumOpenCount(xyftLotterySgs, 5));
        sumOpentCount.setNumber6(XyftUtils.getSumOpenCount(xyftLotterySgs, 6));
        sumOpentCount.setNumber7(XyftUtils.getSumOpenCount(xyftLotterySgs, 7));
        sumOpentCount.setNumber8(XyftUtils.getSumOpenCount(xyftLotterySgs, 8));
        sumOpentCount.setNumber9(XyftUtils.getSumOpenCount(xyftLotterySgs, 9));
        sumOpentCount.setNumber10(XyftUtils.getSumOpenCount(xyftLotterySgs, 10));
        sumOpentCount.setNumber11(XyftUtils.getSumOpenCount(xyftLotterySgs, 11));
        sumOpentCount.setNumber12(XyftUtils.getSumOpenCount(xyftLotterySgs, 12));
        sumOpentCount.setNumber13(XyftUtils.getSumOpenCount(xyftLotterySgs, 13));
        sumOpentCount.setNumber14(XyftUtils.getSumOpenCount(xyftLotterySgs, 14));
        sumOpentCount.setNumber15(XyftUtils.getSumOpenCount(xyftLotterySgs, 15));
        sumOpentCount.setNumber16(XyftUtils.getSumOpenCount(xyftLotterySgs, 16));
        sumOpentCount.setNumber17(XyftUtils.getSumOpenCount(xyftLotterySgs, 17));
        sumOpentCount.setNumber18(XyftUtils.getSumOpenCount(xyftLotterySgs, 18));
        sumOpentCount.setNumber19(XyftUtils.getSumOpenCount(xyftLotterySgs, 19));
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
        result.put("listXyftZs", listBJPKSGH);//冠亚和走势list

        return ResultInfo.ok(result);
    }


    /**
     * 首页：幸运飞艇
     *
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getXyftTodayStatistics() {
        Map<String, Object> result = new HashMap<>();
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLike(XyftUtils.dateStr() + "%");
        criteria.andNumberIsNotNull();
        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(xyftLotterySgs)) {
            result.put("bjpksLotterySgs", xyftLotterySgs);
            return ResultInfo.ok(result);
        }

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
        for (int i = 0; i < xyftLotterySgs.size(); i++) {
            dtoCount = new BjpksGuanyaHeDTO();
            XyftLotterySg xyftLotterySg = xyftLotterySgs.get(i);
            dtoCount.setIssue(xyftLotterySg.getIssue());
            String[] number = xyftLotterySg.getNumber().split(",");
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
        sumOpentCount.setNumber3(XyftUtils.getSumOpenCount(xyftLotterySgs, 3));
        sumOpentCount.setNumber4(XyftUtils.getSumOpenCount(xyftLotterySgs, 4));
        sumOpentCount.setNumber5(XyftUtils.getSumOpenCount(xyftLotterySgs, 5));
        sumOpentCount.setNumber6(XyftUtils.getSumOpenCount(xyftLotterySgs, 6));
        sumOpentCount.setNumber7(XyftUtils.getSumOpenCount(xyftLotterySgs, 7));
        sumOpentCount.setNumber8(XyftUtils.getSumOpenCount(xyftLotterySgs, 8));
        sumOpentCount.setNumber9(XyftUtils.getSumOpenCount(xyftLotterySgs, 9));
        sumOpentCount.setNumber10(XyftUtils.getSumOpenCount(xyftLotterySgs, 10));
        sumOpentCount.setNumber11(XyftUtils.getSumOpenCount(xyftLotterySgs, 11));
        sumOpentCount.setNumber12(XyftUtils.getSumOpenCount(xyftLotterySgs, 12));
        sumOpentCount.setNumber13(XyftUtils.getSumOpenCount(xyftLotterySgs, 13));
        sumOpentCount.setNumber14(XyftUtils.getSumOpenCount(xyftLotterySgs, 14));
        sumOpentCount.setNumber15(XyftUtils.getSumOpenCount(xyftLotterySgs, 15));
        sumOpentCount.setNumber16(XyftUtils.getSumOpenCount(xyftLotterySgs, 16));
        sumOpentCount.setNumber17(XyftUtils.getSumOpenCount(xyftLotterySgs, 17));
        sumOpentCount.setNumber18(XyftUtils.getSumOpenCount(xyftLotterySgs, 18));
        sumOpentCount.setNumber19(XyftUtils.getSumOpenCount(xyftLotterySgs, 19));
        sumOpentCount.setSingleTotal(singleTotal); //单出现的总次数
        sumOpentCount.setBigCountTotal(bigCountTotal);//大出现的总次数
        sumOpentCount.setSmallCountTotal(smallCountTotal);//小出现的总次数
        sumOpentCount.setTwoCountTotal(twoCountTotal);//双出现的总次数
        sumOpentCount.setIssueCount(xyftLotterySgs.size());//总期数
        listTotal.add(sumOpentCount);


        List<BjpksGuanyaHeDTO> listNoOutTotal = new ArrayList<>();
        /**
         * 统计【冠亚和出现总次数】
         */
        BjpksGuanyaHeDTO noOutOpentCount = new BjpksGuanyaHeDTO();
        noOutOpentCount.setIssue("遗漏的总次数");
        noOutOpentCount.setNumber3(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 3));
        noOutOpentCount.setNumber4(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 4));
        noOutOpentCount.setNumber5(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 5));
        noOutOpentCount.setNumber6(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 6));
        noOutOpentCount.setNumber7(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 7));
        noOutOpentCount.setNumber8(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 8));
        noOutOpentCount.setNumber9(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 9));
        noOutOpentCount.setNumber10(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 10));
        noOutOpentCount.setNumber11(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 11));
        noOutOpentCount.setNumber12(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 12));
        noOutOpentCount.setNumber13(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 13));
        noOutOpentCount.setNumber14(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 14));
        noOutOpentCount.setNumber15(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 15));
        noOutOpentCount.setNumber16(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 16));
        noOutOpentCount.setNumber17(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 17));
        noOutOpentCount.setNumber18(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 18));
        noOutOpentCount.setNumber19(XyftUtils.getNoOutOpenCount(xyftLotterySgs, 19));
        noOutOpentCount.setSingleTotal(singleNoOut); //单遗漏的总次数
        noOutOpentCount.setBigCountTotal(bigCountNoOut);//大遗漏的总次数
        noOutOpentCount.setSmallCountTotal(smallCountNoOut);//小遗漏的总次数
        noOutOpentCount.setTwoCountTotal(twoCountNoOut);//双遗漏的总次数
        noOutOpentCount.setIssueCount(xyftLotterySgs.size());//总期数
        listNoOutTotal.add(noOutOpentCount);

        result.put("listTotal", listTotal);
        result.put("listNoOutTotal", listNoOutTotal);
        return ResultInfo.ok(result);

    }


    /**
     * web首页彩票幸运飞艇今日历史开奖
     *
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getXyftTodayLotteryHistory() {
        Map<String, Object> result = new HashMap<>();
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria sgCriteria = example.createCriteria();
        sgCriteria.andNumberIsNotNull();
        example.setOffset(0);
        example.setLimit(10);
        example.setOrderByClause("`ideal_time` DESC");
        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);
        result.put("XyftTodayLotteryHistory", xyftLotterySgs);
        return ResultInfo.ok(result);
    }


    /**
     * 幸运飞艇开奖资讯想情
     *
     * @param pageNo
     * @param pageSize
     * @param date
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getTodayAndHistoryNews(Integer pageNo, Integer pageSize, String date) {
        Map<String, Object> result = new HashMap<>();
        List<BjpksHistoryDTO> lsitBj = new ArrayList<>();


        Date nowTime = new Date();

        if (StringUtils.isEmpty(date)) {
            date = DateUtils.formatDate(nowTime, DateUtils.FORMAT_YYYY_MM_DD);
        }
        result.put("totalTime", date);
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);

        if (StringUtils.isNotBlank(date) && date.equals(TimeHelper.date("yyyy-MM-dd"))) {
            date = XyftUtils.dateStr();
        }
        criteria.andIssueLike(date.replace("-", "") + "%");
        criteria.andNumberIsNotNull();
        example.setOrderByClause("`ideal_time` ASC");
        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);

        BjpksHistoryDTO dtoCount;
        for (XyftLotterySg list : xyftLotterySgs) {
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
        result.put("issueCount", xyftLotterySgs.size());

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

        if (date.equals(TimeHelper.date("yyyy-MM-dd"))) {
            date = XyftUtils.dateStr();
        }
        Map<String, Object> result = new HashMap<>();

        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLike(date.replace("-", "") + "%");
        criteria.andNumberIsNotNull();
        List<XyftLotterySg> xyftLotterySgs = xyftLotterySgMapper.selectByExample(example);

        int singleTotal = 0;
        int twoCountTotal = 0;
        int bigCountTotal = 0;
        int smallCountTotal = 0;
        int[] openArr = new int[17]; //统计出现次数
        for (XyftLotterySg list : xyftLotterySgs) {
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
     * @Title: getXyftPksSgLong
     * @Description: 统计幸运飞艇的长龙数据
     * @return
     * @see com.caipiao.live.read.service.result.XyftLotterySgService#getXyftPksSgLong()
     */
    @Override
    public List<Map<String, Object>> getXyftPksSgLong() {
        List<Map<String, Object>> xyftpksLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.XYFEIT_ALGORITHM_VALUE;
            List<XyftLotterySg> xyftLotterySgList = (List<XyftLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(xyftLotterySgList)) {
                xyftLotterySgList = this.getXyftpksAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, xyftLotterySgList, 10, TimeUnit.SECONDS);
            }
            // 大小长龙
            List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(xyftLotterySgList);
            xyftpksLongMapList.addAll(bigAndSmallLongList);
            // 单双长龙
            List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(xyftLotterySgList);
            xyftpksLongMapList.addAll(sigleAndDoubleLongList);
            // 龙虎长龙
            List<Map<String, Object>> dragonAndTigleLongList = this.getTrigleAndDragonLong(xyftLotterySgList);
            xyftpksLongMapList.addAll(dragonAndTigleLongList);
            xyftpksLongMapList = this.addXyftNextIssueInfo(xyftpksLongMapList, xyftLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#BjpksLotterySgServiceImpl_getJspksSgLong_error:", e);
        }
        return xyftpksLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: 返回
     * @author HANS
     * @date 2019年5月26日下午2:43:19
     */
    private List<Map<String, Object>> addXyftNextIssueInfo(List<Map<String, Object>> xyftpksLongMapList, List<XyftLotterySg> xyftLotterySgList) {
        List<Map<String, Object>> xyftpksResultLongMapList = new ArrayList<Map<String, Object>>();

        if (!CollectionUtils.isEmpty(xyftpksLongMapList)) {
            // 获取缓存中下期数据
            String nextRedisKey = RedisKeys.XYFEIT_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.XYFEIT.getRedisTime();
            XyftLotterySg nextXyftLotterySg = (XyftLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextXyftLotterySg == null) {
                nextXyftLotterySg = this.getNextXyftLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextXyftLotterySg, 10, TimeUnit.SECONDS);
            }

            if (nextXyftLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.XYFEIT_RESULT_VALUE;
            XyftLotterySg xyftLotterySg = (XyftLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (xyftLotterySg == null) {
                xyftLotterySg = this.getXyftLotterySg();
                redisTemplate.opsForValue().set(redisKey, xyftLotterySg);
            }

            String nextIssue = nextXyftLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextXyftLotterySg.getIssue();
            String txffnextIssue = xyftLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : xyftLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // 如果长龙期数与下期期数相差太大长龙不存在
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }

            for (Map<String, Object> longMap : xyftpksLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextXyftLotterySg.getIssue());
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextXyftLotterySg.getIdealTime()) / 1000L);
                xyftpksResultLongMapList.add(longMap);
            }
        }
        return xyftpksResultLongMapList;
    }

    /**
     * @Title: getBigAndSmallLong
     * @Description: 获取大小的长龙数据
     * @author HANS
     * @date 2019年5月18日下午6:46:37
     */
    private List<Map<String, Object>> getBigAndSmallLong(List<XyftLotterySg> xyftLotterySgList) {
        List<Map<String, Object>> xyftpksBigLongMapList = new ArrayList<Map<String, Object>>();
        //冠亚和大小
        Map<String, Object> gyhDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getTagType());
        //冠军大小
        Map<String, Object> gjbDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGJBIG.getTagType());
        //亚军大小
        Map<String, Object> yjbDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSYJBIG.getTagType());
        //第三名大小
        Map<String, Object> dsmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDSMBIG.getTagType());
        //第四名大小
        Map<String, Object> dfmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDFMBIG.getTagType());
        //第五名大小
        Map<String, Object> dwmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDWMBIG.getTagType());
        //第六名大小
        Map<String, Object> dlmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDLMBIG.getTagType());
        //第七名大小
        Map<String, Object> dqmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDQMBIG.getTagType());
        //第八名大小
        Map<String, Object> dmmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDMMBIG.getTagType());
        //第九名大小
        Map<String, Object> djmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDJMBIG.getTagType());
        //第十名大小
        Map<String, Object> dtmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDTMBIG.getTagType());

        // 计算后的数据放入返回集合
        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(gyhDragonMap);
        }

        if (gjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(gjbDragonMap);
        }

        if (yjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(yjbDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksBigLongMapList.add(dtmDragonMap);
        }
        return xyftpksBigLongMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: 公共方法，获取长龙数据
     * @author HANS
     * @date 2019年5月18日下午7:04:47
     */
    private Map<String, Object> getXyftDragonInfo(List<XyftLotterySg> xyftLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(xyftLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < xyftLotterySgList.size(); index++) {
                    XyftLotterySg xyftLotterySg = xyftLotterySgList.get(index);
                    String numberString = xyftLotterySg.getNumber();
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateXyftResult(type, numberString);

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
                // 组织返回数据
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TenpksLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: 按照玩法计算结果
     * @author HANS
     * @date 2019年5月18日上午11:22:00
     */
    private String calculateXyftResult(int type, String numberString) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 245:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠亚和大小
                break;
            case 246:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠军大小
                break;
            case 247:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//亚军大小
                break;
            case 248:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第三名大小
                break;
            case 249:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第四名大小
                break;
            case 250:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第五名大小
                break;
            case 251:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第六名大小
                break;
            case 252:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第七名大小
                break;
            case 253:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第八名大小
                break;
            case 254:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第九名大小
                break;
            case 255:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第十名大小
                break;
            case 256:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠亚和单双
                break;
            case 257:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠军单双
                break;
            case 258:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//亚军单双
                break;
            case 259:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第三名单双
                break;
            case 260:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第四名单双
                break;
            case 261:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第五名单双
                break;
            case 262:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第六名单双
                break;
            case 263:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第七名单双
                break;
            case 264:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第八名单双
                break;
            case 265:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第九名单双
                break;
            case 266:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第十名单双
                break;
            case 267:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//冠军龙虎
                break;
            case 268:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//亚军龙虎
                break;
            case 269:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第三名龙虎
                break;
            case 270:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第四名龙虎
                break;
            case 271:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第五名龙虎
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: 返回前端数据
     * @author HANS
     * @date 2019年5月18日下午7:11:28
     */
    private Map<String, Object> organizationDragonResultMap(int type, Set<String> dragonSet, Integer dragonSize) {
        // 有龙情况下查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // 获取德州PK10    两面 赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.XYFEIT.getTagCnName(), CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getPlayName(),
                    CaipiaoTypeEnum.XYFEIT.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getTagType() + "");

            List<String> dragonList = new ArrayList<String>(dragonSet);
            // 玩法赔率
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 245) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGBIG);
            } else if (type == 246) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 247) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 248) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 249) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 250) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 251) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDLMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDLMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDLMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 252) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDQMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDQMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDQMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 253) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDMMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDMMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDMMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 254) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDJMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDJMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDJMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 255) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDTMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDTMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDTMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 256) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGYHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGYHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGYHDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGDOUBLE);
            } else if (type == 257) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 258) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 259) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 260) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 261) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 262) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDLMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDLMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDLMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 263) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDQMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDQMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDQMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 264) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDMMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDMMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDMMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 265) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDJMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDJMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDJMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 266) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDTMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDTMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDTMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 267) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSGJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 268) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSYJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 269) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDSMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 270) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDFMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 271) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.XYFTPKSDWMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            }
            // 把获取的赔率加入到返回MAP中
            longDragonMap.putAll(oddsListMap);
            // 加入其它返回值
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.XYFEIT.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.XYFEIT.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#XyftLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: getDoubleAndSingleLong
     * @Description: 获取单双长龙的数据
     * @author HANS
     * @date 2019年5月18日下午6:47:11
     */
    private List<Map<String, Object>> getDoubleAndSingleLong(List<XyftLotterySg> xyftLotterySgList) {
        List<Map<String, Object>> xyftpksDoubleLongMapList = new ArrayList<Map<String, Object>>();
        //冠亚和单双
        Map<String, Object> gyhDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGYHDOUBLE.getTagType());
        //冠军单双
        Map<String, Object> gjDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGJDOUBLE.getTagType());
        //亚军单双
        Map<String, Object> syjDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSYJDOUBLE.getTagType());
        //第三名单双
        Map<String, Object> dsmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDSMDOUBLE.getTagType());
        //第四名单双
        Map<String, Object> dfmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDFMDOUBLE.getTagType());
        //第五名单双
        Map<String, Object> dwmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDWMDOUBLE.getTagType());
        //第六名单双
        Map<String, Object> dlmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDLMDOUBLE.getTagType());
        //第七名单双
        Map<String, Object> dqmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDQMDOUBLE.getTagType());
        //第八名单双
        Map<String, Object> dmmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDMMDOUBLE.getTagType());
        //第九名单双
        Map<String, Object> djmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDJMDOUBLE.getTagType());
        //第十名单双
        Map<String, Object> dtmDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDTMDOUBLE.getTagType());

        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(gyhDragonMap);
        }

        if (gjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(gjDragonMap);
        }

        if (syjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(syjDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksDoubleLongMapList.add(dtmDragonMap);
        }
        return xyftpksDoubleLongMapList;
    }

    /**
     * @Title: getTrigleAndDragonLong
     * @Description: 获取龙虎长龙的数据
     * @author HANS
     * @date 2019年5月18日下午6:47:50
     */
    private List<Map<String, Object>> getTrigleAndDragonLong(List<XyftLotterySg> xyftLotterySgList) {
        List<Map<String, Object>> xyftpksTrigLongMapList = new ArrayList<Map<String, Object>>();
        //冠军龙虎
        Map<String, Object> gjtiDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGJTIDRAGON.getTagType());
        //亚军龙虎
        Map<String, Object> yjtiDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSYJTIDRAGON.getTagType());
        //第三名龙虎
        Map<String, Object> dsmtiDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDSMTIDRAGON.getTagType());
        //第四名龙虎
        Map<String, Object> dfmtiDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDFMTIDRAGON.getTagType());
        //第五名龙虎
        Map<String, Object> dwmtiDragonMap = this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDWMTIDRAGON.getTagType());

        if (gjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksTrigLongMapList.add(gjtiDragonMap);
        }

        if (yjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksTrigLongMapList.add(yjtiDragonMap);
        }

        if (dsmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksTrigLongMapList.add(dsmtiDragonMap);
        }

        if (dfmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksTrigLongMapList.add(dfmtiDragonMap);
        }

        if (dwmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            xyftpksTrigLongMapList.add(dwmtiDragonMap);
        }
        return xyftpksTrigLongMapList;
    }

    /**
     * @Title: getXyftpksAlgorithmData
     * @Description: 查询幸运飞艇近期开奖数据
     * @author HANS
     * @date 2019年5月29日下午10:26:51
     */
    private List<XyftLotterySg> getXyftpksAlgorithmData() {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        return xyftLotterySgMapper.selectByExample(example);
    }

}
