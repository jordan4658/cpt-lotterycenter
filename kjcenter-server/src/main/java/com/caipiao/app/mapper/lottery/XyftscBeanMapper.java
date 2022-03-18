package com.caipiao.app.mapper.lottery;

import com.caipiao.core.library.vo.result.BjpksSgVO;
import com.mapper.domain.BjpksLotterySg;
import com.mapper.domain.XyftscLotterySg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface XyftscBeanMapper {

    @Select("SELECT s.`number` FROM `xyftsc_lottery_sg` s WHERE s.`issue` LIKE #{date} and s.number is not null ORDER BY s.`issue` ASC")
    List<String> selectNumberByDate(@Param("date") String date);

    @Select("SELECT s.`number` FROM `xyftsc_lottery_sg` s WHERE s.`issue` LIKE #{date} and s.number is not null ORDER BY s.`issue` DESC")
    List<String> selectNumberByDateDesc(@Param("date") String date);

    @Select("SELECT s.`number` FROM `xyftsc_lottery_sg` s where s.number is not null ORDER BY s.`issue` DESC LIMIT #{size}")
    List<String> selectNumberLimitDesc(@Param("size") Integer size);

    @Select("SELECT s.`id`, s.`issue`, s.`number`, s.`time` FROM `xyftsc_lottery_sg` s WHERE s.`issue` LIKE #{date} and s.number is not null ORDER BY s.`issue` DESC")
    List<BjpksLotterySg> selectByDateDesc(@Param("date") String date);

    @Select("SELECT s.`issue`, s.`number` FROM `xyftsc_lottery_sg` s where s.number is not null ORDER BY s.`issue` DESC LIMIT #{size}")
    List<BjpksSgVO> selectLimitDesc(@Param("size") Integer size);

    @Select("SELECT count(1) FROM `xyftsc_lottery_sg` s where open_status = #{openStatus,jdbcType=CHAR} and ideal_time like #{paramTime,jdbcType=CHAR}")
    int openCountByExample(@Param("openStatus") String openStatus, @Param("paramTime") String paramTime);

    @Update("update xyftsc_lottery_sg set `number` = #{updateSg.number,jdbcType=CHAR}, `time` = #{updateSg.time,jdbcType=CHAR}, ideal_time = #{updateSg.idealTime,jdbcType=CHAR}," +
            " open_status = #{updateSg.openStatus,jdbcType=CHAR} where issue = #{updateSg.issue,jdbcType=CHAR}")
    int updateByIssue(@Param("updateSg") XyftscLotterySg updateSg);







}
