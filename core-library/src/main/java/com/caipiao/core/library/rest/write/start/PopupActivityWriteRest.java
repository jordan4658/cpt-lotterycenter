package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.PopupActivityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface PopupActivityWriteRest {

    /**
     * 添加或修改最新活动
     * @param popupActivityDTO
     * @return
     */
    @PostMapping("/start/addOrUpdatePopupActivity.json")
    boolean addOrUpdatePopupActivity(@RequestBody PopupActivityDTO popupActivityDTO);

    /**
     * 根据id删除最新活动
     * @param activityId
     * @return
     */
    @PostMapping("/start/deletePopupActivityById.json")
    boolean deletePopupActivityById(@RequestParam("activityId") Integer activityId);
}
