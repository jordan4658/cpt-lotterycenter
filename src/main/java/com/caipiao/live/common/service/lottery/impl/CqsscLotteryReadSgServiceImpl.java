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
import com.caipiao.live.common.model.dto.result.CqsscMissDTO;
import com.caipiao.live.common.model.dto.result.CqsscMissNumDTO;
import com.caipiao.live.common.model.dto.result.CqsscRatioDTO;
import com.caipiao.live.common.model.dto.result.CqsscShapeDTO;
import com.caipiao.live.common.model.dto.result.CqsscSizeMissDTO;
import com.caipiao.live.common.model.dto.result.CqsscSumValDTO;
import com.caipiao.live.common.model.dto.result.ShapeTypeEnum;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.CqsscLotterySg;
import com.caipiao.live.common.mybatis.entity.CqsscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.CqsscLotterySgMapper;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.CqsscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JssscLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import com.caipiao.live.common.util.lottery.CqsscUtils;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lucien
 * @create 2020/7/28 16:46
 */
@Service
public class CqsscLotteryReadSgServiceImpl implements CqsscLotterySgServiceReadSg {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CqsscLotterySgMapper cqsscLotterySgMapper;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private JssscLotterySgServiceReadSg jssscLotterySgServiceReadSg;

    @Override
    public List<CqsscLotterySg> getSgByDate(String date) {

        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        date = date.replaceAll("-", "") + "%";
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andIssueLike(date);
        example.setOrderByClause("issue DESC");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);
        return cqsscLotterySgs;
    }

    @Override
    public List<CqsscLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize) {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (!org.springframework.util.StringUtils.isEmpty(date)) {
            if (StringUtils.isBlank(date)) {
                date = TimeHelper.date("yyyy-MM-dd");
            }
            date = date.replaceAll("-", "") + "%";
            criteria.andIssueLike(date);
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("issue DESC");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);
        return cqsscLotterySgs;
    }

    @Override
    public List<CqsscLotterySg> getSgByIssues(Integer issues) {
        if (issues == null) {
            issues = 30;
        }
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(issues);
        example.setOrderByClause("issue DESC");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);
        return cqsscLotterySgs;
    }

    /**
     * ???????????????????????????
     *
     * @param type ??????????????????
     * @param date ??????, ???2018-08-21
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> todayCount(String type, String date) {
        // ????????????
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        List<CqsscLotterySg> cqsscLotterySgs = getSgByDate(date);
        if (cqsscLotterySgs == null || cqsscLotterySgs.size() == 0) {
            return ResultInfo.getInstance(null, StatusCode.NO_DATA);
        }
        Map<String, Object> map = CqsscUtils.todayCount(cqsscLotterySgs);
        return ResultInfo.ok(map);
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo, Integer pageSize) {
        // ????????????
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        List<CqsscLotterySg> cqsscLotterySgs = getSgByDatePageSize(date, pageNo, pageSize);
        List<Map<String, Object>> list = CqsscUtils.lishiKaiJiang(cqsscLotterySgs);
        return ResultInfo.ok(list);
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues) {
        // ????????????
        if (!LotteryInformationType.CQSSC_LSKJ_KJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        // ????????????????????????
        List<CqsscLotterySg> cqsscLotterySgs = getSgByIssues(issues);
        // ????????????
        List<Map<String, Object>> list = CqsscUtils.lishiKaiJiang(cqsscLotterySgs);
        return ResultInfo.ok(list);
    }

    @Override
    public Map<String, Object> lishiSg(Integer pageNo, Integer pageSize) {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
//        cqsscCriteria.andWanIsNotNull();
        cqsscCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        cqsscCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<CqsscLotterySg> cqsscLotterySgs = null;
        //??????100??? ????????????????????????????????????????????????
        if (!redisTemplate.hasKey(RedisKeys.CQSSC_SG_HS_LIST)) {
            CqsscLotterySgExample exampleOne = new CqsscLotterySgExample();
            CqsscLotterySgExample.Criteria cqsscCriteriaOne = exampleOne.createCriteria();
            cqsscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<CqsscLotterySg> cqsscLotterySgsOne = cqsscLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.CQSSC_SG_HS_LIST, cqsscLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //???????????????
            cqsscLotterySgs = redisTemplate.opsForList().range(RedisKeys.CQSSC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //??????????????????
            cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);
        }

        List<Map<String, Object>> maps = CqsscUtils.lishiSg(cqsscLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return result;
    }

    @Override
    public ResultInfo<Map<String, Object>> sgDetails(String issue) {
        if (StringUtils.isBlank(issue)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueEqualTo(issue);
        CqsscLotterySg cqsscLotterySg = cqsscLotterySgMapper.selectOneByExample(example);
        Map<String, Object> map = CqsscUtils.sgDetails(cqsscLotterySg);
        return ResultInfo.ok(map);
    }

    @Override
    public ResultInfo<List<MapListVO>> lishiLengRe(String type) {
        //????????????????????????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(100);
        example.setOrderByClause("issue desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);
        List<MapListVO> list = CqsscUtils.lishiLengRe(cqsscLotterySgs, type);
        return ResultInfo.ok(list);
    }

    @Override
    public ResultInfo<List<CqsscMissDTO>> getCqsscMissCount(String date) {
        // ????????????????????????
        List<CqsscMissDTO> list = new ArrayList<>();

        // ??????????????????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(date);
        criteria.andWanIsNotNull();
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        CqsscMissDTO dto;
        int allCount, currCount;
        for (int i = 0; i < 10; i++) {
            dto = new CqsscMissDTO();
            currCount = allCount = 0;
            dto.setNumber(i);
            for (CqsscLotterySg sg : cqsscLotterySgs) {
                if (sg.getGe().equals(i)) {
                    allCount++;
                }
                if (sg.getShi().equals(i)) {
                    allCount++;
                }
                if (sg.getBai().equals(i)) {
                    allCount++;
                }
                if (sg.getQian().equals(i)) {
                    allCount++;
                }
                if (sg.getWan().equals(i)) {
                    allCount++;
                }

                if (sg.getGe().equals(i) || sg.getShi().equals(i) || sg.getBai().equals(i) || sg.getQian().equals(i) || sg.getWan().equals(i)) {
                    currCount = 0;
                } else {
                    currCount++;
                }
            }
            dto.setAllCount(allCount);
            dto.setCurrCount(currCount);
            list.add(dto);
        }
        return ResultInfo.ok(list);
    }

    /**
     * ??????????????????
     *
     * @param count  ?????????
     * @param number ?????????1 | 2 | 3 | 4 | 5???
     * @return
     */
    @Override
    public Map<String, List<CqsscSizeMissDTO>> getCqsscSizeMissCount(Integer count, Integer number) {
        Map<String, List<CqsscSizeMissDTO>> result = new HashMap<>();

        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        Date dayAfter = DateUtils.getDayAfter(new Date(), -(count * 30L));
        String dateStr = DateUtils.formatDate(dayAfter, DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateGreaterThan(dateStr);
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        List<Integer> bigMissCount = new ArrayList<>();
        Integer bigCount = 0;
        List<Integer> smallMissCount = new ArrayList<>();
        Integer smallCount = 0;

        Iterator iter = cqsscLotterySgs.iterator();
        while (iter.hasNext()) {
            CqsscLotterySg sg = (CqsscLotterySg) iter.next();
            switch (number) {
                case 5:
                    if (sg.getGe() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 4:
                    if (sg.getShi() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 3:
                    if (sg.getBai() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 2:
                    if (sg.getQian() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                default:
                    if (sg.getWan() > 4) {
                        smallCount++;
                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;
            }
        }

        List<CqsscSizeMissDTO> bigList = this.countList(bigMissCount);
        Collections.sort(bigList);
        result.put("bigList", bigList);

        List<CqsscSizeMissDTO> smallList = this.countList(smallMissCount);
        Collections.sort(smallList);
        result.put("smallList", smallList);

        return result;
    }

    /**
     * ??????????????????
     *
     * @param count  ?????????
     * @param number ?????????1 | 2 | 3 | 4 | 5???
     * @return
     */
    @Override
    public Map<String, List<CqsscSizeMissDTO>> getCqsscSingleMissCount(Integer count, Integer number) {
        Map<String, List<CqsscSizeMissDTO>> result = new HashMap<>();

        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        Date dayAfter = DateUtils.getDayAfter(new Date(), -(count * 30L));
        String dateStr = DateUtils.formatDate(dayAfter, DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateGreaterThan(dateStr);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        List<Integer> singleMissCount = new ArrayList<>();
        Integer singleCount = 0;
        List<Integer> doubleMissCount = new ArrayList<>();
        Integer doubleCount = 0;

        Iterator iter = cqsscLotterySgs.iterator();
        while (iter.hasNext()) {
            CqsscLotterySg sg = (CqsscLotterySg) iter.next();
            switch (number) {
                case 5:
                    if (sg.getGe() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 4:
                    if (sg.getShi() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 3:
                    if (sg.getBai() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 2:
                    if (sg.getQian() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                default:
                    if (sg.getWan() % 2 == 1) {
                        doubleCount++;
                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;
            }
        }

        List<CqsscSizeMissDTO> singleList = this.countList(singleMissCount);
        // ??????
        Collections.sort(singleList);
        result.put("singleList", singleList);
        List<CqsscSizeMissDTO> doubleList = this.countList(doubleMissCount);
        // ??????
        Collections.sort(doubleList);
        result.put("doubleList", doubleList);

        return result;
    }

    private List<CqsscSizeMissDTO> countList(List<Integer> list) {
        List<CqsscSizeMissDTO> result = new ArrayList<>();
        for (Integer num : list) {
            Boolean found = false;
            for (CqsscSizeMissDTO dto : result) {
                if (dto.getMissValue().equals(num)) {
                    found = true;
                    dto.setMissCount(dto.getMissCount() + 1);
                    break;
                }
            }
            if (!found) {
                CqsscSizeMissDTO dto = new CqsscSizeMissDTO();
                dto.setMissValue(num);
                dto.setMissCount(1);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * ??????????????????????????????
     *
     * @param number 1:?????? 2:??????  3:?????? 4:?????? 5:??????
     * @param limit  ????????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscTrend(Integer number, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscMissNumDTO> list = this.missValueByNum(number, cqsscLotterySgs, limit);

        // ????????????????????????
        List<CqsscMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("list", list);
        result.put("statistics", statistics);

        return result;
    }

    /**
     * ??????
     *
     * @param number 1:??????  2:??????  3:?????? 4:?????? 5:??????
     * @param limit  ????????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscAmplitude(Integer number, Integer limit) {
        Map<String, Object> result = new HashMap<>();

        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscMissNumDTO> dtos = new ArrayList<>();

        CqsscMissNumDTO dto;
        for (int i = 0; i < limit && i < cqsscLotterySgs.size(); i++) {
            dto = new CqsscMissNumDTO();
            CqsscLotterySg sg1 = cqsscLotterySgs.get(i);
            dto.setIssue(sg1.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg1));
            if (i == limit - 1) {
                dto.setNumber("0");
            } else {
                CqsscLotterySg sg2 = cqsscLotterySgs.get(i + 1);
                int diff;
                switch (number) {
                    case 1:
                        diff = Math.abs(sg1.getWan() - sg2.getWan());
                        break;

                    case 2:
                        diff = Math.abs(sg1.getQian() - sg2.getQian());
                        break;

                    case 3:
                        diff = Math.abs(sg1.getBai() - sg2.getBai());
                        break;

                    case 4:
                        diff = Math.abs(sg1.getShi() - sg2.getShi());
                        break;

                    default:
                        diff = Math.abs(sg1.getGe() - sg2.getGe());
                        break;
                }
                dto.setNumber(Integer.toString(diff));
            }
            dtos.add(dto);
        }

        // ???????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<CqsscMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("list", list);
        result.put("statistics", statistics);

        return result;
    }

    /**
     * ????????????
     *
     * @param type  ?????????2 ??????????????? 3 ????????????
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscTrendGroup(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();

        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscMissNumDTO> dtos = new ArrayList<>();

        CqsscMissNumDTO dto;
        // ???????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(type, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<CqsscMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("list", list);
        result.put("statistics", statistics);
        return result;
    }

    /**
     * ????????????
     *
     * @param type  ?????????2 ??????????????? 3 ????????????
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscSpan(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscMissNumDTO> dtos = new ArrayList<>();

        CqsscMissNumDTO dto;
        // ???????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSpan(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<CqsscMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    @Override
    public Map<String, Object> getCqsscSpanMaxMin(Integer number, Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscMissNumDTO> dtos = new ArrayList<>();

        CqsscMissNumDTO dto;
        // ????????????????????????????????????/?????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSpanMaxMin(sg, type, number).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<CqsscMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ?????????????????????/?????????
     *
     * @param sg     ??????
     * @param number ?????? ??? 2 ?????? / 3 ??????
     * @param type   ??????: 1 ?????? / 2 ??????
     * @return
     */
    private Integer countSpanMaxMin(CqsscLotterySg sg, Integer type, Integer number) {
        Integer bai = sg.getBai();
        Integer shi = sg.getShi();
        Integer ge = sg.getGe();
        int max, min;

        if (number.equals(2)) {
            max = shi > ge ? shi : ge;
            min = shi > ge ? ge : shi;
        } else {
            max = bai > shi ? bai : shi;
            max = max > ge ? max : ge;
            min = bai < shi ? bai : shi;
            min = min < ge ? min : ge;
        }
        return type.equals(1) ? max : min;
    }

    /**
     * ????????????????????????????????? - ????????????
     *
     * @param type  ?????? ??? 2 ?????? | 3 ?????? | 5 ??????
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscSumTail(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscMissNumDTO> dtos = new ArrayList<>();

        CqsscMissNumDTO dto;
        // ???????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSumTail(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<CqsscMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ????????????????????????????????? - ????????????
     *
     * @param type  ?????? ??? 2 ?????? | 3 ?????? | 5 ??????
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscSumVal(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 200);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscSumValDTO> dtos = new ArrayList<>();

        CqsscSumValDTO dto;
        // ???????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscSumValDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSumValue(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueBySumVal(dtos, type);

        // ??????????????????????????????
        List<CqsscSumValDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscSumValDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscSumValDTO openCount = this.countSumOpenCountBySumVal(list, type);

        // ???????????????????????????
        CqsscSumValDTO maxMissVal = this.countMaxMissValueBySumVal(list);

        // ???????????????????????????
        CqsscSumValDTO avgMissVal = this.countAvgMissValueBySumVal(limit, openCount);

        // ???????????????????????????
        CqsscSumValDTO maxContinuous = this.countMaxContinuousBySumVal(list, type);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    private CqsscSumValDTO countMaxContinuousBySumVal(List<CqsscSumValDTO> list, Integer type) {
        CqsscSumValDTO continuous = new CqsscSumValDTO();
        continuous.setIssue("???????????????");
        continuous.setNum0(this.getContinuousValueBySumVal(list, type, 0));
        continuous.setNum1(this.getContinuousValueBySumVal(list, type, 1));
        continuous.setNum2(this.getContinuousValueBySumVal(list, type, 2));
        continuous.setNum3(this.getContinuousValueBySumVal(list, type, 3));
        continuous.setNum4(this.getContinuousValueBySumVal(list, type, 4));
        continuous.setNum5(this.getContinuousValueBySumVal(list, type, 5));
        continuous.setNum6(this.getContinuousValueBySumVal(list, type, 6));
        continuous.setNum7(this.getContinuousValueBySumVal(list, type, 7));
        continuous.setNum8(this.getContinuousValueBySumVal(list, type, 8));
        continuous.setNum9(this.getContinuousValueBySumVal(list, type, 9));
        continuous.setNum10(this.getContinuousValueBySumVal(list, type, 10));
        continuous.setNum11(this.getContinuousValueBySumVal(list, type, 11));
        continuous.setNum12(this.getContinuousValueBySumVal(list, type, 12));
        continuous.setNum13(this.getContinuousValueBySumVal(list, type, 13));
        continuous.setNum14(this.getContinuousValueBySumVal(list, type, 14));
        continuous.setNum15(this.getContinuousValueBySumVal(list, type, 15));
        continuous.setNum16(this.getContinuousValueBySumVal(list, type, 16));
        continuous.setNum17(this.getContinuousValueBySumVal(list, type, 17));
        continuous.setNum18(this.getContinuousValueBySumVal(list, type, 18));
        continuous.setNum19(this.getContinuousValueBySumVal(list, type, 19));
        continuous.setNum20(this.getContinuousValueBySumVal(list, type, 20));
        continuous.setNum21(this.getContinuousValueBySumVal(list, type, 21));
        continuous.setNum22(this.getContinuousValueBySumVal(list, type, 22));
        continuous.setNum23(this.getContinuousValueBySumVal(list, type, 23));
        continuous.setNum24(this.getContinuousValueBySumVal(list, type, 24));
        continuous.setNum25(this.getContinuousValueBySumVal(list, type, 25));
        continuous.setNum26(this.getContinuousValueBySumVal(list, type, 26));
        continuous.setNum27(this.getContinuousValueBySumVal(list, type, 27));
        return continuous;
    }

    private Integer getContinuousValueBySumVal(List<CqsscSumValDTO> list, Integer type, Integer number) {
        Integer max = 0, count = 0, first = 0;
        Integer beforeValue = -1;
        for (CqsscSumValDTO dto : list) {
            Integer realNum = Integer.parseInt(dto.getNumber());
            if (type == 5) {
                Integer num = number + 13;
                if (number.equals(0)) {
                    // ????????????????????????????????????????????????????????????count?????????1
                    if (first.equals(0) && realNum <= 13) {
                        first++;
                        count = 1;
                    }
                    if (realNum <= 13 && beforeValue <= 13 && beforeValue >= 0) {
                        count++;
                        beforeValue = realNum;
                    } else if (realNum <= 13) {
                        beforeValue = realNum;
                    } else {
                        beforeValue = -1;
                        first = 0;
                    }
                } else if (number.equals(22)) {
                    if (first.equals(0) && realNum >= 35) {
                        first++;
                        count = 1;
                    }
                    if (realNum >= 35 && beforeValue >= 35) {
                        count++;
                        beforeValue = realNum;
                    } else if (realNum >= 35) {
                        beforeValue = realNum;
                    } else {
                        beforeValue = -1;
                        first = 0;
                    }
                } else {
                    // ????????????????????????????????????????????????????????????count?????????1
                    if (first.equals(0) && realNum.equals(num)) {
                        first++;
                        count = 1;
                    }
                    // ??????????????????????????????????????????????????????????????????????????????count ++
                    if ((realNum.equals(num) && realNum.equals(beforeValue))) {
                        count++;
                        beforeValue = num;
                    } else if (realNum.equals(num)) {
                        beforeValue = num;
                    } else {
                        beforeValue = -1;
                        first = 0;
                    }
                }

            } else {
                // ????????????????????????????????????????????????????????????count?????????1
                if (first.equals(0) && realNum.equals(number)) {
                    first++;
                    count = 1;
                }
                // ??????????????????????????????????????????????????????????????????????????????count ++
                if ((realNum.equals(number) && realNum.equals(beforeValue))) {
                    count++;
                    beforeValue = number;
                } else if (realNum.equals(number)) {
                    beforeValue = number;
                } else {
                    beforeValue = -1;
                    first = 0;
                }
            }
            max = max > count ? max : count;
        }
        return max;
    }

    private CqsscSumValDTO countAvgMissValueBySumVal(Integer size, CqsscSumValDTO openCount) {
        CqsscSumValDTO avgMissVal = new CqsscSumValDTO();
        avgMissVal.setIssue("???????????????");
        avgMissVal.setNum0((size - openCount.getNum0()) / (openCount.getNum0() + 1));
        avgMissVal.setNum1((size - openCount.getNum1()) / (openCount.getNum1() + 1));
        avgMissVal.setNum2((size - openCount.getNum2()) / (openCount.getNum2() + 1));
        avgMissVal.setNum3((size - openCount.getNum3()) / (openCount.getNum3() + 1));
        avgMissVal.setNum4((size - openCount.getNum4()) / (openCount.getNum4() + 1));
        avgMissVal.setNum5((size - openCount.getNum5()) / (openCount.getNum5() + 1));
        avgMissVal.setNum6((size - openCount.getNum6()) / (openCount.getNum6() + 1));
        avgMissVal.setNum7((size - openCount.getNum7()) / (openCount.getNum7() + 1));
        avgMissVal.setNum8((size - openCount.getNum8()) / (openCount.getNum8() + 1));
        avgMissVal.setNum9((size - openCount.getNum9()) / (openCount.getNum9() + 1));
        avgMissVal.setNum10((size - openCount.getNum10()) / (openCount.getNum10() + 1));
        avgMissVal.setNum11((size - openCount.getNum11()) / (openCount.getNum11() + 1));
        avgMissVal.setNum12((size - openCount.getNum12()) / (openCount.getNum12() + 1));
        avgMissVal.setNum13((size - openCount.getNum13()) / (openCount.getNum13() + 1));
        avgMissVal.setNum14((size - openCount.getNum14()) / (openCount.getNum14() + 1));
        avgMissVal.setNum15((size - openCount.getNum15()) / (openCount.getNum15() + 1));
        avgMissVal.setNum16((size - openCount.getNum16()) / (openCount.getNum16() + 1));
        avgMissVal.setNum17((size - openCount.getNum17()) / (openCount.getNum17() + 1));
        avgMissVal.setNum18((size - openCount.getNum18()) / (openCount.getNum18() + 1));
        avgMissVal.setNum19((size - openCount.getNum19()) / (openCount.getNum19() + 1));
        avgMissVal.setNum20((size - openCount.getNum20()) / (openCount.getNum20() + 1));
        avgMissVal.setNum21((size - openCount.getNum21()) / (openCount.getNum21() + 1));
        avgMissVal.setNum22((size - openCount.getNum22()) / (openCount.getNum22() + 1));
        avgMissVal.setNum23((size - openCount.getNum23()) / (openCount.getNum23() + 1));
        avgMissVal.setNum24((size - openCount.getNum24()) / (openCount.getNum24() + 1));
        avgMissVal.setNum25((size - openCount.getNum25()) / (openCount.getNum25() + 1));
        avgMissVal.setNum26((size - openCount.getNum26()) / (openCount.getNum26() + 1));
        avgMissVal.setNum27((size - openCount.getNum27()) / (openCount.getNum27() + 1));
        return avgMissVal;
    }

    private CqsscSumValDTO countMaxMissValueBySumVal(List<CqsscSumValDTO> list) {
        CqsscSumValDTO maxMissVal = new CqsscSumValDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }

        int max0 = 0, max1 = 0, max2 = 0, max3 = 0, max4 = 0, max5 = 0, max6 = 0, max7 = 0, max8 = 0, max9 = 0, max10 = 0, max11 = 0, max12 = 0, max13 = 0,
                max14 = 0, max15 = 0, max16 = 0, max17 = 0, max18 = 0, max19 = 0, max20 = 0, max21 = 0, max22 = 0, max23 = 0, max24 = 0, max25 = 0, max26 = 0, max27 = 0;
        for (CqsscSumValDTO dto : list) {
            Integer openNumber = Integer.parseInt(dto.getNumber());
            max0 = max0 > dto.getNum0() && !openNumber.equals(dto.getNum0()) ? max0 : dto.getNum0();
            max1 = max1 > dto.getNum1() && !openNumber.equals(dto.getNum1()) ? max1 : dto.getNum1();
            max2 = max2 > dto.getNum2() && !openNumber.equals(dto.getNum2()) ? max2 : dto.getNum2();
            max3 = max3 > dto.getNum3() && !openNumber.equals(dto.getNum3()) ? max3 : dto.getNum3();
            max4 = max4 > dto.getNum4() && !openNumber.equals(dto.getNum4()) ? max4 : dto.getNum4();
            max5 = max5 > dto.getNum5() && !openNumber.equals(dto.getNum5()) ? max5 : dto.getNum5();
            max6 = max6 > dto.getNum6() && !openNumber.equals(dto.getNum6()) ? max6 : dto.getNum6();
            max7 = max7 > dto.getNum7() && !openNumber.equals(dto.getNum7()) ? max7 : dto.getNum7();
            max8 = max8 > dto.getNum8() && !openNumber.equals(dto.getNum8()) ? max8 : dto.getNum8();
            max9 = max9 > dto.getNum9() && !openNumber.equals(dto.getNum9()) ? max9 : dto.getNum9();
            max10 = max10 > dto.getNum10() && !openNumber.equals(dto.getNum10()) ? max10 : dto.getNum10();
            max11 = max11 > dto.getNum11() && !openNumber.equals(dto.getNum11()) ? max11 : dto.getNum11();
            max12 = max12 > dto.getNum12() && !openNumber.equals(dto.getNum12()) ? max12 : dto.getNum12();
            max13 = max13 > dto.getNum13() && !openNumber.equals(dto.getNum13()) ? max13 : dto.getNum13();
            max14 = max14 > dto.getNum14() && !openNumber.equals(dto.getNum14()) ? max14 : dto.getNum14();
            max15 = max15 > dto.getNum15() && !openNumber.equals(dto.getNum15()) ? max15 : dto.getNum15();
            max16 = max16 > dto.getNum16() && !openNumber.equals(dto.getNum16()) ? max16 : dto.getNum16();
            max17 = max17 > dto.getNum17() && !openNumber.equals(dto.getNum17()) ? max17 : dto.getNum17();
            max18 = max18 > dto.getNum18() && !openNumber.equals(dto.getNum18()) ? max18 : dto.getNum18();
            max19 = max19 > dto.getNum19() && !openNumber.equals(dto.getNum19()) ? max19 : dto.getNum19();
            max20 = max20 > dto.getNum20() && !openNumber.equals(dto.getNum20()) ? max20 : dto.getNum20();
            max21 = max21 > dto.getNum21() && !openNumber.equals(dto.getNum21()) ? max21 : dto.getNum21();
            max22 = max22 > dto.getNum22() && !openNumber.equals(dto.getNum22()) ? max22 : dto.getNum22();
            max23 = max23 > dto.getNum23() && !openNumber.equals(dto.getNum23()) ? max23 : dto.getNum23();
            max24 = max24 > dto.getNum24() && !openNumber.equals(dto.getNum24()) ? max24 : dto.getNum24();
            max25 = max25 > dto.getNum25() && !openNumber.equals(dto.getNum25()) ? max25 : dto.getNum25();
            max26 = max26 > dto.getNum26() && !openNumber.equals(dto.getNum26()) ? max26 : dto.getNum26();
            max27 = max27 > dto.getNum27() && !openNumber.equals(dto.getNum27()) ? max27 : dto.getNum27();
        }

        // ??????
        maxMissVal.setNum0(max0);
        maxMissVal.setNum1(max1);
        maxMissVal.setNum2(max2);
        maxMissVal.setNum3(max3);
        maxMissVal.setNum4(max4);
        maxMissVal.setNum5(max5);
        maxMissVal.setNum6(max6);
        maxMissVal.setNum7(max7);
        maxMissVal.setNum8(max8);
        maxMissVal.setNum9(max9);
        maxMissVal.setNum10(max10);
        maxMissVal.setNum11(max11);
        maxMissVal.setNum12(max12);
        maxMissVal.setNum13(max13);
        maxMissVal.setNum14(max14);
        maxMissVal.setNum15(max15);
        maxMissVal.setNum16(max16);
        maxMissVal.setNum17(max17);
        maxMissVal.setNum18(max18);
        maxMissVal.setNum19(max19);
        maxMissVal.setNum20(max20);
        maxMissVal.setNum21(max21);
        maxMissVal.setNum22(max22);
        maxMissVal.setNum23(max23);
        maxMissVal.setNum24(max24);
        maxMissVal.setNum25(max25);
        maxMissVal.setNum26(max26);
        maxMissVal.setNum27(max27);
        return maxMissVal;
    }

    private CqsscSumValDTO countSumOpenCountBySumVal(List<CqsscSumValDTO> list, Integer type) {
        CqsscSumValDTO openCount = new CqsscSumValDTO();
        openCount.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // ????????????
        List<Integer> numCount = new ArrayList<>();

        // ??????
        for (int i = 0; i < 28; i++) {
            Integer count = 0;
            for (CqsscSumValDTO sumValDTO : list) {
                Integer num = i;
                Integer openNumber = Integer.parseInt(sumValDTO.getNumber());
                if (type.equals(5)) {
                    num = i + 13;
                    if (num.equals(13)) {
                        if (openNumber <= num) {
                            count++;
                        }
                    } else if (num >= 35) {
                        if (openNumber >= num) {
                            count++;
                        }
                    } else {
                        if (openNumber.equals(num)) {
                            count++;
                        }
                    }
                } else {
                    if (openNumber.equals(num)) {
                        count++;
                    }
                }
            }
            numCount.add(count);
        }

        // ??????
        openCount.setNum0(numCount.get(0));
        openCount.setNum1(numCount.get(1));
        openCount.setNum2(numCount.get(2));
        openCount.setNum3(numCount.get(3));
        openCount.setNum4(numCount.get(4));
        openCount.setNum5(numCount.get(5));
        openCount.setNum6(numCount.get(6));
        openCount.setNum7(numCount.get(7));
        openCount.setNum8(numCount.get(8));
        openCount.setNum9(numCount.get(9));
        openCount.setNum10(numCount.get(10));
        openCount.setNum11(numCount.get(11));
        openCount.setNum12(numCount.get(12));
        openCount.setNum13(numCount.get(13));
        openCount.setNum14(numCount.get(14));
        openCount.setNum15(numCount.get(15));
        openCount.setNum16(numCount.get(16));
        openCount.setNum17(numCount.get(17));
        openCount.setNum18(numCount.get(18));
        openCount.setNum19(numCount.get(19));
        openCount.setNum20(numCount.get(20));
        openCount.setNum21(numCount.get(21));
        openCount.setNum22(numCount.get(22));
        openCount.setNum23(numCount.get(23));
        openCount.setNum24(numCount.get(24));
        openCount.setNum25(numCount.get(25));
        openCount.setNum26(numCount.get(26));
        openCount.setNum27(numCount.get(27));
        return openCount;
    }

    private void countMissValueBySumVal(List<CqsscSumValDTO> list, Integer type) {
        for (int i = 0; i < list.size(); i++) {
            CqsscSumValDTO missNumDTO = list.get(i);
            missNumDTO.setNum0(this.countMissNumberBySumVal(i, list, type, 0));
            missNumDTO.setNum1(this.countMissNumberBySumVal(i, list, type, 1));
            missNumDTO.setNum2(this.countMissNumberBySumVal(i, list, type, 2));
            missNumDTO.setNum3(this.countMissNumberBySumVal(i, list, type, 3));
            missNumDTO.setNum4(this.countMissNumberBySumVal(i, list, type, 4));
            missNumDTO.setNum5(this.countMissNumberBySumVal(i, list, type, 5));
            missNumDTO.setNum6(this.countMissNumberBySumVal(i, list, type, 6));
            missNumDTO.setNum7(this.countMissNumberBySumVal(i, list, type, 7));
            missNumDTO.setNum8(this.countMissNumberBySumVal(i, list, type, 8));
            missNumDTO.setNum9(this.countMissNumberBySumVal(i, list, type, 9));
            missNumDTO.setNum10(this.countMissNumberBySumVal(i, list, type, 10));
            missNumDTO.setNum11(this.countMissNumberBySumVal(i, list, type, 11));
            missNumDTO.setNum12(this.countMissNumberBySumVal(i, list, type, 12));
            missNumDTO.setNum13(this.countMissNumberBySumVal(i, list, type, 13));
            missNumDTO.setNum14(this.countMissNumberBySumVal(i, list, type, 14));
            missNumDTO.setNum15(this.countMissNumberBySumVal(i, list, type, 15));
            missNumDTO.setNum16(this.countMissNumberBySumVal(i, list, type, 16));
            missNumDTO.setNum17(this.countMissNumberBySumVal(i, list, type, 17));
            missNumDTO.setNum18(this.countMissNumberBySumVal(i, list, type, 18));
            missNumDTO.setNum19(this.countMissNumberBySumVal(i, list, type, 19));
            missNumDTO.setNum20(this.countMissNumberBySumVal(i, list, type, 20));
            missNumDTO.setNum21(this.countMissNumberBySumVal(i, list, type, 21));
            missNumDTO.setNum22(this.countMissNumberBySumVal(i, list, type, 22));
            missNumDTO.setNum23(this.countMissNumberBySumVal(i, list, type, 23));
            missNumDTO.setNum24(this.countMissNumberBySumVal(i, list, type, 24));
            missNumDTO.setNum25(this.countMissNumberBySumVal(i, list, type, 25));
            missNumDTO.setNum26(this.countMissNumberBySumVal(i, list, type, 26));
            missNumDTO.setNum27(this.countMissNumberBySumVal(i, list, type, 27));
        }
    }

    private Integer countMissNumberBySumVal(int i, List<CqsscSumValDTO> list, Integer type, Integer number) {
        int count = 0;
        for (int j = i; j < list.size(); j++) {
            CqsscSumValDTO dto = list.get(j);
            Integer openNumber = Integer.parseInt(dto.getNumber());
            if (type.equals(5)) {
                Integer num = number + 13;
                if (number.equals(0)) {
                    if (i == j && openNumber <= 13) {
                        return openNumber;
                    } else if (openNumber <= 13) {
                        return count;
                    } else {
                        count++;
                    }
                } else if (number < 22) {
                    if (i == j && openNumber.equals(num)) {
                        return openNumber;
                    } else if (openNumber.equals(num)) {
                        return count;
                    } else {
                        count++;
                    }
                } else if (number.equals(22)) {
                    if (i == j && openNumber >= 35) {
                        return openNumber;
                    } else if (openNumber >= 35) {
                        return count;
                    } else {
                        count++;
                    }
                } else {
                    return -1;
                }
            } else if (type.equals(2)) {
                if (number > 18) {
                    return -1;
                } else {
                    if (i == j && openNumber.equals(number)) {
                        return number;
                    } else if (openNumber.equals(number)) {
                        return count;
                    } else {
                        count++;
                    }
                }
            } else {
                if (i == j && openNumber.equals(number)) {
                    return number;
                } else if (openNumber.equals(number)) {
                    return count;
                } else {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * ????????????????????????????????? - ??????
     *
     * @param type  ?????? ??? 1 ?????? | 2 ?????? | 3 ?????? | 4 ?????? | 5 ?????? | 6 ????????????
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscShape(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscShapeDTO> dtos = new ArrayList<>();

        CqsscShapeDTO dto;
        // ??????????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscShapeDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getNumber(type, sg).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByShape(dtos);

        // ??????????????????????????????
        List<CqsscShapeDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscShapeDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscShapeDTO openCount = this.countSumOpenCountOther(list);

        // ???????????????????????????
        CqsscShapeDTO maxMissVal = this.countMaxMissValueOther(list);

        // ???????????????????????????
        CqsscShapeDTO avgMissVal = this.countAvgMissValueOther(limit, openCount);

        // ???????????????????????????
        CqsscShapeDTO maxContinuous = this.countMaxContinuousOther(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ????????????????????????????????? - 012???
     *
     * @param type  ?????? ??? 1 ?????? | 2 ?????? | 3 ?????? | 4 ?????? | 5 ??????
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getCqssc012Way(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscMissNumDTO> dtos = new ArrayList<>();

        CqsscMissNumDTO dto;
        // ???????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.count012Way(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<CqsscMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ????????????????????????????????? - ??????
     *
     * @param type  ?????? ??? 1 ?????? | 2 ?????? | 3 ?????? | 4 ?????? | 5 ??????
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscToGo(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscShapeDTO> dtos = new ArrayList<>();

        CqsscShapeDTO dto;
        // ???????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscShapeDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getNumber(type, sg).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByToGo(dtos);

        // ??????????????????????????????
        List<CqsscShapeDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscShapeDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscShapeDTO openCount = this.countSumOpenCountOther(list);

        // ???????????????????????????
        CqsscShapeDTO maxMissVal = this.countMaxMissValueOther(list);

        // ???????????????????????????
        CqsscShapeDTO avgMissVal = this.countAvgMissValueOther(limit, openCount);

        // ???????????????????????????
        CqsscShapeDTO maxContinuous = this.countMaxContinuousOther(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ????????????????????????????????? - ??????
     *
     * @param number ?????????2 ??????????????? 3 ????????????
     * @param type   ?????? ??? 1 ?????????  2 ?????????  3 ?????????
     * @param limit  ?????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscrRatio(Integer number, Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscRatioDTO> dtos = new ArrayList<>();

        CqsscRatioDTO dto;
        // ???????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscRatioDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(number, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByRatio(dtos, type);

        // ??????????????????????????????
        List<CqsscRatioDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscRatioDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscRatioDTO openCount = this.countSumOpenCountRatio(list);

        // ???????????????????????????
        CqsscRatioDTO maxMissVal = this.countMaxMissValueRatio(list);

        // ???????????????????????????
        CqsscRatioDTO avgMissVal = this.countAvgMissValueRatio(limit, openCount);

        // ???????????????????????????
        CqsscRatioDTO maxContinuous = this.countMaxContinuousRatio(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ????????????????????????????????? - ??????
     *
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscrNumType(Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 300);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscRatioDTO> dtos = new ArrayList<>();

        CqsscRatioDTO dto;
        // ???????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscRatioDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(3, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByNumType(dtos);

        // ??????????????????????????????
        List<CqsscRatioDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscRatioDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscRatioDTO openCount = this.countSumOpenCountRatio(list);

        // ???????????????????????????
        CqsscRatioDTO maxMissVal = this.countMaxMissValueRatio(list);

        // ???????????????????????????
        CqsscRatioDTO avgMissVal = this.countAvgMissValueRatio(limit, openCount);

        // ???????????????????????????
        CqsscRatioDTO maxContinuous = this.countMaxContinuousRatio(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ????????????????????????????????? - ????????????????????????
     *
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscSizeCount(Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 200);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscShapeDTO> dtos = new ArrayList<>();

        CqsscShapeDTO dto;
        // ???????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscShapeDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(2, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueShape(dtos);

        // ??????????????????????????????
        List<CqsscShapeDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscShapeDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscShapeDTO openCount = this.countSumOpenCountShape(list);

        // ???????????????????????????
        CqsscShapeDTO maxMissVal = this.countMaxMissValueShape(list);

        // ???????????????????????????
        CqsscShapeDTO avgMissVal = this.countAvgMissValueShape(limit, openCount);

        // ???????????????????????????
        CqsscShapeDTO maxContinuous = this.countMaxContinuousShape(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    @Override
    public Map<String, Object> getCqsscSizePosition(Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 200);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(cqsscLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<CqsscSumValDTO> dtos = new ArrayList<>();

        CqsscSumValDTO dto;
        // ???????????????????????????????????????
        for (CqsscLotterySg sg : cqsscLotterySgs) {
            dto = new CqsscSumValDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(2, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueShapePosition(dtos);

        // ??????????????????????????????
        List<CqsscSumValDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<CqsscSumValDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        CqsscSumValDTO openCount = this.countSumOpenCountShapePosition(list);

        // ???????????????????????????
        CqsscSumValDTO maxMissVal = this.countMaxMissValueShapePosition(list);

        // ???????????????????????????
        CqsscSumValDTO avgMissVal = this.countAvgMissValueShapePosition(limit, openCount);

        // ???????????????????????????
        CqsscSumValDTO maxContinuous = this.countMaxContinuousShapePosition(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    private CqsscSumValDTO countMaxContinuousShapePosition(List<CqsscSumValDTO> list) {
        CqsscSumValDTO sumValDTO = new CqsscSumValDTO();
        sumValDTO.setIssue("???????????????");
        sumValDTO.setNum0(this.countMaxContinuous(list, 0));
        sumValDTO.setNum1(this.countMaxContinuous(list, 1));
        sumValDTO.setNum2(this.countMaxContinuous(list, 2));
        sumValDTO.setNum3(this.countMaxContinuous(list, 3));
        sumValDTO.setNum4(this.countMaxContinuous(list, 4));
        sumValDTO.setNum5(this.countMaxContinuous(list, 5));
        sumValDTO.setNum6(this.countMaxContinuous(list, 6));
        sumValDTO.setNum7(this.countMaxContinuous(list, 7));
        sumValDTO.setNum8(this.countMaxContinuous(list, 8));
        sumValDTO.setNum9(this.countMaxContinuous(list, 9));
        sumValDTO.setNum10(this.countMaxContinuous(list, 10));
        sumValDTO.setNum11(this.countMaxContinuous(list, 11));
        sumValDTO.setNum12(this.countMaxContinuous(list, 12));
        sumValDTO.setNum13(this.countMaxContinuous(list, 13));
        sumValDTO.setNum14(this.countMaxContinuous(list, 14));
        sumValDTO.setNum15(this.countMaxContinuous(list, 15));
        return sumValDTO;
    }

    private Integer countMaxContinuous(List<CqsscSumValDTO> list, int num) {
        Integer max = 0, count = 0;
        for (CqsscSumValDTO dto : list) {
            switch (num) {
                case 0:
                    if (dto.getNum0().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 1:
                    if (dto.getNum1().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 2:
                    if (dto.getNum2().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 3:
                    if (dto.getNum3().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 4:
                    if (dto.getNum4().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 5:
                    if (dto.getNum5().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 6:
                    if (dto.getNum6().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 7:
                    if (dto.getNum7().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 8:
                    if (dto.getNum8().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 9:
                    if (dto.getNum9().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 10:
                    if (dto.getNum10().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 11:
                    if (dto.getNum11().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 12:
                    if (dto.getNum12().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 13:
                    if (dto.getNum13().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                case 14:
                    if (dto.getNum14().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
                default:
                    if (dto.getNum15().equals(0)) {
                        count++;
                    } else {
                        max = count > max ? count : max;
                        count = 0;
                    }
                    break;
            }
        }
        return max;
    }

    private CqsscSumValDTO countMaxMissValueShapePosition(List<CqsscSumValDTO> list) {
        CqsscSumValDTO sumValDTO = new CqsscSumValDTO();
        sumValDTO.setIssue("???????????????");
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0,
                num8 = 0, num9 = 0, num10 = 0, num11 = 0, num12 = 0, num13 = 0, num14 = 0, num15 = 0;
        for (CqsscSumValDTO dto : list) {
            num0 = num0 > dto.getNum0() ? num0 : dto.getNum0();
            num1 = num1 > dto.getNum1() ? num1 : dto.getNum1();
            num2 = num2 > dto.getNum2() ? num2 : dto.getNum2();
            num3 = num3 > dto.getNum3() ? num3 : dto.getNum3();
            num4 = num4 > dto.getNum4() ? num4 : dto.getNum4();
            num5 = num5 > dto.getNum5() ? num5 : dto.getNum5();
            num6 = num6 > dto.getNum6() ? num6 : dto.getNum6();
            num7 = num7 > dto.getNum7() ? num7 : dto.getNum7();
            num8 = num8 > dto.getNum8() ? num8 : dto.getNum8();
            num9 = num9 > dto.getNum9() ? num9 : dto.getNum9();
            num10 = num10 > dto.getNum10() ? num10 : dto.getNum10();
            num11 = num11 > dto.getNum11() ? num11 : dto.getNum11();
            num12 = num12 > dto.getNum12() ? num12 : dto.getNum12();
            num13 = num13 > dto.getNum13() ? num13 : dto.getNum13();
            num14 = num14 > dto.getNum14() ? num14 : dto.getNum14();
            num15 = num15 > dto.getNum15() ? num15 : dto.getNum15();
        }
        sumValDTO.setNum0(num0);
        sumValDTO.setNum1(num1);
        sumValDTO.setNum2(num2);
        sumValDTO.setNum3(num3);
        sumValDTO.setNum4(num4);
        sumValDTO.setNum5(num5);
        sumValDTO.setNum6(num6);
        sumValDTO.setNum7(num7);
        sumValDTO.setNum8(num8);
        sumValDTO.setNum9(num9);
        sumValDTO.setNum10(num10);
        sumValDTO.setNum11(num11);
        sumValDTO.setNum12(num12);
        sumValDTO.setNum13(num13);
        sumValDTO.setNum14(num14);
        sumValDTO.setNum15(num15);
        return sumValDTO;
    }

    private CqsscSumValDTO countAvgMissValueShapePosition(Integer limit, CqsscSumValDTO openCount) {
        CqsscSumValDTO sumValDTO = new CqsscSumValDTO();
        sumValDTO.setIssue("???????????????");
        sumValDTO.setNum0((limit - openCount.getNum0()) / (openCount.getNum0() + 1));
        sumValDTO.setNum1((limit - openCount.getNum1()) / (openCount.getNum1() + 1));
        sumValDTO.setNum2((limit - openCount.getNum2()) / (openCount.getNum2() + 1));
        sumValDTO.setNum3((limit - openCount.getNum3()) / (openCount.getNum3() + 1));
        sumValDTO.setNum4((limit - openCount.getNum4()) / (openCount.getNum4() + 1));
        sumValDTO.setNum5((limit - openCount.getNum5()) / (openCount.getNum5() + 1));
        sumValDTO.setNum6((limit - openCount.getNum6()) / (openCount.getNum6() + 1));
        sumValDTO.setNum7((limit - openCount.getNum7()) / (openCount.getNum7() + 1));
        sumValDTO.setNum8((limit - openCount.getNum8()) / (openCount.getNum8() + 1));
        sumValDTO.setNum9((limit - openCount.getNum9()) / (openCount.getNum9() + 1));
        sumValDTO.setNum10((limit - openCount.getNum10()) / (openCount.getNum10() + 1));
        sumValDTO.setNum11((limit - openCount.getNum11()) / (openCount.getNum11() + 1));
        sumValDTO.setNum12((limit - openCount.getNum12()) / (openCount.getNum12() + 1));
        sumValDTO.setNum13((limit - openCount.getNum13()) / (openCount.getNum13() + 1));
        sumValDTO.setNum14((limit - openCount.getNum14()) / (openCount.getNum14() + 1));
        sumValDTO.setNum15((limit - openCount.getNum15()) / (openCount.getNum15() + 1));
        return sumValDTO;
    }

    private CqsscSumValDTO countSumOpenCountShapePosition(List<CqsscSumValDTO> list) {
        CqsscSumValDTO sumValDTO = new CqsscSumValDTO();
        sumValDTO.setIssue("???????????????");
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0,
                num8 = 0, num9 = 0, num10 = 0, num11 = 0, num12 = 0, num13 = 0, num14 = 0, num15 = 0;
        for (CqsscSumValDTO dto : list) {
            if (dto.getNum0().equals(0)) {
                num0++;
            }
            if (dto.getNum1().equals(0)) {
                num1++;
            }
            if (dto.getNum2().equals(0)) {
                num2++;
            }
            if (dto.getNum3().equals(0)) {
                num3++;
            }
            if (dto.getNum4().equals(0)) {
                num4++;
            }
            if (dto.getNum5().equals(0)) {
                num5++;
            }
            if (dto.getNum6().equals(0)) {
                num6++;
            }
            if (dto.getNum7().equals(0)) {
                num7++;
            }
            if (dto.getNum8().equals(0)) {
                num8++;
            }
            if (dto.getNum9().equals(0)) {
                num9++;
            }
            if (dto.getNum10().equals(0)) {
                num10++;
            }
            if (dto.getNum11().equals(0)) {
                num11++;
            }
            if (dto.getNum12().equals(0)) {
                num12++;
            }
            if (dto.getNum13().equals(0)) {
                num13++;
            }
            if (dto.getNum14().equals(0)) {
                num14++;
            }
            if (dto.getNum15().equals(0)) {
                num15++;
            }
        }
        sumValDTO.setNum0(num0);
        sumValDTO.setNum1(num1);
        sumValDTO.setNum2(num2);
        sumValDTO.setNum3(num3);
        sumValDTO.setNum4(num4);
        sumValDTO.setNum5(num5);
        sumValDTO.setNum6(num6);
        sumValDTO.setNum7(num7);
        sumValDTO.setNum8(num8);
        sumValDTO.setNum9(num9);
        sumValDTO.setNum10(num10);
        sumValDTO.setNum11(num11);
        sumValDTO.setNum12(num12);
        sumValDTO.setNum13(num13);
        sumValDTO.setNum14(num14);
        sumValDTO.setNum15(num15);
        return sumValDTO;
    }

    private void countMissValueShapePosition(List<CqsscSumValDTO> dtos) {
        for (int i = 0; i < dtos.size(); i++) {
            CqsscSumValDTO sumValDTO = dtos.get(i);
            sumValDTO.setNum0(this.countMissNumberShapePosition(i, dtos, 0));
            sumValDTO.setNum1(this.countMissNumberShapePosition(i, dtos, 1));
            sumValDTO.setNum2(this.countMissNumberShapePosition(i, dtos, 2));
            sumValDTO.setNum3(this.countMissNumberShapePosition(i, dtos, 3));
            sumValDTO.setNum4(this.countMissNumberShapePosition(i, dtos, 4));
            sumValDTO.setNum5(this.countMissNumberShapePosition(i, dtos, 5));
            sumValDTO.setNum6(this.countMissNumberShapePosition(i, dtos, 6));
            sumValDTO.setNum7(this.countMissNumberShapePosition(i, dtos, 7));
            sumValDTO.setNum8(this.countMissNumberShapePosition(i, dtos, 8));
            sumValDTO.setNum9(this.countMissNumberShapePosition(i, dtos, 9));
            sumValDTO.setNum10(this.countMissNumberShapePosition(i, dtos, 10));
            sumValDTO.setNum11(this.countMissNumberShapePosition(i, dtos, 11));
            sumValDTO.setNum12(this.countMissNumberShapePosition(i, dtos, 12));
            sumValDTO.setNum13(this.countMissNumberShapePosition(i, dtos, 13));
            sumValDTO.setNum14(this.countMissNumberShapePosition(i, dtos, 14));
            sumValDTO.setNum15(this.countMissNumberShapePosition(i, dtos, 15));
        }
    }

    private Integer countMissNumberShapePosition(int i, List<CqsscSumValDTO> dtos, int num) {
        Integer count = 0;
        for (int j = i; j < dtos.size(); j++) {
            CqsscSumValDTO sumValDTO = dtos.get(j);
            String[] split = sumValDTO.getNumber().split(",");
            int shi = Integer.parseInt(split[0]);
            int ge = Integer.parseInt(split[1]);
            switch (num) {
                case 0: // ??????
                    if (shi > 4 && ge > 4) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 1: // ??????
                    if (shi > 4 && ge < 5) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 2: // ??????
                    if (shi > 4 && ge % 2 != 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 3: // ??????
                    if (shi > 4 && ge % 2 == 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 4: // ??????
                    if (shi < 5 && ge > 4) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 5: // ??????
                    if (shi < 5 && ge < 5) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 6: // ??????
                    if (shi < 5 && ge % 2 != 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 7: // ??????
                    if (shi < 5 && ge % 2 == 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 8: // ??????
                    if (shi % 2 != 0 && ge > 4) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 9: // ??????
                    if (shi % 2 != 0 && ge < 5) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 10: // ??????
                    if (shi % 2 != 0 && ge % 2 != 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 11: // ??????
                    if (shi % 2 != 0 && ge % 2 == 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 12: // ??????
                    if (shi % 2 == 0 && ge > 4) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 13: // ??????
                    if (shi % 2 == 0 && ge < 5) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 14: // ??????
                    if (shi % 2 == 0 && ge % 2 != 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                default: // ??????
                    if (shi % 2 == 0 && ge % 2 == 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;
            }
        }
        return count;
    }

    private CqsscShapeDTO countMaxContinuousShape(List<CqsscShapeDTO> list) {
        Integer max1 = 0, max2 = 0, max3 = 0, max4 = 0;
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        for (CqsscShapeDTO dto : list) {
            if (!dto.getBig().equals(0)) {
                count1++;
            } else {
                max1 = max1 > count1 ? max1 : count1;
                count1 = 0;
            }
            if (!dto.getSmall().equals(0)) {
                count2++;
            } else {
                max2 = max2 > count2 ? max2 : count2;
                count2 = 0;
            }
            if (!dto.getSingular().equals(0)) {
                count3++;
            } else {
                max3 = max3 > count3 ? max3 : count3;
                count3 = 0;
            }
            if (!dto.getQuantity().equals(0)) {
                count4++;
            } else {
                max4 = max4 > count4 ? max4 : count4;
                count4 = 0;
            }
        }
        CqsscShapeDTO dto = new CqsscShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig(max1);
        dto.setSmall(max2);
        dto.setSingular(max3);
        dto.setQuantity(max4);
        return dto;
    }

    private CqsscShapeDTO countAvgMissValueShape(Integer limit, CqsscShapeDTO openCount) {
        CqsscShapeDTO dto = new CqsscShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig((limit - openCount.getBig()) / (openCount.getBig() + 1));
        dto.setSmall((limit - openCount.getSmall()) / (openCount.getSmall() + 1));
        dto.setSingular((limit - openCount.getSingular()) / (openCount.getSingular() + 1));
        dto.setQuantity((limit - openCount.getQuantity()) / (openCount.getQuantity() + 1));
        return dto;
    }

    private CqsscShapeDTO countMaxMissValueShape(List<CqsscShapeDTO> list) {
        Integer max1 = 0, max2 = 0, max3 = 0, max4 = 0;
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        for (CqsscShapeDTO dto : list) {
            if (dto.getBig().equals(0)) {
                count1++;
            } else {
                max1 = max1 > count1 ? max1 : count1;
                count1 = 0;
            }
            if (dto.getSmall().equals(0)) {
                count2++;
            } else {
                max2 = max2 > count2 ? max2 : count2;
                count2 = 0;
            }
            if (dto.getSingular().equals(0)) {
                count3++;
            } else {
                max3 = max3 > count3 ? max3 : count3;
                count3 = 0;
            }
            if (dto.getQuantity().equals(0)) {
                count4++;
            } else {
                max4 = max4 > count4 ? max4 : count4;
                count4 = 0;
            }
        }
        CqsscShapeDTO dto = new CqsscShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig(max1);
        dto.setSmall(max2);
        dto.setSingular(max3);
        dto.setQuantity(max4);
        return dto;
    }

    private CqsscShapeDTO countSumOpenCountShape(List<CqsscShapeDTO> list) {
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        for (CqsscShapeDTO dto : list) {
            if (!dto.getBig().equals(0)) {
                count1++;
            }
            if (!dto.getSmall().equals(0)) {
                count2++;
            }
            if (!dto.getSingular().equals(0)) {
                count3++;
            }
            if (!dto.getQuantity().equals(0)) {
                count4++;
            }
        }
        CqsscShapeDTO dto = new CqsscShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig(count1);
        dto.setSmall(count2);
        dto.setSingular(count3);
        dto.setQuantity(count4);
        return dto;
    }

    private void countMissValueShape(List<CqsscShapeDTO> dtos) {
        for (CqsscShapeDTO dto : dtos) {
            String[] strs = dto.getNumber().split(",");
            int shi = Integer.parseInt(strs[0]);
            int ge = Integer.parseInt(strs[1]);
            if (shi > 4 && ge > 4) {
                dto.setBig(2);
                dto.setSmall(0);
            } else if (shi > 4 || ge > 4) {
                dto.setBig(1);
                dto.setSmall(1);
            } else {
                dto.setBig(0);
                dto.setSmall(2);
            }
            if (shi % 2 == 0 && ge % 2 == 0) {
                dto.setSingular(0);
                dto.setQuantity(2);
            } else if (shi % 2 == 0 || ge % 2 == 0) {
                dto.setSingular(1);
                dto.setQuantity(1);
            } else {
                dto.setSingular(2);
                dto.setQuantity(0);
            }
        }
    }

    /**
     * ?????????????????????
     *
     * @param list ??????????????????
     */
    private void countMissValueByNumType(List<CqsscRatioDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            CqsscRatioDTO dto = list.get(i);
            dto.setRatio1(this.countMissNumberType(i, list, 1));
            dto.setRatio2(this.countMissNumberType(i, list, 2));
            dto.setRatio3(this.countMissNumberType(i, list, 3));
            dto.setRatio4(this.countMissNumberType(i, list, 4));
        }
    }

    /**
     * ???????????????
     *
     * @param i    ??????????????????
     * @param list ??????????????????
     * @param num  ????????????
     * @return
     */
    private Integer countMissNumberType(int i, List<CqsscRatioDTO> list, int num) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            CqsscRatioDTO dto = list.get(j);
            String[] strs = dto.getNumber().split(",");
            String str1 = strs[0], str2 = strs[1], str3 = strs[2];
            switch (num) {
                case 1:
                    if ((str1.equals(str2) || str1.equals(str3) || str2.equals(str3)) && !(str1.equals(str2) && str2.equals(str3))) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 2:
                    if (!str1.equals(str2) && !str1.equals(str3) && !str2.equals(str3)) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case 3:
                    if (str1.equals(str2) && str2.equals(str3)) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                default:
                    return -1;
            }
        }
        return count;
    }

    /**
     * ???????????????????????????
     *
     * @param list ???????????????
     * @return
     */
    private CqsscRatioDTO countMaxContinuousRatio(List<CqsscRatioDTO> list) {
        CqsscRatioDTO maxContinuousVal = new CqsscRatioDTO();
        maxContinuousVal.setIssue("???????????????");
        maxContinuousVal.setRatio1(this.getContinuousValueRatio(list, 1));
        maxContinuousVal.setRatio2(this.getContinuousValueRatio(list, 2));
        maxContinuousVal.setRatio3(this.getContinuousValueRatio(list, 3));
        maxContinuousVal.setRatio4(this.getContinuousValueRatio(list, 4));
        return maxContinuousVal;
    }

    /**
     * ?????????????????????
     *
     * @param list ???????????????
     * @param type ??????
     * @return
     */
    private Integer getContinuousValueRatio(List<CqsscRatioDTO> list, Integer type) {
        Integer max = 0, count = 0;
        for (CqsscRatioDTO dto : list) {
            Integer num;
            switch (type) {
                case 1:
                    num = dto.getRatio1();
                    break;

                case 2:
                    num = dto.getRatio2();
                    break;

                case 3:
                    num = dto.getRatio3();
                    break;

                default:
                    num = dto.getRatio4();
                    break;
            }
            if (num.equals(0)) {
                count++;
            } else {
                count = 0;
            }
            max = max > count ? max : count;
        }
        return max;
    }

    /**
     * ???????????????????????????
     *
     * @param size      ????????????
     * @param openCount ?????????????????????
     * @return
     */
    private CqsscRatioDTO countAvgMissValueRatio(Integer size, CqsscRatioDTO openCount) {
        CqsscRatioDTO avgMissVal = new CqsscRatioDTO();
        avgMissVal.setIssue("???????????????");
        avgMissVal.setRatio1(Math.round((size - openCount.getRatio1()) / (openCount.getRatio1() + 1F)));
        avgMissVal.setRatio2(Math.round((size - openCount.getRatio2()) / (openCount.getRatio2() + 1F)));
        avgMissVal.setRatio3(Math.round((size - openCount.getRatio3()) / (openCount.getRatio3() + 1F)));
        avgMissVal.setRatio4(Math.round((size - openCount.getRatio4()) / (openCount.getRatio4() + 1F)));
        return avgMissVal;
    }

    /**
     * ???????????????????????????
     *
     * @param list ???????????????
     * @return
     */
    private CqsscRatioDTO countMaxMissValueRatio(List<CqsscRatioDTO> list) {
        CqsscRatioDTO maxMissVal = new CqsscRatioDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }
        // ????????????
        Integer num1 = 0, num2 = 0, num3 = 0, num4 = 0;
        // ??????
        for (CqsscRatioDTO dto : list) {
            num1 = num1 > dto.getRatio1() ? num1 : dto.getRatio1();
            num2 = num2 > dto.getRatio2() ? num2 : dto.getRatio2();
            num3 = num3 > dto.getRatio3() ? num3 : dto.getRatio3();
            num4 = num4 > dto.getRatio4() ? num4 : dto.getRatio4();
        }
        // ??????
        maxMissVal.setRatio1(num1);
        maxMissVal.setRatio2(num2);
        maxMissVal.setRatio3(num3);
        maxMissVal.setRatio4(num4);
        return maxMissVal;
    }

    /**
     * ???????????????????????????
     *
     * @param list ???????????????
     * @return
     */
    private CqsscRatioDTO countSumOpenCountRatio(List<CqsscRatioDTO> list) {
        CqsscRatioDTO openCount = new CqsscRatioDTO();
        openCount.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // ????????????
        Integer ratio1 = 0, ratio2 = 0, ratio3 = 0, ratio4 = 0;
        // ??????
        for (CqsscRatioDTO dto : list) {
            if (dto.getRatio1().equals(0)) {
                ratio1++;
            }
            if (dto.getRatio2().equals(0)) {
                ratio2++;
            }
            if (dto.getRatio3().equals(0)) {
                ratio3++;
            }
            if (dto.getRatio4().equals(0)) {
                ratio4++;
            }
        }
        // ??????
        openCount.setRatio1(ratio1);
        openCount.setRatio2(ratio2);
        openCount.setRatio3(ratio3);
        openCount.setRatio4(ratio4);
        return openCount;
    }

    /**
     * ???????????????????????????
     *
     * @param list ???????????????
     * @param type ??????
     */
    private void countMissValueByRatio(List<CqsscRatioDTO> list, Integer type) {
        for (int i = 0; i < list.size(); i++) {
            CqsscRatioDTO dto = list.get(i);
            dto.setRatio1(this.countMissNumberRatio(i, list, type, 1));
            dto.setRatio2(this.countMissNumberRatio(i, list, type, 2));
            dto.setRatio3(this.countMissNumberRatio(i, list, type, 3));
            dto.setRatio4(this.countMissNumberRatio(i, list, type, 4));
        }
    }

    /**
     * ???????????????
     *
     * @param i    ????????????
     * @param list ????????????
     * @param type ??????
     * @param num  ??????
     * @return
     */
    private Integer countMissNumberRatio(int i, List<CqsscRatioDTO> list, Integer type, Integer num) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            CqsscRatioDTO dto = list.get(j);
            String[] strs = dto.getNumber().split(",");
            String ratio = this.getRatio(strs, type);
            if (strs.length == 2) {
                switch (num) {
                    case 1:
                        if ("2:0".equals(ratio)) {
                            return count;
                        } else {
                            count++;
                        }
                        break;

                    case 2:
                        if ("1:1".equals(ratio)) {
                            return count;
                        } else {
                            count++;
                        }
                        break;

                    case 3:
                        if ("0:2".equals(ratio)) {
                            return count;
                        } else {
                            count++;
                        }
                        break;

                    default:
                        return -1;
                }
            } else {
                switch (num) {
                    case 1:
                        if ("3:0".equals(ratio)) {
                            return count;
                        } else {
                            count++;
                        }
                        break;

                    case 2:
                        if ("2:1".equals(ratio)) {
                            return count;
                        } else {
                            count++;
                        }
                        break;

                    case 3:
                        if ("1:2".equals(ratio)) {
                            return count;
                        } else {
                            count++;
                        }
                        break;

                    default:
                        if ("0:3".equals(ratio)) {
                            return count;
                        } else {
                            count++;
                        }
                        break;
                }
            }
        }
        return count;
    }

    /**
     * ????????????
     *
     * @param opNumber ????????????
     * @param type     ??????
     * @return
     */
    private String getRatio(String[] opNumber, Integer type) {
        Integer a = 0, b = 0;
        for (String str : opNumber) {
            Integer num = Integer.parseInt(str);
            switch (type) {
                case 1:
                    if (num > 4) {
                        a++;
                    } else {
                        b++;
                    }
                    break;

                case 2:
                    if (num % 2 == 1) {
                        a++;
                    } else {
                        b++;
                    }
                    break;

                default:
                    if (num.equals(1) || num.equals(2) || num.equals(3) || num.equals(5) || num.equals(7)) {
                        a++;
                    } else {
                        b++;
                    }
                    break;
            }
        }
        return a.toString() + ":" + b.toString();
    }

    /**
     * ?????????????????????
     *
     * @param list ??????????????????
     */
    private void countMissValueByToGo(List<CqsscShapeDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            CqsscShapeDTO dto = list.get(i);
            dto.setBig(this.countMissNumberToGo(i, list, ShapeTypeEnum.BIG));
            dto.setSmall(this.countMissNumberToGo(i, list, ShapeTypeEnum.SMALL));
            dto.setComposite(this.countMissNumberToGo(i, list, ShapeTypeEnum.COMPOSITE));

            dto.setPrime(this.countMissNumberToGo(i, list, ShapeTypeEnum.PRIME));
            dto.setSingular(this.countMissNumberToGo(i, list, ShapeTypeEnum.SINGULAR));
            dto.setQuantity(this.countMissNumberToGo(i, list, ShapeTypeEnum.QUANTITY));
        }
    }

    /**
     * ???????????????????????????
     *
     * @param i    ??????????????????
     * @param list ????????????
     * @param type ??????
     * @return
     */
    private Integer countMissNumberToGo(int i, List<CqsscShapeDTO> list, ShapeTypeEnum type) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            CqsscShapeDTO dto1 = list.get(j);
            if (j + 1 >= list.size()) {
                return count;
            }
            CqsscShapeDTO dto2 = list.get(j + 1);
            Integer number1 = Integer.parseInt(dto1.getNumber());
            Integer number2 = Integer.parseInt(dto2.getNumber());
            switch (type) {
                case BIG:
                    if (number1 > number2) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case SMALL:
                    if (number1 < number2) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case COMPOSITE:
                    if (number1.equals(number2)) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                default:
                    return -1;
            }
        }
        return count;
    }

    /**
     * ??????012???
     *
     * @param sg   ??????
     * @param type ??????
     * @return
     */
    private Integer count012Way(CqsscLotterySg sg, Integer type) {
        Integer number = this.getNumber(type, sg);
        return number % 3;
    }

    /**
     * ???????????????????????????
     *
     * @param list
     * @return
     */
    private CqsscShapeDTO countMaxContinuousOther(List<CqsscShapeDTO> list) {
        CqsscShapeDTO maxContinuousVal = new CqsscShapeDTO();
        maxContinuousVal.setIssue("???????????????");
        maxContinuousVal.setBig(this.getContinuousValueOther(list, ShapeTypeEnum.BIG));
        maxContinuousVal.setSmall(this.getContinuousValueOther(list, ShapeTypeEnum.SMALL));
        maxContinuousVal.setSingular(this.getContinuousValueOther(list, ShapeTypeEnum.SINGULAR));
        maxContinuousVal.setQuantity(this.getContinuousValueOther(list, ShapeTypeEnum.QUANTITY));
        maxContinuousVal.setPrime(this.getContinuousValueOther(list, ShapeTypeEnum.PRIME));
        maxContinuousVal.setComposite(this.getContinuousValueOther(list, ShapeTypeEnum.COMPOSITE));
        return maxContinuousVal;
    }

    /**
     * ??????????????????????????????
     *
     * @param list ????????????
     * @param type ??????
     * @return
     */
    private Integer getContinuousValueOther(List<CqsscShapeDTO> list, ShapeTypeEnum type) {
        Integer max = 0, count = 0;
        for (CqsscShapeDTO dto : list) {
            Integer num;
            switch (type) {
                case BIG:
                    num = dto.getBig();
                    break;

                case SMALL:
                    num = dto.getSmall();
                    break;

                case SINGULAR:
                    num = dto.getSingular();
                    break;

                case QUANTITY:
                    num = dto.getQuantity();
                    break;

                case PRIME:
                    num = dto.getPrime();
                    break;

                default:
                    num = dto.getComposite();
                    break;
            }
            if (num.equals(0)) {
                count++;
            } else {
                count = 0;
            }
            max = max > count ? max : count;
        }
        return max;
    }

    /**
     * ???????????????????????????
     *
     * @param size      ????????????
     * @param openCount ???????????????
     * @return
     */
    private CqsscShapeDTO countAvgMissValueOther(Integer size, CqsscShapeDTO openCount) {
        CqsscShapeDTO avgMissVal = new CqsscShapeDTO();
        avgMissVal.setIssue("???????????????");
        avgMissVal.setBig((size - openCount.getBig()) / (openCount.getBig() + 1));
        avgMissVal.setSmall((size - openCount.getSmall()) / (openCount.getSmall() + 1));
        avgMissVal.setSingular((size - openCount.getSingular()) / (openCount.getSingular() + 1));
        avgMissVal.setQuantity((size - openCount.getQuantity()) / (openCount.getQuantity() + 1));
        avgMissVal.setPrime((size - openCount.getPrime()) / (openCount.getPrime() + 1));
        avgMissVal.setComposite((size - openCount.getComposite()) / (openCount.getComposite() + 1));
        return avgMissVal;
    }

    /**
     * ???????????????????????????
     *
     * @param list ????????????
     * @return
     */
    private CqsscShapeDTO countMaxMissValueOther(List<CqsscShapeDTO> list) {
        CqsscShapeDTO maxMissVal = new CqsscShapeDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }
        // ????????????
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0;
        // ??????
        for (CqsscShapeDTO dto : list) {
            num0 = num0 > dto.getBig() ? num0 : dto.getBig();
            num1 = num1 > dto.getSmall() ? num1 : dto.getSmall();
            num2 = num2 > dto.getSingular() ? num2 : dto.getSingular();
            num3 = num3 > dto.getQuantity() ? num3 : dto.getQuantity();
            num4 = num4 > dto.getPrime() ? num4 : dto.getPrime();
            num5 = num5 > dto.getComposite() ? num5 : dto.getComposite();
        }
        // ??????
        maxMissVal.setBig(num0);
        maxMissVal.setSmall(num1);
        maxMissVal.setSingular(num2);
        maxMissVal.setQuantity(num3);
        maxMissVal.setPrime(num4);
        maxMissVal.setComposite(num5);
        return maxMissVal;
    }

    /**
     * ?????? ?????????????????????
     *
     * @param list ????????????
     * @return
     */
    private CqsscShapeDTO countSumOpenCountOther(List<CqsscShapeDTO> list) {
        CqsscShapeDTO openCount = new CqsscShapeDTO();
        openCount.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // ????????????
        Integer bigCount = 0, smallCount = 0, singularCount = 0, quantityCount = 0, primeCount = 0, compositeCount = 0;
        // ??????
        for (CqsscShapeDTO dto : list) {
            if (dto.getBig().equals(0)) {
                bigCount++;
            }
            if (dto.getSmall().equals(0)) {
                smallCount++;
            }
            if (dto.getSingular().equals(0)) {
                singularCount++;
            }
            if (dto.getQuantity().equals(0)) {
                quantityCount++;
            }
            if (dto.getPrime().equals(0)) {
                primeCount++;
            }
            if (dto.getComposite().equals(0)) {
                compositeCount++;
            }
        }
        // ??????
        openCount.setBig(bigCount);
        openCount.setSmall(smallCount);
        openCount.setSingular(singularCount);
        openCount.setQuantity(quantityCount);
        openCount.setPrime(primeCount);
        openCount.setComposite(compositeCount);
        return openCount;
    }

    /**
     * ????????????|??????|???????????????
     *
     * @param list ????????????
     */
    private void countMissValueByShape(List<CqsscShapeDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            CqsscShapeDTO dto = list.get(i);
            dto.setBig(this.countMissNumberShape(i, list, ShapeTypeEnum.BIG));
            dto.setSmall(this.countMissNumberShape(i, list, ShapeTypeEnum.SMALL));
            dto.setSingular(this.countMissNumberShape(i, list, ShapeTypeEnum.SINGULAR));
            dto.setQuantity(this.countMissNumberShape(i, list, ShapeTypeEnum.QUANTITY));
            dto.setPrime(this.countMissNumberShape(i, list, ShapeTypeEnum.PRIME));
            dto.setComposite(this.countMissNumberShape(i, list, ShapeTypeEnum.COMPOSITE));
        }
    }

    /**
     * ???????????????????????????
     *
     * @param i    ??????????????????
     * @param list ????????????
     * @param type ??????
     * @return
     */
    private Integer countMissNumberShape(int i, List<CqsscShapeDTO> list, ShapeTypeEnum type) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            CqsscShapeDTO dto = list.get(j);
            Integer number = Integer.parseInt(dto.getNumber());
            int n = 0;
            switch (type) {
                case BIG:
                    if (number > 4) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case SMALL:
                    if (number < 5) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case SINGULAR:
                    if (number % 2 == 1) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case QUANTITY:
                    if (number % 2 == 0) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                case PRIME:
                    if (this.isPrime(number)) {
                        return count;
                    } else {
                        count++;
                    }
                    break;

                default:
                    if (this.isComposite(number)) {
                        return count;
                    } else {
                        count++;
                    }
                    break;
            }
        }
        return count;
    }

    /**
     * ??????????????????????????????
     *
     * @param num ???????????????
     * @return
     */
    private boolean isPrime(int num) {
        if (num == 1 || num == 0) {
            return false; // 0???1????????????
        }
        if (num == 2) {
            return true; // 2????????????
        }
        if (num < 2 || num % 2 == 0) {
            return false;//????????????2???????????????
        }
        boolean bool = true;
        for (int i = 2; i <= num / 2 + 1; i++) {
            if (num % i == 0) {
                bool = false;
                break;
            }
        }
        return bool;
    }

    /**
     * ??????????????????????????????
     *
     * @param num ???????????????
     * @return
     */
    private boolean isComposite(int num) {
        if (num == 0 || num == 1 || num == 2) {
            return false; // 0,1,2????????????
        }
        for (int i = 2; i <= num / 2 + 1; i++) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @param sg ??????
     * @return
     */
    private String getOpenNumberStr(CqsscLotterySg sg) {
        StringBuilder sb = new StringBuilder();
        sb.append(sg.getWan());
        sb.append(",");
        sb.append(sg.getQian());
        sb.append(",");
        sb.append(sg.getBai());
        sb.append(",");
        sb.append(sg.getShi());
        sb.append(",");
        sb.append(sg.getGe());
        return sb.toString();
    }

    /**
     * ????????????
     *
     * @param sg   ??????
     * @param type ?????? ??? 2 ?????? | 3 ?????? | 5 ??????
     * @return
     */
    private Integer countSumTail(CqsscLotterySg sg, Integer type) {
        Integer sum = this.countSumValue(sg, type);
        return sum % 10;
    }

    /**
     * ????????????
     *
     * @param sg   ??????
     * @param type ?????? ??? 2 ?????? | 3 ?????? | 5 ??????
     * @return
     */
    private Integer countSumValue(CqsscLotterySg sg, Integer type) {
        Integer count = 0;
        // ??????
        if (sg == null) {
            return count;
        }

        switch (type) {
            case 5:
                count += sg.getWan() + sg.getQian();

            case 3:
                count += sg.getBai();

            default:
                count += sg.getShi() + sg.getGe();
                break;
        }

        return count;
    }

    /**
     * ??????????????????????????????
     *
     * @param list   ????????????
     * @param number ??????
     * @return
     */
    private Integer getContinuousValue(List<CqsscMissNumDTO> list, Integer number) {
        Integer max = 0, count = 0, first = 0;
        String beforeValue = "-1";
        for (CqsscMissNumDTO dto : list) {
            String realNum = dto.getNumber();
            // ????????????????????????????????????????????????????????????count?????????1
            if (first.equals(0) && realNum.contains(number.toString())) {
                first++;
                count = 1;
            }
            // ??????????????????????????????????????????????????????????????????????????????count ++
            if (realNum.contains(number.toString()) && realNum.contains(beforeValue)) {
                count++;
                beforeValue = number.toString();
            } else if (realNum.contains(number.toString())) {
                beforeValue = number.toString();
            } else {
                beforeValue = "-1";
                first = 0;
            }
            max = max > count ? max : count;
        }
        return max;
    }

    /**
     * ?????????????????????????????????
     *
     * @param cqsscLotterySgs ????????????
     * @param number          ??????
     * @return
     */
    private Integer getSumOpenCount(List<CqsscLotterySg> cqsscLotterySgs, Integer number, List<Integer> types) {
        int count = 0;
        for (int i = 0; i < 100 && cqsscLotterySgs.size() > i; i++) {
            CqsscLotterySg sg = cqsscLotterySgs.get(i);
            List<Integer> realNums = new ArrayList<>();
            if (CollectionUtils.isEmpty(types)) {
                realNums.add(sg.getGe());
            } else {
                for (Integer type : types) {
                    realNums.add(this.getNumber(type, sg));
                }
            }
            if (realNums.contains(number)) {
                count++;
            }
        }
        return count;
    }

    /**
     * ???????????????
     *
     * @param i               ????????????????????????????????????
     * @param cqsscLotterySgs ???????????????
     * @param num             ?????????0-9???
     * @return
     */
    private Integer getMissingCount(int i, List<CqsscLotterySg> cqsscLotterySgs, int num, Integer openNum) {
        int number = 0;

        for (int j = i; j < cqsscLotterySgs.size(); j++) {
            CqsscLotterySg sg = cqsscLotterySgs.get(j);
            Integer sgNumber = this.getNumber(openNum, sg);
            if (j == i && sgNumber.equals(num)) {
                number = num;
                break;
            } else if (sgNumber.equals(num)) {
                break;
            } else {
                number++;
            }
        }
        return number;
    }

    /**
     * ??????????????????????????????
     *
     * @param number ???????????? 6 ??????????????????
     * @param sg     ??????
     * @return
     */
    private Integer getNumber(Integer number, CqsscLotterySg sg) {
        Integer sgNumber;
        switch (number) {
            case 1:
                sgNumber = sg.getWan();
                break;

            case 2:
                sgNumber = sg.getQian();
                break;

            case 3:
                sgNumber = sg.getBai();
                break;

            case 4:
                sgNumber = sg.getShi();
                break;

            case 5:
                sgNumber = sg.getGe();
                break;

            default:
                sgNumber = sg.getShi() + sg.getGe();
                break;
        }
        return sgNumber;
    }

    /**
     * ??????????????????????????????
     *
     * @param type ?????????2 ???????????? | 3 ????????????
     * @param sg   ??????
     * @return
     */
    private String getOpenNumberGroup(Integer type, CqsscLotterySg sg) {
        StringBuilder sb = new StringBuilder();
        if (sg == null) {
            return "";
        }
        List<Integer> weiList = new ArrayList<>();
        switch (type) {
            case 3:
                weiList.add(3);
            default:
                weiList.add(4);
                weiList.add(5);
        }
        Iterator<Integer> iterator = weiList.iterator();
        while (iterator.hasNext()) {
            Integer num = iterator.next();
            sb.append(this.getNumber(num, sg));
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * ???????????????
     *
     * @param i               ????????????????????????????????????
     * @param cqsscLotterySgs ???????????????
     * @param num             ?????????0-9???
     * @return
     */
    private Integer getMissingCount(int i, List<CqsscLotterySg> cqsscLotterySgs, int num, List<Integer> openNums) {
        int number = 0;

        for (int j = i; j < cqsscLotterySgs.size(); j++) {
            CqsscLotterySg sg = cqsscLotterySgs.get(j);

            List<Integer> sgNumbers = new ArrayList<>();
            for (Integer openNum : openNums) {
                sgNumbers.add(this.getNumber(openNum, sg));
            }
            if (j == i && sgNumbers.contains(num)) {
                number = num;
                break;
            } else if (sgNumbers.contains(num)) {
                break;
            } else {
                number++;
            }
        }
        return number;
    }

    /**
     * ???????????????????????????
     *
     * @param list
     * @return
     */
    private CqsscMissNumDTO countMaxContinuous(List<CqsscMissNumDTO> list) {
        CqsscMissNumDTO continuous = new CqsscMissNumDTO();
        continuous.setIssue("???????????????");
        continuous.setMissing0(this.getContinuousValue(list, 0));
        continuous.setMissing1(this.getContinuousValue(list, 1));
        continuous.setMissing2(this.getContinuousValue(list, 2));
        continuous.setMissing3(this.getContinuousValue(list, 3));
        continuous.setMissing4(this.getContinuousValue(list, 4));
        continuous.setMissing5(this.getContinuousValue(list, 5));
        continuous.setMissing6(this.getContinuousValue(list, 6));
        continuous.setMissing7(this.getContinuousValue(list, 7));
        continuous.setMissing8(this.getContinuousValue(list, 8));
        continuous.setMissing9(this.getContinuousValue(list, 9));
        return continuous;
    }

    /**
     * ?????????????????????
     * ?????????????????????/(???????????????+1)
     *
     * @param size      ????????????
     * @param openCount ???????????????
     * @return
     */
    private CqsscMissNumDTO countAvgMissValue(Integer size, CqsscMissNumDTO openCount) {
        CqsscMissNumDTO avgMissVal = new CqsscMissNumDTO();
        avgMissVal.setIssue("???????????????");
        avgMissVal.setMissing0((size - openCount.getMissing0()) / (openCount.getMissing0() + 1));
        avgMissVal.setMissing1((size - openCount.getMissing1()) / (openCount.getMissing1() + 1));
        avgMissVal.setMissing2((size - openCount.getMissing2()) / (openCount.getMissing2() + 1));
        avgMissVal.setMissing3((size - openCount.getMissing3()) / (openCount.getMissing3() + 1));
        avgMissVal.setMissing4((size - openCount.getMissing4()) / (openCount.getMissing4() + 1));
        avgMissVal.setMissing5((size - openCount.getMissing5()) / (openCount.getMissing5() + 1));
        avgMissVal.setMissing6((size - openCount.getMissing6()) / (openCount.getMissing6() + 1));
        avgMissVal.setMissing7((size - openCount.getMissing7()) / (openCount.getMissing7() + 1));
        avgMissVal.setMissing8((size - openCount.getMissing8()) / (openCount.getMissing8() + 1));
        avgMissVal.setMissing9((size - openCount.getMissing9()) / (openCount.getMissing9() + 1));
        return avgMissVal;
    }

    /**
     * ???????????????????????????
     *
     * @param list ????????????
     * @return
     */
    private CqsscMissNumDTO countMaxMissValue(List<CqsscMissNumDTO> list) {
        CqsscMissNumDTO maxMissVal = new CqsscMissNumDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }
        // ????????????
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0, num9 = 0;
        // ??????
        for (CqsscMissNumDTO dto : list) {
            num0 = num0 > dto.getMissing0() ? num0 : dto.getMissing0();
            num1 = num1 > dto.getMissing1() ? num1 : dto.getMissing1();
            num2 = num2 > dto.getMissing2() ? num2 : dto.getMissing2();
            num3 = num3 > dto.getMissing3() ? num3 : dto.getMissing3();
            num4 = num4 > dto.getMissing4() ? num4 : dto.getMissing4();
            num5 = num5 > dto.getMissing5() ? num5 : dto.getMissing5();
            num6 = num6 > dto.getMissing6() ? num6 : dto.getMissing6();
            num7 = num7 > dto.getMissing7() ? num7 : dto.getMissing7();
            num8 = num8 > dto.getMissing8() ? num8 : dto.getMissing8();
            num9 = num9 > dto.getMissing9() ? num9 : dto.getMissing9();
        }
        // ??????
        maxMissVal.setMissing0(num0);
        maxMissVal.setMissing1(num1);
        maxMissVal.setMissing2(num2);
        maxMissVal.setMissing3(num3);
        maxMissVal.setMissing4(num4);
        maxMissVal.setMissing5(num5);
        maxMissVal.setMissing6(num6);
        maxMissVal.setMissing7(num7);
        maxMissVal.setMissing8(num8);
        maxMissVal.setMissing9(num9);
        return maxMissVal;
    }

    /**
     * ???????????????????????????
     *
     * @param list ????????????
     * @return
     */
    private CqsscMissNumDTO countSumOpenCount(List<CqsscMissNumDTO> list) {
        CqsscMissNumDTO openCount = new CqsscMissNumDTO();
        openCount.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // ????????????
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0, num9 = 0;
        // ??????
        for (CqsscMissNumDTO dto : list) {
            String openNumber = dto.getNumber();
            if (openNumber.contains("0")) {
                num0++;
            }
            if (openNumber.contains("1")) {
                num1++;
            }
            if (openNumber.contains("2")) {
                num2++;
            }
            if (openNumber.contains("3")) {
                num3++;
            }
            if (openNumber.contains("4")) {
                num4++;
            }
            if (openNumber.contains("5")) {
                num5++;
            }
            if (openNumber.contains("6")) {
                num6++;
            }
            if (openNumber.contains("7")) {
                num7++;
            }
            if (openNumber.contains("8")) {
                num8++;
            }
            if (openNumber.contains("9")) {
                num9++;
            }
        }
        // ??????
        openCount.setMissing0(num0);
        openCount.setMissing1(num1);
        openCount.setMissing2(num2);
        openCount.setMissing3(num3);
        openCount.setMissing4(num4);
        openCount.setMissing5(num5);
        openCount.setMissing6(num6);
        openCount.setMissing7(num7);
        openCount.setMissing8(num8);
        openCount.setMissing9(num9);
        return openCount;
    }

    /**
     * ??????????????????
     *
     * @param sg   ??????
     * @param type ??????
     * @return
     */
    private Integer countSpan(CqsscLotterySg sg, Integer type) {
        Integer shi = sg.getShi();
        Integer ge = sg.getGe();
        if (type.equals(2)) {
            return Math.abs(shi - ge);
        }
        Integer bai = sg.getBai();
        Integer max = bai > shi ? bai : shi;
        max = max > ge ? max : ge;
        Integer min = bai < shi ? bai : shi;
        min = min < ge ? min : ge;
        return max - min;
    }

    /**
     * ???????????????
     *
     * @param list ???????????????
     */
    private void countMissValue(List<CqsscMissNumDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            CqsscMissNumDTO missNumDTO = list.get(i);
            missNumDTO.setMissing0(this.countMissNumber(i, list, 0));
            missNumDTO.setMissing1(this.countMissNumber(i, list, 1));
            missNumDTO.setMissing2(this.countMissNumber(i, list, 2));
            missNumDTO.setMissing3(this.countMissNumber(i, list, 3));
            missNumDTO.setMissing4(this.countMissNumber(i, list, 4));
            missNumDTO.setMissing5(this.countMissNumber(i, list, 5));
            missNumDTO.setMissing6(this.countMissNumber(i, list, 6));
            missNumDTO.setMissing7(this.countMissNumber(i, list, 7));
            missNumDTO.setMissing8(this.countMissNumber(i, list, 8));
            missNumDTO.setMissing9(this.countMissNumber(i, list, 9));
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param i      ?????????????????????
     * @param list   ????????????
     * @param number ????????????
     * @return
     */
    private Integer countMissNumber(int i, List<CqsscMissNumDTO> list, Integer number) {
        int count = 0;
        for (int j = i; j < list.size(); j++) {
            CqsscMissNumDTO dto = list.get(j);
            String openNumber = dto.getNumber();
            if (j == i && openNumber.contains(number.toString())) {
                count = number;
                break;
            } else if (openNumber.contains(number.toString())) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }

    private CqsscLotterySg getNextCqsscLotterySg() {
        Date nowDate = new Date();
        CqsscLotterySgExample next_example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria next_cqsscCriteria = next_example.createCriteria();
        next_cqsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        next_cqsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(nowDate));
        next_example.setOrderByClause("ideal_time ASC");
        CqsscLotterySg next_cqsscLotterySg = this.cqsscLotterySgMapper.selectOneByExample(next_example);
        return next_cqsscLotterySg;
    }

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullResult();
        try {
            // ???????????????????????????
            String nextRedisKey = RedisKeys.CQSSC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.CQSSC.getRedisTime();
            CqsscLotterySg nextCqsscLotterySg = (CqsscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextCqsscLotterySg == null) {
                nextCqsscLotterySg = this.getNextCqsscLotterySg();
                // ?????????????????????
                redisTemplate.opsForValue().set(nextRedisKey, nextCqsscLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // ????????????????????????
            String redisKey = RedisKeys.CQSSC_RESULT_VALUE;
            CqsscLotterySg cqsscLotterySg = (CqsscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (cqsscLotterySg == null) {
                // ??????????????????????????????
                cqsscLotterySg = this.getCqsscLotterySg();
                redisTemplate.opsForValue().set(redisKey, cqsscLotterySg);
            }

            if (nextCqsscLotterySg != null) {
                String nextIssue = nextCqsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextCqsscLotterySg.getIssue();
                String txffnextIssue = cqsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : cqsscLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextCqsscLotterySg = this.getNextCqsscLotterySg();
                        // ?????????????????????
                        redisTemplate.opsForValue().set(nextRedisKey, nextCqsscLotterySg, redisTime, TimeUnit.MINUTES);
                        // ??????????????????????????????
                        cqsscLotterySg = this.getCqsscLotterySg();
                        redisTemplate.opsForValue().set(redisKey, cqsscLotterySg);
                    }
                }

                if (cqsscLotterySg != null) {
                    // ??????????????????
                    this.getIssueSumAndAllNumber(cqsscLotterySg, result);
                    // ??????????????????
                    this.openCount(cqsscLotterySg, result);
                }

                if (nextCqsscLotterySg != null) {
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextCqsscLotterySg.getIdealTime()) / 1000L);
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextCqsscLotterySg.getIssue());
                }
            } else {
                result = DefaultResultUtil.getNullResult();

                if (cqsscLotterySg != null) {
                    // ??????????????????
                    this.getIssueSumAndAllNumber(cqsscLotterySg, result);
                    // ??????????????????
                    this.openCount(cqsscLotterySg, result);
                }
            }
        } catch (Exception e) {
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    /**
     * @return CqsscLotterySg
     * @Title: getCqsscLotterySg
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???1?????????3:32:47
     */
    public CqsscLotterySg getCqsscLotterySg() {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        CqsscLotterySg cqsscLotterySg = this.cqsscLotterySgMapper.selectOneByExample(example);
        return cqsscLotterySg;
    }

    /**
     * @Title: openCount
     * @Description: ????????????????????????????????????
     */
    public void openCount(CqsscLotterySg cqsscLotterySg, Map<String, Object> result) {
        // ??????????????????
        String issue = cqsscLotterySg.getIssue();
        String openNumString = issue.substring(8, issue.length());
        Integer openNumInteger = Integer.valueOf(openNumString);
        result.put("openCount", openNumInteger);
        // ????????????????????????
        Integer sumCount = CaipiaoSumCountEnum.CQSSC.getSumCount();
        result.put("noOpenCount", sumCount - openNumInteger);
    }

    /**
     * @Title: getIssueSumAndAllNumber
     * @Description: ???????????????????????????
     */
    public void getIssueSumAndAllNumber(CqsscLotterySg cqsscLotterySg, Map<String, Object> result) {
        Integer wan = cqsscLotterySg.getWan();
        Integer qian = cqsscLotterySg.getQian();
        Integer bai = cqsscLotterySg.getBai();
        Integer shi = cqsscLotterySg.getShi();
        Integer ge = cqsscLotterySg.getGe();
        String issue = cqsscLotterySg.getIssue();
        result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
        // ??????????????????
        String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
        // ????????????????????????
        Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
    }

    @Override
    public Map<String, Object> getNewestSgInfoWeb() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "???????????????");

        // ????????????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOrderByClause("issue DESC");
        CqsscLotterySg cqsscLotterySg = this.cqsscLotterySgMapper.selectOneByExample(example);
        if (cqsscLotterySg != null) {
            Integer wan = cqsscLotterySg.getWan();
            Integer qian = cqsscLotterySg.getQian();
            Integer bai = cqsscLotterySg.getBai();
            Integer shi = cqsscLotterySg.getShi();
            Integer ge = cqsscLotterySg.getGe();
            String issue = cqsscLotterySg.getIssue();
            result.put("issue", issue);
            StringBuilder number = new StringBuilder();
            result.put("number", number.append(wan).append(qian).append(bai).append(shi).append(ge).toString());
            result.put("he", wan + qian + bai + shi + ge);
            result.put("time", cqsscLotterySg.getTime());
        } else {
            result.put("issue", null);
            result.put("number", null);
            result.put("he", null);
            result.put("time", null);
        }

        return result;
    }

    @Override
    public Map<String, Object> getNowIssueAndTime() {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????????????????

        CqsscLotterySg next_cqsscLotterySg = getNextCqsscLotterySg();
        // ?????????????????????????????????
        if (next_cqsscLotterySg != null && null != next_cqsscLotterySg.getIdealTime()) {
            result.put("issue", next_cqsscLotterySg.getIssue());
            result.put("time", DateUtils.getTimeMillis(next_cqsscLotterySg.getIdealTime()) / 1000L);
        } else {
            result.put("issue", "");
            result.put("time", "");
        }
        return result;
    }


    /*******************************************************
     *****                   WEB?????????                 *****
     ******************************************************/


    /**
     * ????????????????????????????????????
     *
     * @param number ??????
     * @param limit  ?????????
     * @return
     */
    @Override
    public Map<String, Object> getCqsscBaseTrend(Integer number, Integer limit) {
        Map<String, Object> result = new HashMap<>();

        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> lotterySgs = cqsscLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(lotterySgs)) {
            return result;
        }

        // ?????????????????????????????????
        result = this.trendWei(number, lotterySgs, limit);
        return result;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param number     ??????
     * @param lotterySgs ????????????
     * @param limit      ????????????
     * @return
     */
    private Map<String, Object> trendWei(Integer number, List<CqsscLotterySg> lotterySgs, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ????????????????????????
        List<CqsscMissNumDTO> list;
        switch (number) {
            case 5:
                list = this.missValueByNum(1, lotterySgs, limit);
                result.put("wan", list);
                result.put("wanStat", this.missValCount(list, limit));

            case 4:
                list = this.missValueByNum(2, lotterySgs, limit);
                result.put("qian", list);
                result.put("qianStat", this.missValCount(list, limit));

            case 3:
                list = this.missValueByNum(3, lotterySgs, limit);
                result.put("bai", list);
                result.put("baiStat", this.missValCount(list, limit));

            case 2:
                list = this.missValueByNum(4, lotterySgs, limit);
                result.put("shi", list);
                result.put("shiStat", this.missValCount(list, limit));

            default:
                list = this.missValueByNum(5, lotterySgs, limit);
                result.put("ge", list);
                result.put("geStat", this.missValCount(list, limit));
                break;
        }
        return result;
    }

    /**
     * ??????????????????
     *
     * @param list  ??????????????????
     * @param limit ????????????
     * @return
     */
    private List<CqsscMissNumDTO> missValCount(List<CqsscMissNumDTO> list, Integer limit) {
        List<CqsscMissNumDTO> statistics = new ArrayList<>();
        // ???????????????????????????
        CqsscMissNumDTO openCount = this.countSumOpenCount(list);
        // ???????????????????????????
        CqsscMissNumDTO maxMissVal = this.countMaxMissValue(list);
        // ???????????????????????????
        CqsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);
        // ???????????????????????????
        CqsscMissNumDTO maxContinuous = this.countMaxContinuous(list);
        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);
        return statistics;
    }

    /**
     * ?????????????????????????????????
     *
     * @param number     ?????????1 ?????????2 ?????????3 ?????????4 ?????????5 ?????????
     * @param lotterySgs ????????????
     * @return
     */
    private List<CqsscMissNumDTO> missValueByNum(Integer number, List<CqsscLotterySg> lotterySgs, Integer limit) {
        // ???????????????????????????
        List<CqsscMissNumDTO> dtos = new ArrayList<>();
        // ???????????????????????????????????????
        CqsscMissNumDTO dto;
        for (CqsscLotterySg sg : lotterySgs) {
            dto = new CqsscMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getNumber(number, sg).toString());
            dtos.add(dto);
        }
        // ???????????????????????????
        this.countMissValue(dtos);
        return dtos.subList(0, limit);
    }

    @Override
    public List<CqsscLotterySg> queryHistorySg(Integer pageNo, Integer pageSize) {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setOrderByClause("issue desc");
        if (pageNo != null && pageSize != null) {
            example.setOffset((pageNo - 1) * pageSize);
            example.setLimit(pageSize);
        }
        return cqsscLotterySgMapper.selectByExample(example);
    }

    @Override
    public CqsscLotterySg queryLastCqsscSg() {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = example.createCriteria();
        cqsscCriteria.andWanIsNotNull();
        example.setLimit(1);
        example.setOrderByClause("`time` DESC");
        return cqsscLotterySgMapper.selectOneByExample(example);
    }

    @Override
    public ResultInfo<Map<String, Object>> cqsscTodayAndHistoryNews(Integer pageNo, Integer pageSize, String date) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isEmpty(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        result.put("countTime", date);
        date = date.replaceAll("-", "") + "%";
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        criteria.andIssueLike(date);
        example.setOrderByClause("issue ASC");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        //?????????????????????
        List<Map<String, Object>> lishiKaiJiang = CqsscUtils.lishiKaiJiang(cqsscLotterySgs);
        result.put("todayAndlishiKaiJiang", lishiKaiJiang);

        //????????????????????????
        Map<String, Object> cqsscCount = CqsscUtils.todayCount(cqsscLotterySgs);
        result.put("numberCount", cqsscCount);
        return ResultInfo.ok(result);
    }

    /**
     * ?????????????????????0-9????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getCqsscTodayCount() {
        String date = TimeHelper.date("yyyy-MM-dd").replaceAll("-", "") + "%";
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andIssueLike(date);
        example.setOrderByClause("`ideal_time` DESC");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);
        Map<String, Object> map = CqsscUtils.todayCount1(cqsscLotterySgs);

        return ResultInfo.ok(map);
    }

    @Override
    public Map<String, List<CqsscSizeMissDTO>> getCqsscWebSizeMissCount(String date, int number) {
        Map<String, List<CqsscSizeMissDTO>> result = new HashMap<>();
        if (StringUtils.isEmpty(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        // Date dayAfter = DateUtils.getDayAfter(new Date(), -(count * 30L));
        // String dateStr = DateUtils.formatDate(date, DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateEqualTo(date);
        criteria.andWanIsNotNull();
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        List<Integer> bigMissCount = new ArrayList<>();
        Integer bigCount = 0;
        List<Integer> smallMissCount = new ArrayList<>();
        Integer smallCount = 0;

        Iterator iter = cqsscLotterySgs.iterator();
        while (iter.hasNext()) {
            CqsscLotterySg sg = (CqsscLotterySg) iter.next();
            switch (number) {
                case 5:
                    if (sg.getGe() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 4:
                    if (sg.getShi() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 3:
                    if (sg.getBai() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 2:
                    if (sg.getQian() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                default:
                    if (sg.getWan() > 4) {
                        smallCount++;
                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;
            }
        }

        List<CqsscSizeMissDTO> bigList = this.countList(bigMissCount);
        Collections.sort(bigList);
        result.put("bigList", bigList);

        List<CqsscSizeMissDTO> smallList = this.countList(smallMissCount);
        Collections.sort(smallList);
        result.put("smallList", smallList);

        return result;
    }

    /**
     * webd?????????????????????????????????
     *
     * @param date
     * @param number
     * @return
     */
    @Override
    public Map<String, List<CqsscSizeMissDTO>> getCqsscWebSingleMissCount(String date, int number) {
        Map<String, List<CqsscSizeMissDTO>> result = new HashMap<>();
        if (StringUtils.isEmpty(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }

        // ??????????????????
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(date);
        criteria.andWanIsNotNull();
        //example.setOrderByClause("`time` desc");
        List<CqsscLotterySg> cqsscLotterySgs = cqsscLotterySgMapper.selectByExample(example);

        List<Integer> singleMissCount = new ArrayList<>();
        Integer singleCount = 0;
        List<Integer> doubleMissCount = new ArrayList<>();
        Integer doubleCount = 0;

        Iterator iter = cqsscLotterySgs.iterator();
        while (iter.hasNext()) {
            CqsscLotterySg sg = (CqsscLotterySg) iter.next();
            switch (number) {
                case 5:
                    if (sg.getGe() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 4:
                    if (sg.getShi() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 3:
                    if (sg.getBai() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 2:
                    if (sg.getQian() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                default:
                    if (sg.getWan() % 2 == 1) {
                        doubleCount++;
                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;
            }
        }

        List<CqsscSizeMissDTO> singleList = this.countList(singleMissCount);
        // ??????
        Collections.sort(singleList);
        result.put("singleList", singleList);
        List<CqsscSizeMissDTO> doubleList = this.countList(doubleMissCount);
        // ??????
        Collections.sort(doubleList);
        result.put("doubleList", doubleList);

        return result;
    }

    /* (non Javadoc)
     * @Title: getCqsscSgLong
     * @Description: ???????????????????????????
     * @return
     * @see com.caipiao.live.read.issue.service.result.CqsscLotterySgService#getCqsscSgLong()
     */
    @Override
    public List<Map<String, Object>> getCqsscSgLong() {
        List<Map<String, Object>> cqsscLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.CQSSC_ALGORITHM_VALUE;
            List<CqsscLotterySg> cqsscLotterySgList = (List<CqsscLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(cqsscLotterySgList)) {
                cqsscLotterySgList = this.getCqsscAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, cqsscLotterySgList, 10, TimeUnit.SECONDS);
            }
            // ??????????????????
            List<Map<String, Object>> cqsscBigLongMapList = this.getCqsscBigAndSmallLong(cqsscLotterySgList);
            cqsscLongMapList.addAll(cqsscBigLongMapList);
            // ??????????????????
            List<Map<String, Object>> cqsscSigleLongMapList = this.getCqsscSigleAndDoubleLong(cqsscLotterySgList);
            cqsscLongMapList.addAll(cqsscSigleLongMapList);
            cqsscLongMapList = this.addNextIssueInfo(cqsscLongMapList, cqsscLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#CqsscLotterySgServiceImpl_getActSgLong_error:", e);
        }
        // ??????
        return cqsscLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: ??????
     * @author HANS
     * @date 2019???5???26?????????2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> cqsscLongMapList, List<CqsscLotterySg> cqsscLotterySgList) {
        List<Map<String, Object>> cqsscResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(cqsscLongMapList)) {
            // ???????????????????????????
            String nextRedisKey = RedisKeys.CQSSC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.CQSSC.getRedisTime();
            CqsscLotterySg nextCqsscLotterySg = (CqsscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextCqsscLotterySg == null) {
                nextCqsscLotterySg = this.getNextCqsscLotterySg();
                // ?????????????????????
                redisTemplate.opsForValue().set(nextRedisKey, nextCqsscLotterySg, 10, TimeUnit.SECONDS);
            }

            if (nextCqsscLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // ????????????????????????
            String redisKey = RedisKeys.CQSSC_RESULT_VALUE;
            CqsscLotterySg cqsscLotterySg = (CqsscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (cqsscLotterySg == null) {
                // ??????????????????????????????
                cqsscLotterySg = this.getCqsscLotterySg();
                redisTemplate.opsForValue().set(redisKey, cqsscLotterySg);
            }
            String nextIssue = nextCqsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextCqsscLotterySg.getIssue();
            String txffnextIssue = cqsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : cqsscLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // ????????????????????????????????????????????????????????????
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }
            // ????????????????????????????????????
            String issueString = nextCqsscLotterySg.getIssue();
            Long nextTimeLong = DateUtils.getTimeMillis(nextCqsscLotterySg.getIdealTime()) / 1000L;

            for (Map<String, Object> longMap : cqsscLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), issueString);
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextTimeLong);
                cqsscResultLongMapList.add(longMap);
            }
        }
        return cqsscResultLongMapList;
    }

    /**
     * @Title: getCqsscBigAndSmallLong
     * @Description: ?????????????????????????????????
     * @author HANS
     * @date 2019???5???17?????????5:05:14
     */
    private List<Map<String, Object>> getCqsscBigAndSmallLong(List<CqsscLotterySg> cqsscLotterySgList) {
        List<Map<String, Object>> cqsscBigLongMapList = new ArrayList<Map<String, Object>>();
        // ???????????????????????????????????????
        Map<String, Object> twoWallBigAndSmallDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> firstBigAndSmallDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDYQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> secondBigAndSmallDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDEQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> thirdBigAndSmallDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDSQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fourthBigAndSmallDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDFQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fivethBigAndSmallDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDWQBIG.getTagType());

        // ????????????????????????????????????
        if (twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(twoWallBigAndSmallDragonMap);
        }

        if (firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(firstBigAndSmallDragonMap);
        }

        if (secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(secondBigAndSmallDragonMap);
        }

        if (thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(thirdBigAndSmallDragonMap);
        }

        if (fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(fourthBigAndSmallDragonMap);
        }

        if (fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(fivethBigAndSmallDragonMap);
        }
        return cqsscBigLongMapList;
    }

    /**
     * @Title: getCqsscSigleAndDoubleLong
     * @Description: ?????????????????????????????????
     * @author HANS
     * @date 2019???5???17?????????6:45:48
     */
    private List<Map<String, Object>> getCqsscSigleAndDoubleLong(List<CqsscLotterySg> cqsscLotterySgList) {
        List<Map<String, Object>> cqsscBigLongMapList = new ArrayList<Map<String, Object>>();
        // ???????????????????????????????????????
        Map<String, Object> twoWallSigleAndDoubleDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCLMZHDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> firstSigleAndDoubleDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDYQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> secondSigleAndDoubleDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDEQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> thirdSigleAndDoubleDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDSQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fourthSigleAndDoubleDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDFQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fivethSigleAndDoubleDragonMap = this.getDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDWQDOUBLE.getTagType());
        // ??????
        if (twoWallSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(twoWallSigleAndDoubleDragonMap);
        }

        if (firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(firstSigleAndDoubleDragonMap);
        }

        if (secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(secondSigleAndDoubleDragonMap);
        }

        if (thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(thirdSigleAndDoubleDragonMap);
        }

        if (fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(fourthSigleAndDoubleDragonMap);
        }

        if (fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            cqsscBigLongMapList.add(fivethSigleAndDoubleDragonMap);
        }
        return cqsscBigLongMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: ?????????????????????????????????
     * @author HANS
     * @date 2019???5???17?????????6:53:51
     */
    private Map<String, Object> getDragonInfo(List<CqsscLotterySg> cqsscLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(cqsscLotterySgList)) {
                // ????????????
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < cqsscLotterySgList.size(); index++) {
                    CqsscLotterySg cqsscLotterySg = cqsscLotterySgList.get(index);
                    // ????????????????????????
                    String bigOrSmallName = this.calculateResult(type, cqsscLotterySg);

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
                CqsscLotterySg cqsscLotterySg = cqsscLotterySgList.get(Constants.DEFAULT_INTEGER);
                // ??????????????????
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, cqsscLotterySg, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#CqsscLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }


    /**
     * @Title: organizationDragonResultMap
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???5???17?????????7:06:07
     */
    private Map<String, Object> organizationDragonResultMap(int type, CqsscLotterySg cqsscLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // ?????????????????????????????????
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // ????????????????????? ?????? ????????????
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.CQSSC.getTagCnName(), CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getPlayName(),
                    CaipiaoTypeEnum.CQSSC.getTagType(), CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getTagType() + "");
            List<String> dragonList = new ArrayList<String>(dragonSet);
            // ????????????
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 108) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALBIG);
            } else if (type == 109) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDYQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDYQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDYQBIG.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 110) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDEQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDEQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDEQBIG.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 111) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDSQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDSQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDSQBIG.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 112) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDFQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDFQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDFQBIG.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 113) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDWQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDWQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDWQBIG.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 114) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCLMZHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCLMZHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCLMZHDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALDOUBLE);
            } else if (type == 115) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDYQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDYQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDYQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 116) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDEQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDEQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDEQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 117) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDSQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDSQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDSQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 118) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDFQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDFQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDFQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 119) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDWQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDWQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.CQSSCDWQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgServiceReadSg.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            }

            // ?????????????????????????????????MAP???
            longDragonMap.putAll(oddsListMap);
            // ?????????????????????
            String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
            //String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.CQSSC.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.CQSSC.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#XjsscLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???17?????????6:57:10
     */
    private String calculateResult(int type, CqsscLotterySg cqsscLotterySg) {
        String result = Constants.DEFAULT_NULL;

        String number = Constants.DEFAULT_NULL;
        number = cqsscLotterySg.getCpkNumber() == null ? Constants.DEFAULT_NULL : cqsscLotterySg.getCpkNumber();

        if (StringUtils.isEmpty(number)) {
            number = cqsscLotterySg.getKcwNumber() == null ? Constants.DEFAULT_NULL : cqsscLotterySg.getKcwNumber();
        }

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 108:
                result = AusactSgUtils.getJssscBigOrSmall(number);//??????????????????
                break;
            case 109:
                result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getWan());//???????????????
                break;
            case 110:
                result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getQian());//???????????????
                break;
            case 111:
                result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getBai());//???????????????
                break;
            case 112:
                result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getShi());//???????????????
                break;
            case 113:
                result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getGe());//???????????????
                break;
            case 114:
                result = AusactSgUtils.getSingleAndDouble(number);//??????????????????
                break;
            case 115:
                result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getWan());//???????????????
                break;
            case 116:
                result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getQian());//???????????????
                break;
            case 117:
                result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getBai());//???????????????
                break;
            case 118:
                result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getShi());//???????????????
                break;
            case 119:
                result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getGe());//???????????????
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: getCqsscAlgorithmData
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???17?????????4:20:03
     */
    private List<CqsscLotterySg> getCqsscAlgorithmData() {
        CqsscLotterySgExample cqsscExample = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = cqsscExample.createCriteria();
        cqsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        cqsscExample.setOrderByClause("`ideal_time` DESC");
        cqsscExample.setOffset(Constants.DEFAULT_INTEGER);
        cqsscExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        return this.cqsscLotterySgMapper.selectByExample(cqsscExample);
    }
}


