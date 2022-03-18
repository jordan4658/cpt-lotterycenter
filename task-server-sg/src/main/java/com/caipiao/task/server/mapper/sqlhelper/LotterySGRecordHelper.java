package com.caipiao.task.server.mapper.sqlhelper;

import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.config.LotterySGRecordConfig;
import com.caipiao.task.server.req.LotterySGRecordReq;

import java.util.List;
import java.util.Map;

public class LotterySGRecordHelper {

    public String getSgRecordList(LotterySGRecordReq req){
        String game = req.getGame();
        String issue = req.getIssue();
        String startTime = req.getStartTime();
        String endTime = req.getEndTime();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t.* FROM ( ");
        if(StringUtils.isNotBlank(game)){
            sql.append("SELECT '").append(game).append("' AS game, issue, number, time, open_status AS openStatus  FROM ").append(game);
        }else{
            List<String> lotteryList = LotterySGRecordConfig.getLotteryList();
            for (int i=0;i<lotteryList.size();i++) {
                String lottery = lotteryList.get(i);
                sql.append("SELECT '").append(lottery).append("' AS game, issue, number, time, open_status AS openStatus  FROM ").append(lottery);
                if(i!=lotteryList.size()-1){
                    sql.append(" UNION ");
                }
            }
        }
        sql.append(" ) AS t WHERE ( t.openStatus = 'AUTO' OR t.openStatus = 'HANDLE' )");
        if(StringUtils.isNotBlank(issue)){
            sql.append(" AND t.issue ='").append(issue).append("' ");
        }
        if(StringUtils.isNotBlank(startTime)){
            sql.append(" AND t.time >'").append(startTime).append("' ");
        }
        if(StringUtils.isNotBlank(endTime)){
            sql.append(" AND t.time <='").append(endTime).append("' ");
        }
        sql.append("ORDER BY time DESC");
        System.out.println(sql);
        return sql.toString();
    }
}
