package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.mybatis.entity.FtxyftLotterySg;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  Class Name: FtxyftUtils.java
 *  Description: 对象翻转工具类
 *  @author hp  DateTime 2019年3月30日 下午11:24:37 
 *  @company CPT 
 *  @email 
 *  @version 1.0
 */
public class FtxyftUtils {
	
	
	/**
	 *  Description: 赛果历史
	 *  @author hp  DateTime 2019年3月30日 下午11:30:33
	 *  @param ftxyftLotterySgList
	 *  @return
	 */
	public static List<Map<String, Object>> lishiSg(List<FtxyftLotterySg> ftxyftLotterySgList) {
        if (CollectionUtils.isEmpty(ftxyftLotterySgList)) {
            return null;
        }
        int totalIssue = ftxyftLotterySgList.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
        	FtxyftLotterySg sg = ftxyftLotterySgList.get(i);
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
