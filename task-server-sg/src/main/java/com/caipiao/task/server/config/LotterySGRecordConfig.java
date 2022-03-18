package com.caipiao.task.server.config;

import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.enums.LotteryTableNameEnum;

import java.util.LinkedList;
import java.util.List;

/**
 * 开奖记录配置, 把需要查询开奖记录的彩票配置到这里
 */
public class LotterySGRecordConfig {
    private static final List<String> lotteryList = new LinkedList<>();
    private static final List<LotteryTableNameEnum> lotteryEnumList = new LinkedList<>();

    static {
        lotteryEnumList.add(LotteryTableNameEnum.DZKS);//    德州快三(一分快三)
        lotteryEnumList.add(LotteryTableNameEnum.JSPKS);//   极速PK10(一分赛车)
        lotteryEnumList.add(LotteryTableNameEnum.FIVELHC);// 5分六合彩
        lotteryEnumList.add(LotteryTableNameEnum.TENSSC);//  10分时时彩
        lotteryEnumList.stream().forEach(lottery->lotteryList.add(lottery.getTableName()));
    }

    public static List<String> getLotteryList(){
        return lotteryList;
    }

    public static List<LotteryTableNameEnum> getLotteryEnumList(){
        return lotteryEnumList;
    }
}
