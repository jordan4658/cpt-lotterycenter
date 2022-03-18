package com.caipiao.core.library.rest.read.app;

import com.caipiao.core.library.dto.start.EditionUpdateDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.start.EditionUpdateVO;
import com.mapper.domain.App;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface AppReadRest extends BaseRest {

    @GetMapping("/app/downloadUrlNews.json")
    App selectAppInfoById(@RequestParam("id") Integer id);

    @PostMapping("/app/appEditionUpdate.json")
    ResultInfo<EditionUpdateVO> editionUpdate(@RequestBody EditionUpdateDTO editionUpdateDTO);

}
