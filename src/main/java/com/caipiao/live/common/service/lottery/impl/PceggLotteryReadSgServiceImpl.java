package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.result.PceggColdHotDTO;
import com.caipiao.live.common.model.dto.result.PceggLotterySgDTO;
import com.caipiao.live.common.model.dto.result.PceggLotterySgDTO2;
import com.caipiao.live.common.model.dto.result.PceggLotterySgDTO3;
import com.caipiao.live.common.model.dto.result.PceggLotterySgDTO4;
import com.caipiao.live.common.model.dto.result.PceggLotteryTodayAndHistoryNews;
import com.caipiao.live.common.model.dto.result.PceggMissDTO;
import com.caipiao.live.common.model.dto.result.PceggNumRegionDTO;
import com.caipiao.live.common.model.dto.result.PceggNumberDTO;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;
import com.caipiao.live.common.mybatis.entity.PceggLotterySg;
import com.caipiao.live.common.mybatis.entity.PceggLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.PceggLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.PceggLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.PceggLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
import com.caipiao.live.common.util.lottery.PceggUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ShaoMing
 * @datetime 2018/7/26 16:37
 */
@Service
public class PceggLotteryReadSgServiceImpl implements PceggLotterySgServiceReadSg {

    private final Logger logger = LoggerFactory.getLogger(PceggLotteryReadSgServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;
    @Autowired
    private PceggLotterySgMapperExt pceggLotterySgMapperExt;
    @Autowired
    private PceggLotterySgServiceReadSg pceggLotterySgService;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;

    //红波
    public final List<Integer> RED = Lists.newArrayList(3, 6, 9, 12, 15, 18, 21, 24);
    //蓝波
    public final List<Integer> BLUE = Lists.newArrayList(2, 5, 8, 11, 17, 20, 23, 26);
    //绿波
    public final List<Integer> GREEN = Lists.newArrayList(1, 4, 7, 10, 16, 19, 22, 25);

    @Override
    public ResultInfo<Map<String, Object>> getSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullPkResult();
        try {
            // 缓存中取开奖数量
            String openRedisKey = RedisKeys.PCDAND_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("openStatus", LotteryResultStatus.AUTO);
                map.put("paramTime", TimeHelper.date("yyyy-MM-dd")+"%");
                openCount = pceggLotterySgMapperExt.openCountByExample(map);
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }

            if (openCount != null) {
                result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
                // 获取开奖总期数
                Integer sumCount = CaipiaoSumCountEnum.PCDAND.getSumCount();
                // 计算当日剩余未开奖次数
                result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            }
            String nextRedisKey = RedisKeys.PCDAND_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.PCDAND.getRedisTime();
            PceggLotterySg nextPceggLotterySg = (PceggLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextPceggLotterySg == null) {
                nextPceggLotterySg = this.getNextPceggLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextPceggLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.PCDAND_RESULT_VALUE;
            PceggLotterySg pceggLotterySg = (PceggLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (pceggLotterySg == null) {
                pceggLotterySg = this.getPceggLotterySg();
                redisTemplate.opsForValue().set(redisKey, pceggLotterySg);
            }
            if (nextPceggLotterySg != null) {
                String nextIssue = nextPceggLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextPceggLotterySg.getIssue();
                String txffnextIssue = pceggLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : pceggLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextPceggLotterySg = this.getNextPceggLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextPceggLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        pceggLotterySg = this.getPceggLotterySg();
                        redisTemplate.opsForValue().set(redisKey, pceggLotterySg);
                    }
                }
                if (pceggLotterySg != null) {
                    if (StringUtils.isEmpty(pceggLotterySg.getNumber())) {
                        pceggLotterySg = this.getPceggLotterySg();
                        redisTemplate.opsForValue().set(redisKey, pceggLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), pceggLotterySg == null ? Constants.DEFAULT_NULL : pceggLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), pceggLotterySg == null ? Constants.DEFAULT_NULL : pceggLotterySg.getNumber());
                }

                if (nextPceggLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextPceggLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextPceggLotterySg.getIdealTime()) / 1000L);
                }
            } else {
                if (pceggLotterySg != null) {
                    if (StringUtils.isEmpty(pceggLotterySg.getNumber())) {
                        pceggLotterySg = this.getPceggLotterySg();
                        redisTemplate.opsForValue().set(redisKey, pceggLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), pceggLotterySg == null ? Constants.DEFAULT_NULL : pceggLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), pceggLotterySg == null ? Constants.DEFAULT_NULL : pceggLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.PCDAND.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    /**
     * @return PceggLotterySg
     * @Title: getPceggLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月1日下午4:13:36
     */
    public PceggLotterySg getPceggLotterySg() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        PceggLotterySg pceggLotterySg = pceggLotterySgMapper.selectOneByExample(example);
        return pceggLotterySg;
    }

    /**
     * @return PceggLotterySg
     * @Title: getPceggLotterySg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年4月29日下午9:33:29
     */
    public PceggLotterySg getNextPceggLotterySg() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        example.setOrderByClause("`ideal_time` ASC");
        PceggLotterySg nextPceggLotterySg = pceggLotterySgMapper.selectOneByExample(example);
        return nextPceggLotterySg;
    }

    @Override
    public ResultInfo<Map<String, Object>> getSgInfoWeb() {
        Map<String, Object> result = new HashMap<>();

//        // 查询最后一期信息
//        PceggLotterySgExample example = new PceggLotterySgExample();
//        example.setOrderByClause("`time` DESC");
//        example.setLimit(1);
//        PceggLotterySg pceggLotterySg = pceggLotterySgMapper.selectOneByExample(example);

        // 查询最后一期信息
        PceggLotterySg pceggLotterySg = this.queryLastSg(true);

        String name = "PC蛋蛋", issue = "", number = "", time = "", he = "";

        if (pceggLotterySg != null && !StringUtils.isEmpty(pceggLotterySg.getNumber())) {
            String sgNumber = pceggLotterySg.getNumber();
            String[] numArr = sgNumber.split(",");
            issue = pceggLotterySg.getIssue();
            number = sgNumber;
            time = pceggLotterySg.getTime();
            he = Integer.toString(Integer.valueOf(numArr[0]) + Integer.valueOf(numArr[1]) + Integer.valueOf(numArr[2]));
        }

        result.put("name", name);
        result.put("issue", issue);
        result.put("number", number);
        result.put("time", time);
        result.put("he", he);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> getNextIssue() {
        Map<String, Object> result = new HashMap<>();
        // 获取下一期未开奖的期号信息
        PceggLotterySg pceggLotterySg = this.queryNextSg();
        result.put("nextIssue", pceggLotterySg.getIssue());
        result.put("nextOpenTime", DateUtils.dateStringToDate(pceggLotterySg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<List<PceggLotterySgDTO>> pceggSgHistoryList(String date, Integer pageNo, Integer pageSize) {
        List<PceggLotterySg> pceggLotterySgs = this.querySgList(date, "DESC", pageNo, pageSize);

        // 封装新结果
        List<PceggLotterySgDTO> list = new ArrayList<>();
        PceggLotterySgDTO dto;
        // 遍历结果
        for (PceggLotterySg sg : pceggLotterySgs) {
            dto = new PceggLotterySgDTO();
            BeanUtils.copyProperties(sg, dto);
            String number = sg.getNumber();
            if (StringUtils.isEmpty(number)) {
                list.add(dto);
                continue;
            }
            Integer sum = PceggUtil.sumNumber(sg.getNumber());
//            dto.setSgSign(1);
            dto.setSum(sum);
            dto.setNumberstr(sg.getNumber());
            dto.setBigOrSmall(PceggUtil.checkBigOrSmall(sum));
            dto.setSingleOrDouble(PceggUtil.checkSingleOrDouble(sum));
            list.add(dto);
        }
        return ResultInfo.ok(list);
    }

    @Override
    public ResultInfo<Map<String, Object>> webPcEggSgHistoryList(String date, Integer pageNo, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        // 查询总量
        int totalNum = this.querySgListCount(date, "DESC");
        // 查询数据
        List<PceggLotterySg> pceggLotterySgs = this.querySgList(date, "DESC", pageNo, pageSize);

        // 封装新结果
        List<PceggLotterySgDTO> list = new ArrayList<>();
        PceggLotterySgDTO dto;
        // 遍历结果
        for (PceggLotterySg sg : pceggLotterySgs) {
            dto = new PceggLotterySgDTO();
            BeanUtils.copyProperties(sg, dto);
            String number = sg.getNumber();
            if (StringUtils.isEmpty(number)) {
                list.add(dto);
                continue;
            }
            Integer sum = PceggUtil.sumNumber(sg.getNumber());
            dto.setSum(sum);
            dto.setNumberstr(sg.getNumber());
            dto.setBigOrSmall(PceggUtil.checkBigOrSmall(sum));
            dto.setSingleOrDouble(PceggUtil.checkSingleOrDouble(sum));
            list.add(dto);
        }

        result.put("list", list);
        result.put("totalNum", totalNum);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> pceggStatistics(String date) {
        Map<String, Object> result = new HashMap<>();
//        // 根据条件查询
//        PceggLotterySgExample example = new PceggLotterySgExample();
//        PceggLotterySgExample.Criteria criteria = example.createCriteria();
//        criteria.andTimeLike(date+"%");
//        example.setOrderByClause("`time` DESC");
//        // 查询当日开奖记录
//        List<PceggLotterySg> sgList = pceggLotterySgMapper.selectByExample(example);
        List<PceggLotterySg> sgList = this.querySgList(date, "", null, null);

        List<PceggNumberDTO> list = new ArrayList<>();
        PceggNumberDTO numberDTO;
        PceggNumRegionDTO regionDTO;

        // 当日当前已开总期数
        Integer total = sgList.size();

        for (Integer i = 0; i < 10; i++) {
            numberDTO = new PceggNumberDTO();
            Integer num1, num2, num3, count1 = 0, count2 = 0, count3 = 0;
            for (PceggLotterySg pcEgg : sgList) {
                String number = pcEgg.getNumber();
                if (StringUtils.isEmpty(number)) {
                    continue;
                }
                String[] numbers = number.split(",");
                num1 = Integer.parseInt(numbers[0]);
                num2 = Integer.parseInt(numbers[1]);
                num3 = Integer.parseInt(numbers[2]);
                if (num1.equals(i)) {
                    count1++;
                }
                if (num2.equals(i)) {
                    count2++;
                }
                if (num3.equals(i)) {
                    count3++;
                }
            }

            regionDTO = new PceggNumRegionDTO(count1, count2, count3, total - count1, total - count2, total - count3);
            numberDTO.setNum(i);
            numberDTO.setNumRegion(regionDTO);
            numberDTO.setOpenCount(count1 + count2 + count3);
            list.add(numberDTO);
        }
        result.put("list", list);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<List<PceggLotterySgDTO2>> pceggSgHistoryList2(Integer pageSize) {
        // 根据条件查询
        List<PceggLotterySg> sgList = this.querySgList(null, "DESC", 1, pageSize);

        // 封装新结果
        List<PceggLotterySgDTO2> list = new ArrayList<>();
        PceggLotterySgDTO2 dto;

        // 遍历结果
        for (PceggLotterySg pcEgg : sgList) {
            dto = new PceggLotterySgDTO2();
            BeanUtils.copyProperties(pcEgg, dto);
            String number = pcEgg.getNumber();
            if (StringUtils.isEmpty(number)) {
                continue;
            }
            Integer sum = PceggUtil.sumNumber(number);
            dto.setSum(sum);
            dto.setBigOrSmall(PceggUtil.checkBigOrSmall(sum));
            dto.setSingleOrDouble(PceggUtil.checkSingleOrDouble(sum));
            dto.setLimitValue(PceggUtil.checkLimitValue(sum));
            dto.setLeopard(PceggUtil.checkLeopard(number));
            list.add(dto);
        }
        return ResultInfo.ok(list);
    }

    @Override
    public ResultInfo<Map<String, Object>> getColdHot() {
        // 查询PC蛋蛋开奖记录前100条
        List<PceggLotterySg> sgList = this.querySgList(null, null, null, 100);

        // 获取第一区冷热列表
        List<PceggColdHotDTO> list1 = this.getColdHotList(sgList, 1);

        // 获取第二区冷热列表
        List<PceggColdHotDTO> list2 = this.getColdHotList(sgList, 2);

        // 获取第三区冷热列表
        List<PceggColdHotDTO> list3 = this.getColdHotList(sgList, 3);

        Map<String, Object> result = new HashMap<>();
        result.put("coldHotList1", list1);
        result.put("coldHotList2", list2);
        result.put("coldHotList3", list3);
        return ResultInfo.ok(result);
    }

    private List<PceggColdHotDTO> getColdHotList(List<PceggLotterySg> pceggLotterySgs, Integer region) {
        if (CollectionUtils.isEmpty(pceggLotterySgs)) {
            return new ArrayList<>();
        }

        List<PceggColdHotDTO> list = new ArrayList<>();
        PceggColdHotDTO dto;
        for (int i = 0; i < 10; i++) {
            dto = new PceggColdHotDTO();
            int count10 = 0, count30 = 0, count50 = 0, count100 = 0;
            dto.setNumber(i);
            // 计算近10期
            for (int j = 0; j < 10 && pceggLotterySgs.size() > j; j++) {
                String number = pceggLotterySgs.get(j).getNumber();
                if (StringUtils.isEmpty(number)) {
                    continue;
                }
                Integer regionNumber = PceggUtil.getRegionNumber(number, region);
                if (regionNumber.equals(i)) {
                    count10++;
                }
            }
            dto.setBefore10(count10);

            // 计算近30期
            for (int j = 0; j < 30 && pceggLotterySgs.size() > j; j++) {
                String number = pceggLotterySgs.get(j).getNumber();
                if (StringUtils.isEmpty(number)) {
                    continue;
                }
                Integer regionNumber = PceggUtil.getRegionNumber(number, region);
                if (regionNumber.equals(i)) {
                    count30++;
                }
            }
            dto.setBefore30(count30);

            // 计算近50期
            for (int j = 0; j < 50 && pceggLotterySgs.size() > j; j++) {
                String number = pceggLotterySgs.get(j).getNumber();
                if (StringUtils.isEmpty(number)) {
                    continue;
                }
                Integer regionNumber = PceggUtil.getRegionNumber(number, region);
                if (regionNumber.equals(i)) {
                    count50++;
                }
            }
            dto.setBefore50(count50);

            // 计算近100期
            for (int j = 0; j < 100 && pceggLotterySgs.size() > j; j++) {
                Integer regionNumber = PceggUtil.getRegionNumber(pceggLotterySgs.get(j).getNumber(), region);
                if (regionNumber.equals(i)) {
                    count100++;
                }
            }
            dto.setBefore100(count100);
            list.add(dto);
        }

        return list;
    }

    @Override
    public ResultInfo<Map<String, Object>> getRegionMissingValueList(Integer region, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();

        // 查询PC蛋蛋开奖记录前100条
//        PceggLotterySgExample example = new PceggLotterySgExample();
//        example.setOrderByClause("`time` DESC");
//        example.setOffset(0);
//        example.setLimit(pageSize+100);
//        List<PceggLotterySg> sgList = pceggLotterySgMapper.selectByExample(example);
        List<PceggLotterySg> sgList = this.querySgList(null, null, null, pageSize + 100);

        List<PceggMissDTO> list = new ArrayList<>();
        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            result.put("list", list);
            return ResultInfo.ok(result);
        }

        PceggMissDTO dto;
        for (int i = 0; i < pageSize && sgList.size() > i; i++) {
            dto = new PceggMissDTO();
            PceggLotterySg sg = sgList.get(i);
            dto.setIssue(sg.getIssue());
            String number = sg.getNumber();
            if (StringUtils.isEmpty(number)) {
                list.add(dto);
                continue;
            }
            Integer realNum = PceggUtil.getRegionNumber(sg.getNumber(), region);
            dto.setRealNum(realNum);
            dto.setNumber0(this.getMissingCount(i, sgList, 0, region));
            dto.setNumber1(this.getMissingCount(i, sgList, 1, region));
            dto.setNumber2(this.getMissingCount(i, sgList, 2, region));
            dto.setNumber3(this.getMissingCount(i, sgList, 3, region));
            dto.setNumber4(this.getMissingCount(i, sgList, 4, region));
            dto.setNumber5(this.getMissingCount(i, sgList, 5, region));
            dto.setNumber6(this.getMissingCount(i, sgList, 6, region));
            dto.setNumber7(this.getMissingCount(i, sgList, 7, region));
            dto.setNumber8(this.getMissingCount(i, sgList, 8, region));
            dto.setNumber9(this.getMissingCount(i, sgList, 9, region));
            list.add(dto);
        }

        List<PceggMissDTO> statistics = new ArrayList<>();

        /**
         * 统计【出现总次数】
         */
        PceggMissDTO sumOpentCount = new PceggMissDTO();
        sumOpentCount.setIssue("出现总次数");
        sumOpentCount.setNumber0(this.getSumOpenCount(sgList, 0, region));
        sumOpentCount.setNumber1(this.getSumOpenCount(sgList, 1, region));
        sumOpentCount.setNumber2(this.getSumOpenCount(sgList, 2, region));
        sumOpentCount.setNumber3(this.getSumOpenCount(sgList, 3, region));
        sumOpentCount.setNumber4(this.getSumOpenCount(sgList, 4, region));
        sumOpentCount.setNumber5(this.getSumOpenCount(sgList, 5, region));
        sumOpentCount.setNumber6(this.getSumOpenCount(sgList, 6, region));
        sumOpentCount.setNumber7(this.getSumOpenCount(sgList, 7, region));
        sumOpentCount.setNumber8(this.getSumOpenCount(sgList, 8, region));
        sumOpentCount.setNumber9(this.getSumOpenCount(sgList, 9, region));

        /**
         * 统计【最大遗漏值】
         */
        int max0 = 0, max1 = 0, max2 = 0, max3 = 0, max4 = 0, max5 = 0, max6 = 0, max7 = 0, max8 = 0, max9 = 0;
        for (PceggMissDTO DTO : list) {
            if (!DTO.getNumber0().equals(DTO.getRealNum()) && DTO.getNumber0() > max0) {
                max0 = DTO.getNumber0();
            }
            if (!DTO.getNumber1().equals(DTO.getRealNum()) && DTO.getNumber1() > max1) {
                max1 = DTO.getNumber1();
            }
            if (!DTO.getNumber2().equals(DTO.getRealNum()) && DTO.getNumber2() > max2) {
                max2 = DTO.getNumber2();
            }
            if (!DTO.getNumber3().equals(DTO.getRealNum()) && DTO.getNumber3() > max3) {
                max3 = DTO.getNumber3();
            }
            if (!DTO.getNumber4().equals(DTO.getRealNum()) && DTO.getNumber4() > max4) {
                max4 = DTO.getNumber4();
            }
            if (!DTO.getNumber5().equals(DTO.getRealNum()) && DTO.getNumber5() > max5) {
                max5 = DTO.getNumber5();
            }
            if (!DTO.getNumber6().equals(DTO.getRealNum()) && DTO.getNumber6() > max6) {
                max6 = DTO.getNumber6();
            }
            if (!DTO.getNumber7().equals(DTO.getRealNum()) && DTO.getNumber7() > max7) {
                max7 = DTO.getNumber7();
            }
            if (!DTO.getNumber8().equals(DTO.getRealNum()) && DTO.getNumber8() > max8) {
                max8 = DTO.getNumber8();
            }
            if (!DTO.getNumber9().equals(DTO.getRealNum()) && DTO.getNumber9() > max9) {
                max9 = DTO.getNumber9();
            }
        }
        PceggMissDTO maxMissing = new PceggMissDTO();
        maxMissing.setIssue("最大遗漏值");
        maxMissing.setNumber0(max0);
        maxMissing.setNumber1(max1);
        maxMissing.setNumber2(max2);
        maxMissing.setNumber3(max3);
        maxMissing.setNumber4(max4);
        maxMissing.setNumber5(max5);
        maxMissing.setNumber6(max6);
        maxMissing.setNumber7(max7);
        maxMissing.setNumber8(max8);
        maxMissing.setNumber9(max9);

        /**
         * 统计【平均遗漏值】
         */
        PceggMissDTO avgMissing = new PceggMissDTO();
        avgMissing.setIssue("平均遗漏值");
        avgMissing.setNumber0((max0 - sumOpentCount.getNumber0()) / (sumOpentCount.getNumber0() + 1));
        avgMissing.setNumber1((max1 - sumOpentCount.getNumber1()) / (sumOpentCount.getNumber1() + 1));
        avgMissing.setNumber2((max2 - sumOpentCount.getNumber2()) / (sumOpentCount.getNumber2() + 1));
        avgMissing.setNumber3((max3 - sumOpentCount.getNumber3()) / (sumOpentCount.getNumber3() + 1));
        avgMissing.setNumber4((max4 - sumOpentCount.getNumber4()) / (sumOpentCount.getNumber4() + 1));
        avgMissing.setNumber5((max5 - sumOpentCount.getNumber5()) / (sumOpentCount.getNumber5() + 1));
        avgMissing.setNumber6((max6 - sumOpentCount.getNumber6()) / (sumOpentCount.getNumber6() + 1));
        avgMissing.setNumber7((max7 - sumOpentCount.getNumber7()) / (sumOpentCount.getNumber7() + 1));
        avgMissing.setNumber8((max8 - sumOpentCount.getNumber8()) / (sumOpentCount.getNumber8() + 1));
        avgMissing.setNumber9((max9 - sumOpentCount.getNumber9()) / (sumOpentCount.getNumber9() + 1));

        /**
         * 统计【最大连出值】
         */
        PceggMissDTO continuous = new PceggMissDTO();
        continuous.setIssue("最大连出值");
        continuous.setNumber0(this.getContinuousValue(list, 0));
        continuous.setNumber1(this.getContinuousValue(list, 1));
        continuous.setNumber2(this.getContinuousValue(list, 2));
        continuous.setNumber3(this.getContinuousValue(list, 3));
        continuous.setNumber4(this.getContinuousValue(list, 4));
        continuous.setNumber5(this.getContinuousValue(list, 5));
        continuous.setNumber6(this.getContinuousValue(list, 6));
        continuous.setNumber7(this.getContinuousValue(list, 7));
        continuous.setNumber8(this.getContinuousValue(list, 8));
        continuous.setNumber9(this.getContinuousValue(list, 9));

        statistics.add(sumOpentCount);
        statistics.add(avgMissing);
        statistics.add(maxMissing);
        statistics.add(continuous);

        result.put("missingValList", list);
        result.put("statistics", statistics);
        return ResultInfo.ok(result);
    }

    /**
     * 统计【最大连出值】
     *
     * @param list   遗漏值集合
     * @param number 号码（0-9）
     * @return
     */
    private Integer getContinuousValue(List<PceggMissDTO> list, int number) {
        Integer count = 0, first = 0;
        Integer beforeValue = -1;
        for (PceggMissDTO dto : list) {
            Integer realNum = dto.getRealNum();
            // 判断是不是第一次出现，如果是第一次出现，count赋值为1
            if (first.equals(0) && realNum.equals(number)) {
                first++;
                count = 1;
            }
            // 如果此次开奖号码为当前号码并且与上一次开奖号码一直，count ++
            if (realNum.equals(number) && realNum.equals(beforeValue)) {
                count++;
            }
            beforeValue = realNum;
        }
        return count;
    }

    /**
     * 获取指定号码出现总次数
     *
     * @param pceggLotterySgs 开奖结果集
     * @param number          码号（0-9）
     * @param region          第几区
     * @return
     */
    private Integer getSumOpenCount(List<PceggLotterySg> pceggLotterySgs, int number, Integer region) {
        int count = 0;
        for (int i = 0; i < 100 && pceggLotterySgs.size() > i; i++) {
            PceggLotterySg sg = pceggLotterySgs.get(i);
            String num = sg.getNumber();
            if (StringUtils.isEmpty(num)) {
                continue;
            }
            Integer realNum = PceggUtil.getRegionNumber(num, region);
            if (realNum.equals(number)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取指定号码出现总次数
     *
     * @param pceggLotterySgs 开奖结果集
     * @param number          码号（0-27）
     * @return
     */
    private Integer getTemaSumOpenCount(List<PceggLotterySg> pceggLotterySgs, int number) {
        int count = 0;
        for (int i = 0; i < 100 && pceggLotterySgs.size() > i; i++) {
            PceggLotterySg sg = pceggLotterySgs.get(i);
            String num = sg.getNumber();
            if (StringUtils.isEmpty(num)) {
                continue;
            }
            Integer realNum = PceggUtil.sumNumber(sg.getNumber());
            if (realNum.equals(number)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取遗漏值
     *
     * @param i               第一重循环的当前循环变量
     * @param pceggLotterySgs 开奖结果集
     * @param num             码号（0-9）
     * @param region          第几区
     * @return
     */
    private Integer getMissingCount(int i, List<PceggLotterySg> pceggLotterySgs, int num, int region) {
        int number = 0;
        for (int j = i; j < 200 && j < pceggLotterySgs.size(); j++) {
            String numberStr = pceggLotterySgs.get(j).getNumber();
            if (StringUtils.isEmpty(numberStr)) {
                continue;
            }
            Integer regionNumber = PceggUtil.getRegionNumber(pceggLotterySgs.get(j).getNumber(), region);
            if (j == i && regionNumber.equals(num)) {
                number = num;
                break;
            } else if (regionNumber.equals(num)) {
                break;
            } else {
                number++;
            }
        }
        return number;
    }

    @Override
    public ResultInfo<Map<String, Object>> sgDetails(String issue) {
        Map<String, Object> map = new HashMap<>();

        // 判断参数是否为空
        if (StringUtils.isBlank(issue)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        // 查询相应的开奖信息
        PceggLotterySg sg = this.querySgByIssue(issue);

        // 判空
        if (sg == null || StringUtils.isEmpty(sg.getNumber())) {
            return ResultInfo.ok(map);
        }

        String number = sg.getNumber();
        map.put("issue", sg.getIssue());
        map.put("time", sg.getTime());
        map.put("number", number);
        String[] numArr = number.split(",");
        int he = Integer.valueOf(numArr[0]) + Integer.valueOf(numArr[1]) + Integer.valueOf(numArr[2]);
        map.put("he", he);
        String dx;
        if (he >= 14) {
            dx = "大";
        } else {
            dx = "小";
        }
        String ds;
        if (he % 2 == 1) {
            ds = "单";
        } else {
            ds = "双";
        }
        map.put("lm", new StringBuffer().append(dx).append("/").append(ds).append("/").append(dx).append(ds).toString());
        String sebo = "";
        if (RED.contains(he)) {
            sebo = "红波";
        } else if (BLUE.contains(he)) {
            sebo = "蓝波";
        } else if (GREEN.contains(he)) {
            sebo = "绿波";
        }
        map.put("sebo", sebo);
        String baozi = "无";
        if (numArr[0].equals(numArr[1]) && numArr[0].equals(numArr[2])) {
            baozi = "有";
        }
        map.put("baozi", baozi);
        map.put("baosan", number);
        map.put("tema", he);
        return ResultInfo.ok(map);
    }

    @Override
    public ResultInfo<Map<String, Object>> getNowIssueAndTime() {
        Map<String, Object> result = new HashMap<>();

//        // 查询最后一期信息
//        PceggLotterySgExample example = new PceggLotterySgExample();
//        example.setOrderByClause("`time` DESC");
//        example.setLimit(1);
//        PceggLotterySg pceggLotterySg = pceggLotterySgMapper.selectOneByExample(example);
//        String issue = pceggLotterySg.getIssue();
//        String time = pceggLotterySg.getTime();
//        // 计算下一期【期号】
//        Integer nextIssue = NextIssueTimeUtil.nextIssuePcegg(time, issue);
//        result.put("issue", nextIssue + "");
//
//        // 获取下一期开奖时间
//        result.put("time", NextIssueTimeUtil.getNextIssueTimePCDD());

        PceggLotterySg sg = this.queryNextSg();
        result.put("issue", sg.getIssue());
        result.put("time", DateUtils.dateStringToDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS).getTime() / 1000);

        return ResultInfo.ok(result);
    }


    /**
     * pc蛋蛋web端今日统计
     *
     * @param date 当前日期
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getPcEggWebStatistics(String date) {
        Map<String, Object> result = new HashMap<>();
//        // 根据条件查询
//        PceggLotterySgExample example = new PceggLotterySgExample();
//        PceggLotterySgExample.Criteria criteria = example.createCriteria();
//        criteria.andTimeLike(date + "%");
//        example.setOrderByClause("`time` DESC");
//        // 查询当日开奖记录
//        List<PceggLotterySg> sgList = pceggLotterySgMapper.selectByExample(example);
        List<PceggLotterySg> sgList = this.querySgList(date, null, null, null);

        PceggLotterySgDTO3 pceggLotterySgDTO3 = new PceggLotterySgDTO3(true);
        PceggLotterySgDTO4 pceggLotterySgDTO4 = new PceggLotterySgDTO4(true);
        List<Integer> list = new ArrayList<>();

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            result.put("list", list);
            return ResultInfo.ok(result);
        }

        int sum; //记录和值
        for (PceggLotterySg pc : sgList) {
            sum = 0;
            String number = pc.getNumber();
            //分割开奖号
            String[] arr = number.split(",");
            //判断是否是豹子
            if (arr[0].equals(arr[1]) && arr[0].equals(arr[2])) {
                pceggLotterySgDTO4.setLeopard(pceggLotterySgDTO4.getLeopard() + 1);
            }

            for (int j = 0; j < arr.length; j++) {
                //计算每次开奖的和值
                sum += Integer.parseInt(arr[j]);
            }
            list.add(sum);
        }

        pceggLotterySgDTO3.setCountIssue(list.size());
        pceggLotterySgDTO4.setCountIssue(list.size());

        //特码记录次数
        for (Integer numb : list) {
            switch (numb) {
                case 0:
                    pceggLotterySgDTO3.setNumber0(pceggLotterySgDTO3.getNumber0() + 1);
                    break;
                case 1:
                    pceggLotterySgDTO3.setNumber1(pceggLotterySgDTO3.getNumber1() + 1);
                    break;
                case 2:
                    pceggLotterySgDTO3.setNumber2(pceggLotterySgDTO3.getNumber2() + 1);
                    break;
                case 3:
                    pceggLotterySgDTO3.setNumber3(pceggLotterySgDTO3.getNumber3() + 1);
                    break;
                case 4:
                    pceggLotterySgDTO3.setNumber4(pceggLotterySgDTO3.getNumber4() + 1);
                    break;
                case 5:
                    pceggLotterySgDTO3.setNumber5(pceggLotterySgDTO3.getNumber5() + 1);
                    break;
                case 6:
                    pceggLotterySgDTO3.setNumber6(pceggLotterySgDTO3.getNumber6() + 1);
                    break;
                case 7:
                    pceggLotterySgDTO3.setNumber7(pceggLotterySgDTO3.getNumber7() + 1);
                    break;
                case 8:
                    pceggLotterySgDTO3.setNumber8(pceggLotterySgDTO3.getNumber8() + 1);
                    break;
                case 9:
                    pceggLotterySgDTO3.setNumber9(pceggLotterySgDTO3.getNumber9() + 1);
                    break;
                case 10:
                    pceggLotterySgDTO3.setNumber10(pceggLotterySgDTO3.getNumber10() + 1);
                    break;
                case 11:
                    pceggLotterySgDTO3.setNumber11(pceggLotterySgDTO3.getNumber11() + 1);
                    break;
                case 12:
                    pceggLotterySgDTO3.setNumber12(pceggLotterySgDTO3.getNumber12() + 1);
                    break;
                case 13:
                    pceggLotterySgDTO3.setNumber13(pceggLotterySgDTO3.getNumber13() + 1);
                    break;
                case 14:
                    pceggLotterySgDTO3.setNumber14(pceggLotterySgDTO3.getNumber14() + 1);
                    break;
                case 15:
                    pceggLotterySgDTO3.setNumber15(pceggLotterySgDTO3.getNumber15() + 1);
                    break;
                case 16:
                    pceggLotterySgDTO3.setNumber16(pceggLotterySgDTO3.getNumber16() + 1);
                    break;
                case 17:
                    pceggLotterySgDTO3.setNumber17(pceggLotterySgDTO3.getNumber17() + 1);
                    break;
                case 18:
                    pceggLotterySgDTO3.setNumber18(pceggLotterySgDTO3.getNumber18() + 1);
                    break;
                case 19:
                    pceggLotterySgDTO3.setNumber19(pceggLotterySgDTO3.getNumber19() + 1);
                    break;
                case 20:
                    pceggLotterySgDTO3.setNumber20(pceggLotterySgDTO3.getNumber20() + 1);
                    break;
                case 21:
                    pceggLotterySgDTO3.setNumber21(pceggLotterySgDTO3.getNumber21() + 1);
                    break;
                case 22:
                    pceggLotterySgDTO3.setNumber22(pceggLotterySgDTO3.getNumber22() + 1);
                    break;
                case 23:
                    pceggLotterySgDTO3.setNumber23(pceggLotterySgDTO3.getNumber23() + 1);
                    break;
                case 24:
                    pceggLotterySgDTO3.setNumber24(pceggLotterySgDTO3.getNumber24() + 1);
                    break;
                case 25:
                    pceggLotterySgDTO3.setNumber25(pceggLotterySgDTO3.getNumber25() + 1);
                    break;
                case 26:
                    pceggLotterySgDTO3.setNumber26(pceggLotterySgDTO3.getNumber26() + 1);
                    break;
                case 27:
                    pceggLotterySgDTO3.setNumber27(pceggLotterySgDTO3.getNumber27() + 1);
                    break;
            }
        }

        //混合记录次数
        for (Integer numb : list) {
            if (RED.contains(numb)) {
                pceggLotterySgDTO4.setRedWave(pceggLotterySgDTO4.getRedWave() + 1);
            } else if (BLUE.contains(numb)) {
                pceggLotterySgDTO4.setLambo(pceggLotterySgDTO4.getLambo() + 1);
            } else if (GREEN.contains(numb)) {
                pceggLotterySgDTO4.setGreenWave(pceggLotterySgDTO4.getGreenWave() + 1);
            }

            switch (numb) {
                case 0:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);  //小
                    pceggLotterySgDTO4.setMinimum(pceggLotterySgDTO4.getMinimum() + 1); //极小
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); // 双
                    pceggLotterySgDTO4.setSmallTwo(pceggLotterySgDTO4.getSmallTwo() + 1);//小双
                    break;
                case 1:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);    //小
                    pceggLotterySgDTO4.setMinimum(pceggLotterySgDTO4.getMinimum() + 1); //极小
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1);   // 单
                    pceggLotterySgDTO4.setSmallOne(pceggLotterySgDTO4.getSmallOne() + 1);//小单
                    break;
                case 2:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);    //小
                    pceggLotterySgDTO4.setMinimum(pceggLotterySgDTO4.getMinimum() + 1); //极小
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); // 双
                    pceggLotterySgDTO4.setSmallTwo(pceggLotterySgDTO4.getSmallTwo() + 1);//小双
                    break;
                case 3:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);   //小
                    pceggLotterySgDTO4.setMinimum(pceggLotterySgDTO4.getMinimum() + 1); //极小
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); // 单
                    pceggLotterySgDTO4.setSmallOne(pceggLotterySgDTO4.getSmallOne() + 1);//小单
                    break;
                case 4:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);    //小
                    pceggLotterySgDTO4.setMinimum(pceggLotterySgDTO4.getMinimum() + 1); //极小
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1);       // 双
                    pceggLotterySgDTO4.setSmallTwo(pceggLotterySgDTO4.getSmallTwo() + 1);//小双
                    break;
                case 5:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);     //小
                    pceggLotterySgDTO4.setMinimum(pceggLotterySgDTO4.getMinimum() + 1); //极小
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); // 单
                    pceggLotterySgDTO4.setSmallOne(pceggLotterySgDTO4.getSmallOne() + 1);//小单
                    break;
                case 6:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);  //小
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1);   //双
                    pceggLotterySgDTO4.setSmallTwo(pceggLotterySgDTO4.getSmallTwo() + 1);//小双
                    break;
                case 7:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);  //小
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setSmallOne(pceggLotterySgDTO4.getSmallOne() + 1);//小单
                    break;
                case 8:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1); //小
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1);   //双
                    pceggLotterySgDTO4.setSmallTwo(pceggLotterySgDTO4.getSmallTwo() + 1);//小双
                    break;
                case 9:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1); //小
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setSmallOne(pceggLotterySgDTO4.getSmallOne() + 1);//小单
                    break;
                case 10:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);  //小
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1);  //双
                    pceggLotterySgDTO4.setSmallTwo(pceggLotterySgDTO4.getSmallTwo() + 1);//小双
                    break;
                case 11:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1); //小
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setSmallOne(pceggLotterySgDTO4.getSmallOne() + 1);//小单
                    break;
                case 12:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);  //小
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1);   //双
                    pceggLotterySgDTO4.setSmallTwo(pceggLotterySgDTO4.getSmallTwo() + 1);//小双
                    break;
                case 13:
                    pceggLotterySgDTO4.setSmall(pceggLotterySgDTO4.getSmall() + 1);  //小
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setSmallOne(pceggLotterySgDTO4.getSmallOne() + 1);//小单
                    break;
                case 14:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1);  //大
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); //双
                    pceggLotterySgDTO4.setBigTwo(pceggLotterySgDTO4.getBigTwo() + 1); //大双
                    break;
                case 15:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1);  //大
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setBigSingle(pceggLotterySgDTO4.getBigSingle() + 1);  //大单
                    break;
                case 16:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); //双
                    pceggLotterySgDTO4.setBigTwo(pceggLotterySgDTO4.getBigTwo() + 1); //大双
                    break;
                case 17:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1);  //大
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setBigSingle(pceggLotterySgDTO4.getBigSingle() + 1);  //大单
                    break;
                case 18:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); //双
                    pceggLotterySgDTO4.setBigTwo(pceggLotterySgDTO4.getBigTwo() + 1); //大双
                    break;
                case 19:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setBigSingle(pceggLotterySgDTO4.getBigSingle() + 1);  //大单
                    break;
                case 20:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); //双
                    pceggLotterySgDTO4.setBigTwo(pceggLotterySgDTO4.getBigTwo() + 1); //大双
                    break;
                case 21:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1);  //单
                    pceggLotterySgDTO4.setBigSingle(pceggLotterySgDTO4.getBigSingle() + 1);  //大单
                    break;
                case 22:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setMaximum(pceggLotterySgDTO4.getMaximum() + 1); //极大
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1);  //双
                    pceggLotterySgDTO4.setBigTwo(pceggLotterySgDTO4.getBigTwo() + 1); //大双
                    break;
                case 23:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setMaximum(pceggLotterySgDTO4.getMaximum() + 1); //极大
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1);  //单
                    pceggLotterySgDTO4.setBigSingle(pceggLotterySgDTO4.getBigSingle() + 1);  //大单
                    break;
                case 24:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setMaximum(pceggLotterySgDTO4.getMaximum() + 1); //极大
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); //双
                    pceggLotterySgDTO4.setBigTwo(pceggLotterySgDTO4.getBigTwo() + 1); //大双
                    break;
                case 25:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setMaximum(pceggLotterySgDTO4.getMaximum() + 1); //极大
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1);//单
                    pceggLotterySgDTO4.setBigSingle(pceggLotterySgDTO4.getBigSingle() + 1);  //大单
                    break;
                case 26:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setMaximum(pceggLotterySgDTO4.getMaximum() + 1); //极大
                    pceggLotterySgDTO4.setTwo(pceggLotterySgDTO4.getTwo() + 1); //双
                    pceggLotterySgDTO4.setBigTwo(pceggLotterySgDTO4.getBigTwo() + 1); //大双
                    break;
                case 27:
                    pceggLotterySgDTO4.setBig(pceggLotterySgDTO4.getBig() + 1); //大
                    pceggLotterySgDTO4.setMaximum(pceggLotterySgDTO4.getMaximum() + 1); //极大
                    pceggLotterySgDTO4.setSingle(pceggLotterySgDTO4.getSingle() + 1); //单
                    pceggLotterySgDTO4.setBigSingle(pceggLotterySgDTO4.getBigSingle() + 1);  //大单
                    break;
            }
        }
        result.put("pceggLotterySgDTO3", pceggLotterySgDTO3);
        result.put("pceggLotterySgDTO4", pceggLotterySgDTO4);
        return ResultInfo.ok(result);
    }

    /**
     * 获取PC蛋蛋Web端：曲线图特码走势
     *
     * @param pageSize 近多少期
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getPcEggWebCurveTemaList(Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        // 根据条件查询
//        PceggLotterySgExample example = new PceggLotterySgExample();
//        example.setOrderByClause("`time` DESC");
//        example.setOffset(0);
//        example.setLimit(pageSize+100);
//        List<PceggLotterySg> sgList = pceggLotterySgMapper.selectByExample(example);
        List<PceggLotterySg> sgList = this.querySgList(null, null, null, pageSize + 100);

        List<Map<String, Object>> listMissing = new ArrayList<>();

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            result.put("list", sgList);
            return ResultInfo.ok(result);
        }

        for (int i = 1; i < 4; i++) {
            //调用app的接口
            ResultInfo<Map<String, Object>> regionMissingValueList = pceggLotterySgService.getRegionMissingValueList(i, pageSize);
            //获取data中的所有数据
            Map<String, Object> regionMissingData = regionMissingValueList.getData();

            listMissing.add(regionMissingData);
        }

        List<PceggLotterySgDTO3> list = new ArrayList<>();

        /**
         * 遗漏值
         */
        PceggLotterySgDTO3 dtoCount;
        for (int i = 0; i < pageSize && sgList.size() > i; i++) {
            dtoCount = new PceggLotterySgDTO3();
            PceggLotterySg sg = sgList.get(i);
            dtoCount.setIssue(sg.getIssue());
            Integer realNum = PceggUtil.sumNumber(sg.getNumber());
            dtoCount.setRealNum(realNum);
            dtoCount.setNumber0(this.getTemaMissingCount(i, sgList, 0));
            dtoCount.setNumber1(this.getTemaMissingCount(i, sgList, 1));
            dtoCount.setNumber2(this.getTemaMissingCount(i, sgList, 2));
            dtoCount.setNumber3(this.getTemaMissingCount(i, sgList, 3));
            dtoCount.setNumber4(this.getTemaMissingCount(i, sgList, 4));
            dtoCount.setNumber5(this.getTemaMissingCount(i, sgList, 5));
            dtoCount.setNumber6(this.getTemaMissingCount(i, sgList, 6));
            dtoCount.setNumber7(this.getTemaMissingCount(i, sgList, 7));
            dtoCount.setNumber8(this.getTemaMissingCount(i, sgList, 8));
            dtoCount.setNumber9(this.getTemaMissingCount(i, sgList, 9));
            dtoCount.setNumber10(this.getTemaMissingCount(i, sgList, 10));
            dtoCount.setNumber11(this.getTemaMissingCount(i, sgList, 11));
            dtoCount.setNumber12(this.getTemaMissingCount(i, sgList, 12));
            dtoCount.setNumber13(this.getTemaMissingCount(i, sgList, 13));
            dtoCount.setNumber14(this.getTemaMissingCount(i, sgList, 14));
            dtoCount.setNumber15(this.getTemaMissingCount(i, sgList, 15));
            dtoCount.setNumber16(this.getTemaMissingCount(i, sgList, 16));
            dtoCount.setNumber17(this.getTemaMissingCount(i, sgList, 17));
            dtoCount.setNumber18(this.getTemaMissingCount(i, sgList, 18));
            dtoCount.setNumber19(this.getTemaMissingCount(i, sgList, 19));
            dtoCount.setNumber20(this.getTemaMissingCount(i, sgList, 20));
            dtoCount.setNumber21(this.getTemaMissingCount(i, sgList, 21));
            dtoCount.setNumber22(this.getTemaMissingCount(i, sgList, 22));
            dtoCount.setNumber23(this.getTemaMissingCount(i, sgList, 23));
            dtoCount.setNumber24(this.getTemaMissingCount(i, sgList, 24));
            dtoCount.setNumber25(this.getTemaMissingCount(i, sgList, 25));
            dtoCount.setNumber26(this.getTemaMissingCount(i, sgList, 26));
            dtoCount.setNumber27(this.getTemaMissingCount(i, sgList, 27));

            list.add(dtoCount);
        }

        List<PceggLotterySgDTO3> statisticsTema = new ArrayList<>();


        /**
         * 统计【出现总次数】
         */
        PceggLotterySgDTO3 sumOpentCount = new PceggLotterySgDTO3();
        sumOpentCount.setIssue("出现总次数");
        sumOpentCount.setNumber0(this.getTemaSumOpenCount(sgList, 0));
        sumOpentCount.setNumber1(this.getTemaSumOpenCount(sgList, 1));
        sumOpentCount.setNumber2(this.getTemaSumOpenCount(sgList, 2));
        sumOpentCount.setNumber3(this.getTemaSumOpenCount(sgList, 3));
        sumOpentCount.setNumber4(this.getTemaSumOpenCount(sgList, 4));
        sumOpentCount.setNumber5(this.getTemaSumOpenCount(sgList, 5));
        sumOpentCount.setNumber6(this.getTemaSumOpenCount(sgList, 6));
        sumOpentCount.setNumber7(this.getTemaSumOpenCount(sgList, 7));
        sumOpentCount.setNumber8(this.getTemaSumOpenCount(sgList, 8));
        sumOpentCount.setNumber9(this.getTemaSumOpenCount(sgList, 9));
        sumOpentCount.setNumber10(this.getTemaSumOpenCount(sgList, 10));
        sumOpentCount.setNumber11(this.getTemaSumOpenCount(sgList, 11));
        sumOpentCount.setNumber12(this.getTemaSumOpenCount(sgList, 12));
        sumOpentCount.setNumber13(this.getTemaSumOpenCount(sgList, 13));
        sumOpentCount.setNumber14(this.getTemaSumOpenCount(sgList, 14));
        sumOpentCount.setNumber15(this.getTemaSumOpenCount(sgList, 15));
        sumOpentCount.setNumber16(this.getTemaSumOpenCount(sgList, 16));
        sumOpentCount.setNumber17(this.getTemaSumOpenCount(sgList, 17));
        sumOpentCount.setNumber18(this.getTemaSumOpenCount(sgList, 18));
        sumOpentCount.setNumber19(this.getTemaSumOpenCount(sgList, 19));
        sumOpentCount.setNumber20(this.getTemaSumOpenCount(sgList, 20));
        sumOpentCount.setNumber21(this.getTemaSumOpenCount(sgList, 21));
        sumOpentCount.setNumber22(this.getTemaSumOpenCount(sgList, 22));
        sumOpentCount.setNumber23(this.getTemaSumOpenCount(sgList, 23));
        sumOpentCount.setNumber24(this.getTemaSumOpenCount(sgList, 24));
        sumOpentCount.setNumber25(this.getTemaSumOpenCount(sgList, 25));
        sumOpentCount.setNumber26(this.getTemaSumOpenCount(sgList, 26));
        sumOpentCount.setNumber27(this.getTemaSumOpenCount(sgList, 27));

        /**
         * 统计【最大遗漏值】
         */
        int max0 = 0, max2 = 0, max3 = 0, max4 = 0, max5 = 0, max6 = 0, max7 = 0, max8 = 0, max9 = 0, max10 = 0, max11 = 0, max12 = 0, max13 = 0, max14 = 0, max15 = 0, max16 = 0, max17 = 0, max18 = 0, max19 = 0, max20 = 0, max21 = 0, max22 = 0, max23 = 0, max24 = 0, max25 = 0, max26 = 0, max27 = 0;
        int max1 = 0;
        for (PceggLotterySgDTO3 dto : list) {

            if (!dto.getNumber0().equals(dto.getRealNum()) && dto.getNumber0() > max0) {
                max0 = dto.getNumber0();
            }
            if (!dto.getNumber1().equals(dto.getRealNum()) && dto.getNumber1() > max1) {
                max1 = dto.getNumber1();
            }
            if (!dto.getNumber2().equals(dto.getRealNum()) && dto.getNumber2() > max2) {
                max2 = dto.getNumber2();
            }
            if (!dto.getNumber3().equals(dto.getRealNum()) && dto.getNumber3() > max3) {
                max3 = dto.getNumber3();
            }
            if (!dto.getNumber4().equals(dto.getRealNum()) && dto.getNumber4() > max4) {
                max4 = dto.getNumber4();
            }
            if (!dto.getNumber5().equals(dto.getRealNum()) && dto.getNumber5() > max5) {
                max5 = dto.getNumber5();
            }
            if (!dto.getNumber6().equals(dto.getRealNum()) && dto.getNumber6() > max6) {
                max6 = dto.getNumber6();
            }
            if (!dto.getNumber7().equals(dto.getRealNum()) && dto.getNumber7() > max7) {
                max7 = dto.getNumber7();
            }
            if (!dto.getNumber8().equals(dto.getRealNum()) && dto.getNumber8() > max8) {
                max8 = dto.getNumber8();
            }
            if (!dto.getNumber9().equals(dto.getRealNum()) && dto.getNumber9() > max9) {
                max9 = dto.getNumber9();
            }
            if (!dto.getNumber10().equals(dto.getRealNum()) && dto.getNumber10() > max10) {
                max10 = dto.getNumber10();
            }
            if (!dto.getNumber11().equals(dto.getRealNum()) && dto.getNumber11() > max11) {
                max11 = dto.getNumber11();
            }
            if (!dto.getNumber12().equals(dto.getRealNum()) && dto.getNumber12() > max12) {
                max12 = dto.getNumber12();
            }
            if (!dto.getNumber13().equals(dto.getRealNum()) && dto.getNumber13() > max13) {
                max13 = dto.getNumber13();
            }
            if (!dto.getNumber14().equals(dto.getRealNum()) && dto.getNumber14() > max14) {
                max14 = dto.getNumber14();
            }
            if (!dto.getNumber15().equals(dto.getRealNum()) && dto.getNumber15() > max15) {
                max15 = dto.getNumber15();
            }
            if (!dto.getNumber16().equals(dto.getRealNum()) && dto.getNumber16() > max16) {
                max16 = dto.getNumber16();
            }
            if (!dto.getNumber17().equals(dto.getRealNum()) && dto.getNumber17() > max17) {
                max17 = dto.getNumber17();
            }
            if (!dto.getNumber18().equals(dto.getRealNum()) && dto.getNumber18() > max18) {
                max18 = dto.getNumber18();
            }
            if (!dto.getNumber19().equals(dto.getRealNum()) && dto.getNumber19() > max19) {
                max19 = dto.getNumber19();
            }
            if (!dto.getNumber20().equals(dto.getRealNum()) && dto.getNumber20() > max20) {
                max20 = dto.getNumber20();
            }
            if (!dto.getNumber21().equals(dto.getRealNum()) && dto.getNumber21() > max21) {
                max21 = dto.getNumber21();
            }
            if (!dto.getNumber22().equals(dto.getRealNum()) && dto.getNumber22() > max22) {
                max22 = dto.getNumber22();
            }
            if (!dto.getNumber23().equals(dto.getRealNum()) && dto.getNumber23() > max23) {
                max23 = dto.getNumber23();
            }
            if (!dto.getNumber24().equals(dto.getRealNum()) && dto.getNumber24() > max24) {
                max24 = dto.getNumber24();
            }
            if (!dto.getNumber25().equals(dto.getRealNum()) && dto.getNumber25() > max25) {
                max25 = dto.getNumber25();
            }
            if (!dto.getNumber26().equals(dto.getRealNum()) && dto.getNumber26() > max26) {
                max26 = dto.getNumber26();
            }
            if (!dto.getNumber27().equals(dto.getRealNum()) && dto.getNumber27() > max27) {
                max27 = dto.getNumber27();
            }
        }
        PceggLotterySgDTO3 maxMissing = new PceggLotterySgDTO3();
        maxMissing.setIssue("最大遗漏值");
        maxMissing.setNumber0(max0);
        maxMissing.setNumber1(max1);
        maxMissing.setNumber2(max2);
        maxMissing.setNumber3(max3);
        maxMissing.setNumber4(max4);
        maxMissing.setNumber5(max5);
        maxMissing.setNumber6(max6);
        maxMissing.setNumber7(max7);
        maxMissing.setNumber8(max8);
        maxMissing.setNumber9(max9);
        maxMissing.setNumber10(max10);
        maxMissing.setNumber11(max11);
        maxMissing.setNumber12(max12);
        maxMissing.setNumber13(max13);
        maxMissing.setNumber14(max14);
        maxMissing.setNumber15(max15);
        maxMissing.setNumber16(max16);
        maxMissing.setNumber17(max17);
        maxMissing.setNumber18(max18);
        maxMissing.setNumber19(max19);
        maxMissing.setNumber20(max20);
        maxMissing.setNumber21(max21);
        maxMissing.setNumber22(max22);
        maxMissing.setNumber23(max23);
        maxMissing.setNumber24(max24);
        maxMissing.setNumber25(max25);
        maxMissing.setNumber26(max26);
        maxMissing.setNumber27(max27);

        /**
         * 统计【平均遗漏值】
         */
        PceggLotterySgDTO3 avgMissing = new PceggLotterySgDTO3(true);
        avgMissing.setIssue("平均遗漏值");
        avgMissing.setNumber0(max0 / (sumOpentCount.getNumber0() + 1));
        avgMissing.setNumber1(max1 / (sumOpentCount.getNumber1() + 1));
        avgMissing.setNumber2(max2 / (sumOpentCount.getNumber2() + 1));
        avgMissing.setNumber3(max3 / (sumOpentCount.getNumber3() + 1));
        avgMissing.setNumber4(max4 / (sumOpentCount.getNumber4() + 1));
        avgMissing.setNumber5(max5 / (sumOpentCount.getNumber5() + 1));
        avgMissing.setNumber6(max6 / (sumOpentCount.getNumber6() + 1));
        avgMissing.setNumber7(max7 / (sumOpentCount.getNumber7() + 1));
        avgMissing.setNumber8(max8 / (sumOpentCount.getNumber8() + 1));
        avgMissing.setNumber9(max9 / (sumOpentCount.getNumber9() + 1));
        avgMissing.setNumber10(max10 / (sumOpentCount.getNumber10() + 1));
        avgMissing.setNumber11(max11 / (sumOpentCount.getNumber11() + 1));
        avgMissing.setNumber12(max12 / (sumOpentCount.getNumber12() + 1));
        avgMissing.setNumber13(max13 / (sumOpentCount.getNumber13() + 1));
        avgMissing.setNumber14(max14 / (sumOpentCount.getNumber14() + 1));
        avgMissing.setNumber15(max15 / (sumOpentCount.getNumber15() + 1));
        avgMissing.setNumber16(max16 / (sumOpentCount.getNumber16() + 1));
        avgMissing.setNumber17(max17 / (sumOpentCount.getNumber17() + 1));
        avgMissing.setNumber18(max18 / (sumOpentCount.getNumber18() + 1));
        avgMissing.setNumber19(max19 / (sumOpentCount.getNumber19() + 1));
        avgMissing.setNumber21(max11 / (sumOpentCount.getNumber21() + 1));
        avgMissing.setNumber22(max12 / (sumOpentCount.getNumber22() + 1));
        avgMissing.setNumber23(max13 / (sumOpentCount.getNumber23() + 1));
        avgMissing.setNumber24(max14 / (sumOpentCount.getNumber24() + 1));
        avgMissing.setNumber25(max15 / (sumOpentCount.getNumber25() + 1));
        avgMissing.setNumber26(max16 / (sumOpentCount.getNumber26() + 1));
        avgMissing.setNumber27(max17 / (sumOpentCount.getNumber27() + 1));


        /**
         * 统计【最大连出值】
         */
        PceggLotterySgDTO3 continuous = new PceggLotterySgDTO3();
        continuous.setIssue("最大连出值");
        continuous.setNumber0(this.getContinuousTemaValue(list, 0));
        continuous.setNumber1(this.getContinuousTemaValue(list, 1));
        continuous.setNumber2(this.getContinuousTemaValue(list, 2));
        continuous.setNumber3(this.getContinuousTemaValue(list, 3));
        continuous.setNumber4(this.getContinuousTemaValue(list, 4));
        continuous.setNumber5(this.getContinuousTemaValue(list, 5));
        continuous.setNumber6(this.getContinuousTemaValue(list, 6));
        continuous.setNumber7(this.getContinuousTemaValue(list, 7));
        continuous.setNumber8(this.getContinuousTemaValue(list, 8));
        continuous.setNumber9(this.getContinuousTemaValue(list, 9));
        continuous.setNumber10(this.getContinuousTemaValue(list, 10));
        continuous.setNumber11(this.getContinuousTemaValue(list, 11));
        continuous.setNumber12(this.getContinuousTemaValue(list, 12));
        continuous.setNumber13(this.getContinuousTemaValue(list, 13));
        continuous.setNumber14(this.getContinuousTemaValue(list, 14));
        continuous.setNumber15(this.getContinuousTemaValue(list, 15));
        continuous.setNumber16(this.getContinuousTemaValue(list, 16));
        continuous.setNumber17(this.getContinuousTemaValue(list, 17));
        continuous.setNumber18(this.getContinuousTemaValue(list, 18));
        continuous.setNumber19(this.getContinuousTemaValue(list, 19));
        continuous.setNumber20(this.getContinuousTemaValue(list, 20));
        continuous.setNumber21(this.getContinuousTemaValue(list, 21));
        continuous.setNumber22(this.getContinuousTemaValue(list, 22));
        continuous.setNumber23(this.getContinuousTemaValue(list, 23));
        continuous.setNumber24(this.getContinuousTemaValue(list, 24));
        continuous.setNumber25(this.getContinuousTemaValue(list, 25));
        continuous.setNumber26(this.getContinuousTemaValue(list, 26));
        continuous.setNumber27(this.getContinuousTemaValue(list, 27));

        statisticsTema.add(sumOpentCount);
        statisticsTema.add(avgMissing);
        statisticsTema.add(maxMissing);
        statisticsTema.add(continuous);

        result.put("TemaRunChart", list);
        result.put("statisticsTema", statisticsTema);
        result.put("listMissing", listMissing);
        return ResultInfo.ok(result);
    }

    /**
     * pc蛋蛋获取遗漏值
     *
     * @param i               第一重循环的当前循环变量
     * @param pceggLotterySgs 开奖结果集
     * @param num             码号（0-27）
     * @return
     */
    private Integer getTemaMissingCount(int i, List<PceggLotterySg> pceggLotterySgs, int num) {
        int number = 0;
        for (int j = i; j < 200 && j < pceggLotterySgs.size(); j++) {
            //开奖号码
            Integer regionNumber = PceggUtil.sumNumber(pceggLotterySgs.get(j).getNumber());
            if (j == i && regionNumber.equals(num)) {
                number = num;
                break;
            } else if (regionNumber.equals(num)) {
                break;
            } else {
                number++;
            }
        }
        return number;
    }

    /**
     * 统计【pc蛋蛋特码最大连出值】
     *
     * @param list   遗漏值集合
     * @param number 号码（0-27）
     * @return
     */
    private Integer getContinuousTemaValue(List<PceggLotterySgDTO3> list, int number) {
        Integer count = 0, first = 0;
        Integer beforeValue = -1;
        for (PceggLotterySgDTO3 dto : list) {
            Integer realNum = dto.getRealNum();
            // 判断是不是第一次出现，如果是第一次出现，count赋值为1
            if (first.equals(0) && realNum.equals(number)) {
                first++;
                count = 1;
            }
            // 如果此次开奖号码为当前号码并且与上一次开奖号码一直，count ++
            if (realNum.equals(number) && realNum.equals(beforeValue)) {
                count++;
            }
            beforeValue = realNum;
        }
        return count;
    }


    /**
     * 获取pc蛋蛋web端：今日单个号码出现的次数
     *
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getPceggLotteryNumber(String date) {
        Map<String, Object> result = new HashMap<>();
        // 根据条件查询
//        PceggLotterySgExample example = new PceggLotterySgExample();
//        PceggLotterySgExample.Criteria criteria = example.createCriteria();
//        criteria.andTimeLike(date + "%");
//        example.setOrderByClause("`time` DESC");
//        //按条日期件查出来的数据
//        List<PceggLotterySg> sgList = pceggLotterySgMapper.selectByExample(example);
        List<PceggLotterySg> sgList = this.querySgList(date, "desc", null, null);

        if (CollectionUtils.isEmpty(sgList)) {
            result.put("list", sgList);
            return ResultInfo.ok(result);
        }

        List<PceggMissDTO> list = new ArrayList<>();

        /**
         * 统计【第几区的号码出现总次数】
         */
        PceggMissDTO sumOpenCount;
        for (int i = 1; i < 4; i++) {
            sumOpenCount = new PceggMissDTO();
            sumOpenCount.setIssue("第" + i + "区开奖号码总次数");
            sumOpenCount.setNumber0(this.getSumOpenCountTotal(sgList, 0, i));
            sumOpenCount.setNumber1(this.getSumOpenCountTotal(sgList, 1, i));
            sumOpenCount.setNumber2(this.getSumOpenCountTotal(sgList, 2, i));
            sumOpenCount.setNumber3(this.getSumOpenCountTotal(sgList, 3, i));
            sumOpenCount.setNumber4(this.getSumOpenCountTotal(sgList, 4, i));
            sumOpenCount.setNumber5(this.getSumOpenCountTotal(sgList, 5, i));
            sumOpenCount.setNumber6(this.getSumOpenCountTotal(sgList, 6, i));
            sumOpenCount.setNumber7(this.getSumOpenCountTotal(sgList, 7, i));
            sumOpenCount.setNumber8(this.getSumOpenCountTotal(sgList, 8, i));
            sumOpenCount.setNumber9(this.getSumOpenCountTotal(sgList, 9, i));

            list.add(sumOpenCount);
        }

        result.put("pceggLotteryNumber", list);

        return ResultInfo.ok(result);
    }

    /**
     * 获取pc蛋蛋（今日）开奖单个数字出现的总次数
     *
     * @param pceggLotterySgs 开奖结果集
     * @param number          码号（0-9）
     * @param region          第几区
     * @return
     */
    private Integer getSumOpenCountTotal(List<PceggLotterySg> pceggLotterySgs, int number, Integer region) {
        int count = 0;
        for (int i = 0; i < pceggLotterySgs.size(); i++) {
            PceggLotterySg sg = pceggLotterySgs.get(i);
            Integer realNum = PceggUtil.getRegionNumber(sg.getNumber(), region);
            if (realNum.equals(number)) {
                count++;
            }
        }
        return count;
    }


    /************************************************************************
     *                          修改后新增接口                               **
     ************************************************************************/


    /**
     * 获取当前最后一期已开结果（可能还在等待开奖）
     *
     * @param isOpen 是否全部开奖的记录（忽略未开奖的记录）
     * @return
     */
    @Override
    public PceggLotterySg queryLastSg(Boolean isOpen) {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        // TODO 忽略未开奖记录
        if (isOpen) {
            criteria.andNumberNotEqualTo("");
            criteria.andNumberIsNotNull();
        }
        example.setOrderByClause("`ideal_time` DESC");
        return pceggLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期期号及时间
     *
     * @return
     */
    @Override
    public PceggLotterySg queryNextSg() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        example.setOrderByClause("`ideal_time` ASC");
        return pceggLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 查询已开期数
     *
     * @return
     */
    @Override
    public Integer queryOpenedCount() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeLike(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD) + "%");
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        return pceggLotterySgMapper.countByExample(example);
    }

    /**
     * 查询下一期开奖时间（时间戳：秒）
     *
     * @return
     */
    @Override
    public Long queryCountDown() {
        PceggLotterySg sg = this.queryNextSg();
        if (sg != null) {
            Date dateTime = DateUtils.dateStringToDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS);
            return dateTime.getTime() / 1000;
        } else {
            return 0l;
        }

    }

    /**
     * 查询指定日期已开历史记录（默认：当天）
     *
     * @param date     日期，例如：2018-11-13
     * @param sort     排序方式：ASC 顺序 | DESC 倒序
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public List<PceggLotterySg> querySgList(String date, String sort, Integer pageNo, Integer pageSize) {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        Date nowTime = new Date();
        String dateStr = DateUtils.formatDate(nowTime, DateUtils.FORMAT_YYYY_MM_DD);

        if (StringUtils.isNotEmpty(date) && !dateStr.equals(date)) {
            criteria.andIdealTimeLike(date + "%");
        }

        // TODO 忽略未开奖记录
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        if (pageNo != null && pageSize != null) {
            example.setOffset((pageNo - 1) * pageSize);
            example.setLimit(pageSize);
        } else if (pageSize != null) {
            example.setLimit(pageSize);
        }
        example.setOrderByClause("`ideal_time` DESC");

        List<PceggLotterySg> pceggLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.PCEGG_SG_HS_LIST)) {
            PceggLotterySgExample exampleOne = new PceggLotterySgExample();
            PceggLotterySgExample.Criteria cqsscCriteriaOne = exampleOne.createCriteria();
            cqsscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<PceggLotterySg> pceggLotterySgsOne = pceggLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.PCEGG_SG_HS_LIST, pceggLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            pceggLotterySgs = redisTemplate.opsForList().range(RedisKeys.PCEGG_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            pceggLotterySgs = pceggLotterySgMapper.selectByExample(example);
        }

        return pceggLotterySgs;
    }

    private int querySgListCount(String date, String sort) {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        Date nowTime = new Date();
        String dateStr = DateUtils.formatDate(nowTime, DateUtils.FORMAT_YYYY_MM_DD);
        if (StringUtils.isEmpty(date) || dateStr.equals(date)) {
            //  criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(nowTime, DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
            //  criteria.andIdealTimeLike(dateStr + "%");
        } else {
            criteria.andIdealTimeLike(date + "%");
        }
        // TODO 忽略未开奖记录
        criteria.andNumberIsNotNull();
        criteria.andNumberNotEqualTo("");
        example.setOrderByClause("`issue` " + (StringUtils.isEmpty(sort) ? "DESC" : sort));
        int count = pceggLotterySgMapper.countByExample(example);
        return count;
    }

    @Override
    public PceggLotterySg querySgByIssue(String issue) {
        if (StringUtils.isEmpty(issue)) {
            return null;
        }
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueEqualTo(issue);
        return pceggLotterySgMapper.selectOneByExample(example);
    }


    /**
     * 获取pc蛋蛋web端开奖资讯详情
     *
     * @param date 日期
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> getTodayAndHistoryNews(String date) {

        Map<String, Object> result = new HashMap<>();
        List<PceggLotteryTodayAndHistoryNews> pcList = new ArrayList<>();
//        Date nowTime=new Date();
//        if(date==null){
//           String countTime = DateUtils.formatDate(nowTime, DateUtils.FORMAT_YYYY_MM_DD);
//            result.put("countTime",countTime);
//        }else {
//            result.put("countTime",date);
//        }
//        List<PceggLotterySg> pceggLotterySgss = this.querySgList(date, "DESC", null, null);

        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeLike(date + "%");
//        example.setOrderByClause("`issue` DESC");

        List<PceggLotterySg> pceggLotterySgs = pceggLotterySgMapper.selectByExample(example);

        PceggLotteryTodayAndHistoryNews plth;
        for (PceggLotterySg list : pceggLotterySgs) {
            plth = new PceggLotteryTodayAndHistoryNews();
            String number = list.getNumber();
            if (StringUtils.isBlank(number)) {
                plth.setIssue(list.getIssue());
                plth.setBigOrSmall("-");
                plth.setSingleOrDouble("-");
                plth.setNumber("-");
                plth.setRealNum("-");

                pcList.add(plth);
            } else {
                Integer realNum = PceggUtil.sumNumber(number);
                plth.setIssue(list.getIssue());
                plth.setRealNum(realNum.toString());
                plth.setNumber(number);
                if (realNum >= 14 && realNum % 2 == 0) {
                    plth.setBigOrSmall("大");
                    plth.setSingleOrDouble("双");
                } else if (realNum >= 14 && realNum % 2 != 0) {
                    plth.setBigOrSmall("大");
                    plth.setSingleOrDouble("单");
                } else if (realNum < 14 && realNum % 2 == 0) {
                    plth.setBigOrSmall("小");
                    plth.setSingleOrDouble("双");
                } else if (realNum < 14 && realNum % 2 != 0) {
                    plth.setBigOrSmall("小");
                    plth.setSingleOrDouble("单");
                }

                pcList.add(plth);

            }
        }

        //已开奖总期数
//        result.put("countNumber", pceggLotterySgss.size());

        //统计
        ResultInfo<Map<String, Object>> pcEggWebStatistics = pceggLotterySgService.getPcEggWebStatistics(date);
        Map<String, Object> statistics = pcEggWebStatistics.getData();

        result.put("list", pcList);
        result.put("statistics", statistics);
        return ResultInfo.ok(result);
    }

    /* (non Javadoc)
     * @Title: getPceggSgLong
     * @Description: 获取PC蛋蛋长龙
     * @return
     * @see com.caipiao.live.read.service.result.PceggLotterySgService#getPceggSgLong()
     */
    @Override
    public List<Map<String, Object>> getPceggSgLong() {
        List<Map<String, Object>> pceggLongMapList = new ArrayList<Map<String, Object>>();
        // 收集PC蛋蛋混合大小
        Map<String, Object> bigAndSmallDragonMap = new HashMap<String, Object>();
        // 收集PC蛋蛋混合单双
        Map<String, Object> sigleAndDoubleDragonMap = new HashMap<String, Object>();

        try {
            String algorithm = RedisKeys.PCDAND_ALGORITHM_VALUE;
            List<PceggLotterySg> pceggLotterySgList = (List<PceggLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(pceggLotterySgList)) {
                pceggLotterySgList = this.getAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, pceggLotterySgList);
            }

            if (!CollectionUtils.isEmpty(pceggLotterySgList)) {
                // 大/小标记变量
                Integer bigAndSmallDragonSize = Constants.DEFAULT_INTEGER;
                Set<String> bigAndSmallDragonSet = new HashSet<String>();
                boolean bigAllowFlag = true;
                // 单/双标记变量
                Integer sigleAndDoubleDragonSize = Constants.DEFAULT_INTEGER;
                Set<String> sigleAndDoubleDragonSet = new HashSet<String>();
                boolean sigleAllowFlag = true;

                for (int index = Constants.DEFAULT_INTEGER; index < pceggLotterySgList.size(); index++) {
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                    PceggLotterySg pceggLotterySg = pceggLotterySgList.get(index);
                    // 大/小
                    String bigOrSmallName = AusactSgUtils.getPceegBigOrSmall(pceggLotterySg.getNumber());

                    if (StringUtils.isEmpty(bigOrSmallName)) {
                        bigAllowFlag = false;
                    }
                    // 把第一个结果加入SET集合
                    if (index == Constants.DEFAULT_INTEGER) {
                        if (bigAllowFlag) {
                            bigAndSmallDragonSet.add(bigOrSmallName);
                        }
                    }
                    // 单/双
                    String singleAndDoubleName = AusactSgUtils.getSingleAndDouble(pceggLotterySg.getNumber());

                    if (StringUtils.isEmpty(singleAndDoubleName)) {
                        sigleAllowFlag = false;
                    }
                    // 把第一个结果加入SET集合
                    if (index == Constants.DEFAULT_INTEGER) {
                        if (sigleAllowFlag) {
                            sigleAndDoubleDragonSet.add(singleAndDoubleName);
                        }
                    }
                    // 如果第一个和第二个开奖结果不一样，统计截止
                    if (index == Constants.DEFAULT_ONE) {
                        if (!bigAndSmallDragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了，不再统计
                            bigAllowFlag = false;
                        }

                        if (!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
                            // 单/双已经没有龙了，不再统计
                            sigleAllowFlag = false;
                        }
                    }
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                    // 规则：连续3个开奖一样
                    if (index == Constants.DEFAULT_TWO) {
                        // 第三个数据
                        if (!bigAndSmallDragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了，不再统计
                            bigAllowFlag = false;
                        }

                        if (!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
                            // 单/双已经没有龙了，不再统计
                            sigleAllowFlag = false;
                        }

                        if (bigAllowFlag) {
                            bigAndSmallDragonSize = Constants.DEFAULT_THREE;
                        }

                        if (sigleAllowFlag) {
                            sigleAndDoubleDragonSize = Constants.DEFAULT_THREE;
                        }
                    }
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                    // 如果大于3个以上，继续统计，直到结果不一样
                    if (index > Constants.DEFAULT_TWO) {
                        if (!bigAndSmallDragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了，不再统计
                            bigAllowFlag = false;
                        }

                        if (bigAllowFlag) {
                            bigAndSmallDragonSize++;
                        }
                        // 单/双统计
                        if (!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
                            // 单/双已经没有龙了，不再统计
                            sigleAllowFlag = false;
                        }
                        if (sigleAllowFlag) {
                            sigleAndDoubleDragonSize++;
                        }
                    }
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                }
                // 组织返回数据
                if (bigAndSmallDragonSize >= Constants.DEFAULT_THREE) {
                    bigAndSmallDragonMap = this.organizationDragonResultMap(CaipiaoPlayTypeEnum.PCEGGBIG.getTagCnName(), CaipiaoPlayTypeEnum.PCEGGBIG.getPlayTag(), CaipiaoPlayTypeEnum.PCEGGBIG.getTagType(), bigAndSmallDragonSize, bigAndSmallDragonSet);
                }

                if (sigleAndDoubleDragonSize >= Constants.DEFAULT_THREE) {
                    sigleAndDoubleDragonMap = this.organizationDragonResultMap(CaipiaoPlayTypeEnum.PCEGGDOUBLE.getTagCnName(), CaipiaoPlayTypeEnum.PCEGGDOUBLE.getPlayTag(), CaipiaoPlayTypeEnum.PCEGGDOUBLE.getTagType(), sigleAndDoubleDragonSize, sigleAndDoubleDragonSet);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#getPceggSgLong_error:", e);
        }
        // 计算后的数据，放入返回集合
        if (bigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            pceggLongMapList.add(bigAndSmallDragonMap);
        }
        if (sigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            pceggLongMapList.add(sigleAndDoubleDragonMap);
        }
        // 返回
        return pceggLongMapList;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: 组织长龙结果返回 Map格式
     * @author HANS
     * @date 2019年5月10日下午3:25:13
     */
    public Map<String, Object> organizationDragonResultMap(String playTyep, String playTag, int tagType, Integer dragonSize, Set<String> dragonSet) {
        // 有龙情况下，查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        List<String> dragonList = new ArrayList<String>(dragonSet);
        longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), tagType);
        longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.PCDAND.getTagCnName());
        longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.PCDAND.getTagType());
        longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), playTyep);
        longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), playTag);
        longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
        longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);

        // 获取赔率数据
        PlayAndOddListInfoVO playAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.PCDAND.getTagCnName(), CaipiaoPlayTypeEnum.PCEGGBIG.getPlayName(),
                CaipiaoTypeEnum.PCDAND.getTagType(), CaipiaoTypeEnum.PCDAND.getTagType());

        if (playAndOddListInfo != null) {
            LotteryPlaySetting lotteryPlaySetting = playAndOddListInfo.getLotteryPlaySetting();

            if (lotteryPlaySetting != null) {
                longDragonMap.put(AppMianParamEnum.COTEGORY.getParamEnName(), lotteryPlaySetting.getCateId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getCateId());
                longDragonMap.put(AppMianParamEnum.PLAYTAGID.getParamEnName(), lotteryPlaySetting.getPlayTagId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getPlayTagId());
            }
            List<LotteryPlayOdds> oddsList = playAndOddListInfo.getOddsList();

            if (!CollectionUtils.isEmpty(oddsList)) {
                List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
                for (LotteryPlayOdds lotteryPlayOdds : oddsList) {
                    // 大小
                    if (CaipiaoPlayTypeEnum.PCEGGBIG.getTagCnName().equalsIgnoreCase(playTyep)) {
                        if (Constants.BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                    // 单双
                    if (CaipiaoPlayTypeEnum.PCEGGDOUBLE.getTagCnName().equalsIgnoreCase(playTyep)) {
                        if (Constants.BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(playMapList)) {
                    longDragonMap.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
                }
            }
        }
        // 缓存中取下一期信息
        String nextRedisKey = RedisKeys.PCDAND_NEXT_VALUE;
        Long redisTime = CaipiaoRedisTimeEnum.PCDAND.getRedisTime();
        PceggLotterySg nextPceggLotterySg = (PceggLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

        if (nextPceggLotterySg == null) {
            nextPceggLotterySg = this.queryNextSg();
            redisTemplate.opsForValue().set(nextRedisKey, nextPceggLotterySg, redisTime, TimeUnit.MINUTES);
        }

        if (nextPceggLotterySg == null) {
            return new HashMap<String, Object>();
        }
        String redisKey = RedisKeys.PCDAND_RESULT_VALUE;
        PceggLotterySg pceggLotterySg = (PceggLotterySg) redisTemplate.opsForValue().get(redisKey);

        if (pceggLotterySg == null) {
            pceggLotterySg = this.getPceggLotterySg();
            redisTemplate.opsForValue().set(redisKey, pceggLotterySg);
        }
        String nextIssue = nextPceggLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextPceggLotterySg.getIssue();
        String txffnextIssue = pceggLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : pceggLotterySg.getIssue();

        if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
            Long nextIssueNum = Long.parseLong(nextIssue);
            Long txffnextIssueNum = Long.parseLong(txffnextIssue);
            Long differenceNum = nextIssueNum - txffnextIssueNum;

            // 如果长龙期数与下期期数相差太大，长龙不存在
            if (differenceNum < 1 || differenceNum > 2) {
                return new HashMap<String, Object>();
            }
        }
        longDragonMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextPceggLotterySg.getIssue());
        longDragonMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextPceggLotterySg.getIdealTime()) / 1000L);
        return longDragonMap;
    }

    /**
     * @return List<PceggLotterySg>
     * @Title: getAlgorithmData
     * @Description: 获取PC蛋蛋最近200期开奖数据
     * @author HANS
     * @date 2019年5月12日下午7:58:30
     */
    public List<PceggLotterySg> getAlgorithmData() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        return pceggLotterySgMapper.selectByExample(example);
    }

    /* (non Javadoc)
     * @Title: lishitenpksSg
     * @Description: 获取PC蛋蛋的历史开奖数据
     * @return
     * @see com.caipiao.live.read.service.result.PceggLotterySgService#lishitenpksSg(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public ResultInfo<Map<String, Object>> lishitenpksSg(Integer pageNo, Integer pageSize) {
        ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", this.pceggSgHistoryList(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD), pageNo, pageSize).getData());
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        resultInfo = ResultInfo.ok(result);
        return resultInfo;
    }

}
