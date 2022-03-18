package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.model.vo.MapListVO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
}
