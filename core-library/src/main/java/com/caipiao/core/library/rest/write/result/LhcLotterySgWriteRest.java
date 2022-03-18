package com.caipiao.core.library.rest.write.result;

import com.caipiao.core.library.dto.lotterymanage.LhcHandicapDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.mapper.domain.LhcKillNumber;
import com.mapper.domain.LhcLotterySg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface LhcLotterySgWriteRest {

    /**
     * 六合彩开奖
     *
     * @param issue  期号
     * @param number 开奖号码
     * @param date   开奖日期
     * @return
     */
    @PostMapping("/lhcSg/startLotto.json")
    boolean startLotto(@RequestParam("issue") String issue, @RequestParam("number") String number, @RequestParam("date") String date);

    /**
     * 添加或修改六合彩预开盘口
     *
     * @param lhcHandicapDTO
     * @return
     */
    @PostMapping("/lhcSg/addOrUpdateLhcHandicap.json")
    ResultInfo<Void> addOrUpdateLhcHandicap(@RequestBody LhcHandicapDTO lhcHandicapDTO);

    /**
     * 删除六合彩预开盘口
     *
     * @param handicapId
     * @return
     */
    @PostMapping("/lhcSg/deleteLhcHandicapById.json")
    boolean deleteLhcHandicapById(@RequestParam("handicapId") Integer handicapId);

    /**
     * 插入数据
     *
     * @param sg 赛果
     */
    @PostMapping("/lhcSg/insert.json")
    boolean insert(@RequestBody LhcLotterySg sg);

    /**
     * 杀号
     *
     * @param lhcKillNumber
     */
    @PostMapping("/lhcSg/killNumber.json")
    void killNumber(@RequestBody LhcKillNumber lhcKillNumber);
}
