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
import com.caipiao.live.common.model.dto.result.ShapeTypeEnum;
import com.caipiao.live.common.model.dto.result.TxffcLotterySgDTO;
import com.caipiao.live.common.model.dto.result.TxffcMissDTO;
import com.caipiao.live.common.model.dto.result.TxffcMissNumDTO;
import com.caipiao.live.common.model.dto.result.TxffcRatioDTO;
import com.caipiao.live.common.model.dto.result.TxffcShapeDTO;
import com.caipiao.live.common.model.dto.result.TxffcSizeMissDTO;
import com.caipiao.live.common.model.dto.result.TxffcSumValDTO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.TxffcLotterySg;
import com.caipiao.live.common.mybatis.entity.TxffcLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.TxffcLotterySgMapper;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JssscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TxffcLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import com.caipiao.live.common.util.lottery.NextIssueTimeUtil;
import com.caipiao.live.common.util.lottery.TxffcUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
 * ??????????????????????????????
 *
 * @author lzy
 * @create 2018-07-28 11:07
 **/
@Service
public class TxffcLotteryReadSgServiceImpl implements TxffcLotterySgServiceReadSg {

    private static final Logger logger = LoggerFactory.getLogger(TxffcLotteryReadSgServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JssscLotterySgServiceReadSg jssscLotterySgService;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private TxffcLotterySgMapper txffcLotterySgMapper;

    @Override
    public List<TxffcLotterySg> getSgByDate(String date) {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (!org.springframework.util.StringUtils.isEmpty(date)) {
            if (StringUtils.isBlank(date)) {
                date = TimeHelper.date("yyyy-MM-dd");
            }
            date = date.replaceAll("-", "") + "%";
            criteria.andIssueLike(date);
        }
        example.setOrderByClause("ideal_time DESC");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);
        return txffcLotterySgs;
    }

    @Override
    public List<TxffcLotterySg> getSgByIssues(Integer issues) {
        if (issues == null) {
            issues = 30;
        }
        TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
        txffcCriteria.andWanIsNotNull();
        txffcExample.setOffset(0);
        txffcExample.setLimit(issues);
        txffcExample.setOrderByClause("ideal_time DESC");
        return txffcLotterySgMapper.selectByExample(txffcExample);
    }

    /**
     * ??????????????????????????????
     *
     * @param type ??????????????????
     * @param date ??????, ???2018-08-21
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> todayCount(String type, String date) {

        //??????????????????????????????
        if (LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            List<TxffcLotterySg> txffcLotterySgs = getSgByDate(date);
            if (txffcLotterySgs == null || txffcLotterySgs.size() == 0) {
                return ResultInfo.getInstance(null, StatusCode.NO_DATA);
            }
            Map<String, Object> map = TxffcUtils.todayCount(txffcLotterySgs);
            return ResultInfo.ok(map);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues) {
        // ????????????
        if (!LotteryInformationType.CQSSC_LSKJ_KJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        // ????????????
        List<TxffcLotterySg> txffcLotterySgs = getSgByIssues(issues);

        List<Map<String, Object>> list = TxffcUtils.lishiKaiJiang(txffcLotterySgs);

        return ResultInfo.ok(list);
    }

    @Override
    public Map<String, Object> lishiSg(Integer pageNo, Integer pageSize) {
        TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
//        txffcCriteria.andWanIsNotNull();

        txffcCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        txffcCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        txffcExample.setOffset((pageNo - 1) * pageSize);
        txffcExample.setLimit(pageSize);
        txffcExample.setOrderByClause("ideal_time DESC");

        List<TxffcLotterySg> txffcLotterySgs = null;
        //??????100??? ????????????????????????????????????????????????
        if (!redisTemplate.hasKey(RedisKeys.TXFFC_SG_HS_LIST)) {
            TxffcLotterySgExample exampleOne = new TxffcLotterySgExample();
            TxffcLotterySgExample.Criteria txffcCriteriaOne = exampleOne.createCriteria();
            txffcCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<TxffcLotterySg> txffcLotterySgsOne = txffcLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.TXFFC_SG_HS_LIST, txffcLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //???????????????
            txffcLotterySgs = redisTemplate.opsForList().range(RedisKeys.TXFFC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //??????????????????
            txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);
        }

//        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);
        List<Map<String, Object>> maps = TxffcUtils.lishiSg(txffcLotterySgs);
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
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andIssueEqualTo(issue);
        TxffcLotterySg txffcLotterySg = txffcLotterySgMapper.selectOneByExample(example);
        Map<String, Object> map = TxffcUtils.sgDetails(txffcLotterySg);
        return ResultInfo.ok(map);
    }

    @Override
    public ResultInfo<List<MapListVO>> lishiLengRe(String type) {
        // ???????????????????????????????????????
        TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
        txffcCriteria.andWanIsNotNull();
        txffcExample.setOrderByClause("ideal_time desc");
        txffcExample.setOffset(0);
        txffcExample.setLimit(100);
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);
        List<MapListVO> list = TxffcUtils.lishiLengRe(txffcLotterySgs, type);
        return ResultInfo.ok(list);
    }


    @Override
    public ResultInfo<List<TxffcMissDTO>> getTxffcMissCount(String date) {
        // ????????????????????????
        List<TxffcMissDTO> list = new ArrayList<>();

        // ??????????????????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andDateEqualTo(date);
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        TxffcMissDTO dto;
        int allCount, currCount;
        for (int i = 0; i < 10; i++) {
            dto = new TxffcMissDTO();
            allCount = currCount = 0;
            dto.setNumber(i);

            for (TxffcLotterySg sg : txffcLotterySgs) {
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
    public Map<String, List<TxffcSizeMissDTO>> getTxffcSizeMissCount(Integer count, Integer number) {
        Map<String, List<TxffcSizeMissDTO>> result = new HashMap<>();

        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        Date dayAfter = DateUtils.getDayAfter(new Date(), -(count * 30L));
        String dateStr = DateUtils.formatDate(dayAfter, DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateGreaterThan(dateStr);
        criteria.andWanIsNotNull();
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        List<Integer> bigMissCount = new ArrayList<>();
        int bigCount = 0;
        List<Integer> smallMissCount = new ArrayList<>();
        int smallCount = 0;

        Iterator iter = txffcLotterySgs.iterator();
        while (iter.hasNext()) {
            TxffcLotterySg sg = (TxffcLotterySg) iter.next();
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

        List<TxffcSizeMissDTO> bigList = this.countList(bigMissCount);
        Collections.sort(bigList);
        result.put("bigList", bigList);

        List<TxffcSizeMissDTO> smallList = this.countList(smallMissCount);
        Collections.sort(bigList);
        result.put("smallList", smallList);

        return result;
    }

    /**
     * ??????????????????
     *
     * @param date   ??????
     * @param number ?????????1 | 2 | 3 | 4 | 5???
     * @return
     */
    @Override
    public Map<String, List<TxffcSizeMissDTO>> getTxffcSizeMissCount(String date, Integer number) {
        Map<String, List<TxffcSizeMissDTO>> result = new HashMap<>();

        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(date);
        criteria.andWanIsNotNull();
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        List<Integer> bigMissCount = new ArrayList<>();
        int bigCount = 0;
        List<Integer> smallMissCount = new ArrayList<>();
        int smallCount = 0;

        Iterator iter = txffcLotterySgs.iterator();
        while (iter.hasNext()) {
            TxffcLotterySg sg = (TxffcLotterySg) iter.next();
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

        List<TxffcSizeMissDTO> bigList = this.countList(bigMissCount);
        Collections.sort(bigList);
        result.put("bigList", bigList);

        List<TxffcSizeMissDTO> smallList = this.countList(smallMissCount);
        Collections.sort(bigList);
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
    public Map<String, List<TxffcSizeMissDTO>> getTxffcSingleMissCount(Integer count, Integer number) {
        Map<String, List<TxffcSizeMissDTO>> result = new HashMap<>();

        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        Date dayAfter = DateUtils.getDayAfter(new Date(), -(count * 30L));
        String dateStr = DateUtils.formatDate(dayAfter, DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateGreaterThan(dateStr);
        example.setOrderByClause("`time` desc");
        criteria.andWanIsNotNull();
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        List<Integer> singleMissCount = new ArrayList<>();
        Integer singleCount = 0;
        List<Integer> doubleMissCount = new ArrayList<>();
        Integer doubleCount = 0;

        Iterator iter = txffcLotterySgs.iterator();
        while (iter.hasNext()) {
            TxffcLotterySg sg = (TxffcLotterySg) iter.next();
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

        List<TxffcSizeMissDTO> singleList = this.countList(singleMissCount);
        // ??????
        Collections.sort(singleList);
        result.put("singleList", singleList);
        List<TxffcSizeMissDTO> doubleList = this.countList(doubleMissCount);
        // ??????
        Collections.sort(doubleList);
        result.put("doubleList", doubleList);

        return result;
    }

    /**
     * ??????????????????
     *
     * @param date   ??????
     * @param number ?????????1 | 2 | 3 | 4 | 5???
     * @return
     */
    @Override
    public Map<String, List<TxffcSizeMissDTO>> getTxffcSingleMissCount(String date, Integer number) {
        Map<String, List<TxffcSizeMissDTO>> result = new HashMap<>();

        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(date);
        criteria.andWanIsNotNull();
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        List<Integer> singleMissCount = new ArrayList<>();
        Integer singleCount = 0;
        List<Integer> doubleMissCount = new ArrayList<>();
        Integer doubleCount = 0;

        Iterator iter = txffcLotterySgs.iterator();
        while (iter.hasNext()) {
            TxffcLotterySg sg = (TxffcLotterySg) iter.next();
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

        List<TxffcSizeMissDTO> singleList = this.countList(singleMissCount);
        // ??????
        Collections.sort(singleList);
        result.put("singleList", singleList);
        List<TxffcSizeMissDTO> doubleList = this.countList(doubleMissCount);
        // ??????
        Collections.sort(doubleList);
        result.put("doubleList", doubleList);

        return result;
    }

    private List<TxffcSizeMissDTO> countList(List<Integer> list) {
        List<TxffcSizeMissDTO> result = new ArrayList<>();
        for (Integer num : list) {
            Boolean found = false;
            for (TxffcSizeMissDTO dto : result) {
                if (dto.getMissValue().equals(num)) {
                    found = true;
                    dto.setMissCount(dto.getMissCount() + 1);
                    break;
                }
            }
            if (!found) {
                TxffcSizeMissDTO dto = new TxffcSizeMissDTO();
                dto.setMissValue(num);
                dto.setMissCount(1);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * ?????????????????????????????????
     *
     * @param number 1:?????? 2:??????  3:?????? 4:?????? 5:??????
     * @param limit  ????????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcTrend(Integer number, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
        txffcCriteria.andWanIsNotNull();
        txffcExample.setOffset(0);
        txffcExample.setLimit(limit + 100);
        txffcExample.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcMissNumDTO> list = this.missValueByNum(number, txffcLotterySgs, limit);

        // ????????????????????????
        List<TxffcMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);

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
    public Map<String, Object> getTxffcAmplitude(Integer number, Integer limit) {
        Map<String, Object> result = new HashMap<>();

        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcMissNumDTO> dtos = new ArrayList<>();

        TxffcMissNumDTO dto;
        for (int i = 0; i < limit && i < txffcLotterySgs.size(); i++) {
            dto = new TxffcMissNumDTO();
            TxffcLotterySg sg1 = txffcLotterySgs.get(i);
            dto.setIssue(sg1.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg1));
            if (i == limit - 1) {
                dto.setNumber("0");
            } else {
                TxffcLotterySg sg2 = txffcLotterySgs.get(i + 1);
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
        List<TxffcMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);

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
    public Map<String, Object> getTxffcTrendGroup(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();

        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcMissNumDTO> dtos = new ArrayList<>();

        TxffcMissNumDTO dto;
        // ???????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(type, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<TxffcMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);

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
    public Map<String, Object> getTxffcSpan(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcMissNumDTO> dtos = new ArrayList<>();

        TxffcMissNumDTO dto;
        // ???????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSpan(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<TxffcMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    @Override
    public Map<String, Object> getTxffcSpanMaxMin(Integer number, Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcMissNumDTO> dtos = new ArrayList<>();

        TxffcMissNumDTO dto;
        // ????????????????????????????????????/?????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSpanMaxMin(sg, type, number).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<TxffcMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);

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
    private Integer countSpanMaxMin(TxffcLotterySg sg, Integer type, Integer number) {
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
     * ???????????????????????????????????? - ????????????
     *
     * @param type  ?????? ??? 2 ?????? | 3 ?????? | 5 ??????
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcSumTail(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcMissNumDTO> dtos = new ArrayList<>();

        TxffcMissNumDTO dto;
        // ???????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSumTail(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<TxffcMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ???????????????????????????????????? - ????????????
     *
     * @param type  ?????? ??? 2 ?????? | 3 ?????? | 5 ??????
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcSumVal(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 200);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcSumValDTO> dtos = new ArrayList<>();

        TxffcSumValDTO dto;
        // ???????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcSumValDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.countSumValue(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueBySumVal(dtos, type);

        // ??????????????????????????????
        List<TxffcSumValDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcSumValDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcSumValDTO openCount = this.countSumOpenCountBySumVal(list, type);

        // ???????????????????????????
        TxffcSumValDTO maxMissVal = this.countMaxMissValueBySumVal(list);

        // ???????????????????????????
        TxffcSumValDTO avgMissVal = this.countAvgMissValueBySumVal(limit, openCount);

        // ???????????????????????????
        TxffcSumValDTO maxContinuous = this.countMaxContinuousBySumVal(list, type);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    private TxffcSumValDTO countMaxContinuousBySumVal(List<TxffcSumValDTO> list, Integer type) {
        TxffcSumValDTO continuous = new TxffcSumValDTO();
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

    private Integer getContinuousValueBySumVal(List<TxffcSumValDTO> list, Integer type, Integer number) {
        Integer max = 0, count = 0, first = 0;
        Integer beforeValue = -1;
        for (TxffcSumValDTO dto : list) {
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

    private TxffcSumValDTO countAvgMissValueBySumVal(Integer size, TxffcSumValDTO openCount) {
        TxffcSumValDTO avgMissVal = new TxffcSumValDTO();
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

    private TxffcSumValDTO countMaxMissValueBySumVal(List<TxffcSumValDTO> list) {
        TxffcSumValDTO maxMissVal = new TxffcSumValDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }

        int max0 = 0, max1 = 0, max2 = 0, max3 = 0, max4 = 0, max5 = 0, max6 = 0, max7 = 0, max8 = 0, max9 = 0, max10 = 0, max11 = 0, max12 = 0, max13 = 0,
                max14 = 0, max15 = 0, max16 = 0, max17 = 0, max18 = 0, max19 = 0, max20 = 0, max21 = 0, max22 = 0, max23 = 0, max24 = 0, max25 = 0, max26 = 0, max27 = 0;
        for (TxffcSumValDTO dto : list) {
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

    private TxffcSumValDTO countSumOpenCountBySumVal(List<TxffcSumValDTO> list, Integer type) {
        TxffcSumValDTO openCount = new TxffcSumValDTO();
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
            for (TxffcSumValDTO sumValDTO : list) {
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

    private void countMissValueBySumVal(List<TxffcSumValDTO> list, Integer type) {
        for (int i = 0; i < list.size(); i++) {
            TxffcSumValDTO missNumDTO = list.get(i);
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

    private Integer countMissNumberBySumVal(int i, List<TxffcSumValDTO> list, Integer type, Integer number) {
        int count = 0;
        for (int j = i; j < list.size(); j++) {
            TxffcSumValDTO dto = list.get(j);
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
                        return num;
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
     * ???????????????????????????????????? - ??????
     *
     * @param type  ?????? ??? 1 ?????? | 2 ?????? | 3 ?????? | 4 ?????? | 5 ?????? | 6 ????????????
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcShape(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcShapeDTO> dtos = new ArrayList<>();

        TxffcShapeDTO dto;
        // ??????????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcShapeDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getNumber(type, sg).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByShape(dtos);

        // ??????????????????????????????
        List<TxffcShapeDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcShapeDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcShapeDTO openCount = this.countSumOpenCountOther(list);

        // ???????????????????????????
        TxffcShapeDTO maxMissVal = this.countMaxMissValueOther(list);

        // ???????????????????????????
        TxffcShapeDTO avgMissVal = this.countAvgMissValueOther(limit, openCount);

        // ???????????????????????????
        TxffcShapeDTO maxContinuous = this.countMaxContinuousOther(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ???????????????????????????????????? - 012???
     *
     * @param type  ?????? ??? 1 ?????? | 2 ?????? | 3 ?????? | 4 ?????? | 5 ??????
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffc012Way(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcMissNumDTO> dtos = new ArrayList<>();

        TxffcMissNumDTO dto;
        // ???????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.count012Way(sg, type).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValue(dtos);

        // ??????????????????????????????
        List<TxffcMissNumDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcMissNumDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);

        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ???????????????????????????????????? - ??????
     *
     * @param type  ?????? ??? 1 ?????? | 2 ?????? | 3 ?????? | 4 ?????? | 5 ??????
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcToGo(Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcShapeDTO> dtos = new ArrayList<>();

        TxffcShapeDTO dto;
        // ???????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcShapeDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getNumber(type, sg).toString());
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByToGo(dtos);

        // ??????????????????????????????
        List<TxffcShapeDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcShapeDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcShapeDTO openCount = this.countSumOpenCountOther(list);

        // ???????????????????????????
        TxffcShapeDTO maxMissVal = this.countMaxMissValueOther(list);

        // ???????????????????????????
        TxffcShapeDTO avgMissVal = this.countAvgMissValueOther(limit, openCount);

        // ???????????????????????????
        TxffcShapeDTO maxContinuous = this.countMaxContinuousOther(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ???????????????????????????????????? - ??????
     *
     * @param number ?????????2 ??????????????? 3 ????????????
     * @param type   ?????? ??? 1 ?????????  2 ?????????  3 ?????????
     * @param limit  ?????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcRatio(Integer number, Integer type, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcRatioDTO> dtos = new ArrayList<>();

        TxffcRatioDTO dto;
        // ???????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcRatioDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(number, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByRatio(dtos, type);

        // ??????????????????????????????
        List<TxffcRatioDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcRatioDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcRatioDTO openCount = this.countSumOpenCountRatio(list);

        // ???????????????????????????
        TxffcRatioDTO maxMissVal = this.countMaxMissValueRatio(list);

        // ???????????????????????????
        TxffcRatioDTO avgMissVal = this.countAvgMissValueRatio(limit, openCount);

        // ???????????????????????????
        TxffcRatioDTO maxContinuous = this.countMaxContinuousRatio(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ???????????????????????????????????? - ??????
     *
     * @param limit ?????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcNumType(Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 300);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcRatioDTO> dtos = new ArrayList<>();

        TxffcRatioDTO dto;
        // ???????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcRatioDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(3, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueByNumType(dtos);

        // ??????????????????????????????
        List<TxffcRatioDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcRatioDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcRatioDTO openCount = this.countSumOpenCountRatio(list);

        // ???????????????????????????
        TxffcRatioDTO maxMissVal = this.countMaxMissValueRatio(list);

        // ???????????????????????????
        TxffcRatioDTO avgMissVal = this.countAvgMissValueRatio(limit, openCount);

        // ???????????????????????????
        TxffcRatioDTO maxContinuous = this.countMaxContinuousRatio(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    /**
     * ???????????????????????????????????? - ????????????????????????
     *
     * @param limit ????????????
     * @return
     */
    @Override
    public Map<String, Object> getTxffcSizeCount(Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 200);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcShapeDTO> dtos = new ArrayList<>();

        TxffcShapeDTO dto;
        // ???????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcShapeDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(2, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueShape(dtos);

        // ??????????????????????????????
        List<TxffcShapeDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcShapeDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcShapeDTO openCount = this.countSumOpenCountShape(list);

        // ???????????????????????????
        TxffcShapeDTO maxMissVal = this.countMaxMissValueShape(list);

        // ???????????????????????????
        TxffcShapeDTO avgMissVal = this.countAvgMissValueShape(limit, openCount);

        // ???????????????????????????
        TxffcShapeDTO maxContinuous = this.countMaxContinuousShape(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    @Override
    public Map<String, Object> getTxffcSizePosition(Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 200);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);

        // ??????
        if (CollectionUtils.isEmpty(txffcLotterySgs)) {
            return result;
        }

        // ???????????????????????????
        List<TxffcSumValDTO> dtos = new ArrayList<>();

        TxffcSumValDTO dto;
        // ???????????????????????????????????????
        for (TxffcLotterySg sg : txffcLotterySgs) {
            dto = new TxffcSumValDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getOpenNumberGroup(2, sg));
            dtos.add(dto);
        }

        // ???????????????????????????
        this.countMissValueShapePosition(dtos);

        // ??????????????????????????????
        List<TxffcSumValDTO> list = dtos.subList(0, limit);

        // ????????????????????????
        List<TxffcSumValDTO> statistics = new ArrayList<>();

        // ???????????????????????????
        TxffcSumValDTO openCount = this.countSumOpenCountShapePosition(list);

        // ???????????????????????????
        TxffcSumValDTO maxMissVal = this.countMaxMissValueShapePosition(list);

        // ???????????????????????????
        TxffcSumValDTO avgMissVal = this.countAvgMissValueShapePosition(limit, openCount);

        // ???????????????????????????
        TxffcSumValDTO maxContinuous = this.countMaxContinuousShapePosition(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("statistics", statistics);
        result.put("list", list);
        return result;
    }

    private TxffcSumValDTO countMaxContinuousShapePosition(List<TxffcSumValDTO> list) {
        TxffcSumValDTO sumValDTO = new TxffcSumValDTO();
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

    private Integer countMaxContinuous(List<TxffcSumValDTO> list, int num) {
        Integer max = 0, count = 0;
        for (TxffcSumValDTO dto : list) {
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

    private TxffcSumValDTO countMaxMissValueShapePosition(List<TxffcSumValDTO> list) {
        TxffcSumValDTO sumValDTO = new TxffcSumValDTO();
        sumValDTO.setIssue("???????????????");
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0,
                num8 = 0, num9 = 0, num10 = 0, num11 = 0, num12 = 0, num13 = 0, num14 = 0, num15 = 0;
        for (TxffcSumValDTO dto : list) {
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

    private TxffcSumValDTO countAvgMissValueShapePosition(Integer limit, TxffcSumValDTO openCount) {
        TxffcSumValDTO sumValDTO = new TxffcSumValDTO();
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

    private TxffcSumValDTO countSumOpenCountShapePosition(List<TxffcSumValDTO> list) {
        TxffcSumValDTO sumValDTO = new TxffcSumValDTO();
        sumValDTO.setIssue("???????????????");
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0,
                num8 = 0, num9 = 0, num10 = 0, num11 = 0, num12 = 0, num13 = 0, num14 = 0, num15 = 0;
        for (TxffcSumValDTO dto : list) {
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

    private void countMissValueShapePosition(List<TxffcSumValDTO> dtos) {
        for (int i = 0; i < dtos.size(); i++) {
            TxffcSumValDTO sumValDTO = dtos.get(i);
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

    private Integer countMissNumberShapePosition(int i, List<TxffcSumValDTO> dtos, int num) {
        Integer count = 0;
        for (int j = i; j < dtos.size(); j++) {
            TxffcSumValDTO sumValDTO = dtos.get(j);
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

    private TxffcShapeDTO countMaxContinuousShape(List<TxffcShapeDTO> list) {
        Integer max1 = 0, max2 = 0, max3 = 0, max4 = 0;
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        for (TxffcShapeDTO dto : list) {
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
        TxffcShapeDTO dto = new TxffcShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig(max1);
        dto.setSmall(max2);
        dto.setSingular(max3);
        dto.setQuantity(max4);
        return dto;
    }

    private TxffcShapeDTO countAvgMissValueShape(Integer limit, TxffcShapeDTO openCount) {
        TxffcShapeDTO dto = new TxffcShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig((limit - openCount.getBig()) / (openCount.getBig() + 1));
        dto.setSmall((limit - openCount.getSmall()) / (openCount.getSmall() + 1));
        dto.setSingular((limit - openCount.getSingular()) / (openCount.getSingular() + 1));
        dto.setQuantity((limit - openCount.getQuantity()) / (openCount.getQuantity() + 1));
        return dto;
    }

    private TxffcShapeDTO countMaxMissValueShape(List<TxffcShapeDTO> list) {
        Integer max1 = 0, max2 = 0, max3 = 0, max4 = 0;
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        for (TxffcShapeDTO dto : list) {
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
        TxffcShapeDTO dto = new TxffcShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig(max1);
        dto.setSmall(max2);
        dto.setSingular(max3);
        dto.setQuantity(max4);
        return dto;
    }

    private TxffcShapeDTO countSumOpenCountShape(List<TxffcShapeDTO> list) {
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        for (TxffcShapeDTO dto : list) {
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
        TxffcShapeDTO dto = new TxffcShapeDTO();
        dto.setIssue("???????????????");
        dto.setBig(count1);
        dto.setSmall(count2);
        dto.setSingular(count3);
        dto.setQuantity(count4);
        return dto;
    }

    private void countMissValueShape(List<TxffcShapeDTO> dtos) {
        for (TxffcShapeDTO dto : dtos) {
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
    private void countMissValueByNumType(List<TxffcRatioDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            TxffcRatioDTO dto = list.get(i);
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
    private Integer countMissNumberType(int i, List<TxffcRatioDTO> list, int num) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            TxffcRatioDTO dto = list.get(j);
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
    private TxffcRatioDTO countMaxContinuousRatio(List<TxffcRatioDTO> list) {
        TxffcRatioDTO maxContinuousVal = new TxffcRatioDTO();
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
    private Integer getContinuousValueRatio(List<TxffcRatioDTO> list, Integer type) {
        Integer max = 0, count = 0;
        for (TxffcRatioDTO dto : list) {
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
    private TxffcRatioDTO countAvgMissValueRatio(Integer size, TxffcRatioDTO openCount) {
        TxffcRatioDTO avgMissVal = new TxffcRatioDTO();
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
    private TxffcRatioDTO countMaxMissValueRatio(List<TxffcRatioDTO> list) {
        TxffcRatioDTO maxMissVal = new TxffcRatioDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }
        // ????????????
        Integer num1 = 0, num2 = 0, num3 = 0, num4 = 0;
        // ??????
        for (TxffcRatioDTO dto : list) {
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
    private TxffcRatioDTO countSumOpenCountRatio(List<TxffcRatioDTO> list) {
        TxffcRatioDTO openCount = new TxffcRatioDTO();
        openCount.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // ????????????
        Integer ratio1 = 0, ratio2 = 0, ratio3 = 0, ratio4 = 0;
        // ??????
        for (TxffcRatioDTO dto : list) {
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
    private void countMissValueByRatio(List<TxffcRatioDTO> list, Integer type) {
        for (int i = 0; i < list.size(); i++) {
            TxffcRatioDTO dto = list.get(i);
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
    private Integer countMissNumberRatio(int i, List<TxffcRatioDTO> list, Integer type, Integer num) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            TxffcRatioDTO dto = list.get(j);
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
    private void countMissValueByToGo(List<TxffcShapeDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            TxffcShapeDTO dto = list.get(i);
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
    private Integer countMissNumberToGo(int i, List<TxffcShapeDTO> list, ShapeTypeEnum type) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            TxffcShapeDTO dto1 = list.get(j);
            if (j + 1 >= list.size()) {
                return count;
            }
            TxffcShapeDTO dto2 = list.get(j + 1);
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
    private Integer count012Way(TxffcLotterySg sg, Integer type) {
        Integer number = this.getNumber(type, sg);
        return number % 3;
    }

    /**
     * ???????????????????????????
     *
     * @param list
     * @return
     */
    private TxffcShapeDTO countMaxContinuousOther(List<TxffcShapeDTO> list) {
        TxffcShapeDTO maxContinuousVal = new TxffcShapeDTO();
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
    private Integer getContinuousValueOther(List<TxffcShapeDTO> list, ShapeTypeEnum type) {
        Integer max = 0, count = 0;
        for (TxffcShapeDTO dto : list) {
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
    private TxffcShapeDTO countAvgMissValueOther(Integer size, TxffcShapeDTO openCount) {
        TxffcShapeDTO avgMissVal = new TxffcShapeDTO();
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
    private TxffcShapeDTO countMaxMissValueOther(List<TxffcShapeDTO> list) {
        TxffcShapeDTO maxMissVal = new TxffcShapeDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }
        // ????????????
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0;
        // ??????
        for (TxffcShapeDTO dto : list) {
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
    private TxffcShapeDTO countSumOpenCountOther(List<TxffcShapeDTO> list) {
        TxffcShapeDTO openCount = new TxffcShapeDTO();
        openCount.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // ????????????
        Integer bigCount = 0, smallCount = 0, singularCount = 0, quantityCount = 0, primeCount = 0, compositeCount = 0;
        // ??????
        for (TxffcShapeDTO dto : list) {
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
    private void countMissValueByShape(List<TxffcShapeDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            TxffcShapeDTO dto = list.get(i);
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
    private Integer countMissNumberShape(int i, List<TxffcShapeDTO> list, ShapeTypeEnum type) {
        Integer count = 0;
        for (int j = i; j < list.size(); j++) {
            TxffcShapeDTO dto = list.get(j);
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
    private String getOpenNumberStr(TxffcLotterySg sg) {
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
    private Integer countSumTail(TxffcLotterySg sg, Integer type) {
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
    private Integer countSumValue(TxffcLotterySg sg, Integer type) {
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
    private Integer getContinuousValue(List<TxffcMissNumDTO> list, Integer number) {
        Integer max = 0, count = 0, first = 0;
        String beforeValue = "-1";
        for (TxffcMissNumDTO dto : list) {
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
     * @param txffcLotterySgs ????????????
     * @param number          ??????
     * @return
     */
    private Integer getSumOpenCount(List<TxffcLotterySg> txffcLotterySgs, Integer number, List<Integer> types) {
        int count = 0;
        for (int i = 0; i < 100 && txffcLotterySgs.size() > i; i++) {
            TxffcLotterySg sg = txffcLotterySgs.get(i);
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
     * @param txffcLotterySgs ???????????????
     * @param num             ?????????0-9???
     * @return
     */
    private Integer getMissingCount(int i, List<TxffcLotterySg> txffcLotterySgs, int num, Integer openNum) {
        int number = 0;

        for (int j = i; j < txffcLotterySgs.size(); j++) {
            TxffcLotterySg sg = txffcLotterySgs.get(j);
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
    private Integer getNumber(Integer number, TxffcLotterySg sg) {
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
    private String getOpenNumberGroup(Integer type, TxffcLotterySg sg) {
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
     * @param txffcLotterySgs ???????????????
     * @param num             ?????????0-9???
     * @return
     */
    private Integer getMissingCount(int i, List<TxffcLotterySg> txffcLotterySgs, int num, List<Integer> openNums) {
        int number = 0;

        for (int j = i; j < txffcLotterySgs.size(); j++) {
            TxffcLotterySg sg = txffcLotterySgs.get(j);

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
    private TxffcMissNumDTO countMaxContinuous(List<TxffcMissNumDTO> list) {
        TxffcMissNumDTO continuous = new TxffcMissNumDTO();
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
    private TxffcMissNumDTO countAvgMissValue(Integer size, TxffcMissNumDTO openCount) {
        TxffcMissNumDTO avgMissVal = new TxffcMissNumDTO();
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
    private TxffcMissNumDTO countMaxMissValue(List<TxffcMissNumDTO> list) {
        TxffcMissNumDTO maxMissVal = new TxffcMissNumDTO();
        maxMissVal.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }
        // ????????????
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0, num9 = 0;
        // ??????
        for (TxffcMissNumDTO dto : list) {
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
    private TxffcMissNumDTO countSumOpenCount(List<TxffcMissNumDTO> list) {
        TxffcMissNumDTO openCount = new TxffcMissNumDTO();
        openCount.setIssue("???????????????");
        // ??????
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // ????????????
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0, num9 = 0;
        // ??????
        for (TxffcMissNumDTO dto : list) {
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
    private Integer countSpan(TxffcLotterySg sg, Integer type) {
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
    private void countMissValue(List<TxffcMissNumDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            TxffcMissNumDTO missNumDTO = list.get(i);
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
    private Integer countMissNumber(int i, List<TxffcMissNumDTO> list, Integer number) {
        int count = 0;
        for (int j = i; j < list.size(); j++) {
            TxffcMissNumDTO dto = list.get(j);
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

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullResult();
        try {
            // ????????????????????????
            String redisKey = RedisKeys.TXFFC_RESULT_VALUE;
            TxffcLotterySg txffcLotterySg = (TxffcLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (txffcLotterySg == null) {
                txffcLotterySg = this.getTxffcLotterySg();
                redisTemplate.opsForValue().set(redisKey, txffcLotterySg);
            }
            // ???????????????????????????
            String nextRedisKey = RedisKeys.TXFFC_NEXT_VALUE;
            TxffcLotterySg nextTxffcLotterySg = (TxffcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            // ?????????????????????
            Long redisTime = CaipiaoRedisTimeEnum.TXFFC.getRedisTime();
            if (nextTxffcLotterySg == null) {
                nextTxffcLotterySg = this.getNextTxffcLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextTxffcLotterySg, redisTime, TimeUnit.SECONDS);
            }
            if (nextTxffcLotterySg != null) {
                String nextIssue = nextTxffcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTxffcLotterySg.getIssue();
                String txffnextIssue = txffcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : txffcLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    String nextIssueNumber = nextIssue.substring(9, nextIssue.length());
                    String txffnextIssueNumber = txffnextIssue.substring(9, txffnextIssue.length());

                    Long nextIssueNum = Long.parseLong(nextIssueNumber);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssueNumber);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextTxffcLotterySg = this.getNextTxffcLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextTxffcLotterySg, redisTime, TimeUnit.SECONDS);
                        // ??????????????????????????????
                        txffcLotterySg = this.getTxffcLotterySg();
                        redisTemplate.opsForValue().set(redisKey, txffcLotterySg);
                    }
                }
                if (txffcLotterySg != null) {
                    // ??????????????????
                    this.getIssueSumAndAllNumber(txffcLotterySg, result);
                    // ??????????????????
                    this.openTxffcCount(txffcLotterySg, result);
                }

                if (nextTxffcLotterySg != null) {
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextTxffcLotterySg.getIdealTime()) / 1000L);
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTxffcLotterySg.getIssue());
                }
            } else {
                if (txffcLotterySg != null) {
                    // ??????????????????
                    this.getIssueSumAndAllNumber(txffcLotterySg, result);
                    // ??????????????????
                    this.openTxffcCount(txffcLotterySg, result);
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TXFFC.getTagType() + "????????? ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    /**
     * @return TxffcLotterySg
     * @Title: getTxffcLotterySg
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???3?????????1:31:56
     */
    public TxffcLotterySg getTxffcLotterySg() {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        TxffcLotterySg txffcLotterySg = txffcLotterySgMapper.selectOneByExample(example);
        return txffcLotterySg;
    }

    /**
     * @return TxffcLotterySg
     * @Title: getTxffcLotterySg
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???4???29?????????10:03:35
     */
    public TxffcLotterySg getNextTxffcLotterySg() {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        txffcCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
        example.setOrderByClause("ideal_time ASC");
        TxffcLotterySg nextTxffcLotterySg = txffcLotterySgMapper.selectOneByExample(example);
        return nextTxffcLotterySg;
    }

    /**
     * @Title: getIssueSumAndAllNumber
     * @Description: ???????????????????????????
     */
    public void getIssueSumAndAllNumber(TxffcLotterySg txffcLotterySg, Map<String, Object> result) {
        Integer wan = txffcLotterySg.getWan();
        Integer qian = txffcLotterySg.getQian();
        Integer bai = txffcLotterySg.getBai();
        Integer shi = txffcLotterySg.getShi();
        Integer ge = txffcLotterySg.getGe();
        String issue = txffcLotterySg.getIssue();
        result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
        // ??????????????????
        String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
        // ????????????????????????
        Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
    }

    /**
     * @Title: openCount
     * @Description: ????????????????????????????????????
     */
    public void openTxffcCount(TxffcLotterySg txffcLotterySg, Map<String, Object> result) {
        // ??????????????????
        String issue = txffcLotterySg.getIssue();
        String openNumString = issue.substring(9, issue.length());
        Integer openNumInteger = Integer.valueOf(openNumString);
        result.put("openCount", openNumInteger);
        // ????????????????????????
        Integer sumCount = CaipiaoSumCountEnum.TXFFC.getSumCount();
        result.put("noOpenCount", sumCount - openNumInteger);
    }

    /**
     * ??????????????????????????????
     *
     * @return
     */
    public TxffcLotterySg queryNextSg() {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        example.setOrderByClause("`ideal_time` ASC");
        return txffcLotterySgMapper.selectOneByExample(example);
    }

    @Override
    public Map<String, Object> getNewestSgInfoWeb() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "??????????????????");
        // ????????????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOrderByClause("ideal_time DESC");
        TxffcLotterySg txffcLotterySg = txffcLotterySgMapper.selectOneByExample(example);
        if (txffcLotterySg != null) {
            Integer wan = txffcLotterySg.getWan();
            Integer qian = txffcLotterySg.getQian();
            Integer bai = txffcLotterySg.getBai();
            Integer shi = txffcLotterySg.getShi();
            Integer ge = txffcLotterySg.getGe();
            String issue = txffcLotterySg.getIssue();
            result.put("issue", issue);
            result.put("number", String.valueOf(wan) + qian + bai + shi + ge);
            result.put("he", wan + qian + bai + shi + ge);
            result.put("time", txffcLotterySg.getTime());
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
        result.put("issue", NextIssueTimeUtil.nextIssueTxffc());
        //??????????????????????????????
        result.put("time", NextIssueTimeUtil.nextIssueTimeTxffc() / 1000L);
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
    public Map<String, Object> getTxffcBaseTrend(Integer number, Integer limit) {
        Map<String, Object> result = new HashMap<>();

        // ??????????????????
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TxffcLotterySg> lotterySgs = txffcLotterySgMapper.selectByExample(example);

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
    private Map<String, Object> trendWei(Integer number, List<TxffcLotterySg> lotterySgs, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // ????????????????????????
        List<TxffcMissNumDTO> list;
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
    private List<TxffcMissNumDTO> missValCount(List<TxffcMissNumDTO> list, Integer limit) {
        List<TxffcMissNumDTO> statistics = new ArrayList<>();
        // ???????????????????????????
        TxffcMissNumDTO openCount = this.countSumOpenCount(list);
        // ???????????????????????????
        TxffcMissNumDTO maxMissVal = this.countMaxMissValue(list);
        // ???????????????????????????
        TxffcMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);
        // ???????????????????????????
        TxffcMissNumDTO maxContinuous = this.countMaxContinuous(list);
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
    private List<TxffcMissNumDTO> missValueByNum(Integer number, List<TxffcLotterySg> lotterySgs, Integer limit) {
        // ???????????????????????????
        List<TxffcMissNumDTO> dtos = new ArrayList<>();
        // ???????????????????????????????????????
        TxffcMissNumDTO dto;
        for (TxffcLotterySg sg : lotterySgs) {
            dto = new TxffcMissNumDTO();
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
    public List<TxffcLotterySg> queryHistorySg(Integer pageNo, Integer pageSize) {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andWanIsNotNull();
        example.setOrderByClause("ideal_time desc");
        if (pageNo != null && pageSize != null) {
            example.setOffset((pageNo - 1) * pageSize);
            example.setLimit(pageSize);
        }
        return txffcLotterySgMapper.selectByExample(example);
    }

    @Override
    public ResultInfo<Map<String, Object>> getTodayAndHistoryNews(Integer pageNo, Integer pageSize, String date) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isEmpty(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        result.put("countTime", date);
        date = date.replaceAll("-", "") + "%";
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueLike(date);
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`ideal_time` ASC");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);
        //?????????????????????
        List<Map<String, Object>> lishiKaiJiang = TxffcUtils.lishiKaiJiang(txffcLotterySgs);
        result.put("todayAndlishiKaiJiang", lishiKaiJiang);

        //????????????????????????
        Map<String, Object> txffcCount = TxffcUtils.todayCount(txffcLotterySgs);
        result.put("numberCount", txffcCount);

        result.put("pageNo", pageNo);//??????
        result.put("pageSize", pageSize);//????????????
        result.put("total", 1440);//?????????
        return ResultInfo.ok(result);
    }


    /**
     * ?????????????????????0-9????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getTxffcTodayCount() {
        Date nowTime = new Date();
        String dateStr = DateUtils.formatDate(nowTime, DateUtils.FORMAT_YYYY_MM_DD);
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andTimeLike(dateStr + "%");
        example.setOrderByClause("`ideal_time` DESC");
        List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(example);
        Map<String, Object> map = TxffcUtils.todayCount1(txffcLotterySgs);

        return ResultInfo.ok(map);
    }

    @Override
    public Map<String, Object> getNextTxffcRecommend() {
        Map<String, Object> result = new HashMap<>();
        // ??????????????????????????????
        TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
        txffcCriteria.andWanIsNotNull();
        txffcExample.setOrderByClause("`time` desc");
        TxffcLotterySg lastSg = txffcLotterySgMapper.selectOneByExample(txffcExample);
        TxffcLotterySgDTO sgDTO = new TxffcLotterySgDTO();
        BeanUtils.copyProperties(lastSg, sgDTO);
//	        sgDTO.setSum(lastSg.getWan()+lastSg.getQian()+lastSg.getBai()+lastSg.getShi()+lastSg.getGe());
//	        sgDTO.setDragonTiger(lastSg.getWan()>lastSg.getGe() ? "???" : "???");
        result.put("lastSg", sgDTO);
        return result;
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo,
                                                           Integer pageSize) {
        // ????????????
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        List<TxffcLotterySg> txffcLotterySg = getSgByDatePageSize(date, pageNo, pageSize);
        List<Map<String, Object>> list = TxffcUtils.lishiKaiJiang(txffcLotterySg);
        return ResultInfo.ok(list);
    }

    @Override
    public List<TxffcLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize) {

        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        date = date.replaceAll("-", "") + "%";
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andIssueLike(date);
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<TxffcLotterySg> txffcLotterySg = txffcLotterySgMapper.selectByExample(example);
        return txffcLotterySg;
    }

    /* (non Javadoc)
     * @Title: getTxffcSgLong
     * @Description: ??????????????????????????????
     * @return
     * @see com.caipiao.live.read.service.result.TxffcLotterySgService#getTxffcSgLong()
     */
    @Override
    public List<Map<String, Object>> getTxffcSgLong() {
        List<Map<String, Object>> txffcLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.TXFFC_ALGORITHM_VALUE;
            List<TxffcLotterySg> txffcLotterySgList = (List<TxffcLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(txffcLotterySgList)) {
                txffcLotterySgList = this.selectNearTxffcIssue();
                redisTemplate.opsForValue().set(algorithm, txffcLotterySgList, 10, TimeUnit.SECONDS);
            }

            // ??????????????????
            List<Map<String, Object>> jssscBigLongMapList = this.getTxffcBigAndSmallLong(txffcLotterySgList);
            txffcLongMapList.addAll(jssscBigLongMapList);
            // ??????????????????
            List<Map<String, Object>> jssscSigleLongMapList = this.getTxffcSigleAndDoubleLong(txffcLotterySgList);
            txffcLongMapList.addAll(jssscSigleLongMapList);
            txffcLongMapList = this.addNextIssueInfo(txffcLongMapList, txffcLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TxffcLotterySgServiceImpl_getTxffcSgLong_error:", e);
        }
        return txffcLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???5???28?????????8:32:47
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> txffcLongMapList, List<TxffcLotterySg> txffcLotterySgList) {
        List<Map<String, Object>> txffcResultLongMapList = new ArrayList<Map<String, Object>>();
        try {
            if (!CollectionUtils.isEmpty(txffcLongMapList)) {
                // ???????????????????????????
                String nextRedisKey = RedisKeys.TXFFC_NEXT_VALUE;
                TxffcLotterySg nextTxffcLotterySg = (TxffcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

                // ?????????????????????
                Long redisTime = CaipiaoRedisTimeEnum.TXFFC.getRedisTime();

                if (nextTxffcLotterySg == null) {
                    nextTxffcLotterySg = this.getNextTxffcLotterySg();
                    redisTemplate.opsForValue().set(nextRedisKey, nextTxffcLotterySg, redisTime, TimeUnit.SECONDS);
                }

                if (nextTxffcLotterySg == null) {
                    return new ArrayList<Map<String, Object>>();
                }
                // ????????????????????????
                String redisKey = RedisKeys.TXFFC_RESULT_VALUE;
                TxffcLotterySg txffcLotterySg = (TxffcLotterySg) redisTemplate.opsForValue().get(redisKey);

                if (txffcLotterySg == null) {
                    txffcLotterySg = this.getTxffcLotterySg();
                    redisTemplate.opsForValue().set(redisKey, txffcLotterySg);
                }

                String nextIssue = nextTxffcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTxffcLotterySg.getIssue();
                String txffnextIssue = txffcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : txffcLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    if (nextIssue.length() > 4) {
                        nextIssue = nextIssue.substring(nextIssue.length() - 4, nextIssue.length());
                    }

                    if (txffnextIssue.length() > 4) {
                        txffnextIssue = txffnextIssue.substring(txffnextIssue.length() - 4, txffnextIssue.length());
                    }

                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    // ????????????????????????????????????????????????????????????
                    if (differenceNum < 1 || differenceNum > 2) {
                        return new ArrayList<Map<String, Object>>();
                    }
                }

                // ???????????????????????????????????????
                String issueString = nextTxffcLotterySg.getIssue();
                Long nextTimeLong = DateUtils.getTimeMillis(nextTxffcLotterySg.getIdealTime()) / 1000L;
                for (Map<String, Object> longMap : txffcLongMapList) {
                    longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), issueString);
                    longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextTimeLong);
                    txffcResultLongMapList.add(longMap);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TxffcLotterySgServiceImpl_addNextIssueInfo_error:", e);
        }
        return txffcResultLongMapList;
    }

    /**
     * @Title: getTxffcBigAndSmallLong
     * @Description: ????????????????????????????????????
     * @author HANS
     * @date 2019???5???16?????????4:46:35
     */
    private List<Map<String, Object>> getTxffcBigAndSmallLong(List<TxffcLotterySg> txffcLotterySgList) {
        List<Map<String, Object>> txffcBigLongMapList = new ArrayList<Map<String, Object>>();
        // ??????????????????????????????????????????
        Map<String, Object> twoWallBigAndSmallDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> firstBigAndSmallDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDYQBIG.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> secondBigAndSmallDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDEQBIG.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> thirdBigAndSmallDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDSQBIG.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> fourthBigAndSmallDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDFQBIG.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> fivethBigAndSmallDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDWQBIG.getTagType());

        // ????????????????????????????????????
        if (twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcBigLongMapList.add(twoWallBigAndSmallDragonMap);
        }

        if (firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcBigLongMapList.add(firstBigAndSmallDragonMap);
        }

        if (secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcBigLongMapList.add(secondBigAndSmallDragonMap);
        }

        if (thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcBigLongMapList.add(thirdBigAndSmallDragonMap);
        }

        if (fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcBigLongMapList.add(fourthBigAndSmallDragonMap);
        }

        if (fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcBigLongMapList.add(fivethBigAndSmallDragonMap);
        }
        return txffcBigLongMapList;
    }

    /**
     * @Title: getTxffcSigleAndDoubleLong
     * @Description: ????????????????????????????????????
     * @author HANS
     * @date 2019???5???16?????????4:47:06
     */
    private List<Map<String, Object>> getTxffcSigleAndDoubleLong(List<TxffcLotterySg> txffcLotterySgList) {
        List<Map<String, Object>> txffcSigleLongMapList = new ArrayList<Map<String, Object>>();
        // ??????????????????????????????????????????
        Map<String, Object> twoWallSigleAndDoubleDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCLMZHDOUBLE.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> firstSigleAndDoubleDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDYQDOUBLE.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> secondSigleAndDoubleDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDEQDOUBLE.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> thirdSigleAndDoubleDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDSQDOUBLE.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> fourthSigleAndDoubleDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDFQDOUBLE.getTagType());
        // ???????????????????????????????????????
        Map<String, Object> fivethSigleAndDoubleDragonMap = this.getDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDWQDOUBLE.getTagType());
        // ??????
        if (twoWallSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcSigleLongMapList.add(twoWallSigleAndDoubleDragonMap);
        }

        if (firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcSigleLongMapList.add(firstSigleAndDoubleDragonMap);
        }

        if (secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcSigleLongMapList.add(secondSigleAndDoubleDragonMap);
        }

        if (thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcSigleLongMapList.add(thirdSigleAndDoubleDragonMap);
        }

        if (fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcSigleLongMapList.add(fourthSigleAndDoubleDragonMap);
        }

        if (fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            txffcSigleLongMapList.add(fivethSigleAndDoubleDragonMap);
        }
        return txffcSigleLongMapList;
    }

    /**
     * @return Map<String, Object>
     * @Title: getDragonInfo
     * @Description: ?????????????????????????????????
     * @author HANS
     * @date 2019???5???13?????????12:00:46
     */
    private Map<String, Object> getDragonInfo(List<TxffcLotterySg> txffcLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(txffcLotterySgList)) {
                // ????????????
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < txffcLotterySgList.size(); index++) {
                    TxffcLotterySg txffcLotterySg = txffcLotterySgList.get(index);
                    // ????????????????????????
                    String bigOrSmallName = this.calculateResult(type, txffcLotterySg);

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
                // ??????????????????
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#JssscLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @return String
     * @Title: calculateResult
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???13?????????10:33:30
     */
    private String calculateResult(int type, TxffcLotterySg txffcLotterySg) {
        String result = Constants.DEFAULT_NULL;
        String number = Constants.DEFAULT_NULL;
        number = txffcLotterySg.getCpkNumber() == null ? Constants.DEFAULT_NULL : txffcLotterySg.getCpkNumber();

        if (StringUtils.isEmpty(number)) {
            number = txffcLotterySg.getKcwNumber() == null ? Constants.DEFAULT_NULL : txffcLotterySg.getKcwNumber();
        }
        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 233:
                result = AusactSgUtils.getJssscBigOrSmall(number);//??????????????????
                break;
            case 234:
                result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getWan());//???????????????
                break;
            case 235:
                result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getQian());//???????????????
                break;
            case 236:
                result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getBai());//???????????????
                break;
            case 237:
                result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getShi());//???????????????
                break;
            case 238:
                result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getGe());//???????????????
                break;
            case 239:
                result = AusactSgUtils.getSingleAndDouble(number);//??????????????????
                break;
            case 240:
                result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getWan());//???????????????
                break;
            case 241:
                result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getQian());//???????????????
                break;
            case 242:
                result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getBai());//???????????????
                break;
            case 243:
                result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getShi());//???????????????
                break;
            case 244:
                result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getGe());//???????????????
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: organizationDragonResultMap
     * @author HANS
     * @date 2019???5???13?????????1:53:19
     */
    private Map<String, Object> organizationDragonResultMap(int type, Set<String> dragonSet, Integer dragonSize) {
        // ?????????????????????????????????
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // ????????????????????? ?????? ????????????
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.TXFFC.getTagCnName(), CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getPlayName(),
                    CaipiaoTypeEnum.TXFFC.getTagType(), CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getTagType() + "");
            List<String> dragonList = new ArrayList<String>(dragonSet);
            // ????????????
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 233) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALBIG);
            } else if (type == 234) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDYQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDYQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDYQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 235) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDEQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDEQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDEQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 236) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDSQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDSQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDSQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 237) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDFQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDFQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDFQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 238) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDWQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDWQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDWQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 239) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCLMZHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCLMZHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCLMZHDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALDOUBLE);
            } else if (type == 240) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDYQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDYQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDYQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 241) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDEQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDEQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDEQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 242) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDSQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDSQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDSQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 243) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDFQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDFQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDFQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 244) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDWQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDWQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TXFFCDWQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            }
            // ?????????????????????????????????MAP???
            longDragonMap.putAll(oddsListMap);
            // ?????????????????????
            String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
            //String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.TXFFC.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.TXFFC.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TxffcLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: selectNearTxffcIssue
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???5???28?????????8:26:41
     */
    private List<TxffcLotterySg> selectNearTxffcIssue() {
        TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
        txffcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        txffcExample.setOrderByClause("`ideal_time` DESC");
        txffcExample.setOffset(Constants.DEFAULT_INTEGER);
        txffcExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<TxffcLotterySg> txffcLotterySgList = txffcLotterySgMapper.selectByExample(txffcExample);
        return txffcLotterySgList;
    }

}
