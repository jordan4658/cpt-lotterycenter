package com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface PostInfoWriteMapper {

    /**
     * 统计我关注用户的某一时间段内的发帖数量
     * @param startTime
     * @param endTime
     * @param meId
     * @return
     */
    Integer countFocusPostNumber(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("meId") Integer meId);

    /**
     * 统计某一时间段内帖子回复我的数量
     * @param startTime
     * @param endTime
     * @param meId
     * @return
     */
    Integer countPostReplyMeNumber(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("meId") Integer meId);

    
    
    /**
     * 个人设置
     */
    List<Map<String,Object>> getPersonnalSettingList(@Param("userId") Integer userId, @Param("classify") String classify);
}
