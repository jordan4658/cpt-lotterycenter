package com.caipiao.core.library.tool;

/**
 * @author ShaoMing
 * @datetime 2018/7/27 9:40
 */
public class PceggUtil {

    /**
     * PC蛋蛋开奖号码之和
     * @param number 开奖号码
     * @return 和
     */
    public static Integer sumNumber(String number) {
        String[] nums = number.split(",");
        Integer sum = 0;
        for (String num : nums) {
            sum += Integer.parseInt(num);
        }
        return sum;
    }

    /**
     * 获取开奖号码中第几区的值
     * @param number 开奖号码
     * @param region 第几区
     * @return
     */
    public static Integer getRegionNumber(String number, Integer region) {
        String[] nums = number.split(",");
        return Integer.parseInt(nums[region-1]);
    }

    /**
     * 通过【开奖号码】判断PC蛋蛋开奖号码大小
     * @param number 开奖号码
     * @return 大 | 小
     */
    public static String checkBigOrSmall(String number) {
        Integer sum = sumNumber(number);
        return sum >= 14 ? "大" : "小";
    }

    /**
     * 通过【开奖号码之和】判断PC蛋蛋开奖号码大小
     * @param sum 开奖号码之和
     * @return 大 | 小
     */
    public static String checkBigOrSmall(Integer sum) {
        return sum >= 14 ? "大" : "小";
    }

    /**
     * 通过【开奖号码】判断PC蛋蛋开奖号码单双
     * @param number 开奖号码
     * @return 单 | 双
     */
    public static String checkSingleOrDouble(String number) {
        Integer sum = sumNumber(number);
        return sum % 2 == 0 ? "双" : "单";
    }

    /**
     * 通过【开奖号码之和】判断PC蛋蛋开奖号码单双
     * @param sum 开奖号码之和
     * @return 单 | 双
     */
    public static String checkSingleOrDouble(Integer sum) {
        return sum % 2 == 0 ? "双" : "单";
    }

    /**
     * 通过【开奖号码】判断极值
     * @param number 开奖号码
     * @return
     */
    public static String checkLimitValue(String number) {
        Integer sum = sumNumber(number);
        return sum >= 0 && sum <= 5 ? "极小" : sum >= 22 && sum <= 27 ? "极大" : "";
    }

    /**
     * 通过【开奖号码之和】判断极值
     * @param sum 开奖号码
     * @return
     */
    public static String checkLimitValue(Integer sum) {
        return sum >= 0 && sum <= 5 ? "极小" : sum >= 22 && sum <= 27 ? "极大" : "";
    }

    /**
     * 判断豹子
     * @return
     */
    public static String checkLeopard(String number) {
        String[] nums = number.split(",");
        return nums[0].equals(nums[1]) && nums[0].equals(nums[2]) ? "豹子" : "";
    }

}
