package com.caipiao.core.library.tool;

import com.caipiao.core.library.constant.LotteryInformationType;
import com.caipiao.core.library.dto.result.SscMissNumDTO;
import com.caipiao.core.library.dto.result.TenpksGuanYaHeCountDTO;
import com.caipiao.core.library.dto.result.TenpksLiangMian;
import com.caipiao.core.library.vo.common.*;
import com.caipiao.core.library.vo.result.KillNumberVO;
import com.caipiao.core.library.vo.web.LhcCountVO;
import com.mapper.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 10分pk10功能类
 *
 * @author lzy
 * @create 2018-07-30 17:32
 **/
public class TenpksUtils {

    /**
     * 今日号码
     *
     * @param sgList
     * @return
     */
    public static List<MapListVO> todayNumber(List<String> sgList) {
        List<MapListVO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(sgList)) {
            return list;
        }

        // 创建一个二维数组 -- 总开
        int[][] arr = new int[10][20];

        String[] sgStr;
        for (String sg : sgList) {
            sgStr = sg.split(",");
            for (int i = 0; i < 10; i++) {
                // num: 车号。 i : 名次
                int num = Integer.valueOf(sgStr[i]);
                arr[num - 1][i * 2] += 1;
                arr[0][i * 2 + 1] = num == 1 ? 0 : arr[0][i * 2 + 1] + 1;
                arr[1][i * 2 + 1] = num == 2 ? 0 : arr[1][i * 2 + 1] + 1;
                arr[2][i * 2 + 1] = num == 3 ? 0 : arr[2][i * 2 + 1] + 1;
                arr[3][i * 2 + 1] = num == 4 ? 0 : arr[3][i * 2 + 1] + 1;
                arr[4][i * 2 + 1] = num == 5 ? 0 : arr[4][i * 2 + 1] + 1;
                arr[5][i * 2 + 1] = num == 6 ? 0 : arr[5][i * 2 + 1] + 1;
                arr[6][i * 2 + 1] = num == 7 ? 0 : arr[6][i * 2 + 1] + 1;
                arr[7][i * 2 + 1] = num == 8 ? 0 : arr[7][i * 2 + 1] + 1;
                arr[8][i * 2 + 1] = num == 9 ? 0 : arr[8][i * 2 + 1] + 1;
                arr[9][i * 2 + 1] = num == 10 ? 0 : arr[9][i * 2 + 1] + 1;
            }
        }

        List<Integer> data;
        for (int i = 0; i < 10; i++) {
            data = new ArrayList<>();
            for (int j = 0; j < arr[i].length; j++) {
                data.add(arr[i][j]);
            }
            list.add(new MapListVO(i + 1 + "", data));
        }
        return list;
    }

    /**
     * 冷热分析
     *
     * @param sgList
     * @return
     */
    public static List<MapListVO> lengRe(List<String> sgList) {
        if (CollectionUtils.isEmpty(sgList)) {
            return null;
        }
        int totalIssue = sgList.size();
        int[][] arr = new int[10][10];

        for (int i = 0; i < totalIssue; i++) {
            String number = sgList.get(i);
            String[] numberStr = number.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numberStr[j]);
                arr[j][num - 1] += 1;
            }
        }

        ArrayList<MapListVO> result = new ArrayList<>(10);
        for (int k = 0; k < 10; k++) {
            ArrayList<MapVO> data = new ArrayList<>(10);
            for (int x = 0; x < 10; x++) {
                data.add(new MapVO(x + 1 + "", arr[k][x]));
            }
            result.add(new MapListVO(k + 1 + "", data));
        }

        return result;
    }

    /**
     * 号码遗漏
     *
     * @param sgList 倒序的开奖结果
     * @return
     */
    public static List<MapListVO> numNoOpen(List<String> sgList) {
        if (CollectionUtils.isEmpty(sgList)) {
            return null;
        }

        int totalIssue = sgList.size();
        int[][] openArr = new int[10][10]; //统计出现次数
        Integer[][] noOpenArr = new Integer[10][10]; //统计遗漏次数
        int[] firstNum = new int[10]; //最近一期出现的号码
        int[] count = new int[10];  // 获取当前连续出现号码的总次数

        //保存最近一期的开奖号码
        if (totalIssue > 0) {
            String number0 = sgList.get(0);
            String[] numberStr0 = number0.split(",");
            for (int y = 0; y < 10; y++) {
                int first = Integer.valueOf(numberStr0[y]);
                firstNum[y] = first;
            }
        }

        for (int i = 0; i < totalIssue; i++) {
            String number = sgList.get(i);
            String[] numberStr = number.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numberStr[j]);
                //统计出现次数
                openArr[j][num - 1] += 1;
                //统计遗漏次数
                if (noOpenArr[j][num - 1] == null) {
                    noOpenArr[j][num - 1] = i;
                }
                // 统计当前连续出现号码的总次数
                if (i == count[j] && num == firstNum[j]) {
                    count[j]++;
                }
            }
        }

        //设置最近一期出现号码的遗漏值
        for (int a = 0; a < 10; a++) {
            noOpenArr[a][firstNum[a] - 1] = count[a] * (-1);
        }

        //设置一直没出现过的号码的遗漏值为总期数
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                if (noOpenArr[j][k] == null) {
                    noOpenArr[j][k] = totalIssue;
                }
            }
        }

        ArrayList<MapListVO> result = new ArrayList<>(10);
        for (int k = 0; k < 10; k++) {
            ArrayList<ThereIntegerVO> data = new ArrayList<>(10);
            for (int x = 0; x < 10; x++) {
                data.add(new ThereIntegerVO(x + 1, openArr[k][x], noOpenArr[k][x]));
            }
            result.add(new MapListVO(k + 1 + "", data));
        }

        return result;
    }

    /**
     * 北京PK10冠亚和统计
     *
     * @param sgList 倒序的开奖结果
     * @return
     */
    public static Map<String, List<ThereIntegerVO>> guanYaCount(List<String> sgList) {
        Map<String, List<ThereIntegerVO>> result = new HashMap<>();
        List<ThereIntegerVO> data1 = new ArrayList<>();
        List<ThereIntegerVO> data2 = new ArrayList<>();

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            result.put("he", data1);
            result.put("lm", data2);
            return result;
        }

        // 遍历赛果计算冠亚和
        List<Integer> sumList = new ArrayList<>();
        for (String sgStr : sgList) {
            String[] sg = sgStr.split(",");
            sumList.add(Integer.valueOf(sg[0]) + Integer.valueOf(sg[1]));
        }

        // 冠亚和统计
        int open, noOpen, before;
        for (int i = 3; i < 20; i++) {
            open = noOpen = 0;
            before = sumList.get(0);
            for (Integer sum : sumList) {
                if (sum.equals(i)) {
                    open++;
                    noOpen = sum.equals(before) ? noOpen - 1 : -1;
                } else {
                    noOpen = noOpen > 0 ? noOpen + 1 : 1;
                }
                before = sum;
            }
            data1.add(new ThereIntegerVO(i, open, noOpen));
        }

        // 冠亚和两面统计
        for (int i = 1; i < 5; i++) {
            open = noOpen = 0;
            before = sumList.get(0);
            for (Integer sum : sumList) {
                switch (i) {
                    case 1:
                        if (sum > 11) {
                            open++;
                            noOpen = before > 11 ? noOpen - 1 : -1;
                        } else {
                            noOpen = noOpen > 0 ? noOpen + 1 : 1;
                        }
                        break;

                    case 2:
                        if (sum <= 11) {
                            open++;
                            noOpen = before <= 11 ? noOpen - 1 : -1;
                        } else {
                            noOpen = noOpen > 0 ? noOpen + 1 : 1;
                        }
                        break;

                    case 3:
                        if (sum % 2 == 1) {
                            open++;
                            noOpen = before % 2 == 1 ? noOpen - 1 : -1;
                        } else {
                            noOpen = noOpen > 0 ? noOpen + 1 : 1;
                        }
                        break;

                    default:
                        if (sum % 2 == 0) {
                            open++;
                            noOpen = before % 2 == 0 ? noOpen - 1 : -1;
                        } else {
                            noOpen = noOpen > 0 ? noOpen + 1 : 1;
                        }
                        break;
                }
                before = sum;
            }
            data2.add(new ThereIntegerVO(i, open, noOpen));
        }

        result.put("he", data1);
        result.put("lm", data2);
        return result;
    }

    /**
     * 北京pk10两面长龙
     *
     * @param sg
     * @return
     */
    public static List<ThereMemberVO> liangMianC(List<String> sg) {
        if (sg == null) {
            return null;
        }
        String[] type = new String[]{"大", "小", "单", "双", "龙", "虎"};
        int[] arr = new int[54]; // 冠亚和(1*4) + 1-5名(5*6) + 6-10名(5*4) = 54
        int totalIssue = sg.size();
        for (int i = 0; i < totalIssue; i++) {
            String number = sg.get(i);
            String[] numberStr = number.split(",");
            int num1 = Integer.valueOf(numberStr[0]);
            int num2 = Integer.valueOf(numberStr[1]);
            int num3 = Integer.valueOf(numberStr[2]);
            int num4 = Integer.valueOf(numberStr[3]);
            int num5 = Integer.valueOf(numberStr[4]);
            int num6 = Integer.valueOf(numberStr[5]);
            int num7 = Integer.valueOf(numberStr[6]);
            int num8 = Integer.valueOf(numberStr[7]);
            int num9 = Integer.valueOf(numberStr[8]);
            int num10 = Integer.valueOf(numberStr[9]);
            //冠亚和的两面统计, 11为和值
            int numHe = num1 + num2;
            if (numHe > 11) {
                arr[0] += 1;
            } else if (numHe <= 11) {
                arr[1] += 1;
            }
            if (dan(numHe)) {
                arr[2] += 1;
            } else {
                arr[3] += 1;
            }

            //1-5名次的统计
            //冠军统计
            if (da(num1)) {
                arr[4] += 1;
            } else {
                arr[5] += 1;
            }
            if (dan(num1)) {
                arr[6] += 1;
            } else {
                arr[7] += 1;
            }
            if (num1 > num10) {
                arr[8] += 1;
            } else {
                arr[9] += 1;
            }
            //亚军统计
            if (da(num2)) {
                arr[10] += 1;
            } else {
                arr[11] += 1;
            }
            if (dan(num2)) {
                arr[12] += 1;
            } else {
                arr[13] += 1;
            }
            if (num2 > num9) {
                arr[14] += 1;
            } else {
                arr[15] += 1;
            }
            //第三名统计
            if (da(num3)) {
                arr[16] += 1;
            } else {
                arr[17] += 1;
            }
            if (dan(num3)) {
                arr[18] += 1;
            } else {
                arr[19] += 1;
            }
            if (num3 > num8) {
                arr[20] += 1;
            } else {
                arr[21] += 1;
            }
            //第四名统计
            if (da(num4)) {
                arr[22] += 1;
            } else {
                arr[23] += 1;
            }
            if (dan(num4)) {
                arr[24] += 1;
            } else {
                arr[25] += 1;
            }
            if (num4 > num7) {
                arr[26] += 1;
            } else {
                arr[27] += 1;
            }
            //第五名统计
            if (da(num5)) {
                arr[28] += 1;
            } else {
                arr[29] += 1;
            }
            if (dan(num5)) {
                arr[30] += 1;
            } else {
                arr[31] += 1;
            }
            if (num5 > num6) {
                arr[32] += 1;
            } else {
                arr[33] += 1;
            }

            //第6-10名统计
            //第六名统计
            if (da(num6)) {
                arr[34] += 1;
            } else {
                arr[35] += 1;
            }
            if (dan(num6)) {
                arr[36] += 1;
            } else {
                arr[37] += 1;
            }
            //第七名统计
            if (da(num7)) {
                arr[38] += 1;
            } else {
                arr[39] += 1;
            }
            if (dan(num7)) {
                arr[40] += 1;
            } else {
                arr[41] += 1;
            }
            //第八名统计
            if (da(num8)) {
                arr[42] += 1;
            } else {
                arr[43] += 1;
            }
            if (dan(num8)) {
                arr[44] += 1;
            } else {
                arr[45] += 1;
            }
            //第九名统计
            if (da(num9)) {
                arr[46] += 1;
            } else {
                arr[47] += 1;
            }
            if (dan(num9)) {
                arr[48] += 1;
            } else {
                arr[49] += 1;
            }
            //第十名统计
            if (da(num10)) {
                arr[50] += 1;
            } else {
                arr[51] += 1;
            }
            if (dan(num10)) {
                arr[52] += 1;
            } else {
                arr[53] += 1;
            }
        }

        ArrayList<ThereMemberVO> result = new ArrayList<>();
        for (int j = 0; j < 54; j++) {
            if (arr[j] > 0) {
                if (j < 4) {
                    result.add(new ThereMemberVO("0", type[j], arr[j]));
                } else if (j < 10) {
                    result.add(new ThereMemberVO("1", type[(j - 4)], arr[j]));
                } else if (j < 16) {
                    result.add(new ThereMemberVO("2", type[(j - 10)], arr[j]));
                } else if (j < 22) {
                    result.add(new ThereMemberVO("3", type[(j - 16)], arr[j]));
                } else if (j < 28) {
                    result.add(new ThereMemberVO("4", type[(j - 22)], arr[j]));
                } else if (j < 34) {
                    result.add(new ThereMemberVO("5", type[(j - 28)], arr[j]));
                } else if (j < 38) {
                    result.add(new ThereMemberVO("6", type[(j - 34)], arr[j]));
                } else if (j < 42) {
                    result.add(new ThereMemberVO("7", type[(j - 38)], arr[j]));
                } else if (j < 46) {
                    result.add(new ThereMemberVO("8", type[(j - 42)], arr[j]));
                } else if (j < 50) {
                    result.add(new ThereMemberVO("9", type[(j - 46)], arr[j]));
                } else {
                    result.add(new ThereMemberVO("10", type[(j - 50)], arr[j]));
                }
            }
        }
        return result;
    }

    /**
     * 北京pk10两面长龙
     *
     * @param sg
     * @param postion 位置, 0 代表冠亚和
     * @return
     */
    public static List<LhcCountVO> liangMianCWeb(List<String> sg, int postion) {
        if (sg == null || postion > 10) {
            return null;
        }
        String[] type = new String[]{"大", "小", "单", "双", "龙", "虎"};
        int[] arr = new int[6]; // 冠亚和(1*4) + 1-5名(5*6) + 6-10名(5*4) = 54
        int[] noOpenArr = new int[12]; // 遗漏统计
        String[] tag = new String[3];
        int totalIssue = sg.size();
        //设置第一期的值
        if (totalIssue > 0) {
            String number = sg.get(0);
            String[] numStr = number.split(",");
            if (postion == 0) {
                // 冠亚和
                int heNum = Integer.valueOf(numStr[0]) + Integer.valueOf(numStr[1]);
                if (heNum > 11) {
                    tag[0] = "大";
                } else {
                    tag[0] = "小";
                }
                if (heNum % 2 == 1) {
                    tag[1] = "单";
                } else {
                    tag[1] = "双";
                }
            } else {
                int kjNum = Integer.valueOf(numStr[postion - 1]);
                if (kjNum >= 6) {
                    tag[0] = "大";
                } else {
                    tag[0] = "小";
                }
                if (kjNum % 2 == 1) {
                    tag[1] = "单";
                } else {
                    tag[1] = "双";
                }
                if (postion < 6) {
                    // 1-5名的龙虎
                    int kjNum2 = Integer.valueOf(numStr[10 - postion]);
                    if (kjNum > kjNum2) {
                        tag[2] = "龙";
                    } else {
                        tag[2] = "虎";
                    }
                }
            }

        }

        if (postion == 0) {
            // 冠亚和统计
            for (int i = 0; i < totalIssue; i++) {
                String number = sg.get(i);
                String[] numberStr = number.split(",");
                int num1 = Integer.valueOf(numberStr[0]);
                int num2 = Integer.valueOf(numberStr[1]);
                int numHe = num1 + num2;
                if (numHe > 11) {
                    arr[0] += 1;
                    if (tag[0].equals("小") && noOpenArr[0] > noOpenArr[1]) {
                        noOpenArr[1] = noOpenArr[0];
                    }
                    noOpenArr[2]++;
                    noOpenArr[0] = 0;
                    tag[0] = "大";
                } else if (numHe <= 11) {
                    arr[1] += 1;
                    if (tag[0].equals("大") && noOpenArr[2] > noOpenArr[3]) {
                        noOpenArr[3] = noOpenArr[2];
                    }
                    noOpenArr[0]++;
                    noOpenArr[2] = 0;
                    tag[0] = "小";
                }
                if (dan(numHe)) {
                    arr[2] += 1;
                    if (tag[1].equals("双") && noOpenArr[4] > noOpenArr[5]) {
                        noOpenArr[5] = noOpenArr[4];
                    }
                    noOpenArr[6]++;
                    noOpenArr[4] = 0;
                    tag[1] = "单";
                } else {
                    arr[3] += 1;
                    if (tag[1].equals("单") && noOpenArr[6] > noOpenArr[7]) {
                        noOpenArr[7] = noOpenArr[6];
                    }
                    noOpenArr[4]++;
                    noOpenArr[6] = 0;
                    tag[1] = "双";
                }
            }
        } else {
            // 大小单双统计
            for (int i = 0; i < totalIssue; i++) {
                String number = sg.get(i);
                String[] numberStr = number.split(",");
                int num1 = Integer.valueOf(numberStr[postion - 1]);
                if (num1 > 5) {
                    arr[0] += 1;
                    if (tag[0].equals("小") && noOpenArr[0] > noOpenArr[1]) {
                        noOpenArr[1] = noOpenArr[0];
                    }
                    noOpenArr[2]++;
                    noOpenArr[0] = 0;
                    tag[0] = "大";
                } else {
                    arr[1] += 1;
                    if (tag[0].equals("大") && noOpenArr[2] > noOpenArr[3]) {
                        noOpenArr[3] = noOpenArr[2];
                    }
                    noOpenArr[0]++;
                    noOpenArr[2] = 0;
                    tag[0] = "小";
                }
                if (dan(num1)) {
                    arr[2] += 1;
                    if (tag[1].equals("双") && noOpenArr[4] > noOpenArr[5]) {
                        noOpenArr[5] = noOpenArr[4];
                    }
                    noOpenArr[6]++;
                    noOpenArr[4] = 0;
                    tag[1] = "单";
                } else {
                    arr[3] += 1;
                    if (tag[1].equals("单") && noOpenArr[6] > noOpenArr[7]) {
                        noOpenArr[7] = noOpenArr[6];
                    }
                    noOpenArr[4]++;
                    noOpenArr[6] = 0;
                    tag[1] = "双";
                }
            }

            if (postion < 6) {
                // 1-5名, 统计龙虎
                for (int i = 0; i < totalIssue; i++) {
                    String number = sg.get(i);
                    String[] numberStr = number.split(",");
                    int num1 = Integer.valueOf(numberStr[postion - 1]);
                    int num2 = Integer.valueOf(numberStr[10 - postion]);
                    if (num1 > num2) {
                        arr[4] += 1;
                        if (tag[2].equals("虎") && noOpenArr[8] > noOpenArr[9]) {
                            noOpenArr[9] = noOpenArr[8];
                        }
                        noOpenArr[10]++;
                        noOpenArr[8] = 0;
                        tag[2] = "龙";
                    } else {
                        arr[5] += 1;
                        if (tag[2].equals("龙") && noOpenArr[10] > noOpenArr[11]) {
                            noOpenArr[11] = noOpenArr[10];
                        }
                        noOpenArr[8]++;
                        noOpenArr[10] = 0;
                        tag[2] = "虎";
                    }
                }
            }

        }

        List<LhcCountVO> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            result.add(new LhcCountVO(arr[i], noOpenArr[i * 2] > noOpenArr[i * 2 + 1] ? noOpenArr[i * 2] : noOpenArr[i * 2 + 1], type[i]));
        }
        if (postion > 0 && postion < 6) {
            for (int i = 4; i < 6; i++) {
                result.add(new LhcCountVO(arr[i], noOpenArr[i * 2] > noOpenArr[i * 2 + 1] ? noOpenArr[i * 2] : noOpenArr[i * 2 + 1], type[i]));
            }
        }

        return result;
    }

    /**
     * 北京PK10前后路珠
     *
     * @param sg
     * @return
     */
    public static Map<String, ThereMemberListVO> luzhuQ(List<String> sg) {
        if (sg == null) {
            return null;
        }
        String[] type = new String[]{"", "", "", "", "", "", "", "", "", ""}; //10
        int[] count = new int[20];
        String[] num = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
        ArrayList<ArrayList<String>> lists = new ArrayList<>(10);
        for (int j = 0; j < 10; j++) {
            lists.add(new ArrayList<String>());
        }

        int totalIssue = sg.size();
        for (int i = 0; i < totalIssue; i++) {
            String number = sg.get(i);
            for (int k = 0; k < 10; k++) {
                if (number.indexOf(num[k]) < 14) {
                    count[(2 * k)] += 1;
                    if (type[k].contains("前") || "".equals(type[k])) {
                        type[k] += "前";
                    } else {
                        lists.get(k).add(type[k]);
                        type[k] = "前";
                    }
                } else {
                    count[(2 * k + 1)] += 1;
                    if (type[k].contains("后") || "".equals(type[k])) {
                        type[k] += "后";
                    } else {
                        lists.get(k).add(type[k]);
                        type[k] = "后";
                    }
                }
                // 存入最后的开奖结果
                if (i == totalIssue - 1) {
                    lists.get(k).add(type[k]);
                }
            }
        }

        HashMap<String, ThereMemberListVO> map = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            map.put(num[x], new ThereMemberListVO(count[(x * 2)], count[(x * 2 + 1)], lists.get(x)));
        }
        return map;
    }


    /**
     * 北京PK10两面路珠
     *
     * @param sg
     * @param countType 统计类型
     * @return
     */
    public static Map<String, ThereMemberListVO> luzhuLiangMian(List<String> sg, String countType) {
        if (sg == null) {
            return null;
        }
        String[] type = new String[]{"", "", "", "", "", "", "", "", "", ""}; //10
        int[] count = new int[20];
        String[] num = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
        ArrayList<ArrayList<String>> lists = new ArrayList<>(10);
        for (int j = 0; j < 10; j++) {
            lists.add(new ArrayList<String>());
        }
        int totalIssue = sg.size();

        if (LotteryInformationType.TENPKS_LMLZ_DX.equals(countType)) {
            //统计两面路珠之大小
            for (int i = 0; i < totalIssue; i++) {
                String number = sg.get(i);
                String[] numStr = number.split(",");
                for (int k = 0; k < 10; k++) {
                    int kjNum = Integer.valueOf(numStr[k]);
                    if (kjNum >= 6) {
                        count[(2 * k)] += 1;
                        if (type[k].contains("大") || "".equals(type[k])) {
                            type[k] += "大";
                        } else {
                            lists.get(k).add(type[k]);
                            type[k] = "大";
                        }
                    } else {
                        count[(2 * k + 1)] += 1;
                        if (type[k].contains("小") || "".equals(type[k])) {
                            type[k] += "小";
                        } else {
                            lists.get(k).add(type[k]);
                            type[k] = "小";
                        }
                    }
                    // 存入最后的开奖结果
                    if (i == totalIssue - 1) {
                        lists.get(k).add(type[k]);
                    }
                }
            }
        } else if (LotteryInformationType.TENPKS_LMLZ_DS.equals(countType)) {
            //统计两面路珠之单双
            for (int i = 0; i < totalIssue; i++) {
                String number = sg.get(i);
                String[] numStr = number.split(",");
                for (int k = 0; k < 10; k++) {
                    int kjNum = Integer.valueOf(numStr[k]);
                    if (kjNum % 2 == 1) {
                        count[(2 * k)] += 1;
                        if (type[k].contains("单") || "".equals(type[k])) {
                            type[k] += "单";
                        } else {
                            lists.get(k).add(type[k]);
                            type[k] = "单";
                        }
                    } else {
                        count[(2 * k + 1)] += 1;
                        if (type[k].contains("双") || "".equals(type[k])) {
                            type[k] += "双";
                        } else {
                            lists.get(k).add(type[k]);
                            type[k] = "双";
                        }
                    }
                    // 存入最后的开奖结果
                    if (i == totalIssue - 1) {
                        lists.get(k).add(type[k]);
                    }
                }
            }

        } else if (LotteryInformationType.TENPKS_LMLZ_LH.equals(countType)) {
            //统计两面路珠之龙虎
            for (int i = 0; i < totalIssue; i++) {
                String number = sg.get(i);
                String[] numStr = number.split(",");
                for (int k = 0; k < 5; k++) {
                    int kjNum1 = Integer.valueOf(numStr[k]);
                    int kjNum2 = Integer.valueOf(numStr[9 - k]);
                    if (kjNum1 > kjNum2) {
                        count[(2 * k)] += 1;
                        if (type[k].contains("龙") || "".equals(type[k])) {
                            type[k] += "龙";
                        } else {
                            lists.get(k).add(type[k]);
                            type[k] = "龙";
                        }
                    } else {
                        count[(2 * k + 1)] += 1;
                        if (type[k].contains("虎") || "".equals(type[k])) {
                            type[k] += "虎";
                        } else {
                            lists.get(k).add(type[k]);
                            type[k] = "虎";
                        }
                    }
                    // 存入最后的开奖结果
                    if (i == totalIssue - 1) {
                        lists.get(k).add(type[k]);
                    }
                }
            }

        }

        HashMap<String, ThereMemberListVO> map = new HashMap<>();
        int len = 10;
        if (LotteryInformationType.TENPKS_LMLZ_LH.equals(countType)) {
            len = 5;
        }
        for (int x = 0; x < len; x++) {
            map.put(num[x], new ThereMemberListVO(count[(x * 2)], count[(x * 2 + 1)], lists.get(x)));
        }
        return map;
    }

    /**
     * 北京PK10两面遗漏大小
     *
     * @param sg
     * @param postion 统计位置
     * @return
     */
    public static Map<String, ArrayList<MapIntegerVO>> noOpenLiangMianDx(List<String> sg, int postion) {
        if (sg == null) {
            return null;
        }
        int[] countDArr = new int[20]; //统计大的遗漏,最大统计连续遗漏19次
        int[] countXArr = new int[20]; //统计小的遗漏,最大统计连续遗漏19次
        int countD = 0;
        int countX = 0;
        String numTag = "";
        int totalIssue = sg.size();

        //设置第一期的值
        if (totalIssue > 0) {
            String number = sg.get(0);
            String[] numStr = number.split(",");
            int kjNum = Integer.valueOf(numStr[postion]);
            if (kjNum >= 6) {
                countX++;
                countD = 0;
                numTag = "大";
            } else {
                countD++;
                countX = 0;
                numTag = "小";
            }
        }

        for (int i = 1; i < totalIssue; i++) {
            String number = sg.get(i);
            String[] numStr = number.split(",");
            int kjNum = Integer.valueOf(numStr[postion]);
            if (kjNum >= 6) {
                if (numTag.equals("小")) {
                    countDArr[countD]++;
                }
                countX++;
                countD = 0;
                numTag = "大";
            } else {
                if (numTag.equals("大")) {
                    countXArr[countX]++;
                }
                countD++;
                countX = 0;
                numTag = "小";
            }
        }

        //设置最后一期的值
        if (totalIssue > 0) {
            String number = sg.get(totalIssue - 1);
            String[] numStr = number.split(",");
            int kjNum = Integer.valueOf(numStr[postion]);
            if (kjNum >= 6) {
                countXArr[countX]++;
            } else {
                countDArr[countD]++;
            }
        }


        HashMap<String, ArrayList<MapIntegerVO>> map = new HashMap<>();
        ArrayList<MapIntegerVO> dnumList = new ArrayList<>();
        ArrayList<MapIntegerVO> xnumList = new ArrayList<>();
        for (int j = 1; j < 20; j++) {
            if (countDArr[j] > 0) {
                dnumList.add(new MapIntegerVO(j, countDArr[j]));
            }
            if (countXArr[j] > 0) {
                xnumList.add(new MapIntegerVO(j, countXArr[j]));
            }
        }
        map.put("大", dnumList);
        map.put("小", xnumList);
        return map;
    }

    /**
     * 北京PK10两面遗漏大小
     *
     * @param sg
     * @return
     */
    public static HashMap<Integer, ArrayList<Integer>> noOpenLiangMianDxWeb(List<String> sg) {
        if (sg == null) {
            return null;
        }
        int[][] countDArr = new int[10][19]; //统计1-10位置大的遗漏,最大统计连续遗漏18次
        int[][] countXArr = new int[10][19]; //统计1-10位置小的遗漏,最大统计连续遗漏18次
        int[] countD = new int[10];
        int[] countX = new int[10];
        String[] numTag = new String[10];
        int totalIssue = sg.size();

        //设置第一期的值
        if (totalIssue > 0) {
            String number = sg.get(0);
            String[] numStr = number.split(",");
            for (int i = 0; i < 10; i++) {
                int kjNum = Integer.valueOf(numStr[i]);
                if (kjNum >= 6) {
                    countX[i]++;
                    countD[i] = 0;
                    numTag[i] = "大";
                } else {
                    countD[i]++;
                    countX[i] = 0;
                    numTag[i] = "小";
                }
            }
        }

        for (int i = 1; i < totalIssue; i++) {
            String number = sg.get(i);
            String[] numStr = number.split(",");
            for (int j = 0; j < 10; j++) {
                int kjNum = Integer.valueOf(numStr[j]);
                if (kjNum >= 6) {
                    if (numTag[j].equals("小")) {
                        countDArr[j][countD[j]]++;
                    }
                    countX[j]++;
                    countD[j] = 0;
                    numTag[j] = "大";
                } else {
                    if (numTag[j].equals("大")) {
                        countXArr[j][countX[j]]++;
                    }
                    countD[j]++;
                    countX[j] = 0;
                    numTag[j] = "小";
                }
            }

        }

        //设置最后一期的统计值
        if (totalIssue > 0) {
            String number = sg.get(totalIssue - 1);
            String[] numStr = number.split(",");
            for (int j = 0; j < 10; j++) {
                int kjNum = Integer.valueOf(numStr[j]);
                if (kjNum >= 6) {
                    countXArr[j][countX[j]]++;
                } else {
                    countDArr[j][countD[j]]++;
                }
            }

        }

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int j = 1; j < 19; j++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int k = 0; k < 10; k++) {
                list.add(countDArr[k][j]);
                list.add(countXArr[k][j]);
            }
            map.put(j, list);
        }
        return map;
    }

    /**
     * 北京PK10两面遗漏单双
     *
     * @param sg
     * @return
     */
    public static Map<Integer, ArrayList<Integer>> noOpenLiangMianDsWeb(List<String> sg) {
        if (sg == null) {
            return null;
        }
        int[][] countDArr = new int[10][19]; //统计单1-10位置的遗漏,最大统计连续遗漏19次
        int[][] countXArr = new int[10][19]; //统计双1-10位置的遗漏,最大统计连续遗漏19次
        int countD[] = new int[10];
        int countX[] = new int[10];
        String[] numTag = new String[10];
        int totalIssue = sg.size();

        //设置第一期的值
        if (totalIssue > 0) {
            String number = sg.get(0);
            String[] numStr = number.split(",");
            for (int i = 0; i < 10; i++) {
                int kjNum = Integer.valueOf(numStr[i]);
                if (kjNum % 2 == 1) {
                    countX[i]++;
                    countD[i] = 0;
                    numTag[i] = "单";
                } else {
                    countD[i]++;
                    countX[i] = 0;
                    numTag[i] = "双";
                }
            }

        }

        for (int i = 1; i < totalIssue; i++) {
            String number = sg.get(i);
            String[] numStr = number.split(",");
            for (int j = 0; j < 10; j++) {
                int kjNum = Integer.valueOf(numStr[j]);
                if (kjNum % 2 == 1) {
                    if (numTag[j].equals("双")) {
                        countDArr[j][countD[j]]++;
                    }
                    countX[j]++;
                    countD[j] = 0;
                    numTag[j] = "单";
                } else {
                    if (numTag[j].equals("单")) {
                        countXArr[j][countX[j]]++;
                    }
                    countD[j]++;
                    countX[j] = 0;
                    numTag[j] = "双";
                }
            }
        }

        //设置最后一期的值
        if (totalIssue > 0) {
            String number = sg.get(totalIssue - 1);
            String[] numStr = number.split(",");
            for (int i = 0; i < 10; i++) {
                int kjNum = Integer.valueOf(numStr[i]);
                if (kjNum % 2 == 1) {
                    countXArr[i][countX[i]]++;
                } else {
                    countDArr[i][countD[i]]++;
                }
            }
        }

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int j = 1; j < 19; j++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int k = 0; k < 10; k++) {
                list.add(countDArr[k][j]);
                list.add(countXArr[k][j]);
            }
            map.put(j, list);
        }
        return map;
    }

    /**
     * 北京PK10两面遗漏单双
     *
     * @param sg
     * @param postion 统计位置
     * @return
     */
    public static Map<String, ArrayList<MapIntegerVO>> noOpenLiangMianDs(List<String> sg, int postion) {
        if (sg == null) {
            return null;
        }
        int[] countDArr = new int[20]; //统计单的遗漏,最大统计连续遗漏19次
        int[] countXArr = new int[20]; //统计双的遗漏,最大统计连续遗漏19次
        int countD = 0;
        int countX = 0;
        String numTag = "";
        int totalIssue = sg.size();

        //设置第一期的值
        if (totalIssue > 0) {
            String number = sg.get(0);
            String[] numStr = number.split(",");
            int kjNum = Integer.valueOf(numStr[postion]);
            if (kjNum % 2 == 1) {
                countX++;
                countD = 0;
                numTag = "单";
            } else {
                countD++;
                countX = 0;
                numTag = "双";
            }
        }

        for (int i = 1; i < totalIssue; i++) {
            String number = sg.get(i);
            String[] numStr = number.split(",");
            int kjNum = Integer.valueOf(numStr[postion]);
            if (kjNum % 2 == 1) {
                if (numTag.equals("双")) {
                    countDArr[countD]++;
                }
                countX++;
                countD = 0;
                numTag = "单";
            } else {
                if (numTag.equals("单")) {
                    countXArr[countX]++;
                }
                countD++;
                countX = 0;
                numTag = "双";
            }
        }

        //设置最后一期的值
        if (totalIssue > 0) {
            String number = sg.get(totalIssue - 1);
            String[] numStr = number.split(",");
            int kjNum = Integer.valueOf(numStr[0]);
            if (kjNum % 2 == 1) {
                countXArr[countX]++;
            } else {
                countDArr[countD]++;
            }
        }


        HashMap<String, ArrayList<MapIntegerVO>> map = new HashMap<>();
        ArrayList<MapIntegerVO> dnumList = new ArrayList<>();
        ArrayList<MapIntegerVO> xnumList = new ArrayList<>();
        for (int j = 1; j < 20; j++) {
            if (countDArr[j] > 0) {
                dnumList.add(new MapIntegerVO(j, countDArr[j]));
            }
            if (countXArr[j] > 0) {
                xnumList.add(new MapIntegerVO(j, countXArr[j]));
            }
        }
        map.put("单", dnumList);
        map.put("双", xnumList);
        return map;
    }

    /**
     * 北京PK10冠军和路珠
     *
     * @param sg
     * @return
     */
    public static Map<String, ThereMemberListVO> luzhuG(List<String> sg) {
        if (sg == null) {
            return null;
        }
        int[] count = new int[4]; // "大", "小", "单", "双"
        ArrayList<String> daXiao = new ArrayList<>();
        ArrayList<String> danShuang = new ArrayList<>();
        String daX = "";
        String danS = "";

        int totalIssue = sg.size();
        for (int i = 0; i < totalIssue; i++) {
            String number = sg.get(i);
            String[] numberStr = number.split(",");
            int num1 = Integer.valueOf(numberStr[0]);
            int num2 = Integer.valueOf(numberStr[1]);
            int numHe = num1 + num2;
            if (numHe > 11) {
                count[0] += 1;
                if ("".equals(daX) || daX.contains("大")) {
                    daX += "大";
                } else {
                    daXiao.add(daX);
                    daX = "大";
                }
            } else if (numHe <= 11) {
                count[1] += 1;
                if ("".equals(daX) || daX.contains("小")) {
                    daX += "小";
                } else {
                    daXiao.add(daX);
                    daX = "小";
                }
            }
            if (dan(numHe)) {
                count[2] += 1;
                if ("".equals(danS) || danS.contains("单")) {
                    danS += "单";
                } else {
                    danShuang.add(danS);
                    danS = "单";
                }
            } else {
                count[3] += 1;
                if ("".equals(danS) || danS.contains("双")) {
                    danS += "双";
                } else {
                    danShuang.add(danS);
                    danS = "双";
                }
            }
        }

        HashMap<String, ThereMemberListVO> map = new HashMap<>();
        map.put("大小", new ThereMemberListVO(count[0], count[1], daXiao));
        map.put("单双", new ThereMemberListVO(count[2], count[3], danShuang));

        return map;
    }

    /**
     * 判断号码是否为单
     *
     * @param num
     * @return
     */
    public static boolean dan(int num) {
        return num % 2 == 1;
    }

    /**
     * 判断号码是否为大
     *
     * @param num
     * @return
     */
    public static boolean da(int num) {
        return num > 5;
    }


    /**
     * 判断号码是否为虎
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean hu(int num1, int num2) {
        return num1 < num2;
    }


    /**
     * 历史开奖
     *
     * @param sgs
     * @return
     */
    public static List<KjlsVO> historySg(List<TenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }
        int totalIssue = sgs.size();
        ArrayList<KjlsVO> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            TenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            ArrayList<Integer> data = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {
                data.add(Integer.valueOf(numStrArr[j]));
            }
            result.add(new KjlsVO(sg.getIssue(), sg.getTime().substring(0, 10), sg.getTime().substring(11, 16), data));
        }
        return result;
    }

    /**
     * 历史开奖
     *
     * @param sgs
     * @return
     */
    public static List<Map<String, Object>> lishiSg(List<TenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }
        int totalIssue = sgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            TenpksLotterySg sg = sgs.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());
            result.add(map);
        }
        return result;
    }

    /**
     * 统计赛果大小次数
     *
     * @param sgs
     * @return
     */
    public static TenpksCountSgdx countSgDx(List<TenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        TenpksCountSgdx countSgdx = new TenpksCountSgdx();
        TenpksLotterySg sg1 = sgs.get(0);
        countSgdx.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //大，小
        for (int i = 0; i < totalIssue; i++) {
            TenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num >= 6) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDxData(countSgdx, count);
        return countSgdx;
    }
    
    
    /**
     * 统计赛果大小次数
     *
     * @param sgs
     * @return
     */
    public static TenpksCountSgdx countSgDxTen(List<TenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        TenpksCountSgdx countSgdx = new TenpksCountSgdx();
        TenpksLotterySg sg1 = sgs.get(0);
        countSgdx.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //大，小
        for (int i = 0; i < totalIssue; i++) {
            TenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num >= 6) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDxData(countSgdx, count);
        return countSgdx;
    }
    
    /**
     * 统计赛果大小次数
     *
     * @param sgs
     * @return
     */
    public static FivetenpksCountSgdx countSgDxFive(List<FivetenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        FivetenpksCountSgdx countSgdx = new FivetenpksCountSgdx();
        FivetenpksLotterySg sg1 = sgs.get(0);
        countSgdx.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //大，小
        for (int i = 0; i < totalIssue; i++) {
            FivetenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num >= 6) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDxData(countSgdx, count);
        return countSgdx;
    }
    
    /**
     * 统计赛果大小次数
     *
     * @param sgs
     * @return
     */
    public static JstenpksCountSgdx countSgDxJs(List<JstenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        JstenpksCountSgdx countSgdx = new JstenpksCountSgdx();
        JstenpksLotterySg sg1 = sgs.get(0);
        countSgdx.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //大，小
        for (int i = 0; i < totalIssue; i++) {
            JstenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num >= 6) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDxData(countSgdx, count);
        return countSgdx;
    }

    /**
     * 统计赛果单双次数
     *
     * @param sgs
     * @return
     */
    public static TenpksCountSgds countSgDs(List<TenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        TenpksCountSgds countSgds = new TenpksCountSgds();
        TenpksLotterySg sg1 = sgs.get(0);
        countSgds.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //单，双
        for (int i = 0; i < totalIssue; i++) {
            TenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num % 2 == 1) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDsData(countSgds, count);
        return countSgds;
    }
    
    /**
     * 统计赛果单双次数
     *
     * @param sgs
     * @return
     */
    public static TentenpksCountSgds countSgDsTen(List<TentenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        TentenpksCountSgds countSgds = new TentenpksCountSgds();
        TentenpksLotterySg sg1 = sgs.get(0);
        countSgds.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //单，双
        for (int i = 0; i < totalIssue; i++) {
            TentenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num % 2 == 1) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDsData(countSgds, count);
        return countSgds;
    }
    
    /**
     * 统计赛果单双次数
     *
     * @param sgs
     * @return
     */
    public static FivetenpksCountSgds countSgDsFive(List<FivetenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        FivetenpksCountSgds countSgds = new FivetenpksCountSgds();
        FivetenpksLotterySg sg1 = sgs.get(0);
        countSgds.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //单，双
        for (int i = 0; i < totalIssue; i++) {
            FivetenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num % 2 == 1) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDsData(countSgds, count);
        return countSgds;
    }
    
    /**
     * 统计赛果单双次数
     *
     * @param sgs
     * @return
     */
    public static JstenpksCountSgds countSgDsJs(List<JstenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        JstenpksCountSgds countSgds = new JstenpksCountSgds();
        JstenpksLotterySg sg1 = sgs.get(0);
        countSgds.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[20]; //单，双
        for (int i = 0; i < totalIssue; i++) {
            JstenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numStrArr[j]);
                if (num % 2 == 1) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountDsData(countSgds, count);
        return countSgds;
    }

    /**
     * 统计赛果龙虎次数
     *
     * @param sgs
     * @return
     */
    public static TenpksCountSglh countSgLh(List<TenpksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }

        TenpksCountSglh countSglh = new TenpksCountSglh();
        TenpksLotterySg sg1 = sgs.get(0);
        countSglh.setDate(sg1.getTime().substring(0, 10));

        int totalIssue = sgs.size();
        int[] count = new int[10]; //龙,虎
        for (int i = 0; i < totalIssue; i++) {
            TenpksLotterySg sg = sgs.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            for (int j = 0; j < 5; j++) {
                int num1 = Integer.valueOf(numStrArr[j]);
                int num2 = Integer.valueOf(numStrArr[9 - j]);
                if (num1 > num2) {
                    count[(j * 2)]++;
                } else {
                    count[(j * 2 + 1)]++;
                }
            }
        }

        setCountLhData(countSglh, count);
        return countSglh;
    }
    
    /**
     * 统计赛果龙虎次数
     *
     * @param sgs
     * @return
     */
	public static TentenpksCountSglh countSgLhTen(List<TentenpksLotterySg> sgs) {
		if (sgs == null) {
			return null;
		}

		TentenpksCountSglh countSglh = new TentenpksCountSglh();
		TentenpksLotterySg sg1 = sgs.get(0);
		countSglh.setDate(sg1.getTime().substring(0, 10));

		int totalIssue = sgs.size();
		int[] count = new int[10]; // 龙,虎
		for (int i = 0; i < totalIssue; i++) {
			TentenpksLotterySg sg = sgs.get(i);
			String numStr = sg.getNumber();
			String[] numStrArr = numStr.split(",");
			for (int j = 0; j < 5; j++) {
				int num1 = Integer.valueOf(numStrArr[j]);
				int num2 = Integer.valueOf(numStrArr[9 - j]);
				if (num1 > num2) {
					count[(j * 2)]++;
				} else {
					count[(j * 2 + 1)]++;
				}
			}
		}
		setCountLhData(countSglh, count);
		return countSglh;
	}
        
        /**
         * 统计赛果龙虎次数
         *
         * @param sgs
         * @return
         */
        public static FivetenpksCountSglh countSgLhFive(List<FivetenpksLotterySg> sgs) {
            if (sgs == null) {
                return null;
            }

            FivetenpksCountSglh countSglh = new FivetenpksCountSglh();
            FivetenpksLotterySg sg1 = sgs.get(0);
            countSglh.setDate(sg1.getTime().substring(0, 10));

            int totalIssue = sgs.size();
            int[] count = new int[10]; //龙,虎
            for (int i = 0; i < totalIssue; i++) {
                FivetenpksLotterySg sg = sgs.get(i);
                String numStr = sg.getNumber();
                String[] numStrArr = numStr.split(",");
                for (int j = 0; j < 5; j++) {
                    int num1 = Integer.valueOf(numStrArr[j]);
                    int num2 = Integer.valueOf(numStrArr[9 - j]);
                    if (num1 > num2) {
                        count[(j * 2)]++;
                    } else {
                        count[(j * 2 + 1)]++;
                    }
                }
            }

        setCountLhData(countSglh, count);
        return countSglh;
    }
        
        /**
         * 统计赛果龙虎次数
         *
         * @param sgs
         * @return
         */
        public static JstenpksCountSglh countSgLhJs(List<JstenpksLotterySg> sgs) {
            if (sgs == null) {
                return null;
            }

            JstenpksCountSglh countSglh = new JstenpksCountSglh();
            JstenpksLotterySg sg1 = sgs.get(0);
            countSglh.setDate(sg1.getTime().substring(0, 10));

            int totalIssue = sgs.size();
            int[] count = new int[10]; //龙,虎
            for (int i = 0; i < totalIssue; i++) {
                JstenpksLotterySg sg = sgs.get(i);
                String numStr = sg.getNumber();
                String[] numStrArr = numStr.split(",");
                for (int j = 0; j < 5; j++) {
                    int num1 = Integer.valueOf(numStrArr[j]);
                    int num2 = Integer.valueOf(numStrArr[9 - j]);
                    if (num1 > num2) {
                        count[(j * 2)]++;
                    } else {
                        count[(j * 2 + 1)]++;
                    }
                }
            }

        setCountLhData(countSglh, count);
        return countSglh;
    }    

    /**
     * 保存北京pk10的大小统计数据
     *
     * @param countSgdx
     * @param countArr
     */
    public static void setCountDxData(TenpksCountSgdx countSgdx, int[] countArr) {
        if (countSgdx != null && countArr.length >= 20) {
            countSgdx.setOned(countArr[0]);
            countSgdx.setOnex(countArr[1]);
            countSgdx.setTwod(countArr[2]);
            countSgdx.setTwox(countArr[3]);
            countSgdx.setThreed(countArr[4]);
            countSgdx.setThreex(countArr[5]);
            countSgdx.setFourd(countArr[6]);
            countSgdx.setFourx(countArr[7]);
            countSgdx.setFived(countArr[8]);
            countSgdx.setFivex(countArr[9]);
            countSgdx.setSixd(countArr[10]);
            countSgdx.setSixx(countArr[11]);
            countSgdx.setSevend(countArr[12]);
            countSgdx.setSevenx(countArr[13]);
            countSgdx.setEightd(countArr[14]);
            countSgdx.setEightx(countArr[15]);
            countSgdx.setNightd(countArr[16]);
            countSgdx.setNightx(countArr[17]);
            countSgdx.setTend(countArr[18]);
            countSgdx.setTenx(countArr[19]);
        }
    }
    
    /**
     * 保存北京pk10的大小统计数据
     *
     * @param countSgdx
     * @param countArr
     */
    public static void setCountDxData(TentenpksCountSgdx countSgdx, int[] countArr) {
        if (countSgdx != null && countArr.length >= 20) {
            countSgdx.setOned(countArr[0]);
            countSgdx.setOnex(countArr[1]);
            countSgdx.setTwod(countArr[2]);
            countSgdx.setTwox(countArr[3]);
            countSgdx.setThreed(countArr[4]);
            countSgdx.setThreex(countArr[5]);
            countSgdx.setFourd(countArr[6]);
            countSgdx.setFourx(countArr[7]);
            countSgdx.setFived(countArr[8]);
            countSgdx.setFivex(countArr[9]);
            countSgdx.setSixd(countArr[10]);
            countSgdx.setSixx(countArr[11]);
            countSgdx.setSevend(countArr[12]);
            countSgdx.setSevenx(countArr[13]);
            countSgdx.setEightd(countArr[14]);
            countSgdx.setEightx(countArr[15]);
            countSgdx.setNightd(countArr[16]);
            countSgdx.setNightx(countArr[17]);
            countSgdx.setTend(countArr[18]);
            countSgdx.setTenx(countArr[19]);
        }
    }
    
    /**
     * 保存北京pk10的大小统计数据
     *
     * @param countSgdx
     * @param countArr
     */
    public static void setCountDxData(FivetenpksCountSgdx countSgdx, int[] countArr) {
        if (countSgdx != null && countArr.length >= 20) {
            countSgdx.setOned(countArr[0]);
            countSgdx.setOnex(countArr[1]);
            countSgdx.setTwod(countArr[2]);
            countSgdx.setTwox(countArr[3]);
            countSgdx.setThreed(countArr[4]);
            countSgdx.setThreex(countArr[5]);
            countSgdx.setFourd(countArr[6]);
            countSgdx.setFourx(countArr[7]);
            countSgdx.setFived(countArr[8]);
            countSgdx.setFivex(countArr[9]);
            countSgdx.setSixd(countArr[10]);
            countSgdx.setSixx(countArr[11]);
            countSgdx.setSevend(countArr[12]);
            countSgdx.setSevenx(countArr[13]);
            countSgdx.setEightd(countArr[14]);
            countSgdx.setEightx(countArr[15]);
            countSgdx.setNightd(countArr[16]);
            countSgdx.setNightx(countArr[17]);
            countSgdx.setTend(countArr[18]);
            countSgdx.setTenx(countArr[19]);
        }
    }
    
    /**
     * 保存北京pk10的大小统计数据
     *
     * @param countSgdx
     * @param countArr
     */
    public static void setCountDxData(JstenpksCountSgdx countSgdx, int[] countArr) {
        if (countSgdx != null && countArr.length >= 20) {
            countSgdx.setOned(countArr[0]);
            countSgdx.setOnex(countArr[1]);
            countSgdx.setTwod(countArr[2]);
            countSgdx.setTwox(countArr[3]);
            countSgdx.setThreed(countArr[4]);
            countSgdx.setThreex(countArr[5]);
            countSgdx.setFourd(countArr[6]);
            countSgdx.setFourx(countArr[7]);
            countSgdx.setFived(countArr[8]);
            countSgdx.setFivex(countArr[9]);
            countSgdx.setSixd(countArr[10]);
            countSgdx.setSixx(countArr[11]);
            countSgdx.setSevend(countArr[12]);
            countSgdx.setSevenx(countArr[13]);
            countSgdx.setEightd(countArr[14]);
            countSgdx.setEightx(countArr[15]);
            countSgdx.setNightd(countArr[16]);
            countSgdx.setNightx(countArr[17]);
            countSgdx.setTend(countArr[18]);
            countSgdx.setTenx(countArr[19]);
        }
    }

    /**
     * 保存北京pk10的单双统计数据
     *
     * @param countSgds
     * @param countArr
     */
    public static void setCountDsData(TenpksCountSgds countSgds, int[] countArr) {
        if (countSgds != null && countArr.length >= 20) {
            countSgds.setOned(countArr[0]);
            countSgds.setOnes(countArr[1]);
            countSgds.setTwod(countArr[2]);
            countSgds.setTwos(countArr[3]);
            countSgds.setThreed(countArr[4]);
            countSgds.setThrees(countArr[5]);
            countSgds.setFourd(countArr[6]);
            countSgds.setFours(countArr[7]);
            countSgds.setFived(countArr[8]);
            countSgds.setFives(countArr[9]);
            countSgds.setSixd(countArr[10]);
            countSgds.setSixs(countArr[11]);
            countSgds.setSevend(countArr[12]);
            countSgds.setSevens(countArr[13]);
            countSgds.setEightd(countArr[14]);
            countSgds.setEights(countArr[15]);
            countSgds.setNightd(countArr[16]);
            countSgds.setNights(countArr[17]);
            countSgds.setTend(countArr[18]);
            countSgds.setTens(countArr[19]);
        }
    }
    
    /**
     * 保存北京pk10的单双统计数据
     *
     * @param countSgds
     * @param countArr
     */
    public static void setCountDsData(TentenpksCountSgds countSgds, int[] countArr) {
        if (countSgds != null && countArr.length >= 20) {
            countSgds.setOned(countArr[0]);
            countSgds.setOnes(countArr[1]);
            countSgds.setTwod(countArr[2]);
            countSgds.setTwos(countArr[3]);
            countSgds.setThreed(countArr[4]);
            countSgds.setThrees(countArr[5]);
            countSgds.setFourd(countArr[6]);
            countSgds.setFours(countArr[7]);
            countSgds.setFived(countArr[8]);
            countSgds.setFives(countArr[9]);
            countSgds.setSixd(countArr[10]);
            countSgds.setSixs(countArr[11]);
            countSgds.setSevend(countArr[12]);
            countSgds.setSevens(countArr[13]);
            countSgds.setEightd(countArr[14]);
            countSgds.setEights(countArr[15]);
            countSgds.setNightd(countArr[16]);
            countSgds.setNights(countArr[17]);
            countSgds.setTend(countArr[18]);
            countSgds.setTens(countArr[19]);
        }
    }
    
    /**
     * 保存北京pk10的单双统计数据
     *
     * @param countSgds
     * @param countArr
     */
    public static void setCountDsData(FivetenpksCountSgds countSgds, int[] countArr) {
        if (countSgds != null && countArr.length >= 20) {
            countSgds.setOned(countArr[0]);
            countSgds.setOnes(countArr[1]);
            countSgds.setTwod(countArr[2]);
            countSgds.setTwos(countArr[3]);
            countSgds.setThreed(countArr[4]);
            countSgds.setThrees(countArr[5]);
            countSgds.setFourd(countArr[6]);
            countSgds.setFours(countArr[7]);
            countSgds.setFived(countArr[8]);
            countSgds.setFives(countArr[9]);
            countSgds.setSixd(countArr[10]);
            countSgds.setSixs(countArr[11]);
            countSgds.setSevend(countArr[12]);
            countSgds.setSevens(countArr[13]);
            countSgds.setEightd(countArr[14]);
            countSgds.setEights(countArr[15]);
            countSgds.setNightd(countArr[16]);
            countSgds.setNights(countArr[17]);
            countSgds.setTend(countArr[18]);
            countSgds.setTens(countArr[19]);
        }
    }
    
    /**
     * 保存北京pk10的单双统计数据
     *
     * @param countSgds
     * @param countArr
     */
    public static void setCountDsData(JstenpksCountSgds countSgds, int[] countArr) {
        if (countSgds != null && countArr.length >= 20) {
            countSgds.setOned(countArr[0]);
            countSgds.setOnes(countArr[1]);
            countSgds.setTwod(countArr[2]);
            countSgds.setTwos(countArr[3]);
            countSgds.setThreed(countArr[4]);
            countSgds.setThrees(countArr[5]);
            countSgds.setFourd(countArr[6]);
            countSgds.setFours(countArr[7]);
            countSgds.setFived(countArr[8]);
            countSgds.setFives(countArr[9]);
            countSgds.setSixd(countArr[10]);
            countSgds.setSixs(countArr[11]);
            countSgds.setSevend(countArr[12]);
            countSgds.setSevens(countArr[13]);
            countSgds.setEightd(countArr[14]);
            countSgds.setEights(countArr[15]);
            countSgds.setNightd(countArr[16]);
            countSgds.setNights(countArr[17]);
            countSgds.setTend(countArr[18]);
            countSgds.setTens(countArr[19]);
        }
    }

    /**
     * 保存北京pk10的龙虎统计数据
     *
     * @param countSglh
     * @param countArr
     */
    public static void setCountLhData(TenpksCountSglh countSglh, int[] countArr) {
        if (countSglh != null && countArr.length >= 10) {
            countSglh.setOnel(countArr[0]);
            countSglh.setOneh(countArr[1]);
            countSglh.setTwol(countArr[2]);
            countSglh.setTwoh(countArr[3]);
            countSglh.setThreel(countArr[4]);
            countSglh.setThreeh(countArr[5]);
            countSglh.setFourl(countArr[6]);
            countSglh.setFourh(countArr[7]);
            countSglh.setFivel(countArr[8]);
            countSglh.setFiveh(countArr[9]);
        }
    }
    
    /**
     * 保存北京pk10的龙虎统计数据
     *
     * @param countSglh
     * @param countArr
     */
    public static void setCountLhData(TentenpksCountSglh countSglh, int[] countArr) {
        if (countSglh != null && countArr.length >= 10) {
            countSglh.setOnel(countArr[0]);
            countSglh.setOneh(countArr[1]);
            countSglh.setTwol(countArr[2]);
            countSglh.setTwoh(countArr[3]);
            countSglh.setThreel(countArr[4]);
            countSglh.setThreeh(countArr[5]);
            countSglh.setFourl(countArr[6]);
            countSglh.setFourh(countArr[7]);
            countSglh.setFivel(countArr[8]);
            countSglh.setFiveh(countArr[9]);
        }
    }
    
    /**
     * 保存北京pk10的龙虎统计数据
     *
     * @param countSglh
     * @param countArr
     */
    public static void setCountLhData(FivetenpksCountSglh countSglh, int[] countArr) {
        if (countSglh != null && countArr.length >= 10) {
            countSglh.setOnel(countArr[0]);
            countSglh.setOneh(countArr[1]);
            countSglh.setTwol(countArr[2]);
            countSglh.setTwoh(countArr[3]);
            countSglh.setThreel(countArr[4]);
            countSglh.setThreeh(countArr[5]);
            countSglh.setFourl(countArr[6]);
            countSglh.setFourh(countArr[7]);
            countSglh.setFivel(countArr[8]);
            countSglh.setFiveh(countArr[9]);
        }
    }
    
    /**
     * 保存北京pk10的龙虎统计数据
     *
     * @param countSglh
     * @param countArr
     */
    public static void setCountLhData(JstenpksCountSglh countSglh, int[] countArr) {
        if (countSglh != null && countArr.length >= 10) {
            countSglh.setOnel(countArr[0]);
            countSglh.setOneh(countArr[1]);
            countSglh.setTwol(countArr[2]);
            countSglh.setTwoh(countArr[3]);
            countSglh.setThreel(countArr[4]);
            countSglh.setThreeh(countArr[5]);
            countSglh.setFourl(countArr[6]);
            countSglh.setFourh(countArr[7]);
            countSglh.setFivel(countArr[8]);
            countSglh.setFiveh(countArr[9]);
        }
    }


    public static TenpksKillNumber getKillNumber(String issue) {
        TenpksKillNumber tenpksKillNumber = new TenpksKillNumber();
        tenpksKillNumber.setIssue(issue);
        String time = DateUtils.formatDate(new Date(), "HH:mm");
        if ("23:50".equals(time)) {
            time = "09:30:00";
        } else {
            time = DateUtils.formatDate(DateUtils.getMinuteAfter(new Date(), 5), DateUtils.timePattern);
        }
        tenpksKillNumber.setTime(time);
        tenpksKillNumber.setOne(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTwo(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setThere(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFour(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFive(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSix(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSeven(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setEight(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setNine(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTen(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setCreateTime(TimeHelper.date());
        return tenpksKillNumber;
    }
    
    public static TentenpksKillNumber getTenKillNumber(String issue) {
    	TentenpksKillNumber tenpksKillNumber = new TentenpksKillNumber();
    	tenpksKillNumber.setIssue(issue);
        String time = DateUtils.formatDate(new Date(), "HH:mm");
        if ("23:50".equals(time)) {
            time = "00:00:00";
        } else {
            time = DateUtils.formatDate(DateUtils.getMinuteAfter(new Date(), 10), DateUtils.timePattern);
        }
        tenpksKillNumber.setTime(time);
        tenpksKillNumber.setOne(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTwo(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setThere(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFour(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFive(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSix(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSeven(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setEight(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setNine(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTen(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setCreateTime(TimeHelper.date());
        return tenpksKillNumber;
    }
    
    public static FivetenpksKillNumber getFiveKillNumber(String issue) {
    	FivetenpksKillNumber tenpksKillNumber = new FivetenpksKillNumber();
        tenpksKillNumber.setIssue(issue);
        String time = DateUtils.formatDate(new Date(), "HH:mm");
        if ("23:55".equals(time)) {
            time = "00:00:00";
        } else {
            time = DateUtils.formatDate(DateUtils.getMinuteAfter(new Date(), 5), DateUtils.timePattern);
        }
        tenpksKillNumber.setTime(time);
        tenpksKillNumber.setOne(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTwo(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setThere(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFour(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFive(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSix(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSeven(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setEight(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setNine(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTen(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setCreateTime(TimeHelper.date());
        return tenpksKillNumber;
    }
    
    public static JstenpksKillNumber getJsKillNumber(String issue) {
    	JstenpksKillNumber tenpksKillNumber = new JstenpksKillNumber();
        tenpksKillNumber.setIssue(issue);
        String time = DateUtils.formatDate(new Date(), "HH:mm");
        if ("23:59".equals(time)) {
            time = "00:00:00";
        } else {
            time = DateUtils.formatDate(DateUtils.getMinuteAfter(new Date(), 1), DateUtils.timePattern);
        }
        tenpksKillNumber.setTime(time);
        tenpksKillNumber.setOne(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTwo(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setThere(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFour(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setFive(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSix(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setSeven(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setEight(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setNine(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setTen(RandomUtils.getTenpksKillNumber());
        tenpksKillNumber.setCreateTime(TimeHelper.date());
        return tenpksKillNumber;
    }

    /**
     * 公式杀号
     */
    public static Map<String, Object> killNumber(List<TenpksKillNumber> killNumbers, Integer position) {
        Map<String, Object> result = new HashMap<>();
        // 判空
        if (CollectionUtils.isEmpty(killNumbers)) {
            return result;
        }

        // 已经开奖的期数
        int total = killNumbers.size();
        // 定义杀号VO列表
        ArrayList<KillNumberVO> killNumberVOs = new ArrayList<>();

        int[] winCount = new int[5]; // 杀中数
        int[] maxCount = new int[5]; // 最大连中
        int[] currCount = new int[5]; // 当前连中

        // 遍历杀号列表
        KillNumberVO vo;
        for (int i = killNumbers.size() - 1; i >= 0; i--) {
            TenpksKillNumber killNumber = killNumbers.get(i);
            vo = new KillNumberVO();
            vo.setIssue(killNumber.getIssue());
            vo.setTime(killNumber.getTime());

            String killNum;
            switch (position) {
                case 1:
                    killNum = killNumber.getOne();
                    break;
                case 2:
                    killNum = killNumber.getTwo();
                    break;
                case 3:
                    killNum = killNumber.getThere();
                    break;
                case 4:
                    killNum = killNumber.getFour();
                    break;
                case 5:
                    killNum = killNumber.getFive();
                    break;
                case 6:
                    killNum = killNumber.getSix();
                    break;
                case 7:
                    killNum = killNumber.getSeven();
                    break;
                case 8:
                    killNum = killNumber.getEight();
                    break;
                case 9:
                    killNum = killNumber.getNine();
                    break;
                default:
                    killNum = killNumber.getTen();
                    break;
            }
            // 将杀号拆分开
            String[] numByTypes = killNum.split(",");
            vo.setSin(numByTypes[0]);
            vo.setSec(numByTypes[1]);
            vo.setCos(numByTypes[2]);
            vo.setCot(numByTypes[3]);
            vo.setTan(numByTypes[4]);
            // 开奖号码
            String number = killNumber.getNumber();
            // 判断是否已开奖
            if (StringUtils.isBlank(number)) {
                total--;
                killNumberVOs.add(vo);
                continue;
            }
            vo.setSgNumber(number);
            String[] openNums = number.split(",");
            String openNum = openNums[position - 1];
            vo.setSgNum(openNum);
            killNumberVOs.add(vo);

            // 统计
            Integer openNumber = Integer.valueOf(openNum);
            for (int j = 0; j < 5; j++) {
                if (openNumber.equals(Integer.valueOf(numByTypes[j]))) {
                    currCount[j] = 0;
                } else {
                    winCount[j]++;
                    currCount[j]++;
                    maxCount[j] = currCount[j] > maxCount[j] ? currCount[j] : maxCount[j];
                }
            }
        }

        // 封装数据
        ArrayList<Integer> winList = new ArrayList<>(); // 准确率
        ArrayList<Integer> currList = new ArrayList<>(); // 当前连中
        ArrayList<Integer> maxList = new ArrayList<>(); // 最大连中

        for (int j = 0; j < 5; j++) {
            // 计算准确率
            winList.add(winCount[j] * 100 / (total == 0 ? 1 : total));
            // 当前连中
            currList.add(currCount[j]);
            // 最大连中
            maxList.add(maxCount[j]);
        }

        // 排序
        Collections.sort(killNumberVOs);

        result.put("win", winList);
        result.put("max", maxList);
        result.put("current", currList);
        result.put("killList", killNumberVOs);
        return result;
    }

    /**
     * 期次详情
     *
     * @param sg
     * @return
     */
    public static Map<String, Object> sgDetails(TenpksLotterySg sg) {
        if (sg == null) {
            return null;
        }
        String number = sg.getNumber();
        String[] numArr = number.split(",");
        int one = Integer.valueOf(numArr[0]);
        int two = Integer.valueOf(numArr[1]);
        int three = Integer.valueOf(numArr[2]);
        int four = Integer.valueOf(numArr[3]);
        int five = Integer.valueOf(numArr[4]);
        int six = Integer.valueOf(numArr[5]);
        int seven = Integer.valueOf(numArr[6]);
        int eight = Integer.valueOf(numArr[7]);
        int nine = Integer.valueOf(numArr[8]);
        int ten = Integer.valueOf(numArr[9]);
        Map<String, Object> map = new HashMap<>();
        map.put("issue", sg.getIssue());
        map.put("number", number);
        int he = one + two;
        String guanyahe;
        if (he > 11) {
            guanyahe = "大";
        } else {
            guanyahe = "小";
        }
        if (he % 2 == 1) {
            guanyahe += ",单";
        } else {
            guanyahe += ",双";
        }
        map.put("guanyahelm", he);
        map.put("guanlm", sglm(one, ten));
        map.put("yalm", sglm(two, nine));
        map.put("threelm", sglm(three, eight));
        map.put("fourlm", sglm(four, seven));
        map.put("fivelm", sglm(five, six));
        map.put("sixlm", sglm(six, 0));
        map.put("sevenlm", sglm(seven, 0));
        map.put("eightlm", sglm(eight, 0));
        map.put("ninelm", sglm(nine, 0));
        map.put("tenlm", sglm(ten, 0));

        map.put("guanyahe", guanyahe);
        map.put("guan", one);
        map.put("ya", two);
        map.put("three", three);
        map.put("four", four);
        map.put("five", five);
        map.put("six", six);
        map.put("seven", seven);
        map.put("eight", eight);
        map.put("nine", nine);
        map.put("ten", ten);
        map.put("qianwu", one + "," + two + "," + three + "," + four + "," + five);
        map.put("houwu", six + "," + seven + "," + eight + "," + nine + "," + ten);

        return map;
    }

    private static String sglm(int num1, int num2) {
        String threelm;
        if (num1 > 5) {
            threelm = "大";
        } else {
            threelm = "小";
        }
        if (num1 % 2 == 1) {
            threelm += ",单";
        } else {
            threelm += ",双";
        }
        if (num2 > 0) {
            if (num1 > num2) {
                threelm += ",龙";
            } else {
                threelm += ",虎";
            }
        }
        return threelm;
    }

    /**
     * 获取北京PK10下一期的开奖时间的毫秒值
     *
     * @return
     */
    public static long nextIssueTime() {
        String date = TimeHelper.date("yyyy-MM-dd");
        String oneIssueTime = date + " 09:30:00"; // 获取今天第一期开奖时间
        long oneIssue = TimeHelper.str2time(oneIssueTime, true);
        String lastIssueTime = date + " 23:50:00"; // 获取今天最后一期开奖时间
        long lastIssue = TimeHelper.str2time(lastIssueTime, true);

        long nowTime = System.currentTimeMillis();
        if (nowTime < oneIssue) {
            return oneIssue;
        } else if (nowTime >= lastIssue) {
            return oneIssue + 86400 * 1000L;
        } else {
            long count = (nowTime - oneIssue) / 1000 / 60 / 20;
            return oneIssue + (count + 1) * 20 * 60 * 1000;
        }
    }


    /**
     * 获取北京PK10下一期的期号
     *
     * @return
     */
    public static int nextIssue(String time, String issue) {
        long sgTime = TimeHelper.str2time(time, true);
        long nextIssueTime = TenpksUtils.nextIssueTime();
        long count = (nextIssueTime - sgTime) / 1000 / 60;
        int nextIssue = Integer.valueOf(issue) + 1;
        if (count > 20) {
            String date = TimeHelper.date("yyyy-MM-dd");
            String oneIssueTime = date + " 09:30:00"; // 获取今天第一期开奖时间
            long oneIssue = TimeHelper.str2time(oneIssueTime, true);
            long nowTime = System.currentTimeMillis();
            if (nowTime >= oneIssue) {
                nextIssue++;
            }
        }
        return nextIssue;
    }
    
    

    /**
     * 对应名次的所有车号的当前遗漏值
     *
     * @param sg    倒序的赛果
     * @param start 第几名,从这个名次开始
     * @param end   第几名
     * @return
     */
    public static Map<String, SscMissNumDTO> queryMissVal(List<TenpksLotterySg> sg, Integer start, Integer end) {
        Map<String, SscMissNumDTO> map = new HashMap<>();
        if (sg == null) {
            return map;
        }

        int totalIssue = sg.size();
        Integer[][] noOpenArr = new Integer[10][10]; //统计遗漏次数

        for (int i = 0; i < totalIssue; i++) {
            String number = sg.get(i).getNumber();
            String[] numberStr = number.split(",");
            for (int j = 0; j < 10; j++) {
                int num = Integer.valueOf(numberStr[j]);
                //统计遗漏次数
                if (noOpenArr[j][num - 1] == null) {
                    noOpenArr[j][num - 1] = i;
                }
            }
        }

        //设置一直没出现过的号码的遗漏值为总期数
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                if (noOpenArr[j][k] == null) {
                    noOpenArr[j][k] = totalIssue;
                }
            }
        }

        int index = start - 1;
        for (int k = index; k < end; k++) {
            SscMissNumDTO sscMissNumDTO = new SscMissNumDTO();
            Integer[] noOpenIndexArr = noOpenArr[k];
            sscMissNumDTO.setMissing0(noOpenIndexArr[0]);
            sscMissNumDTO.setMissing1(noOpenIndexArr[1]);
            sscMissNumDTO.setMissing2(noOpenIndexArr[2]);
            sscMissNumDTO.setMissing3(noOpenIndexArr[3]);
            sscMissNumDTO.setMissing4(noOpenIndexArr[4]);
            sscMissNumDTO.setMissing5(noOpenIndexArr[5]);
            sscMissNumDTO.setMissing6(noOpenIndexArr[6]);
            sscMissNumDTO.setMissing7(noOpenIndexArr[7]);
            sscMissNumDTO.setMissing8(noOpenIndexArr[8]);
            sscMissNumDTO.setMissing9(noOpenIndexArr[9]);
            map.put(k + 1 + "", sscMissNumDTO);
        }
        return map;
    }

    /**
     * 统计所有名次两面的遗漏值
     *
     * @param sg 倒序的赛果
     * @return
     */
    public static Map<String, TenpksLiangMian> queryLiangMianMissVal(List<TenpksLotterySg> sg) {
        Map<String, TenpksLiangMian> map = new HashMap<>();
        if (sg == null) {
            return map;
        }

        Integer[] arr = new Integer[54]; // 冠亚和(1*4) + 1-5名(5*6) + 6-10名(5*4) = 54
        int totalIssue = sg.size();
        for (int i = 0; i < totalIssue; i++) {
            String number = sg.get(i).getNumber();
            String[] numberStr = number.split(",");
            int num1 = Integer.valueOf(numberStr[0]);
            int num2 = Integer.valueOf(numberStr[1]);
            int num3 = Integer.valueOf(numberStr[2]);
            int num4 = Integer.valueOf(numberStr[3]);
            int num5 = Integer.valueOf(numberStr[4]);
            int num6 = Integer.valueOf(numberStr[5]);
            int num7 = Integer.valueOf(numberStr[6]);
            int num8 = Integer.valueOf(numberStr[7]);
            int num9 = Integer.valueOf(numberStr[8]);
            int num10 = Integer.valueOf(numberStr[9]);
            //冠亚和的两面统计
            int numHe = num1 + num2;
            if (numHe > 11) {
                if (arr[0] == null) {
                    arr[0] = i;
                }
            } else if (numHe <= 11) {
                if (arr[1] == null) {
                    arr[1] = i;
                }
            }
            if (dan(numHe)) {
                if (arr[2] == null) {
                    arr[2] = i;
                }
            } else {
                if (arr[3] == null) {
                    arr[3] = i;
                }
            }

            //1-5名次的统计
            //冠军统计
            if (da(num1)) {
                if (arr[4] == null) {
                    arr[4] = i;
                }
            } else {
                if (arr[5] == null) {
                    arr[5] = i;
                }
            }
            if (dan(num1)) {
                if (arr[6] == null) {
                    arr[6] = i;
                }
            } else {
                if (arr[7] == null) {
                    arr[7] = i;
                }
            }
            if (num1 > num10) {
                if (arr[8] == null) {
                    arr[8] = i;
                }
            } else {
                if (arr[9] == null) {
                    arr[9] = i;
                }
            }
            //亚军统计
            if (da(num2)) {
                if (arr[10] == null) {
                    arr[10] = i;
                }
            } else {
                if (arr[11] == null) {
                    arr[11] = i;
                }
            }
            if (dan(num2)) {
                if (arr[12] == null) {
                    arr[12] = i;
                }
            } else {
                if (arr[13] == null) {
                    arr[13] = i;
                }
            }
            if (num2 > num9) {
                if (arr[14] == null) {
                    arr[14] = i;
                }
            } else {
                if (arr[15] == null) {
                    arr[15] = i;
                }
            }
            //第三名统计
            if (da(num3)) {
                if (arr[16] == null) {
                    arr[16] = i;
                }
            } else {
                if (arr[17] == null) {
                    arr[17] = i;
                }
            }
            if (dan(num3)) {
                if (arr[18] == null) {
                    arr[18] = i;
                }
            } else {
                if (arr[19] == null) {
                    arr[19] = i;
                }
            }
            if (num3 > num8) {
                if (arr[20] == null) {
                    arr[20] = i;
                }
            } else {
                if (arr[21] == null) {
                    arr[21] = i;
                }
            }
            //第四名统计
            if (da(num4)) {
                if (arr[22] == null) {
                    arr[22] = i;
                }
            } else {
                if (arr[23] == null) {
                    arr[23] = i;
                }
            }
            if (dan(num4)) {
                if (arr[24] == null) {
                    arr[24] = i;
                }
            } else {
                if (arr[25] == null) {
                    arr[25] = i;
                }
            }
            if (num4 > num7) {
                if (arr[26] == null) {
                    arr[26] = i;
                }
            } else {
                if (arr[27] == null) {
                    arr[27] = i;
                }
            }
            //第五名统计
            if (da(num5)) {
                if (arr[28] == null) {
                    arr[28] = i;
                }
            } else {
                if (arr[29] == null) {
                    arr[29] = i;
                }
            }
            if (dan(num5)) {
                if (arr[30] == null) {
                    arr[30] = i;
                }
            } else {
                if (arr[31] == null) {
                    arr[31] = i;
                }
            }
            if (num5 > num6) {
                if (arr[32] == null) {
                    arr[32] = i;
                }
            } else {
                if (arr[33] == null) {
                    arr[33] = i;
                }
            }

            //第6-10名统计
            //第六名统计
            if (da(num6)) {
                if (arr[34] == null) {
                    arr[34] = i;
                }
            } else {
                if (arr[35] == null) {
                    arr[35] = i;
                }
            }
            if (dan(num6)) {
                if (arr[36] == null) {
                    arr[36] = i;
                }
            } else {
                if (arr[37] == null) {
                    arr[37] = i;
                }
            }
            //第七名统计
            if (da(num7)) {
                if (arr[38] == null) {
                    arr[38] = i;
                }
            } else {
                if (arr[39] == null) {
                    arr[39] = i;
                }
            }
            if (dan(num7)) {
                if (arr[40] == null) {
                    arr[40] = i;
                }
            } else {
                if (arr[41] == null) {
                    arr[41] = i;
                }
            }
            //第八名统计
            if (da(num8)) {
                if (arr[42] == null) {
                    arr[42] = i;
                }
            } else {
                if (arr[43] == null) {
                    arr[43] = i;
                }
            }
            if (dan(num8)) {
                if (arr[44] == null) {
                    arr[44] = i;
                }
            } else {
                if (arr[45] == null) {
                    arr[45] = i;
                }
            }
            //第九名统计
            if (da(num9)) {
                if (arr[46] == null) {
                    arr[46] = i;
                }
            } else {
                if (arr[47] == null) {
                    arr[47] = i;
                }
            }
            if (dan(num9)) {
                if (arr[48] == null) {
                    arr[48] = i;
                }
            } else {
                if (arr[49] == null) {
                    arr[49] = i;
                }
            }
            //第十名统计
            if (da(num10)) {
                if (arr[50] == null) {
                    arr[50] = i;
                }
            } else {
                if (arr[51] == null) {
                    arr[51] = i;
                }
            }
            if (dan(num10)) {
                if (arr[52] == null) {
                    arr[52] = i;
                }
            } else {
                if (arr[53] == null) {
                    arr[53] = i;
                }
            }
        }

        map.put("0", new TenpksLiangMian(arr[0], arr[1], arr[2], arr[3]));
        for (int j = 1; j < 6; j++) {
            int k = 6 * j - 2;
            map.put("" + j, new TenpksLiangMian(arr[k], arr[k + 1], arr[k + 2], arr[k + 3], arr[k + 4], arr[k + 5]));
        }
        for (int j = 6; j < 11; j++) {
            int k = 4 * j + 10;
            map.put("" + j, new TenpksLiangMian(arr[k], arr[k + 1], arr[k + 2], arr[k + 3]));
        }
        return map;
    }


    /**
     * 澳洲PK10获取遗漏值
     *
     * @param i              第一重循环的当前循环变量
     * @param bjpksLotterySg 开奖结果集
     * @param num            码号（3-19）
     * @return
     */
    public static Integer getTenpksMissingCount(int i, List<TenpksLotterySg> tenpksLotterySg, int num) {
        int number = 0;
        for (int j = i; j < 200 && j < tenpksLotterySg.size(); j++) {
            //开奖号码
            String[] split = tenpksLotterySg.get(j).getNumber().split(",");
            Integer regionNumber = Integer.parseInt(split[0]) + Integer.parseInt(split[1]);

            //记录相等的号码
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
     * 统计开出的总次数
     *
     * @param bjpksLotterySg 开奖结果集
     * @param number         码号（3-19）
     * @return
     */
    public static Integer getSumOpenCount(List<TenpksLotterySg> tenpksLotterySg, int number) {
        int count = 0;
        for (int i = 0; i < tenpksLotterySg.size(); i++) {
            //开奖号码
            String[] split = tenpksLotterySg.get(i).getNumber().split(",");
            Integer guanyahe = Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
            if (guanyahe.equals(number)) {
                count++;
            }
        }
        return count;
    }


    /**
     * 统计没有开出的总次数
     *
     * @param bjpksLotterySg 开奖结果集
     * @param number         码号（3-19）
     * @return
     */
    public static Integer getNoOutOpenCount(List<TenpksLotterySg> tenpksLotterySg, int number) {
        int count = 0;
        for (int i = 0; i < tenpksLotterySg.size(); i++) {
            //开奖号码
            String[] split = tenpksLotterySg.get(i).getNumber().split(",");
            Integer guanyahe = Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
            if (!guanyahe.equals(number)) {
                count++;
            }
        }
        return count;
    }


    /**
     * 统计大小单双，3-19出现的总次数
     *
     * @param sgList
     * @return
     */
    public static Map<String, Object> queryTotal(List<TenpksLotterySg> sgList) {
        if (sgList == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();

        int singleTotal = 0;
        int twoCountTotal = 0;
        int bigCountTotal = 0;
        int smallCountTotal = 0;

        int[] openArr = new int[17]; //统计出现次数

        for (TenpksLotterySg list : sgList) {
            String[] number = list.getNumber().split(",");
            int guanYahe = Integer.parseInt(number[0]) + Integer.parseInt(number[1]);

            //统计出现次数
            openArr[guanYahe - 3] += 1;

            //计算单双、大小、次数
            if (guanYahe % 2 == 0 && guanYahe > 11) {
                bigCountTotal++;
                twoCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe > 11) {
                bigCountTotal++;
                singleTotal++;
            } else if (guanYahe % 2 == 0 && guanYahe <= 11) {
                smallCountTotal++;
                twoCountTotal++;
            } else if (guanYahe % 2 != 0 && guanYahe <= 11) {
                smallCountTotal++;
                singleTotal++;
            }

        }
        TenpksGuanYaHeCountDTO heCount = new TenpksGuanYaHeCountDTO();
        heCount.setBig(bigCountTotal);
        heCount.setSmall(smallCountTotal);
        heCount.setSingle(singleTotal);
        heCount.setTwo(twoCountTotal);

        result.put("total", heCount);
        result.put("openArr", openArr);
        return result;
    }

}
