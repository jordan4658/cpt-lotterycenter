package com.caipiao.live.common.util.lottery;



import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.mybatis.entity.TcdltLotterySg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TcdltUtils {

	public static List<KjlsVO> historySg(List<TcdltLotterySg> tcdltLotterySgList) {
        if (tcdltLotterySgList == null) {
            return null;
        }
        int totalIssue = tcdltLotterySgList.size();
        ArrayList<KjlsVO> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            TcdltLotterySg sg = tcdltLotterySgList.get(i);
            String numStr = sg.getNumber();
            String[] numStrArr = numStr.split(",");
            ArrayList<Integer> data = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {
                data.add(Integer.valueOf(numStrArr[j]));
            }
            result.add(new KjlsVO(sg.getIssue(), sg.getDate(), sg.getTime().substring(11, 16), data));
        }
        return result;
    }

	public static List<Map<String, Object>> lishiSg(List<TcdltLotterySg> tcdltLotterySgs) {
        if (tcdltLotterySgs == null) {
            return null;
        }
        int totalIssue = tcdltLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            TcdltLotterySg sg = tcdltLotterySgs.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());
            result.add(map);
        }
        return result;
    }

}
