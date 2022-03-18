package com.caipiao.core.library.rest.read.interfacelog;

import com.caipiao.core.library.model.PageResult;
import com.caipiao.core.library.rest.BaseRest;
import com.caipiao.core.library.vo.interfacelog.MemberChatRecordVO;
import com.mapper.domain.MemberChatRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_READ;

@FeignClient(name = BUSINESS_READ)
public interface MemberChatRecordRest extends BaseRest {

    /**
     * 会员聊天记录列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/listMemberChatRecord.json")
    PageResult<List<MemberChatRecordVO>> listMemberChatRecord(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
