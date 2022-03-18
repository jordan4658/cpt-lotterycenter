package com.caipiao.core.library.rest.write.start;

import com.caipiao.core.library.dto.start.AdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface AdWriteRest {

    /**
     * 添加或修改广告活动
     * @param adDTO
     * @return
     */
    @PostMapping("/start/addOrUpdateAd.json")
    boolean addOrUpdateAd(@RequestBody AdDTO adDTO);

    /**
     * 根据id删除广告活动
     * @param adId
     * @return
     */
    @PostMapping("/start/deleteById.json")
    boolean deleteById(@RequestParam("adId") Integer adId);


}
