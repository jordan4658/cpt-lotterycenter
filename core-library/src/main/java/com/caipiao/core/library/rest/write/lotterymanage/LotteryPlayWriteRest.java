package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.rest.BaseRest;
import com.mapper.domain.LotteryPlay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface LotteryPlayWriteRest extends BaseRest {

    /**
     * 添加玩法
     * @param play 玩法对象
     */
    @PostMapping("/play/insert")
    void insert(@RequestBody LotteryPlay play);

    /**
     * 修改玩法
     * @param play 玩法对象
     */
    @PostMapping("/play/update")
    void update(@RequestBody LotteryPlay play);

    /**
     * 删除玩法
     * @param id 要删除的玩法id
     */
    @PostMapping("/play/delete")
    void delete(@RequestParam(name = "id") Integer id);
}
