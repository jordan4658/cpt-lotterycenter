package com.caipiao.live.common.mybatis.mapperbean.provider.startlotto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 手动开奖记录动态sql
 *
 * @author lzy
 * @create 2018-06-07 17:55
 **/
@Component
public class ManualRecordDynaSqlProvider {

    /**
     * 根据条件查询手动开奖记录
     * @param map
     * @return
     */
    public String listManualStartlottoRecord(final Map<String, Object> map) {
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" r.id, l.`name` as lotteryName, r.issue, r.`push_number` as pushNumber, r.create_time as createTime ");
                FROM("manual_startlotto_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
                ORDER_BY("r.create_time DESC limit #{pageNo},#{pageSize}");
            }
        }.toString();

    }

    /**
     * 根据条件查询手动开奖记录数量
     * @param map
     * @return
     */
    public String countManualStartlottoRecord(final Map<String, Object> map) {
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" count(r.id) ");
                FROM("manual_startlotto_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
            }
        }.toString();

    }
}
