package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.constant.LotteryInformationType;

import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.model.vo.lottery.ThereMemberListVO;
import com.caipiao.live.common.mybatis.entity.AuspksLotterySg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AzPrixSgUtils {
	
	public static final String FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	
    /**
     * AOZOU历史开奖
     *
     * @param sgs
     * @return
     */
    public static List<KjlsVO> historySg(List<AuspksLotterySg> sgs) {
        if (sgs == null) {
            return null;
        }
        int totalIssue = sgs.size();
        ArrayList<KjlsVO> result = new ArrayList<KjlsVO>();
        for (int i = 0; i < totalIssue; i++) {
        	AuspksLotterySg sg = sgs.get(i);
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
     * 澳洲F1前后路珠
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
        String[] sigleNum = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayList<ArrayList<String>> lists = new ArrayList<>(10);
        for (int j = 0; j < 10; j++) {
            lists.add(new ArrayList<String>());
        }

        int totalIssue = sg.size();
        for (int i = 0; i < totalIssue; i++) {
        	
            String number = sg.get(i);
            
            for (int k = 0; k < 10; k++) {
                if (number.indexOf(sigleNum[k]) < 14) {
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
     * 澳洲F1两面路珠
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

        if (LotteryInformationType.AUZPKS_LMLZ_DX.equals(countType)) {
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
        } else if (LotteryInformationType.AUZPKS_LMLZ_DS.equals(countType)) {
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

        } else if (LotteryInformationType.AUZPKS_LMLZ_LH.equals(countType)) {
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
        if (LotteryInformationType.BJPKS_LMLZ_LH.equals(countType)) {
            len = 5;
        }
        for (int x = 0; x < len; x++) {
            map.put(num[x], new ThereMemberListVO(count[(x * 2)], count[(x * 2 + 1)], lists.get(x)));
        }
        return map;
    }

}
