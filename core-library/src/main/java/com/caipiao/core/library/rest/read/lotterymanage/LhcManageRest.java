package com.caipiao.core.library.rest.read.lotterymanage;

import com.caipiao.core.library.vo.lotterymanage.LhcHandicapPageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface LhcManageRest {

    @PostMapping("/lhcManage/getLhcHandicapPageVO.json")
    LhcHandicapPageVO getLhcHandicapPageVO();

    /**
     * 获取六合彩没有开奖的期号集合
     * @return
     */
    @GetMapping("/lhcManage/getNoOpenIssue.json")
    List<String> getNoOpenIssue();

}
