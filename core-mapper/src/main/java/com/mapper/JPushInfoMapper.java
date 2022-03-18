package com.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JPushInfoMapper {
    List<Integer> getPushSettingOnUser(@Param("tag") String tag);

    Integer winPushIsOff(@Param("tag") String tag, @Param("userId") Integer userId);
}
