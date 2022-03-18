package com.caipiao.live.common.util.threeway;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.util.lottery.LhcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ThreeWayConversionUtils
 * @Description: 六合彩开奖号码计算
 * @author: HANS
 * @date: 2019年6月11日 下午4:11:13
 */
public class LhcDrawNumberUtils {
    private static final Logger logger = LoggerFactory.getLogger(LhcDrawNumberUtils.class);

    /**
     * 返回六合彩开奖数组
     *
     * @param number
     * @return
     */
    public static List<Integer> splitDrawNumber(String number) {
        List<Integer> drawArray = new ArrayList<>();
        try {
            String[] numberArray = number.split(",");
            for (String drewNum : numberArray) {
                Integer intDraw = Integer.valueOf(drewNum);
                drawArray.add(intDraw);
            }
        } catch (Exception e) {
            logger.error("拆分六合彩开奖结果异常：", e);
        }
        return drawArray;
    }

    /**
     * 获取号码的两面
     *
     * @param num
     * @return
     */
    public static List<Integer> getNumLiangMianList(int num) {
        List<Integer> list = new ArrayList<>();

        if (num != 49) {
            if (num >= 25) {
                list.add(Constants.DEFAULT_ONE); // 大
            } else {
                list.add(Constants.DEFAULT_TWO); // 小
            }

            if (num % 2 == 1) {
                list.add(Constants.DEFAULT_ONE); // 单
            } else {
                list.add(Constants.DEFAULT_TWO); // 双
            }
            int tou = num / 10;
            int wei = num % 10;

            if ((tou + wei) % 2 == 1) {
                list.add(Constants.DEFAULT_ONE); // 合单
            } else {
                list.add(Constants.DEFAULT_TWO); // 合双
            }

            if (wei >= 5) {
                list.add(Constants.DEFAULT_ONE); // 尾大
            } else {
                list.add(Constants.DEFAULT_TWO); // 尾小
            }
        }
        return list;
    }

    /**
     * 正码处理
     *
     * @param sgNumber
     * @return
     */
    public static List<Integer> getTotalLiangMian(String sgNumber) {
        List<Integer> result = new ArrayList<>();
        String[] sgArr = sgNumber.split(",");

        int total = 0;
        for (String num : sgArr) {
            total += Integer.valueOf(num);
        }
        // 判断
        if (total % 2 == 1) {
            result.add(Constants.DEFAULT_ONE); // 总单
        } else {
            result.add(Constants.DEFAULT_TWO); // 总双
        }

        if (total >= 175) {
            result.add(Constants.DEFAULT_ONE); // 总大
        } else {
            result.add(Constants.DEFAULT_TWO); // 总小
        }

        int wei = total % 10;
        if (wei >= 5) {
            result.add(Constants.DEFAULT_ONE); // 总尾大
        } else {
            result.add(Constants.DEFAULT_TWO); // 总尾小
        }

        if (Integer.valueOf(sgArr[0]) > Integer.valueOf(sgArr[6])) {
            result.add(Constants.DEFAULT_ONE); // 龙
        } else {
            result.add(Constants.DEFAULT_TWO); // 虎
        }
        return result;

    }

    /**
     * 特码是家禽、野兽
     *
     * @param num
     * @param date
     * @return
     */
    public static Integer getSpeciaAnimal(Integer num, String date) {
        Integer animal = Constants.DEFAULT_INTEGER;
        try {
            String zodiac = LhcUtils.getShengXiao(num, date);

            if (LhcUtils.JIAQIN.contains(zodiac)) {
                animal = Constants.DEFAULT_ONE;//"家禽";
            } else {
                animal = Constants.DEFAULT_TWO;//"野兽";
            }
        } catch (Exception e) {
            logger.error("特码判断家禽和野兽结果异常：", e);
        }
        return animal;
    }


}
