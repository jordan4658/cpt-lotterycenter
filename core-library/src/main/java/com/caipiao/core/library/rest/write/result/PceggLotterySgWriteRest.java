package com.caipiao.core.library.rest.write.result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

/**
 * 获取第三方PC蛋蛋赛果定时任务
 * @author ShaoMing
 * @datetime 2018/7/27 16:25
 */
@FeignClient(name = BUSINESS_SERVER)
public interface PceggLotterySgWriteRest {

}
