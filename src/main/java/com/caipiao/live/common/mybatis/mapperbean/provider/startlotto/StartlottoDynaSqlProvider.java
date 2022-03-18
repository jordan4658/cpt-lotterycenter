package com.caipiao.live.common.mybatis.mapperbean.provider.startlotto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lzy
 * @create 2018-06-06 17:17
 **/
@Component
public class StartlottoDynaSqlProvider {

    /**
     * 动态查询开放撤单列表的sql
     * @param map
     * @return
     */
    public String listOpenRepealOrderVO(final Map<String, Object> map) {
        int pageNo = (Integer) map.get("pageNo");
        int pageSize = (Integer)map.get("pageSize");
        Integer lotteryId = (Integer)map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" rs.id, l.`name` as lotteryName, rs.issue, rs.order_code as orderCode, rs.create_time as createTime ");
                FROM("open_repeal_order rs ");
                LEFT_OUTER_JOIN("lottery l ON rs.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("rs.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("rs.issue like #{issue}");
                }

                ORDER_BY("rs.id DESC limit #{pageNo},#{pageSize}");
            }
        }.toString();
    }

    /**
     * 动态查询开放撤单数量的sql
     * @param map
     * @return
     */
    public String countOpenRepealOrderVO(final Map<String, Object> map) {
        Integer lotteryId = (Integer)map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" rs.id, l.`name` as lotteryName, rs.issue, rs.order_code as orderCode, rs.create_time as createTime ");
                FROM("open_repeal_order rs ");
                LEFT_OUTER_JOIN("lottery l ON rs.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("rs.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("rs.issue like #{issue}");
                }
            }
        }.toString();
    }

    /**
     * 动态查询撤销注单的sql
     * @param map
     * @return
     */
    public String listRepealBetOrderVO(final Map<String, Object> map) {
        int pageNo = (Integer) map.get("pageNo");
        int pageSize = (Integer)map.get("pageSize");
        Integer lotteryId = (Integer)map.get("lotteryId");
        String issue = (String) map.get("issue");
        String orderCode = (String) map.get("orderCode");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" rs.id, l.`name` as lotteryName, rs.issue, rs.order_code as orderCode, rs.create_time as createTime ");
                FROM("repeal_bet_order rs ");
                LEFT_OUTER_JOIN("lottery l ON rs.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("rs.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("rs.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(orderCode)) {
                    WHERE("rs.order_code like #{orderCode}");
                }
                ORDER_BY("rs.id DESC limit #{pageNo},#{pageSize}");
            }
        }.toString();
    }

    /**
     * 动态查询撤销注单数量的sql
     * @param map
     * @return
     */
    public String countRepealBetOrderVO(final Map<String, Object> map) {
        Integer lotteryId = (Integer)map.get("lotteryId");
        String issue = (String) map.get("issue");
        String orderCode = (String) map.get("orderCode");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" rs.id, l.`name` as lotteryName, rs.issue, rs.order_code as orderCode, rs.create_time as createTime ");
                FROM("repeal_bet_order rs ");
                LEFT_OUTER_JOIN("lottery l ON rs.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("rs.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("rs.issue like #{issue}");
                }
                if (StringUtils.isNotBlank(orderCode)) {
                    WHERE("rs.order_code like #{orderCode}");
                }
            }
        }.toString();
    }

    /**
     * 动态查询撤销开奖的sql
     * @param map
     * @return
     */
    public String listRepealStartlottoVO(final Map<String, Object> map) {
        int pageNo = (Integer) map.get("pageNo");
        int pageSize = (Integer)map.get("pageSize");
        Integer lotteryId = (Integer)map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" rs.id, l.`name` as lotteryName, rs.issue, rs.order_code as orderCode, rs.create_time as createTime ");
                FROM("repeal_startlotto rs ");
                LEFT_OUTER_JOIN("lottery l ON rs.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("rs.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("rs.issue like #{issue}");
                }

                ORDER_BY("rs.id DESC limit #{pageNo},#{pageSize}");
            }
        }.toString();
    }

    /**
     * 动态查询撤销开奖数量的sql
     * @param map
     * @return
     */
    public String countRepealStartlottoVO(final Map<String, Object> map) {
        Integer lotteryId = (Integer)map.get("lotteryId");
        String issue = (String) map.get("issue");

        return new org.apache.ibatis.jdbc.SQL() {
            {
                SELECT(" rs.id, l.`name` as lotteryName, rs.issue, rs.order_code as orderCode, rs.create_time as createTime ");
                FROM("repeal_startlotto rs ");
                LEFT_OUTER_JOIN("lottery l ON rs.lottery_id = l.id ");
                if (lotteryId != null) {
                    WHERE("rs.lottery_id = #{lotteryId}");
                }
                if (StringUtils.isNotBlank(issue)) {
                    WHERE("rs.issue like #{issue}");
                }
            }
        }.toString();
    }
}
