package com.caipiao.core.library.rest.write.appmember;

import com.caipiao.core.library.dto.appmember.AppMemberDTO;
import com.caipiao.core.library.model.ResultInfo;
import com.mapper.domain.MemberInstationMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.caipiao.core.library.constant.ModuleConstant.BUSINESS_SERVER;

@FeignClient(name = BUSINESS_SERVER)
public interface MemberInfoWriteRest {

    /**
     * 修改会员账号信息
     * @param appMemberDTO
     * @return
     */
    @PostMapping("/memberInfo/updateAccountInfo.json")
    ResultInfo<Void> updateAccountInfo(@RequestBody AppMemberDTO appMemberDTO);

    /**
     * 修改会员真实姓名和身份证号
     * @param appMemberDTO
     * @return
     */
    @PostMapping("/memberInfo/updateAccountRealName.json")
    ResultInfo<Void> updateAccountRealName(@RequestBody AppMemberDTO appMemberDTO);

    /**
     * 获取会员站内信详情
     * @param messageId 站内信id
     * @param memberId 会员id
     * @return
     */
    @PostMapping("/memberInfo/findInstationMessageByIdAndMemberId.json")
    MemberInstationMessage findInstationMessageByIdAndMemberId(@RequestParam("messageId") Integer messageId, @RequestParam("memberId") Integer memberId);

    /**
     *  app端获取删除站内信
     * @param messageIdList 站内信id集合
     * @param memberId 会员id
     */
    @PostMapping("/memberInfo/deleteInstationMessageByIdsAndMemberId.json")
    void deleteInstationMessageByIdsAndMemberId(@RequestBody List<Integer> messageIdList, @RequestParam("memberId") Integer memberId);
}
