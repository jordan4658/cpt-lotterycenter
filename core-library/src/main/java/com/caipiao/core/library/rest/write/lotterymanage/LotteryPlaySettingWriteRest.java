package com.caipiao.core.library.rest.write.lotterymanage;

import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.lotterymanage.LotteryPlaySettingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface LotteryPlaySettingWriteRest extends BaseRest {

    /**
     * 添加玩法
     * @param setting 玩法配置
     */
    @PostMapping("/playSetting/editSetting")
    void editSetting(@RequestBody LotteryPlaySettingDTO setting);

}
