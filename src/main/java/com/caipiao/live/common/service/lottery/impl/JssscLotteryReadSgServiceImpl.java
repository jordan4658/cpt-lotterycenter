package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.JssscLotterySg;
import com.caipiao.live.common.mybatis.entity.JssscLotterySgExample;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;
import com.caipiao.live.common.mybatis.mapper.JssscLotterySgMapper;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JssscLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author lucien
 * @create 2020/7/28 22:53
 */
@Service
public class JssscLotteryReadSgServiceImpl implements JssscLotterySgServiceReadSg {

    private final Logger logger = LoggerFactory.getLogger(JssscLotteryReadSgServiceImpl.class);
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private JssscLotterySgMapper jssscLotterySgMapper;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;

    @Override
    public Map<String, Object> getJssscNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullResult();
        try {
            // ???????????????????????????
            String nextRedisKey = RedisKeys.JSSSC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.JSSSC.getRedisTime();
            JssscLotterySg nextTjsscLotterySg = (JssscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextTjsscLotterySg == null) {
                nextTjsscLotterySg = this.getNextJssscLotterySg();
                // ?????????????????????
                redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // ????????????????????????
            String redisKey = RedisKeys.JSSSC_RESULT_VALUE;
            JssscLotterySg jssscLotterySg = (JssscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (jssscLotterySg == null) {
                jssscLotterySg = this.getJssscLotterySg();
                redisTemplate.opsForValue().set(redisKey, jssscLotterySg);
            }

            if (nextTjsscLotterySg != null) {
                String nextIssue = nextTjsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTjsscLotterySg.getIssue();
                String txffnextIssue = jssscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : jssscLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextTjsscLotterySg = this.getNextJssscLotterySg();
                        // ?????????????????????
                        redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
                        // ?????????????????????
                        jssscLotterySg = this.getJssscLotterySg();
                        redisTemplate.opsForValue().set(redisKey, jssscLotterySg);
                    }
                }

                if (jssscLotterySg != null) {
                    // ??????????????????
                    this.getIssueSumAndAllNumber(jssscLotterySg, result);
                    // ??????????????????
                    this.openCount(jssscLotterySg, result);
                }

                if (nextTjsscLotterySg != null) {
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
                            DateUtils.getTimeMillis(nextTjsscLotterySg.getIdealTime()) / 1000L);
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTjsscLotterySg.getIssue());
                }
            } else {
                result = DefaultResultUtil.getNullResult();

                if (jssscLotterySg != null) {
                    // ??????????????????
                    this.getIssueSumAndAllNumber(jssscLotterySg, result);
                    // ??????????????????
                    this.openCount(jssscLotterySg, result);
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.JSSSC.getTagType() + " ????????? ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    /**
     * @Title: getIssueSumAndAllNumber
     * @Description: ???????????????????????????
     */
    public void getIssueSumAndAllNumber(JssscLotterySg jssscLotterySg, Map<String, Object> result) {
        Integer wan = jssscLotterySg.getWan();
        Integer qian = jssscLotterySg.getQian();
        Integer bai = jssscLotterySg.getBai();
        Integer shi = jssscLotterySg.getShi();
        Integer ge = jssscLotterySg.getGe();
        String issue = jssscLotterySg.getIssue();
        result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
        // ??????????????????
        String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
        // ????????????????????????
        Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
    }

    /**
     * @Title: getJssscLotterySg
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???15?????????2:02:00
     */
    public JssscLotterySg getJssscLotterySg() {
        JssscLotterySgExample example = new JssscLotterySgExample();
        JssscLotterySgExample.Criteria jssscCriteria = example.createCriteria();
        jssscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        JssscLotterySg jssscLotterySg = this.jssscLotterySgMapper.selectOneByExample(example);
        return jssscLotterySg;
    }

    /**
     * @Title: openCount
     * @Description: ????????????????????????????????????
     */
    public void openCount(JssscLotterySg jssscLotterySg, Map<String, Object> result) {
        // ??????????????????
        String issue = jssscLotterySg.getIssue();
        String openNumString = issue.substring(8, issue.length());
        Integer openNumInteger = Integer.valueOf(openNumString);
        result.put("openCount", openNumInteger);
        // ????????????????????????
        Integer sumCount = CaipiaoSumCountEnum.JSSSC.getSumCount();
        result.put("noOpenCount", sumCount - openNumInteger);
    }

    /* (non Javadoc)
     * @Title: getActSgLong
     * @Description: TODO
     * @return
     * @see com.caipiao.live.read.issue.service.result.JssscLotterySgService#getActSgLong()
     */
    @Override
    public List<Map<String, Object>> getJssscSgLong() {
        List<Map<String, Object>> jssscLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.JSSSC_ALGORITHM_VALUE;
            List<JssscLotterySg> jssscLotterySgList = (List<JssscLotterySg>)redisTemplate.opsForValue().get(algorithm);

            if(CollectionUtils.isEmpty(jssscLotterySgList)) {
                jssscLotterySgList = this.getAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, jssscLotterySgList, 10, TimeUnit.SECONDS);
            }
            // ??????????????????
            List<Map<String, Object>> jssscBigLongMapList = this.getJssscBigAndSmallLong(jssscLotterySgList);
            jssscLongMapList.addAll(jssscBigLongMapList);
            // ??????????????????
            List<Map<String, Object>> jssscSigleLongMapList = this.getJssscSigleAndDoubleLong(jssscLotterySgList);
            jssscLongMapList.addAll(jssscSigleLongMapList);
            // ??????????????????
            jssscLongMapList = this.addNextIssueInfo(jssscLongMapList, jssscLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#JssscLotterySgServiceImpl_getActSgLong_error:", e);
        }
        // ??????
        return jssscLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: ??????
     * @author HANS
     * @date 2019???5???26?????????2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> jssscLongMapList, List<JssscLotterySg> jssscLotterySgList) {
        List<Map<String, Object>> jssscResultLongMapList = new ArrayList<Map<String, Object>>();
        if(!CollectionUtils.isEmpty(jssscLongMapList)) {
            // ???????????????????????????
            String nextRedisKey = RedisKeys.JSSSC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.JSSSC.getRedisTime();
            JssscLotterySg nextTjsscLotterySg = (JssscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if(nextTjsscLotterySg == null) {
                nextTjsscLotterySg = this.getNextJssscLotterySg();
                // ?????????????????????
                redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if(nextTjsscLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // ????????????????????????
            String redisKey = RedisKeys.JSSSC_RESULT_VALUE;
            JssscLotterySg jssscLotterySg = (JssscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (jssscLotterySg == null) {
                jssscLotterySg = this.getJssscLotterySg();
                redisTemplate.opsForValue().set(redisKey, jssscLotterySg);
            }

            String nextIssue = nextTjsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTjsscLotterySg.getIssue();
            String txffnextIssue = jssscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : jssscLotterySg.getIssue();

            if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // ????????????????????????????????????????????????????????????
                if(differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }
            // ????????????????????????????????????
            String issueString = nextTjsscLotterySg.getIssue();
            Long nextTimeLong = DateUtils.getTimeMillis(nextTjsscLotterySg.getIdealTime()) / 1000L;

            for (Map<String, Object> longMap : jssscLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), issueString);
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(),  nextTimeLong);
                jssscResultLongMapList.add(longMap);
            }
        }
        return jssscResultLongMapList;
    }

    /**
     * @Title: getJssscBigAndSmallLong
     * @Description:  ???????????????????????????
     * @param jssscLotterySgList
     * @return List<Map<String,Object>>
     * @author HANS
     * @date 2019???5???13?????????11:07:39
     */
    private List<Map<String, Object>> getJssscBigAndSmallLong(List<JssscLotterySg> jssscLotterySgList){
        List<Map<String, Object>> jssscBigLongMapList = new ArrayList<Map<String, Object>>();
        // ???????????????????????????????????????
        Map<String, Object> twoWallBigAndSmallDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> firstBigAndSmallDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDYQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> secondBigAndSmallDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDEQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> thirdBigAndSmallDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDSQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fourthBigAndSmallDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDFQBIG.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fivethBigAndSmallDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDWQBIG.getTagType());

        // ????????????????????????????????????
        if(twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscBigLongMapList.add(twoWallBigAndSmallDragonMap);
        }

        if(firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscBigLongMapList.add(firstBigAndSmallDragonMap);
        }

        if(secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscBigLongMapList.add(secondBigAndSmallDragonMap);
        }

        if(thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscBigLongMapList.add(thirdBigAndSmallDragonMap);
        }

        if(fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscBigLongMapList.add(fourthBigAndSmallDragonMap);
        }

        if(fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscBigLongMapList.add(fivethBigAndSmallDragonMap);
        }
        return jssscBigLongMapList;
    }

    /**
     * @Title: getJssscSigleAndDoubleLong
     * @Description: ???????????????????????????
     * @param jssscLotterySgList
     * @return List<Map<String,Object>>
     * @author HANS
     * @date 2019???5???13?????????11:09:35
     */
    private List<Map<String, Object>> getJssscSigleAndDoubleLong(List<JssscLotterySg> jssscLotterySgList){
        List<Map<String, Object>> jssscSigleLongMapList = new ArrayList<Map<String, Object>>();
        // ???????????????????????????????????????
        Map<String, Object> twoWallSigleAndDoubleDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCLMZHDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> firstSigleAndDoubleDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDYQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> secondSigleAndDoubleDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDEQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> thirdSigleAndDoubleDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDSQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fourthSigleAndDoubleDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDFQDOUBLE.getTagType());
        // ????????????????????????????????????
        Map<String, Object> fivethSigleAndDoubleDragonMap = this.getDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDWQDOUBLE.getTagType());
        // ??????
        if(twoWallSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscSigleLongMapList.add(twoWallSigleAndDoubleDragonMap);
        }

        if(firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscSigleLongMapList.add(firstSigleAndDoubleDragonMap);
        }

        if(secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscSigleLongMapList.add(secondSigleAndDoubleDragonMap);
        }

        if(thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscSigleLongMapList.add(thirdSigleAndDoubleDragonMap);
        }

        if(fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscSigleLongMapList.add(fourthSigleAndDoubleDragonMap);
        }

        if(fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jssscSigleLongMapList.add(fivethSigleAndDoubleDragonMap);
        }
        return jssscSigleLongMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: ?????????????????????????????????
     * @return Map<String,Object>
     * @author HANS
     * @date 2019???5???13?????????12:00:46
     */
    private Map<String, Object> getDragonInfo(List<JssscLotterySg> jssscLotterySgList, int type){
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(jssscLotterySgList)) {
                // ????????????
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < jssscLotterySgList.size() ; index++) {
                    JssscLotterySg jssscLotterySg = jssscLotterySgList.get(index);
                    // ????????????????????????
                    String bigOrSmallName = this.calculateResult(type, jssscLotterySg);

                    if(StringUtils.isEmpty(bigOrSmallName)) {
                        break;
                    }
                    // ????????????????????????SET??????
                    if(index == Constants.DEFAULT_INTEGER) {
                        dragonSet.add(bigOrSmallName);
                    }
                    // ???????????????????????????????????????????????????????????????
                    if(index == Constants.DEFAULT_ONE) {
                        if(!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        continue;
                    }
                    // ???????????????3???????????????
                    if(index == Constants.DEFAULT_TWO) {
                        // ???????????????
                        if(!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        dragonSize = Constants.DEFAULT_THREE;
                        continue;
                    }
                    // ????????????3????????????????????????????????????????????????
                    if(index > Constants.DEFAULT_TWO) {
                        // ???/?????????
                        if(!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        dragonSize++;
                    }
                }
                // ??????????????????
                if(dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type,dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#JssscLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: organizationDragonResultMap
     * @author HANS
     * @date 2019???5???13?????????1:53:19
     */
    private Map<String, Object> organizationDragonResultMap(int type,Set<String> dragonSet, Integer dragonSize){
        // ?????????????????????????????????
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // ????????????????????? ?????? ????????????
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.JSSSC.getTagCnName(), CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getPlayName(),
                    CaipiaoTypeEnum.JSSSC.getTagType(), CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getTagType()+"");
            List<String> dragonList = new ArrayList<String>(dragonSet);
            // ????????????
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if(type == 5) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALBIG);
            } else if(type == 6) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDYQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDYQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDYQBIG.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if(type == 7) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDEQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDEQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDEQBIG.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if(type == 8) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDSQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDSQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDSQBIG.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if(type == 9) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDFQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDFQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDFQBIG.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if(type == 10) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDWQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDWQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDWQBIG.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if(type == 11) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCLMZHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCLMZHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCLMZHDOUBLE.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALDOUBLE);
            } else if(type == 12) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDYQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDYQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDYQDOUBLE.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if(type == 13) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDEQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDEQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDEQDOUBLE.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if(type == 14) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDSQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDSQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDSQDOUBLE.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if(type == 15) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDFQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDFQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDFQDOUBLE.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if(type == 16) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDWQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDWQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSSSCDWQDOUBLE.getPlayTag());
                oddsListMap = this.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            }
            // ?????????????????????????????????MAP???
            longDragonMap.putAll(oddsListMap);
            // ?????????????????????
            String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
            //String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.JSSSC.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.JSSSC.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#JssscLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: getOddInfo
     * @Description: ????????????
     * @author HANS
     * @date 2019???5???13?????????4:57:09
     */
    @Override
    public Map<String, Object> getOddInfo(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep) {
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        if(twoWallplayAndOddListInfo != null) {
            LotteryPlaySetting lotteryPlaySetting = twoWallplayAndOddListInfo.getLotteryPlaySetting();

            if(lotteryPlaySetting != null) {
                longDragonMap.put(AppMianParamEnum.COTEGORY.getParamEnName(), lotteryPlaySetting.getCateId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getCateId());
                longDragonMap.put(AppMianParamEnum.PLAYTAGID.getParamEnName(), lotteryPlaySetting.getPlayTagId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getPlayTagId());
            }
            List<LotteryPlayOdds> oddsList = twoWallplayAndOddListInfo.getOddsList();

            if (!CollectionUtils.isEmpty(oddsList)) {
                List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
                for (LotteryPlayOdds lotteryPlayOdds : oddsList) {
                    // ????????????
                    // ????????????
                    if (Constants.SSC_PLAYWAY_NAME_TOTALBIG.equalsIgnoreCase(playTyep)) {
                        if (Constants.TOTAL_BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.TOTAL_BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                    // 1-5???  ??????
                    if (Constants.SSC_PLAYWAY_NAME_FIVEBIG.equalsIgnoreCase(playTyep)) {
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
                    // ????????????
                    if (Constants.SSC_PLAYWAY_NAME_TOTALDOUBLE.equalsIgnoreCase(playTyep)) {
                        if (Constants.TOTAL_BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.TOTAL_BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                    // 1-5???  ??????
                    if (Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE.equalsIgnoreCase(playTyep)) {
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

                if(!CollectionUtils.isEmpty(playMapList)) {
                    longDragonMap.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
                }
            }
        }
        return longDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: ????????????????????????
     * @return String
     * @author HANS
     * @date 2019???5???13?????????10:33:30
     */
    private String calculateResult(int type, JssscLotterySg jssscLotterySg) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 5:
                result = AusactSgUtils.getJssscBigOrSmall(jssscLotterySg.getNumber());//??????????????????
                break;
            case 6:
                result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getWan());//???????????????
                break;
            case 7:
                result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getQian());//???????????????
                break;
            case 8:
                result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getBai());//???????????????
                break;
            case 9:
                result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getShi());//???????????????
                break;
            case 10:
                result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getGe());//???????????????
                break;
            case 11:
                result = AusactSgUtils.getSingleAndDouble(jssscLotterySg.getNumber());//??????????????????
                break;
            case 12:
                result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getWan());//???????????????
                break;
            case 13:
                result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getQian());//???????????????
                break;
            case 14:
                result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getBai());//???????????????
                break;
            case 15:
                result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getShi());//???????????????
                break;
            case 16:
                result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getGe());//???????????????
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: getNextJssscLotterySg
     * @Description: ?????????????????????
     * @return JssscLotterySg
     * @author HANS
     * @date 2019???5???13?????????1:59:40
     */
    private JssscLotterySg getNextJssscLotterySg() {
        Date nowDate = new Date();
        JssscLotterySgExample next_example = new JssscLotterySgExample();
        JssscLotterySgExample.Criteria next_TjsscCriteria = next_example.createCriteria();
        next_TjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        next_TjsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(nowDate));
        next_example.setOrderByClause("ideal_time ASC");
        JssscLotterySg next_TjsscLotterySg = this.jssscLotterySgMapper.selectOneByExample(next_example);
        return next_TjsscLotterySg;
    }

    /**
     * @Title: getAlgorithmData
     * @Description: ??????????????????
     * @return List<JssscLotterySg>
     * @author HANS
     * @date 2019???5???13?????????12:00:24
     */
    private List<JssscLotterySg> getAlgorithmData() {
        JssscLotterySgExample example = new JssscLotterySgExample();
        JssscLotterySgExample.Criteria jssscCriteria = example.createCriteria();
        jssscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<JssscLotterySg> jssscLotterySgList = this.jssscLotterySgMapper.selectByExample(example);
        return jssscLotterySgList;
    }
}
