package com.caipiao.live.common.mybatis.mapperbean;

import com.caipiao.live.common.model.PlayModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BetOrderBeanMapper {

    /**
     * 查询玩法的名称
     *
     * @param playIds 玩法id的集合
     * @return
     */
    @Select("<script>SELECT a.play_tag_id as id, ifnull(CONCAT(b.name,'/',a.name),a.`name`) as name FROM lottery_play a  left join  lottery_play b on  b.id = a.parent_id where a.play_tag_id in <foreach item='playId' index='index' collection='playIds' open='(' separator=',' close=')'> #{playId} </foreach> </script>")
    List<PlayModel> getPlayName(@Param("playIds") List<Integer> playIds);



}
