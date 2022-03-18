package com.caipiao.core.library.rest.write.money;

import com.caipiao.core.library.dto.money.SendDownWayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface SendDownWayWriteRest {

    @PostMapping("/money/addOrUpdateSendDownWay.json")
    boolean addOrUpdateSendDownWay(@RequestBody SendDownWayDTO sendDownWayDTO);

    @PostMapping("/money/deleteSendDownWayById.json")
    boolean deleteSendDownWayById(@RequestParam("id") Integer id);
}
