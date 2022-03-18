package com.caipiao.live.common.mybatis.mapperbean.provider.startlotto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lzy
 * @create 2018-06-07 10:49
 **/
@Component
public class NumberRecordDynaSqlProvider {

    /**
     * 根据条件查询开奖号码跳开记录动态sql
     * @param map
     * @return
     */
    public String listNoLotteryRecord(final Map<String, Object> map) {
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        String status = (String) map.get("status");
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" r.id, l.`name` as lotteryName, r.issue, r.`status`, r.create_time as createTime, r.deal_time as dealTime ");
                FROM("no_lottery_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(status)) {
                    WHERE("r.status = #{status}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(startTime)) {
                    WHERE("CAST(r.create_time AS datetime) >= CAST(#{startTime} AS datetime)");
                }
                if (StringUtils.isNotBlank(endTime)) {
                    WHERE("CAST(r.create_time AS datetime) <= CAST(#{endTime} AS datetime)");
                }

                ORDER_BY("r.id DESC limit #{pageNo},#{pageSize}");
            }
        }.toString();

    }

    /**
     * 根据条件查询开奖号码跳开记录数量动态sql
     * @param map
     * @return
     */
    public String countNoLotteryRecord(final Map<String, Object> map) {
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        String status = (String) map.get("status");
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" count(r.id) ");
                FROM("no_lottery_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(status)) {
                    WHERE("r.status = #{status}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(startTime)) {
                    WHERE("CAST(r.create_time AS datetime) >= CAST(#{startTime} AS datetime)");
                }
                if (StringUtils.isNotBlank(endTime)) {
                    WHERE("CAST(r.create_time AS datetime) <= CAST(#{endTime} AS datetime)");
                }

            }
        }.toString();

    }

    /**
     * 根据条件查询开奖号码采集失败记录动态sql
     * @param map
     * @return
     */
    public String listGatherFailureRecord(final Map<String, Object> map) {
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        String source = (String) map.get("source");
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" r.id, l.`name` as lotteryName, r.issue, r.create_time as createTime, r.source ");
                FROM("gather_failure_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(source)) {
                    WHERE("r.source = #{source}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(startTime)) {
                    WHERE("CAST(r.create_time AS datetime) >= CAST(#{startTime} AS datetime)");
                }
                if (StringUtils.isNotBlank(endTime)) {
                    WHERE("CAST(r.create_time AS datetime) <= CAST(#{endTime} AS datetime)");
                }
                ORDER_BY("r.id DESC limit #{pageNo},#{pageSize}");
            }
        }.toString();

    }

    /**
     * 根据条件查询开奖号码采集失败记录数量动态sql
     * @param map
     * @return
     */
    public String countGatherFailureRecord(final Map<String, Object> map) {
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        String source = (String) map.get("source");
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" count(r.id) ");
                FROM("gather_failure_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(source)) {
                    WHERE("r.source = #{source}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(startTime)) {
                    WHERE("CAST(r.create_time AS datetime) >= CAST(#{startTime} AS datetime)");
                }
                if (StringUtils.isNotBlank(endTime)) {
                    WHERE("CAST(r.create_time AS datetime) <= CAST(#{endTime} AS datetime)");
                }
            }
        }.toString();

    }

    /**
     * 根据条件查询开奖号码异常记录
     * @param map
     * @return
     */
    public String listNumberAbnormalRecord(final Map<String, Object> map) {
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        String source = (String) map.get("source");
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" r.id, l.`name` as lotteryName, r.issue, r.create_time as createTime, r.source ");
                FROM("number_abnormal_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(source)) {
                    WHERE("r.source = #{source}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(startTime)) {
                    WHERE("CAST(r.create_time AS datetime) >= CAST(#{startTime} AS datetime)");
                }
                if (StringUtils.isNotBlank(endTime)) {
                    WHERE("CAST(r.create_time AS datetime) <= CAST(#{endTime} AS datetime)");
                }

                ORDER_BY("r.id DESC limit #{pageNo},#{pageSize}");
            }
        }.toString();

    }

    /**
     * 根据条件查询开奖号码异常记录数量
     * @param map
     * @return
     */
    public String countNumberAbnormalRecord(final Map<String, Object> map) {
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        String source = (String) map.get("source");
        Integer lotteryId = (Integer) map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" count(r.id) ");
                FROM("number_abnormal_record r ");
                LEFT_OUTER_JOIN("lottery l ON r.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("r.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(source)) {
                    WHERE("r.source = #{source}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("r.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(startTime)) {
                    WHERE("CAST(r.create_time AS datetime) >= CAST(#{startTime} AS datetime)");
                }
                if (StringUtils.isNotBlank(endTime)) {
                    WHERE("CAST(r.create_time AS datetime) <= CAST(#{endTime} AS datetime)");
                }
            }
        }.toString();

    }
}
