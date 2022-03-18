package com.caipiao.live.common.util.lottery;

import java.util.Arrays;

/**
 * @Date:Created in 19:222020/2/24
 * @Descriotion
 * @Author
 **/
public class KsUtils {
    //胆拖不能相同 不能有空
    public static boolean judgeDmAndTm(String betNumber) {
        String[] split = betNumber.split("_");
        if (split.length <= 1) {
            return true;
        }
        String betDmSplit = split[0];
        String[] betDm = betDmSplit.split(",");
        String betTmSplit = split[1];
        String[] betTm = betTmSplit.split(",");

        for (String dmNumber : betDm) {
            if(Arrays.asList(betTm).contains(dmNumber)){
                return true;
            }
        }
        return false;
    }

}

