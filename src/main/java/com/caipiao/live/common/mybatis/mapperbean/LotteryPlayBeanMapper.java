package com.caipiao.live.common.mybatis.mapperbean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LotteryPlayBeanMapper {

    /**
     * 批量删除玩法
     * @param ids id集合
     */
    @Update("<script>"
            + "UPDATE `lottery_play` s SET s.`is_delete` = TRUE WHERE s.`id` in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    Integer deletePlayByIds(@Param("ids") List<Integer> ids);

}
