package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.mybatis.entity.FtjspksLotterySg;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  Class Name: FtjspksUtils.java
 *  Description: 
 *  @author hp  DateTime 2019年3月30日 下午11:36:30 
 *  @company bvit 
 *  @email hu.peng@bvit.com.cn  
 *  @version 1.0
 */
public class FtjspksUtils {
	
	
	
	/**
	 *  Description:
	 *  @author hp  DateTime 2019年3月30日 下午11:35:55
	 *  @param ftjspksLotterySgList
	 *  @return
	 */
	public static List<Map<String, Object>> lishiSg(List<FtjspksLotterySg> ftjspksLotterySgList) {
        if (CollectionUtils.isEmpty(ftjspksLotterySgList)) {
            return null;
        }
        int totalIssue = ftjspksLotterySgList.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
        	FtjspksLotterySg sg = ftjspksLotterySgList.get(i);
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
