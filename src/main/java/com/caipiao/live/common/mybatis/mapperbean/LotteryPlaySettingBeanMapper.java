package com.caipiao.live.common.mybatis.mapperbean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LotteryPlaySettingBeanMapper {

    /**
     * 根据分类id删除玩法配置
     * @param cateId 分类id
     */
    @Update("UPDATE `lottery_play_setting` s SET s.`is_delete` = TRUE WHERE s.`cate_id` = #{cateId}")
    Integer deleteSettingByCateId(@Param("cateId") Integer cateId);

    /**
     * 批量删除玩法配置
     * @param playIdList 玩法id集合
     */
    @Update("<script>"
            + "UPDATE `lottery_play_setting` s SET s.`is_delete` = TRUE WHERE s.`play_id` in "
            + "<foreach item='item' index='index' collection='playIdList' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    Integer deletePlaySettingByIds(@Param("playIdList") List<Integer> playIdList);

}
