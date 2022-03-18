package com.caipiao.live.common.mybatis.mapperbean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CqsscBeanMapper {

    @Update("UPDATE `cqssc_recommend` r LEFT JOIN `lottery_cqssc_lottery_sg` s ON r.`issue` = s.`issue` SET r.`open_number` = CONCAT(s.`wan`, ',', s.`qian`, ',', s.`bai`, ',', s.`shi`, ',', s.`ge`) where r.`issue` = #{issue}")
    Integer updateRecommend(@Param("issue") String issue);

    @Update("UPDATE `cqssc_kill_number` k LEFT JOIN `lottery_cqssc_lottery_sg` s ON k.`issue` = s.`issue` SET k.`open_one` = s.`wan`, k.`open_two` = s.`qian`, k.`open_three` = s.`bai`, k.`open_four` = s.`shi`, k.`open_five` = s.`ge`")
    Integer updateKillNumber();


    @Update("UPDATE `tjssc_recommend` r LEFT JOIN `lottery_tjssc_lottery_sg` s ON r.`issue` = s.`issue` SET r.`open_number` = CONCAT(s.`wan`, ',', s.`qian`, ',', s.`bai`, ',', s.`shi`, ',', s.`ge`) where r.`issue` = #{issue}")
    Integer updateRecommendtj(@Param("issue") String issue);

    @Update("UPDATE `tjssc_kill_number` k LEFT JOIN `lottery_tjssc_lottery_sg` s ON k.`issue` = s.`issue` SET k.`open_one` = s.`wan`, k.`open_two` = s.`qian`, k.`open_three` = s.`bai`, k.`open_four` = s.`shi`, k.`open_five` = s.`ge` where k.`issue` = #{issue}")
    Integer updateKillNumbertj(@Param("issue") String issue);
}
