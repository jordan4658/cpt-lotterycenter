package com.caipiao.live.common.util.lottery;



import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.mybatis.entity.Fc7lcLotterySg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fc7lcUtils {

	public static List<KjlsVO> historySg(List<Fc7lcLotterySg> fc7lcLotterySgList) {
        if (fc7lcLotterySgList == null) {
            return null;
        }
        int totalIssue = fc7lcLotterySgList.size();
        ArrayList<KjlsVO> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            Fc7lcLotterySg sg = fc7lcLotterySgList.get(i);
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

	public static List<Map<String, Object>> lishiSg(List<Fc7lcLotterySg> fc7lcLotterySgs) {
        if (fc7lcLotterySgs == null) {
            return null;
        }
        int totalIssue = fc7lcLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            Fc7lcLotterySg sg = fc7lcLotterySgs.get(i);
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
