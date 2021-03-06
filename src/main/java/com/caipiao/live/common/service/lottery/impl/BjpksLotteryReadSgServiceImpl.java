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
 * ??????PK10
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

        // ????????????
        if (!LotteryInformationType.BJPKS_JRHM.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = bjpksBeanMapper.selectNumberByDate(date + "%");
        // ??????
        if (CollectionUtils.isEmpty(sgs)) {
            return ResultInfo.ok(voList);
        }

        // ????????????
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

        //??????pk10????????????
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
        //??????pk10????????????
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
        //??????pk10????????????
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
        //??????pk10???????????????
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
        //??????pk10????????????
        if (LotteryInformationType.BJPKS_QHLZ.equals(type)) {
            Map<String, ThereMemberListVO> result = BjpksUtils.luzhuQ(sg);
            return ResultInfo.ok(result);
        }
        //??????PK10?????????????????????, ?????????, ?????????
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

        // ??????PK10????????????
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
        //??????100??? ????????????????????????????????????????????????
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
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //???????????????
            bjpksLotterySgs = redisTemplate.opsForList().range(RedisKeys.BJPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //??????????????????
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

        //??????PK10???????????????
        Map<String, List<ThereIntegerVO>> result = BjpksUtils.guanYaCount(sgs);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCountbySize(int size) {

        List<String> sgs = bjpksBeanMapper.selectNumberLimitDesc(size);

        //??????PK10???????????????
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
        int limit = 179 * 30;  // ?????????179???
        if ("3".equals(way)) {
            limit = 179 * 30 * 3 + 1;
        } else if ("6".equals(way)) {
            limit = 179 * 30 * 6 + 3;
        }

        List<String> sgList = bjpksBeanMapper.selectNumberLimitDesc(limit);

        if (sgList == null) {
            sgList = new ArrayList<>(0);
        }

        //??????PK10?????????????????????
        if (LotteryInformationType.BJPKS_LMYL_DX.equals(type)) {
            Map<String, ArrayList<MapIntegerVO>> result = BjpksUtils.noOpenLiangMianDx(sgList, number);
            return ResultInfo.ok(result);
        }

        //??????PK10?????????????????????
        if (LotteryInformationType.BJPKS_LMYL_DS.equals(type)) {
            Map<String, ArrayList<MapIntegerVO>> result = BjpksUtils.noOpenLiangMianDs(sgList, number);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<Map<Integer, ArrayList<Integer>>> lianMianYlWeb(String type, String way) {
        int limit = 179 * 30;  // ?????????179???
        if ("3".equals(way)) {
            limit = 179 * 30 * 3 + 1;
        } else if ("6".equals(way)) {
            limit = 179 * 30 * 6 + 3;
        }

        List<String> sgList = bjpksBeanMapper.selectNumberLimitDesc(limit);

        if (sgList == null) {
            sgList = new ArrayList<>(0);
        }

        //??????PK10?????????????????????
        if (LotteryInformationType.BJPKS_LMYL_DX.equals(type)) {
            HashMap<Integer, ArrayList<Integer>> result = BjpksUtils.noOpenLiangMianDxWeb(sgList);
            return ResultInfo.ok(result);
        }

        //??????PK10?????????????????????
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
            // ????????????????????????
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
                // ?????????????????????
                Integer sumCount = CaipiaoSumCountEnum.BJPKS.getSumCount();
                // ?????????????????????????????????
                result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            }
            String nextRedisKey = RedisKeys.BJPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.BJPKS.getRedisTime();
            BjpksLotterySg nextBjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextBjpksLotterySg == null) {
                nextBjpksLotterySg = this.getBjpksSg();
                redisTemplate.opsForValue().set(nextRedisKey, nextBjpksLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // ????????????????????????
            String redisKey = RedisKeys.BJPKS_RESULT_VALUE;
            BjpksLotterySg bjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (bjpksLotterySg == null) {
                // ????????????????????????
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
                        // ????????????????????????
                        bjpksLotterySg = this.getBjpksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, bjpksLotterySg);
                    }
                }

                if (bjpksLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), bjpksLotterySg == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), bjpksLotterySg == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getNumber());
                }

                if (nextBjpksLotterySg != null) {
                    // ???????????????????????????
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
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.BJPKS.getTagType() + " ????????? ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    /**
     * @return BjpksLotterySg
     * @Title: getBjpksLotterySg
     * @Description: ????????????????????????
     * @author admin
     * @date 2019???5???1?????????3:51:58
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
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???4???29?????????9:21:07
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
        result.put("name", "??????PK10");

        // ????????????????????????
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

        // ????????????????????????
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andNumberIsNotNull();
        example.setOrderByClause("ideal_time DESC");
        BjpksLotterySg bjpksLotterySg = bjpksLotterySgMapper.selectOneByExample(example);
        String time = bjpksLotterySg.getTime();
        result.put("issue", BjpksUtils.nextIssue(time, bjpksLotterySg.getIssue()) + "");
        long nextIssueTime = BjpksUtils.nextIssueTime();
        // ???????????????????????????
        result.put("time", nextIssueTime / 1000L);
        return ResultInfo.ok(result);
    }


    /**
     * ??????web???????????????PK10????????????
     *
     * @param pageNo  ???????????????
     * @param pageSize ?????????
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

        //??????list?????????????????????
        List<Map<String, Object>> listCrown = new ArrayList<>();

        //??????list??????????????????
        List<Map<String, Object>> listDragonandTiger = new ArrayList<>();

        //?????????????????????
        Map<String, Object> map;

        //??????????????????map
        Map<String, Object> map2;
        for (BjpksLotterySg listBs : bjpksLotterySgs) {
            map = new HashMap<>();
            String number = listBs.getNumber();
            String[] split = number.split(",");
            //???????????????????????????
            int count = Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
            map.put("count", count);//???

            //???????????????
            if (count > 11 && count % 2 == 0) {
                map.put("big", "???");
                map.put("two", "???");
            } else if (count < 11 && count % 2 != 0) {
                map.put("smile", "???");
                map.put("single", "???");
            } else if (count > 11 && count % 2 != 0) {
                map.put("big", "???");
                map.put("single", "???");
            } else if (count < 11 && count % 2 == 0) {
                map.put("smile", "???");
                map.put("single", "???");
            } else if (count == 11) {
                map.put("make", "???");//???????????????????????????11????????????
                map.put("single", "???");
            }
            listCrown.add(map);

            //1-5??????
            map2 = new HashMap<>();
            for (int i = 0; i < split.length - 5; i++) {
                int vv = Integer.parseInt(split[i]);
                int aa = Integer.parseInt(split[split.length - 1 - i]);

                if (vv > aa) {
                    map2.put("tiger" + i, "???");
                } else {
                    map2.put("dragonand" + i, "???");
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
     * ????????????????????????
     *
     * @param pageSize
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getGuanYaCount(Integer pageSize) {
        Map<String, Object> result = new HashMap<>();

        //??????????????????
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

        //????????????????????????list
        List<BjpksGuanyaHeDTO> listBJPKSGH = new ArrayList<>();
        int singleCount = 0;
        int twoCount = 0;
        int bigCount = 0;
        int smallCount = 0;

        //?????????????????????
        int singleTotal = 0;
        int twoCountTotal = 0;
        int bigCountTotal = 0;
        int smallCountTotal = 0;

        BjpksGuanyaHeDTO dtoCount;

        //???????????????
        for (int i = bjpksLotterySgs.size() - 1; i >= 0; i--) {
            dtoCount = new BjpksGuanyaHeDTO();
            BjpksLotterySg bjpks = bjpksLotterySgs.get(i);
            dtoCount.setIssue(bjpks.getIssue());
            String[] number = bjpks.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);
            //????????????
            dtoCount.setGuanYaHe(guanYahe);

            //??????????????????????????????
            if (guanYahe % 2 == 0 && guanYahe > 11) {//???????????? ????????????11  ?????????????????????????????????????????????1
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


            //???????????????
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

        Collections.reverse(listBJPKSGH);//???????????????

        //??????????????????list
        List<BjpksGuanyaHeDTO> statisticsGuanyaHe = new ArrayList<>();

        /**
         * ???????????????????????????
         */
        BjpksGuanyaHeDTO sumOpentCount = new BjpksGuanyaHeDTO();
        sumOpentCount.setIssue("???????????????");
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
         * ???????????????????????????
         */
        int max0 = 0, max1 = 0, max2 = 0, max3 = 0, max4 = 0, max5 = 0, max6 = 0, max7 = 0, max8 = 0, max9 = 0, max10 = 0, max11 = 0, max12 = 0, max13 = 0, max14 = 0, max15 = 0, max16 = 0, max17 = 0, max18 = 0, max19 = 0, max20 = 0;

        List<Integer> single1 = new ArrayList<>();//???????????????
        List<Integer> two1 = new ArrayList<>();//???????????????
        List<Integer> small1 = new ArrayList<>();//???????????????
        List<Integer> big1 = new ArrayList<>();//???????????????
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
        guanYaHeMissinMax.setIssue("??????????????? ");
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
        guanYaHeMissinMax.setBigCountTotal(Collections.max(big1));//???????????????--???
        guanYaHeMissinMax.setTwoCountTotal(Collections.max(two1));//???????????????--???
        guanYaHeMissinMax.setSmallCountTotal(Collections.max(small1));//???????????????--???
        guanYaHeMissinMax.setSingleTotal(Collections.max(single1));//???????????????--???


        //??????????????????
        statisticsGuanyaHe.add(sumOpentCount);
        //???????????????
        statisticsGuanyaHe.add(guanYaHeMissinMax);


        result.put("statisticsGuanyaHe", statisticsGuanyaHe);//?????????
        result.put("listBJPKSGH", listBJPKSGH);//???????????????list

        return ResultInfo.ok(result);
    }


    /**
     * web?????????????????????
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


        //?????????????????????????????????
        int singleNoOut = 0;
        int twoCountNoOut = 0;
        int bigCountNoOut = 0;
        int smallCountNoOut = 0;

        //?????????????????????
        int singleTotal = 0;
        int twoCountTotal = 0;
        int bigCountTotal = 0;
        int smallCountTotal = 0;

        BjpksGuanyaHeDTO dtoCount;
        //???????????????
        for (int i = 0; i < bjpksLotterySgs.size(); i++) {
            dtoCount = new BjpksGuanyaHeDTO();
            BjpksLotterySg bjpks = bjpksLotterySgs.get(i);
            dtoCount.setIssue(bjpks.getIssue());
            String[] number = bjpks.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);
            //????????????
            dtoCount.setGuanYaHe(guanYahe);

            //??????????????????????????????
            if (guanYahe % 2 == 0 && guanYahe > 11) {//???????????? ????????????11
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
         * ????????????????????????????????????
         */
        BjpksGuanyaHeDTO sumOpentCount = new BjpksGuanyaHeDTO();
        sumOpentCount.setIssue("???????????????");
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
        sumOpentCount.setSingleTotal(singleTotal); //?????????????????????
        sumOpentCount.setBigCountTotal(bigCountTotal);//?????????????????????
        sumOpentCount.setSmallCountTotal(smallCountTotal);//?????????????????????
        sumOpentCount.setTwoCountTotal(twoCountTotal);//?????????????????????
        sumOpentCount.setIssueCount(bjpksLotterySgs.size());//?????????????????????
        listTotal.add(sumOpentCount);


        List<BjpksGuanyaHeDTO> listNoOutTotal = new ArrayList<>();
        /**
         * ????????????????????????????????????
         */
        BjpksGuanyaHeDTO noOutOpentCount = new BjpksGuanyaHeDTO();
        noOutOpentCount.setIssue("??????????????????");
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
        noOutOpentCount.setSingleTotal(singleNoOut); //?????????????????????
        noOutOpentCount.setBigCountTotal(bigCountNoOut);//?????????????????????
        noOutOpentCount.setSmallCountTotal(smallCountNoOut);//?????????????????????
        noOutOpentCount.setTwoCountTotal(twoCountNoOut);//?????????????????????
        noOutOpentCount.setIssueCount(bjpksLotterySgs.size());//?????????????????????
        listNoOutTotal.add(noOutOpentCount);

        result.put("listTotal", listTotal);
        result.put("listNoOutTotal", listNoOutTotal);
        return ResultInfo.ok(result);
    }


    /**
     * web??????????????????PK10??????????????????
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
     * web???pk10??????????????????
     *
     * @param pageNo  ?????????
     * @param pageSize ????????????
     * @param date     ??????????????????
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

            //??????????????????????????????
            if (guanYahe % 2 == 0 && guanYahe > 11) {
                dtoCount.setBigOrSmall("???");
                dtoCount.setSingleOrTwo("???");
            } else if (guanYahe % 2 != 0 && guanYahe > 11) {
                dtoCount.setBigOrSmall("???");
                dtoCount.setSingleOrTwo("???");
            } else if (guanYahe % 2 == 0 && guanYahe <= 11) {
                dtoCount.setBigOrSmall("???");
                dtoCount.setSingleOrTwo("???");
            } else if (guanYahe % 2 != 0 && guanYahe <= 11) {
                dtoCount.setBigOrSmall("???");
                dtoCount.setSingleOrTwo("???");
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
     * ?????????????????????3-19??????????????????
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
        int[] openArr = new int[17]; //??????????????????
        for (BjpksLotterySg list : bjpksLotterySgs) {
            String[] number = list.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);

            //??????????????????
            openArr[guanYahe - 3] += 1;

            //??????????????????????????????
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
     * @Description: ????????????PK10??????
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
            // ????????????
            List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(bjpksLotterySgList);
            bjpksLongMapList.addAll(bigAndSmallLongList);
            // ????????????
            List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(bjpksLotterySgList);
            bjpksLongMapList.addAll(sigleAndDoubleLongList);
            // ????????????
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
     * @Description: ??????
     * @author HANS
     * @date 2019???5???26?????????2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> bjpksLongMapList) {
        List<Map<String, Object>> bjpksResultLongMapList = new ArrayList<Map<String, Object>>();

        if (!CollectionUtils.isEmpty(bjpksLongMapList)) {
            // ???????????????????????????
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
            // ????????????????????????
            String redisKey = RedisKeys.BJPKS_RESULT_VALUE;
            BjpksLotterySg bjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (bjpksLotterySg == null) {
                // ????????????????????????
                bjpksLotterySg = this.getBjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, bjpksLotterySg);
            }
            String nextIssue = nextBjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextBjpksLotterySg.getIssue();
            String txffnextIssue = bjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : bjpksLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // ????????????????????????????????????????????????????????????
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
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???18?????????6:46:37
     */
    public List<Map<String, Object>> getBigAndSmallLong(List<BjpksLotterySg> bjpksLotterySgList) {
        List<Map<String, Object>> bjpksBigLongMapList = new ArrayList<Map<String, Object>>();
        //???????????????
        Map<String, Object> gyhDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagType());
        //????????????
        Map<String, Object> gjbDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJBIG.getTagType());
        //????????????
        Map<String, Object> yjbDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJBIG.getTagType());
        //???????????????
        Map<String, Object> dsmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMBIG.getTagType());
        //???????????????
        Map<String, Object> dfmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMBIG.getTagType());
        //???????????????
        Map<String, Object> dwmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMBIG.getTagType());
        //???????????????
        Map<String, Object> dlmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDLMBIG.getTagType());
        //???????????????
        Map<String, Object> dqmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDQMBIG.getTagType());
        //???????????????
        Map<String, Object> dmmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDMMBIG.getTagType());
        //???????????????
        Map<String, Object> djmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDJMBIG.getTagType());
        //???????????????
        Map<String, Object> dtmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDTMBIG.getTagType());

        // ????????????????????????????????????
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
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???18?????????6:47:11
     */
    public List<Map<String, Object>> getDoubleAndSingleLong(List<BjpksLotterySg> bjpksLotterySgList) {
        List<Map<String, Object>> bjpksDoubleLongMapList = new ArrayList<Map<String, Object>>();
        //???????????????
        Map<String, Object> gyhDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGYHDOUBLE.getTagType());
        //????????????
        Map<String, Object> gjDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJDOUBLE.getTagType());
        //????????????
        Map<String, Object> syjDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dsmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dfmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dwmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dlmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDLMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dqmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDQMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dmmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDMMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> djmDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDJMDOUBLE.getTagType());
        //???????????????
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
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???18?????????6:47:50
     */
    public List<Map<String, Object>> getTrigleAndDragonLong(List<BjpksLotterySg> bjpksLotterySgList) {
        List<Map<String, Object>> bjpksTrigLongMapList = new ArrayList<Map<String, Object>>();
        //????????????
        Map<String, Object> gjtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJTIDRAGON.getTagType());
        //????????????
        Map<String, Object> yjtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dsmtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dfmtiDragonMap = this.getDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMTIDRAGON.getTagType());
        //???????????????
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
     * @Description: ?????????????????????????????????
     * @author HANS
     * @date 2019???5???18?????????7:04:47
     */
    public Map<String, Object> getDragonInfo(List<BjpksLotterySg> bjpksLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(bjpksLotterySgList)) {
                // ????????????
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < bjpksLotterySgList.size(); index++) {
                    BjpksLotterySg bjpksLotterySg = bjpksLotterySgList.get(index);
                    String numberString = bjpksLotterySg.getNumber();
                    // ????????????????????????
                    String bigOrSmallName = this.calculateResult(type, numberString);

                    if (StringUtils.isEmpty(bigOrSmallName)) {
                        break;
                    }
                    // ????????????????????????SET??????
                    if (index == Constants.DEFAULT_INTEGER) {
                        dragonSet.add(bigOrSmallName);
                    }
                    // ???????????????????????????????????????????????????????????????
                    if (index == Constants.DEFAULT_ONE) {
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        continue;
                    }
                    // ???????????????3???????????????
                    if (index == Constants.DEFAULT_TWO) {
                        // ???????????????
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        dragonSize = Constants.DEFAULT_THREE;
                        continue;
                    }
                    // ????????????3????????????????????????????????????????????????
                    if (index > Constants.DEFAULT_TWO) {
                        // ???/?????????
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        dragonSize++;
                    }
                }
                // ????????????????????????
                BjpksLotterySg bjpksLotterySg = bjpksLotterySgList.get(Constants.DEFAULT_INTEGER);
                // ??????????????????
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
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???5???18?????????7:11:28
     */
    public Map<String, Object> organizationDragonResultMap(int type, BjpksLotterySg bjpksLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // ?????????????????????????????????
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // ????????????PK10    ?????? ????????????
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.BJPKS.getTagCnName(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getPlayName(),
                    CaipiaoTypeEnum.BJPKS.getTagType(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagType() + "");

            List<String> dragonList = new ArrayList<String>(dragonSet);
            // ????????????
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
            // ?????????????????????????????????MAP???
            longDragonMap.putAll(oddsListMap);
            // ?????????????????????
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
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???18?????????11:22:00
     */
    public String calculateResult(int type, String numberString) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 174:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 175:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//????????????
                break;
            case 176:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//????????????
                break;
            case 177:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 178:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 179:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 180:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 181:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 182:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 183:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 184:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 185:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 186:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//????????????
                break;
            case 187:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//????????????
                break;
            case 188:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 189:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 190:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 191:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 192:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 193:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 194:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 195:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 196:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//????????????
                break;
            case 197:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//????????????
                break;
            case 198:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//???????????????
                break;
            case 199:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//???????????????
                break;
            case 200:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//???????????????
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: selectOpenIssueList
     * @Description: ??????
     * @author HANS
     * @date 2019???5???18?????????4:56:25
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
