package com.caipiao.live.common.util.lottery;


import com.caipiao.live.common.model.dto.result.SscMissNumDTO;
import com.caipiao.live.common.mybatis.entity.TxffcLotterySg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TxffcSgUtils {
    private static final Logger logger = LoggerFactory.getLogger(TxffcSgUtils.class);
    /**
     * 获取遗漏值（组选）
     * @param list 赛果集合
     * @return
     */
    public static SscMissNumDTO queryGroupMissVal(List<TxffcLotterySg> list) {
        SscMissNumDTO dto = new SscMissNumDTO();
        dto.setMissing0(countMissVal(list, 0));
        dto.setMissing1(countMissVal(list, 1));
        dto.setMissing2(countMissVal(list, 2));
        dto.setMissing3(countMissVal(list, 3));
        dto.setMissing4(countMissVal(list, 4));
        dto.setMissing5(countMissVal(list, 5));
        dto.setMissing6(countMissVal(list, 6));
        dto.setMissing7(countMissVal(list, 7));
        dto.setMissing8(countMissVal(list, 8));
        dto.setMissing9(countMissVal(list, 9));
        return dto;
    }

    /**
     * 计算指定数字组选遗漏次数
     * @param list 赛果
     * @param num 数字
     * @return
     */
    private static Integer countMissVal(List<TxffcLotterySg> list, Integer num) {
        Integer count = 0;
        for (TxffcLotterySg sg : list) {
            if (num.equals(sg.getGe()) || num.equals(sg.getShi()) || num.equals(sg.getBai()) || num.equals(sg.getQian()) || num.equals(sg.getWan())) {
                break;
            } else {
                count ++;
            }
        }
        return count;
    }

    /**
     * 计算指定位置数字组选遗漏次数
     * @param txffcLotterySgs 赛果
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public static Map<String, SscMissNumDTO> queryMissVal(List<TxffcLotterySg> txffcLotterySgs, Integer start, Integer end) {
        Map<String, SscMissNumDTO> map = new HashMap<>();
        for (int i = start; i <= end; i++) {
            switch (i) {
                case 1 :
                    map.put("wan", countMissValByWei(txffcLotterySgs, i));
                    break;

                case 2 :
                    map.put("qian", countMissValByWei(txffcLotterySgs, i));
                    break;

                case 3 :
                    map.put("bai", countMissValByWei(txffcLotterySgs, i));
                    break;

                case 4 :
                    map.put("shi", countMissValByWei(txffcLotterySgs, i));
                    break;

                default :
                    map.put("ge", countMissValByWei(txffcLotterySgs, i));
                    break;
            }
        }
        return map;
    }

    /**
     * 计算指定数字组选遗漏次数
     * @param list 赛果
     * @param wei 位置
     * @return
     */
    private static SscMissNumDTO countMissValByWei(List<TxffcLotterySg> list, Integer wei) {
        SscMissNumDTO dto = new SscMissNumDTO();
        for (int i = 0; i < 10; i++) {
            Integer count = 0;
            for (TxffcLotterySg sg : list) {
                boolean bool = false;
                switch (wei) {
                    case 1 :
                        logger.info(sg.getIssue()+"========="+sg.getWan()+"=========="+i+"============="+sg.getWan().equals(i));
                        if (sg.getWan().equals(i)) {
                            bool = true;
                            break;
                        } else {
                            count ++;
                        }
                        break;

                    case 2 :
                        if (sg.getQian().equals(i)) {
                            bool = true;
                            break;
                        } else {
                            count ++;
                        }
                        break;

                    case 3 :
                        if (sg.getBai().equals(i)) {
                            bool = true;
                            break;
                        } else {
                            count ++;
                        }
                        break;

                    case 4 :
                        if (sg.getShi().equals(i)) {
                            bool = true;
                            break;
                        } else {
                            count ++;
                        }
                        break;

                    default :
                        if (sg.getGe().equals(i)) {
                            bool = true;
                            break;
                        } else {
                            count ++;
                        }
                        break;
                }
                if (bool) {
                    break;
                }
            }
            switch (i) {
                case 0 :
                    dto.setMissing0(count);
                    break;

                case 1 :
                    dto.setMissing1(count);
                    break;

                case 2 :
                    dto.setMissing2(count);
                    break;

                case 3 :
                    dto.setMissing3(count);
                    break;

                case 4 :
                    dto.setMissing4(count);
                    break;

                case 5 :
                    dto.setMissing5(count);
                    break;

                case 6 :
                    dto.setMissing6(count);
                    break;

                case 7 :
                    dto.setMissing7(count);
                    break;

                case 8 :
                    dto.setMissing8(count);
                    break;

                default :
                    dto.setMissing9(count);
                    break;
            }
        }
        return dto;
    }
}
