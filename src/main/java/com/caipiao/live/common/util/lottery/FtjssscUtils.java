package com.caipiao.live.common.util.lottery;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.mybatis.entity.FtjssscLotterySg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  Class Name: FtjssscUtils.java
 *  Description: 
 *  @author hp  DateTime 2019年3月30日 下午11:30:17 
 *  @company CPT 
 *  @email hu.peng@bvit.com.cn  
 *  @version 1.0
 */
public class FtjssscUtils {
	
	
	/**
	 *  Description:赛果历史
	 *  @author hp  DateTime 2019年3月30日 下午11:30:53
	 *  @param
	 *  @return
	 */
	public static List<Map<String, Object>> lishiSg(List<FtjssscLotterySg> ftjssscLotterySgList) {
        if (CollectionUtils.isEmpty(ftjssscLotterySgList)) {
            return null;
        }
        int totalIssue = ftjssscLotterySgList.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
        	FtjssscLotterySg sg = ftjssscLotterySgList.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put("time", sg.getTime());
            map.put("number", sg.getNumber());
            map.put("numberstr", removeComma(sg.getNumber()));
            result.add(map);
        }
        return result;
    }
	
	public static String removeComma(String numberstr) {
		String noCommaString = Constants.DEFAULT_NULL;
		if(StringUtils.isEmpty(numberstr)) {
			return noCommaString;
		}
		
		noCommaString = numberstr.replace(",", "");
		return noCommaString;
	}

}
