package com.caipiao.task.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mapper.AmlhcLotterySgMapper;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.CaipiaoPlayTypeEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.enums.TaskTypeEnum;
import com.caipiao.core.library.utils.RedisKeys;
import com.caipiao.task.server.service.TaskSgService;
import com.caipiao.task.server.service.TenMinuteDragonService;
import com.caipiao.task.server.util.AusactSgUtils;
import com.caipiao.task.server.util.JspksSgUtils;
import com.caipiao.task.server.util.OnelhcSgUtils;
import com.mapper.TenbjpksLotterySgMapper;
import com.mapper.TensscLotterySgMapper;

@Service
public class TenMinuteDragonServiceImpl implements TenMinuteDragonService {

    private static final Logger logger = LoggerFactory.getLogger(TenMinuteDragonServiceImpl.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TaskSgService taskSgService;
    @Autowired
    private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
    @Autowired
    private TensscLotterySgMapper tensscLotterySgMapper;
    @Autowired
    private AmlhcLotterySgMapper sslhcLotterySgMapper;

    /**
     * SYSTEM十分钟开奖彩种长龙统计
     */
    @Override
    public void totalDragonOneMinute() {
        // 10分时时彩  1104    10F
        this.getTensscSgLong();
//        // 澳门六合彩  1204    10F
//        this.getSslhcSgLong();
        // 10分PK10    1302    10F
        this.getTenPksSgLong();
    }

    /**
     * 10分时时彩 长龙数据统计
     */
    public void getTensscSgLong() {
        try {
            String algorithm = RedisKeys.TENSSC_ALGORITHM_VALUE;
            List<TensscLotterySg> tensscLotterySqList = (List<TensscLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(tensscLotterySqList)) {
                tensscLotterySqList = this.getTensscAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, tensscLotterySqList);
            }
            // 获取大小长龙
            this.getTensscBigAndSmallLong(tensscLotterySqList);
            // 获取单双长龙
            this.getTensscSigleAndDoubleLong(tensscLotterySqList);
        } catch (Exception e) {
            logger.error("10分时时彩长龙数据统计异常", e);
        }
    }

    /**
     * 10分PK10 长龙数据统计
     */
    public void getTenPksSgLong() {
        try {
            String algorithm = RedisKeys.TENPKS_ALGORITHM_VALUE;
            List<TenbjpksLotterySg> tenpksLotterySgList = (List<TenbjpksLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(tenpksLotterySgList)) {
                tenpksLotterySgList = this.selectTenbjpksSg();
                redisTemplate.opsForValue().set(algorithm, tenpksLotterySgList);
            }
            // 大小长龙
            this.getBigAndSmallLong(tenpksLotterySgList);
            // 单双长龙
            this.getDoubleAndSingleLong(tenpksLotterySgList);
            // 龙虎长龙
            this.getTrigleAndDragonLong(tenpksLotterySgList);
        } catch (Exception e) {
            logger.error("10分PK10长龙数据统计异常", e);
        }
    }

    /**
     * 10分PK10 大小
     */
    public void getBigAndSmallLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        //冠亚和大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagCnName());
        //冠军大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSGJBIG.getTagCnName());
        //亚军大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSYJBIG.getTagCnName());
        //第三名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDSMBIG.getTagCnName());
        //第四名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDFMBIG.getTagCnName());
        //第五名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDWMBIG.getTagCnName());
        //第六名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDLMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDLMBIG.getTagCnName());
        //第七名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDQMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDQMBIG.getTagCnName());
        //第八名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDMMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDMMBIG.getTagCnName());
        //第九名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDJMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDJMBIG.getTagCnName());
        //第十名大小
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDTMBIG.getTagType(), CaipiaoPlayTypeEnum.TENPKSDTMBIG.getTagCnName());
    }

    /**
     * 10分PK10 单双
     */
    public void getDoubleAndSingleLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        //冠亚和单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGYHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSGYHDOUBLE.getTagCnName());
        //冠军单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSGJDOUBLE.getTagCnName());
        //亚军单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSYJDOUBLE.getTagCnName());
        //第三名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDSMDOUBLE.getTagCnName());
        //第四名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDFMDOUBLE.getTagCnName());
        //第五名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDWMDOUBLE.getTagCnName());
        //第六名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDLMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDLMDOUBLE.getTagCnName());
        //第七名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDQMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDQMDOUBLE.getTagCnName());
        //第八名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDMMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDMMDOUBLE.getTagCnName());
        //第九名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDJMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDJMDOUBLE.getTagCnName());
        //第十名单双
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDTMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENPKSDTMDOUBLE.getTagCnName());
    }

    /**
     * 10分PK10 龙虎
     */
    public void getTrigleAndDragonLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        //冠军龙虎
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.TENPKSGJTIDRAGON.getTagCnName());
        //亚军龙虎
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.TENPKSYJTIDRAGON.getTagCnName());
        //第三名龙虎
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.TENPKSDSMTIDRAGON.getTagCnName());
        //第四名龙虎
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.TENPKSDFMTIDRAGON.getTagCnName());
        //第五名龙虎
        this.getTenPksDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.TENPKSDWMTIDRAGON.getTagCnName());
    }

    /**
     * @param tenpksLotterySgList
     * @param type
     */
    public void getTenPksDragonInfo(List<TenbjpksLotterySg> tenpksLotterySgList, int type, String playType) {
        try {
            if (!CollectionUtils.isEmpty(tenpksLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < tenpksLotterySgList.size(); index++) {
                    TenbjpksLotterySg tenbjpksLotterySg = tenpksLotterySgList.get(index);
                    String numberString = tenbjpksLotterySg.getNumber();
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateTenPksResult(type, numberString);

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
                if (dragonSize >= Constants.DEFAULT_SIX) {
                    Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
                    String caiPiaoType = CaipiaoTypeEnum.JSSSC.getTagCnName();
                    List<String> dragonList = new ArrayList<String>(dragonSet);
                    String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
                    taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("十分PK10获取长龙异常:", e);
        }
    }

    /**
     * @Title: calculateResult
     * @Description: 按照玩法计算结果
     * @author HANS
     * @date 2019年5月18日上午11:22:00
     */
    public String calculateTenPksResult(int type, String numberString) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 147:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠亚和大小
                break;
            case 148:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠军大小
                break;
            case 149:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//亚军大小
                break;
            case 150:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第三名大小
                break;
            case 151:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第四名大小
                break;
            case 152:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第五名大小
                break;
            case 153:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第六名大小
                break;
            case 154:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第七名大小
                break;
            case 155:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第八名大小
                break;
            case 156:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第九名大小
                break;
            case 157:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第十名大小
                break;
            case 158:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠亚和单双
                break;
            case 159:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠军单双
                break;
            case 160:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//亚军单双
                break;
            case 161:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第三名单双
                break;
            case 162:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第四名单双
                break;
            case 163:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第五名单双
                break;
            case 164:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第六名单双
                break;
            case 165:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第七名单双
                break;
            case 166:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第八名单双
                break;
            case 167:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第九名单双
                break;
            case 168:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第十名单双
                break;
            case 169:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//冠军龙虎
                break;
            case 170:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//亚军龙虎
                break;
            case 171:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第三名龙虎
                break;
            case 172:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第四名龙虎
                break;
            case 173:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第五名龙虎
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @param
     * @param playType
     */
    public void getSslhcPlayDragonMap(List<AmlhcLotterySg> amlhcLotterySgList, int type, String playType) {
        try {
            if (!CollectionUtils.isEmpty(amlhcLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < amlhcLotterySgList.size(); index++) {
                    AmlhcLotterySg amlhcLotterySg = amlhcLotterySgList.get(index);
                    String numberString = amlhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : amlhcLotterySg.getNumber();
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateGoriSslhcResult(type, numberString);

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
                if (dragonSize >= Constants.DEFAULT_SIX) {
                    Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
                    String caiPiaoType = CaipiaoTypeEnum.AMLHC.getTagCnName();
                    List<String> dragonList = new ArrayList<String>(dragonSet);
                    String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
                    taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("时时六合彩获取长龙异常:", e);
        }
    }

    /**
     * @Title: calculateResult
     * @Description: 计算每种算法的结果
     * @author HANS
     * @date 2019年5月22日上午10:32:14
     */
    public String calculateGoriSslhcResult(int type, String number) {
        String result = Constants.DEFAULT_NULL;
        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 217:
                result = OnelhcSgUtils.getOnelhcBigOrDouble(number, type);//特码两面单双
                break;
            case 218:
                result = OnelhcSgUtils.getOnelhcBigOrDouble(number, type);//特码两面大小
                break;
            case 219:
                result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number, type);//正码总单总双
                break;
            case 220:
                result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number, type);//正码总大总小
                break;
            case 221:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 0);//正1特单双
                break;
            case 222:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 1);//正2特单双
                break;
            case 223:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 2);//正3特单双
                break;
            case 224:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 3);//正4特单双
                break;
            case 225:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 4);//正5特单双
                break;
            case 226:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 5);//正6特单双
                break;
            case 227:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 0);//正1特大小
                break;
            case 228:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 1);//正2特大小
                break;
            case 229:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 2);//正3特大小
                break;
            case 230:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 3);//正4特大小
                break;
            case 231:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 4);//正5特大小
                break;
            case 232:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 5);//正6特大小
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @param
     */
    private void getTensscBigAndSmallLong(List<TensscLotterySg> tensscLotterySqList) {
        // 收集10分时时彩两面总和大小
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getTagType(), CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getTagCnName());
        // 收集10分时时彩第一球大小
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDYQBIG.getTagType(), CaipiaoPlayTypeEnum.TENSSCDYQBIG.getTagCnName());
        // 收集10分时时彩第二球大小
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDEQBIG.getTagType(), CaipiaoPlayTypeEnum.TENSSCDEQBIG.getTagCnName());
        // 收集10分时时彩第三球大小
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDSQBIG.getTagType(), CaipiaoPlayTypeEnum.TENSSCDSQBIG.getTagCnName());
        // 收集10分时时彩第四球大小
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDFQBIG.getTagType(), CaipiaoPlayTypeEnum.TENSSCDFQBIG.getTagCnName());
        // 收集10分时时彩第五球大小
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDWQBIG.getTagType(), CaipiaoPlayTypeEnum.TENSSCDWQBIG.getTagCnName());

    }

    /**
     * @param
     */
    private void getTensscSigleAndDoubleLong(List<TensscLotterySg> tensscLotterySqList) {
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCLMZHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENSSCLMZHDOUBLE.getTagCnName());
        // 收集10分时时彩第一球单双
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDYQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENSSCDYQDOUBLE.getTagCnName());
        // 收集10分时时彩第二球单双
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDEQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENSSCDEQDOUBLE.getTagCnName());
        // 收集10分时时彩第三球单双
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDSQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENSSCDSQDOUBLE.getTagCnName());
        // 收集10分时时彩第四球单双
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDFQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENSSCDFQDOUBLE.getTagCnName());
        // 收集10分时时彩第五球单双
        this.getTensscDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDWQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TENSSCDWQDOUBLE.getTagCnName());
    }

    /**
     * @param
     * @param type
     */
    private void getTensscDragonInfo(List<TensscLotterySg> tensscLotterySqList, int type, String playType) {
        try {
            if (!CollectionUtils.isEmpty(tensscLotterySqList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < tensscLotterySqList.size(); index++) {
                    TensscLotterySg tensscLotterySg = tensscLotterySqList.get(index);
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateTensscResult(type, tensscLotterySg);

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
                if (dragonSize >= Constants.DEFAULT_SIX) {
                    Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
                    String caiPiaoType = CaipiaoTypeEnum.TENSSC.getTagCnName();
                    List<String> dragonList = new ArrayList<String>(dragonSet);
                    String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
                    taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("10分时时彩获取长龙失败:", e);
        }
    }

    /**
     * @Title: calculateResult
     * @Description: 按照玩法计算结果
     * @author HANS
     * @date 2019年5月16日下午10:03:40
     */
    private String calculateTensscResult(int type, TensscLotterySg tensscLotterySg) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 72:
                result = AusactSgUtils.getJssscBigOrSmall(tensscLotterySg.getNumber());//两面总和大小
                break;
            case 73:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getWan());//第一球大小
                break;
            case 74:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getQian());//第二球大小
                break;
            case 75:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getBai());//第三球大小
                break;
            case 76:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getShi());//第四球大小
                break;
            case 77:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getGe());//第五球大小
                break;
            case 78:
                result = AusactSgUtils.getSingleAndDouble(tensscLotterySg.getNumber());//两面总和单双
                break;
            case 79:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getWan());//第一球单双
                break;
            case 80:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getQian());//第二球单双
                break;
            case 81:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getBai());//第三球单双
                break;
            case 82:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getShi());//第四球单双
                break;
            case 83:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getGe());//第五球单双
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @return List<SslhcLotterySg>
     * @Title: getSslhcAlgorithmData
     * @Description: 获取近期数据
     * @author HANS
     * @date 2019年5月21日下午10:25:17
     */
    public List<AmlhcLotterySg> getAmlhcAlgorithmData() {
        AmlhcLotterySgExample amlhcExample = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria sslhcCriteria = amlhcExample.createCriteria();
        sslhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        amlhcExample.setOrderByClause("`ideal_time` DESC");
        amlhcExample.setOffset(Constants.DEFAULT_INTEGER);
        amlhcExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<AmlhcLotterySg> lhcLotterySgList = sslhcLotterySgMapper.selectByExample(amlhcExample);
        return lhcLotterySgList;
    }

    /**
     * @Title: getAlgorithmData
     * @Description: 缓存近期数据
     * @author HANS
     * @date 2019年5月15日上午10:58:26
     */
    private List<TensscLotterySg> getTensscAlgorithmData() {
        TensscLotterySgExample tensscExample = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria tensscCriteria = tensscExample.createCriteria();
        tensscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        tensscExample.setOrderByClause("`ideal_time` DESC");
        tensscExample.setOffset(Constants.DEFAULT_INTEGER);
        tensscExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<TensscLotterySg> tensscLotterySqList = tensscLotterySgMapper.selectByExample(tensscExample);
        return tensscLotterySqList;
    }

    /**
     * @Title: selectTenbjpksSg
     * @Description: 获取10分PK开奖数据
     * @author HANS
     * @date 2019年5月18日下午2:39:19
     */
    public List<TenbjpksLotterySg> selectTenbjpksSg() {
        TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
        TenbjpksLotterySgExample.Criteria tenpksCriteria = example.createCriteria();
        tenpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<TenbjpksLotterySg> bjpksLotterySgList = tenbjpksLotterySgMapper.selectByExample(example);
        return bjpksLotterySgList;
    }

}
