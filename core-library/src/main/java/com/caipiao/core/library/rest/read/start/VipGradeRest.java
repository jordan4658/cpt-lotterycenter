package com.caipiao.core.library.rest.read.start;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.vo.start.VipGradeVO;
import com.mapper.domain.VipGrade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface VipGradeRest {

    /**
     * VIP等级管理列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/start/pageVipGrade.json")
    PageResult<List<VipGrade>> pageVipGrade(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 通过id获取VIP等级管理信息
     * @param vipGradeId
     * @return
     */
    @GetMapping("/start/findVipGradeById.json")
    VipGrade findVipGradeById(@RequestParam("vipGradeId") Integer vipGradeId);

    /**
     * 获取所有的VIP等级
     * @return
     */
    @GetMapping("/start/findAllVipGrade.json")
    List<VipGradeVO> findAllVipGrade();

}
