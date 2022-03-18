package com.caipiao.core.library.tool;

import java.util.*;

/**
 * 生成随机数工具类
 */
public class RandomUtil {

    /**
     * 生成指定位数的随机数
     * @param length 长度
     * @return
     */
    public static Integer getRandomOne(int length) {
        Double max = Math.pow(10, length);
        Double min = Math.pow(10, length - 1);
        return getRandomOne(min.intValue(), max.intValue());
    }

    /**
     * 生成单个随机数
     *      start 和 end 组成一个区间 [start, end) , 生成的随机数将 大于等于 start 并且 小于end
     * @param start 开始数字（包含在内）
     * @param end 结束数字（不包含在内）
     * @return
     */
    public static Integer getRandomOne(int start, int end) {
        // 创建Random对象
        Random random = new Random();
        return random.nextInt(end-start) + start;
    }

    /**
     * 生成【不重复的随机数】集合
     *      start 和 end 组成一个区间 [start, end) , 生成的随机数将 大于等于 start 并且 小于end
     * @param count 要生成的随机数个数
     * @param start 开始数字（包含在内）
     * @param end 结束数字（不包含在内）
     * @return
     */
    public static List<Integer> getRandomList(int count, int start, int end) {
        // 1.创建集合容器对象
        List<Integer> list = new ArrayList<>();

        // 2.创建Random对象
        Random r = new Random();

        // 3.循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while(list.size() != count){
            int num = r.nextInt(end-start) + start;
            if(!list.contains(num)){
                list.add(num);
            }
        }

        return list;
    }

    /**
     * 生成【不重复的随机数】拼装的字符串
     *      start 和 end 组成一个区间 [start, end) , 生成的随机数将大于
     * @param count 要生成的随机数个数
     * @param start 开始数字（包含在内）
     * @param end 结束数字（不包含在内）
     * @return
     */
    public static String getRandomStringNoSame(int count, int start, int end) {
        // 1.创建集合容器对象
        List<Integer> list = new ArrayList<>();

        // 2.创建Random对象
        Random r = new Random();

        // 3.循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while(list.size() != count){
            int num = r.nextInt(end-start) + start;
            if(!list.contains(num)){
                list.add(num);
            }
        }
        // 4.排序（该方式只是用与普通类型的排序，只能是顺序）
     //   Collections.sort(list);

        return listToString(list);
    }

    /**
     * 生成【可重复的随机数】拼装的字符串
     *      start 和 end 组成一个区间 [start, end) , 生成的随机数将大于
     * @param count 要生成的随机数个数
     * @param start 开始数字（包含在内）
     * @param end 结束数字（不包含在内）
     * @return
     */
    public static String getRandomStringSame(int count, int start, int end) {
        // 1.创建集合容器对象
        List<Integer> list = new ArrayList<>();

        // 2.创建Random对象
        Random r = new Random();

        // 3.循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while(list.size() != count){
            int num = r.nextInt(end-start) + start;
            list.add(num);
        }

        return listToString(list);
    }

    /**
     * List 集合转为String 每个元素之间用半角逗号","隔开
     * @param list 待转换的集合
     * @return
     */
    private static String listToString(List<Integer> list) {
        // 创建字符串容器对象
        StringBuilder numStr = new StringBuilder();

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer num = iterator.next();
            numStr.append(num);
            if (iterator.hasNext()) {
                numStr.append(",");
            }
        }
        return numStr.toString();
    }

}
